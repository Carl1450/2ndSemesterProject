package Database;

import java.sql.*;

import Model.Booking;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

public class BookingDAO {

    ConnectionEnvironment env;

    public BookingDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public BookingDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public boolean saveBooking(Booking booking) {
        boolean hasPersisted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            if (!hasBookingConflict(connection, booking)) {
                hasPersisted = insertBooking(connection, booking);
                if (hasPersisted) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hasPersisted;
    }


    private boolean hasBookingConflict(Connection connection, Booking booking) throws SQLException {
        String conflictCheckQuery = "SELECT 1 FROM Booking WHERE campsiteId = ? AND NOT (startDate > ? OR endDate < ?) " +
                "UNION " +
                "SELECT 1 FROM Reservation WHERE campsiteId = ? AND NOT (startDate > ? OR endDate < ?) AND timeChanged > DATEADD(minute, -10, GETDATE());";

        try (PreparedStatement conflictCheckStmt = connection.prepareStatement(conflictCheckQuery)) {
            conflictCheckStmt.setInt(1, booking.getCampsite().getId());
            conflictCheckStmt.setDate(2, booking.getEndDate());
            conflictCheckStmt.setDate(3, booking.getStartDate());
            // Repeat for Reservation table
            conflictCheckStmt.setInt(4, booking.getCampsite().getId());
            conflictCheckStmt.setDate(5, booking.getEndDate());
            conflictCheckStmt.setDate(6, booking.getStartDate());

            ResultSet rs = conflictCheckStmt.executeQuery();
            return rs.next(); // true if conflict exists, false otherwise
        }
    }

    private boolean insertBooking(Connection connection, Booking booking) throws SQLException {
        String insertBookingQuery = "INSERT INTO booking(startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteId, packageId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement prepStat = connection.prepareStatement(insertBookingQuery)) {
            // Set parameters for booking insert
            prepStat.setDate(1, booking.getStartDate());
            prepStat.setDate(2, booking.getEndDate());
            prepStat.setFloat(3, booking.getTotalPrice());
            prepStat.setInt(4, booking.getAmountOfAdults());
            prepStat.setInt(5, booking.getAmountOfChildren());
            prepStat.setInt(6, booking.getCustomer().getCustomerId());
            prepStat.setInt(7, booking.getEmployee().getId());
            prepStat.setObject(8, booking.getCampsite() != null ? booking.getCampsite().getId() : null);
            prepStat.setObject(9, booking.getPackage() != null ? booking.getPackage().getId() : null);

            int result = prepStat.executeUpdate();
            return (result > 0); // true if insert is successful
        }
    }


}
