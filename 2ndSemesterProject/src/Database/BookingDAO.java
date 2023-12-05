package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import Model.Booking;
import Model.Campsite;

public class BookingDAO {

    ConnectionEnvironment env;

    public BookingDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public BookingDAO(ConnectionEnvironment env) {
        this.env = env;
    }
    
    public boolean saveBooking(Booking booking) {
    	String insertCustomerQ = "INSERT INTO booking(startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteId, packageId) VALUES (?, ?, ?, ?, ?);";
    	
		try (Connection connection = DBConnection.getInstance(env).getConnection()) {
			PreparedStatement prepStat = connection.prepareStatement(insertCustomerQ);
			
			prepStat.setDate(1, booking.getStartDate());
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
}
