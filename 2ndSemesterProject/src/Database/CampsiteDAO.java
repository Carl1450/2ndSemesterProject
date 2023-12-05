package Database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;

public class CampsiteDAO {

	ConnectionEnvironment env;

	public CampsiteDAO() {
		env = ConnectionEnvironment.PRODUCTION;
	}

	public CampsiteDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate) {
		try {
			validateDate(startDate);
			validateDate(endDate);
		} catch (InvalidDateException e) {
			e.printStackTrace();
		}
		List<Campsite> campsiteList = new ArrayList<>();

		try (Connection connection = DBConnection.getInstance(env).getConnection()) {
			String query = "SELECT id, section, road, siteNo, type FROM Campsite WHERE NOT EXISTS ("
					+ "SELECT 1 FROM Booking WHERE campsiteId = Campsite.id " + "AND startDate <= ? AND endDate >= ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setDate(1, endDate);
				preparedStatement.setDate(2, startDate);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {

						Campsite campsite = null;

						int id = resultSet.getInt(1);
						String section = resultSet.getNString(2);
						String road = resultSet.getNString(3);
						int siteNo = resultSet.getInt(4);
						String type = resultSet.getNString(5);

						if (type.equals("Cabin")) {
							campsite = createCabin(connection, id, section, road, siteNo);
						}
						if (type.equals("Pitch")) {
							campsite = createPitch(connection, id, section, road, siteNo);
						}
						campsiteList.add(campsite);
					}

				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return campsiteList;
	}

	private Price getPrice(Connection connection, int id) {
		Price price = null;
		try {
			String query = "SELECT price, effectiveDate, campsiteId FROM price WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();

			float priceAmount = resultSet.getFloat(1);
			Date effectiveDate = resultSet.getDate(2);
			price = new Price(priceAmount, effectiveDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;

	}

	private Cabin createCabin(Connection connection, int id, String section, String road, int siteNo) {

		String cabinQuery = "SELECT maxPeople, deposit FROM Cabin WHERE id = ?";
		try (PreparedStatement cabinPreparedStatement = connection.prepareStatement(cabinQuery)) {

			cabinPreparedStatement.setInt(1, id);
			try (ResultSet cabinResultSet = cabinPreparedStatement.executeQuery()) {

				if (cabinResultSet.next()) {
					int maxPeople = cabinResultSet.getInt(1);
					float deposit = cabinResultSet.getFloat(2);

					Price price = getPrice(connection, id);
					return new Cabin(section, road, siteNo, price, maxPeople, deposit);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Pitch createPitch(Connection connection, int id, String section, String road, int siteNo) {

		String pitchQuery = "SELECT fee FROM Pitch WHERE id = ?";
		try (PreparedStatement pitchPreparedStatement = connection.prepareStatement(pitchQuery)) {
			pitchPreparedStatement.setInt(1, id);
			try (ResultSet pitchResultSet = pitchPreparedStatement.executeQuery()) {

				if (pitchResultSet.next()) {
					float fee = pitchResultSet.getFloat(1);

					Price price = getPrice(connection, id);
					return new Pitch(section, road, siteNo, price, fee);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	private void validateDate(Date date) throws InvalidDateException {
		if(date == null) {
			throw new InvalidDateException();
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.format(date);
		}
		catch(IllegalArgumentException e){
			throw new InvalidDateException();
			
		}
	}

}
