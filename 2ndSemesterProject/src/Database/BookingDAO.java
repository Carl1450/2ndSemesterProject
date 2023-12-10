package Database;

import java.sql.*;

import Model.Booking;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

public class BookingDAO {

    ConnectionEnvironment env;

    public BookingDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public boolean saveBooking(Booking booking) {
        boolean hasPersisted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection(env);
            DBConnection.startTransaction(connection, Connection.TRANSACTION_SERIALIZABLE);

            if (!hasBookingConflict(connection, booking)) {
                hasPersisted = insertBooking(connection, booking);
                if (hasPersisted) {
                    DBConnection.commitTransaction(connection);
                } else {
                    DBConnection.rollbackTransaction(connection);
                }
            } else {
                DBConnection.rollbackTransaction(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DBConnection.closeConnection(connection);
        }
        return hasPersisted;
    }


    private boolean hasBookingConflict(Connection connection, Booking booking) throws SQLException {
        String conflictCheckQuery = "SELECT 1 FROM Booking WHERE campsiteSiteNo = ? AND NOT (startDate > ? OR endDate < ?) " +
                "UNION SELECT 1 FROM reservation WHERE campsiteSiteNo = ? AND NOT (startdate >= ? OR endDate <= ?) " +
                "AND timechanged >= DATEADD(minute, -10, GETDATE()) AND employeeid <> ?;";

        try (PreparedStatement conflictCheckStatement = connection.prepareStatement(conflictCheckQuery)) {
            conflictCheckStatement.setInt(1, booking.getCampsite().getSiteNumber());
            conflictCheckStatement.setDate(2, booking.getEndDate());
            conflictCheckStatement.setDate(3, booking.getStartDate());
            conflictCheckStatement.setInt(4, booking.getCampsite().getSiteNumber());
            conflictCheckStatement.setDate(5, booking.getEndDate());
            conflictCheckStatement.setDate(6, booking.getStartDate());
            conflictCheckStatement.setInt(7, booking.getEmployee().getId());

            ResultSet rs = conflictCheckStatement.executeQuery();
            return rs.next();
        }
    }


    private boolean insertBooking(Connection connection, Booking booking) throws SQLException {
        String insertBookingQuery = "INSERT INTO booking(startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteSiteNo) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement prepStat = connection.prepareStatement(insertBookingQuery)) {
            prepStat.setDate(1, booking.getStartDate());
            prepStat.setDate(2, booking.getEndDate());
            prepStat.setFloat(3, booking.getTotalPrice());
            prepStat.setInt(4, booking.getAmountOfAdults());
            prepStat.setInt(5, booking.getAmountOfChildren());
            prepStat.setInt(6, booking.getCustomer().getCustomerId());
            prepStat.setInt(7, booking.getEmployee().getId());
            prepStat.setObject(8, booking.getCampsite().getSiteNumber());

            int result = prepStat.executeUpdate();
            return (result > 0);
        }
    }
}
