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

import Model.*;

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
            return null;
        }

        return queryAvailableCampsites(startDate, endDate);
    }

    private List<Campsite> queryAvailableCampsites(Date startDate, Date endDate) {
        List<Campsite> campsiteList = new ArrayList<>();

        String query = "SELECT c.id, c.section, c.road, c.siteNo, c.[type], cab.maxpeople, cab.deposit, p.fee, pr.price, pr.effectiveDate " +
                "FROM Campsite c " +
                "LEFT JOIN Cabin cab ON c.id = cab.id " +
                "LEFT JOIN Pitch p ON c.id = p.id " +
                "LEFT JOIN Price pr ON c.id = pr.campsiteId " +
                "WHERE NOT EXISTS (" +
                "SELECT 1 FROM Booking b WHERE b.campsiteId = c.id AND b.startDate <= ? AND b.endDate >= ?" +
                ") AND NOT EXISTS (" +
                "SELECT 1 FROM Reservation r WHERE r.campsiteId = c.id AND r.startDate <= ? AND r.endDate >= ? AND NOT r.timeChanged < DATEADD(minute, -10, GETDATE())" +
                ");";

        try (Connection connection = DBConnection.getInstance(env).getConnection();
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


    public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, int employeeId) {
        boolean reservationStarted = false;

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            if (!checkForConflictingBooking(connection, campsite.getId(), startDate, endDate)) {
                if (insertTentativeReservation(connection, campsite.getId(), startDate, endDate, employeeId)) {
                    connection.commit();
                    reservationStarted = true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationStarted;
    }


    private boolean checkForConflictingBooking(Connection connection, int campsiteId, Date startDate, Date endDate) throws SQLException {
        String checkQuery = "SELECT 1 FROM Booking WHERE campsiteId = ? AND NOT (startDate > ? OR endDate < ?) " +
                "UNION " +
                "SELECT 1 FROM Reservation WHERE campsiteId = ? AND NOT (startDate > ? OR endDate < ?) AND NOT timeChanged < DATEADD(minute, -10, GETDATE())";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, campsiteId);
            checkStmt.setDate(2, endDate);
            checkStmt.setDate(3, startDate);
            checkStmt.setInt(4, campsiteId);
            checkStmt.setDate(5, endDate);
            checkStmt.setDate(6, startDate);

            try (ResultSet rs = checkStmt.executeQuery()) {

                return rs.next();
            }
        }
    }


    private boolean insertTentativeReservation(Connection connection, int campsiteId, Date startDate, Date endDate, int employeeId) throws SQLException {
        String insertQuery = "INSERT INTO Reservation (startDate, endDate, employeeId, campsiteId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setDate(1, startDate);
            insertStmt.setDate(2, endDate);
            insertStmt.setInt(3, employeeId);
            insertStmt.setInt(4, campsiteId);
            return insertStmt.executeUpdate() > 0;
        }
    }

    private void validateDate(Date date) throws InvalidDateException {
        if (date == null) {
            throw new InvalidDateException();
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.format(date);
        } catch (IllegalArgumentException e) {
            throw new InvalidDateException();

        }
    }

}
