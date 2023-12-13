package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Campsite;
import Model.Employee;

public class CampsiteDAO {

	ConnectionEnvironment env;

	public CampsiteDAO(ConnectionEnvironment env) {
		this.env = env;
	}
	
	public List<Campsite> getCampsites(){
		String getCampsiteQ = "SELECT c.siteNo, c.section, c.road, c.[type], c.fee, cab.maxpeople, cab.deposit, pr.price, pr.effectiveDate "
				+ "FROM Campsite c " + "LEFT JOIN Cabin cab ON c.siteNo = cab.siteNo "
				+ "LEFT JOIN Pitch p ON c.siteNo = p.siteNo " + "LEFT JOIN Price pr ON c.siteNo = pr.campsiteSiteNo ";
		List<Campsite> campsites = new ArrayList<>();
		Connection conn = DBConnection.getConnection(env);
		ResultSet resultSet = DBConnection.executeQuery(conn, getCampsiteQ);
		try {
			while(resultSet.next()) {
				Campsite campsite = CampsiteFactory.getCampsite(resultSet);
				campsites.add(campsite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.closeConnection(conn);
		return campsites;
	}

	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate, boolean searchForCabin,
			boolean searchForPitch) {
		return queryAvailableCampsites(startDate, endDate, searchForCabin, searchForPitch);
	}

	private List<Campsite> queryAvailableCampsites(Date startDate, Date endDate, boolean searchForCabin,
			boolean searchForPitch) {
		List<Campsite> campsiteList = new ArrayList<>();

		String query = "SELECT c.siteNo, c.section, c.road, c.[type], c.fee, cab.maxpeople, cab.deposit, pr.price, pr.effectiveDate "
				+ "FROM Campsite c " + "LEFT JOIN Cabin cab ON c.siteNo = cab.siteNo "
				+ "LEFT JOIN Pitch p ON c.siteNo = p.siteNo " + "LEFT JOIN Price pr ON c.siteNo = pr.campsiteSiteNo "
				+ "WHERE NOT EXISTS ("
				+ "SELECT 1 FROM Booking b WHERE b.campsiteSiteNo = c.siteNo AND b.startDate <= ? AND b.endDate >= ?"
				+ ") AND NOT EXISTS ("
				+ "SELECT 1 FROM Reservation r WHERE r.campsiteSiteNo = c.siteNo AND (r.startDate <= ? AND r.endDate >= ?) AND NOT (r.timeChanged < DATEADD(minute, -10, GETDATE()))"
				+ ")";

		if (searchForCabin == searchForPitch) {
			query += " AND (c.[type] = 'cabin' OR c.[type] = 'pitch')";
		} else if (searchForCabin) {
			query += " AND c.[type] = 'cabin'";
		} else if (searchForPitch) {
			query += " AND c.[type] = 'pitch'";
		}

		try (Connection connection = DBConnection.getConnection(env);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setDate(1, endDate);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setDate(4, startDate);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Campsite campsite = CampsiteFactory.getCampsite(resultSet);
					campsiteList.add(campsite);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return campsiteList;
	}

	public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		boolean reservationStarted = false;

		try (Connection connection = DBConnection.getConnection(env)) {
			DBConnection.startTransaction(connection);
			if (!checkForConflictingBooking(connection, campsite.getSiteNumber(), startDate, endDate, employee)) {
				if (insertTentativeReservation(connection, campsite.getSiteNumber(), startDate, endDate,
						employee.getId())) {
					DBConnection.commitTransaction(connection);
					reservationStarted = true;
				} else {
					DBConnection.rollbackTransaction(connection);
				}
			} else {
				DBConnection.rollbackTransaction(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservationStarted;
	}

	public boolean cancelReservationOfCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		String cancelReservationQuery = "DELETE FROM Reservation WHERE campsiteSiteNo = ? AND employeeId = ? AND startDate = ? AND endDate = ? AND timechanged >= DATEADD(minute, -10, GETDATE());";

		int successfulCancel = 0;

		Connection connection = DBConnection.getConnection(env);

		try (PreparedStatement preparedStatement = connection.prepareStatement(cancelReservationQuery)) {
			connection.setAutoCommit(false);

			preparedStatement.setInt(1, campsite.getSiteNumber());
			preparedStatement.setInt(2, employee.getId());
			preparedStatement.setDate(3, startDate);
			preparedStatement.setDate(4, endDate);

			successfulCancel = preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DBConnection.rollbackTransaction(connection);
			} catch (SQLException ex) {
				throw new RuntimeException("Error rolling back transaction", ex);
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Error closing connection", e);
			}
		}

		return successfulCancel > 0;
	}

	private boolean checkForConflictingBooking(Connection connection, int campsiteSiteNo, Date startDate, Date endDate,
			Employee employee) throws SQLException {
		String checkQuery = "SELECT 1 FROM Booking WHERE campsiteSiteNo = ? AND NOT (startDate >= ? OR endDate <= ?) "
				+ "UNION SELECT 1 FROM reservation WHERE campsiteSiteNo = ? AND NOT (startdate >= ? OR endDate <= ?) "
				+ "AND timechanged >= DATEADD(minute, -10, GETDATE()) AND employeeid <> ?;";

		try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
			checkStmt.setInt(1, campsiteSiteNo);
			checkStmt.setDate(2, endDate);
			checkStmt.setDate(3, startDate);
			checkStmt.setInt(4, campsiteSiteNo);
			checkStmt.setDate(5, endDate);
			checkStmt.setDate(6, startDate);
			checkStmt.setInt(7, employee.getId());

			try (ResultSet rs = checkStmt.executeQuery()) {

				return rs.next();
			}
		}
	}

	private boolean insertTentativeReservation(Connection connection, int campsiteSiteNo, Date startDate, Date endDate,
			int employeeId) throws SQLException {
		String insertQuery = "INSERT INTO Reservation (startDate, endDate, employeeId, campsiteSiteNo) VALUES (?, ?, ?, ?)";
		try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
			insertStmt.setDate(1, startDate);
			insertStmt.setDate(2, endDate);
			insertStmt.setInt(3, employeeId);
			insertStmt.setInt(4, campsiteSiteNo);
			return insertStmt.executeUpdate() > 0;
		}
	}

	public boolean saveCampsite(int siteNo, String section, String road, String type, int maxPeople, float deposit,
			float fee, Date effectiveDate, float price) {
		Connection conn = DBConnection.getConnection(env);
		String insertCampsiteQ = "INSERT INTO Campsite(siteNo, section, road, type) VALUES (?, ?, ?, ?);";
		String insertCabinQ = "INSERT INTO Cabin(siteNo, maxPeople, deposit) VALUES (?, ?, ?);";
		String insertPitchQ = "INSERT INTO Pitch(siteNo, fee) VALUES (?, ?)";
		String insertPriceQ = "INSERT INTO Price(effectiveDate, price, campsiteSiteNo) VALUES (?, ?, ?);";

		int rowsAffected = 0;

		try {
			conn.setAutoCommit(false);

			PreparedStatement campsiteStatement = conn.prepareStatement(insertCampsiteQ);
			campsiteStatement.setInt(1, siteNo);
			campsiteStatement.setString(2, section);
			campsiteStatement.setString(3, road);
			campsiteStatement.setString(4, type);

			int campsiteRowsAffected = campsiteStatement.executeUpdate();
			rowsAffected += campsiteRowsAffected;

			if (campsiteRowsAffected > 0 && type.equals("Cabin")) {
				PreparedStatement cabinStatement = conn.prepareStatement(insertCabinQ);
				cabinStatement.setInt(1, siteNo);
				cabinStatement.setInt(2, maxPeople);
				cabinStatement.setFloat(3, deposit);

				int cabinRowsAffected = cabinStatement.executeUpdate();
				rowsAffected += cabinRowsAffected;
			}

			if (campsiteRowsAffected > 0 && type.equals("Pitch")) {
				PreparedStatement pitchStatement = conn.prepareStatement(insertPitchQ);
				pitchStatement.setInt(1, siteNo);
				pitchStatement.setFloat(2, fee);

				int pitchRowsAffected = pitchStatement.executeUpdate();
				rowsAffected += pitchRowsAffected;
			}
			
			PreparedStatement priceStatement = conn.prepareStatement(insertPriceQ);
			priceStatement.setDate(1, effectiveDate);
			priceStatement.setFloat(2, price);
			priceStatement.setInt(3, siteNo);

			int priceRowsAffected = priceStatement.executeUpdate();
			rowsAffected += priceRowsAffected;

			conn.commit();

		} catch (SQLException e) {

			try {
				conn.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DBConnection.closeConnection(conn);
			} catch (SQLException closeException) {
				closeException.printStackTrace();
			}
		}

		return rowsAffected > 0;
	}

	public boolean updateCampsiteBySiteNo(int siteNo, String section, String road, String type, int maxPeople,
			float deposit, float fee, Date effectiveDate, float price) {
		Connection conn = DBConnection.getConnection(env);

		String updateCampsiteQ = "UPDATE Campsite SET siteNo = ?, section = ?, road = ?, type = ? WHERE siteNo = ? WHERE siteNo =  ?";
		String updateCabinQ = "UPDATE Cabin SET siteNo = ?, maxPeople = ?, deposit = ? WHERE siteNo = ?";
		String updatePitchQ = "UPDATE Pitch SET siteNo = ?, fee = ? WHERE siteNo = ?";
		String updatePriceQ = "UPDATE Price SET effectiveDate = ?, price = ?, campsiteSiteNo = ? WHERE campsiteSiteNo = ?";

		int rowsAffected = 0;

		try {
			conn.setAutoCommit(false);

			PreparedStatement campsiteStatement = conn.prepareStatement(updateCampsiteQ);
			campsiteStatement.setInt(1, siteNo);
			campsiteStatement.setString(2, section);
			campsiteStatement.setString(3, road);
			campsiteStatement.setString(4, type);

			rowsAffected += campsiteStatement.executeUpdate();

			if (type.equals("Cabin")) {
				PreparedStatement cabinStatement = conn.prepareStatement(updateCabinQ);
				cabinStatement.setInt(1, siteNo);
				cabinStatement.setInt(2, maxPeople);
				cabinStatement.setFloat(3, deposit);

				int cabinRowsAffected = cabinStatement.executeUpdate();
				rowsAffected += cabinRowsAffected;
			}

			if (type.equals("Pitch")) {
				PreparedStatement pitchStatement = conn.prepareStatement(updatePitchQ);
				pitchStatement.setInt(1, siteNo);
				pitchStatement.setFloat(2, fee);

				int pitchRowsAffected = pitchStatement.executeUpdate();
				rowsAffected += pitchRowsAffected;
			}
			
			PreparedStatement priceStatement = conn.prepareStatement(updatePriceQ);
			priceStatement.setDate(1, effectiveDate);
			priceStatement.setFloat(2, price);
			priceStatement.setInt(3, siteNo);

			int priceRowsAffected = priceStatement.executeUpdate();
			rowsAffected += priceRowsAffected;
			
			conn.commit();

		} catch (SQLException e) {

			try {
				conn.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DBConnection.closeConnection(conn);
			} catch (SQLException closeException) {
				closeException.printStackTrace();
			}
		}
		return rowsAffected > 0;
	}

	public boolean deleteCampsite(int siteNo) {
		Connection conn = DBConnection.getConnection(env);

		String deleteCampsiteQ = "DELETE FROM Campsite WHERE siteNo = ?";

		int rowsAffected = 0;

		try {
			PreparedStatement pitchStatement = conn.prepareStatement(deleteCampsiteQ);
			pitchStatement.setInt(1, siteNo);
			rowsAffected += pitchStatement.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DBConnection.closeConnection(conn);
			} catch (SQLException closeException) {
				closeException.printStackTrace();
			}
		}

		return rowsAffected > 0;
	}
}
