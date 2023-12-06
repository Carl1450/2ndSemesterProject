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
		String insertCustomerQ = "INSERT INTO booking(startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteId, packageId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

		int result = 0;
		boolean hasPersisted = false;
		
		if (booking != null) {

			try (Connection connection = DBConnection.getInstance(env).getConnection()) {
				PreparedStatement prepStat = connection.prepareStatement(insertCustomerQ);

				prepStat.setDate(1, booking.getStartDate());
				prepStat.setDate(2, booking.getEndDate());
				prepStat.setFloat(3, booking.getTotalPrice());
				prepStat.setInt(4, booking.getAmountOfAdults());
				prepStat.setInt(5, booking.getAmountOfChildren());
				prepStat.setInt(6, booking.getCustomer().getCustomerId());
				prepStat.setInt(7, booking.getEmployee().getId());
				if (booking.getCampsite() != null) {
					prepStat.setInt(8, booking.getCampsite().getId());
				} else
					prepStat.setObject(8, null);
				if (booking.getPackage() != null) {
					prepStat.setInt(9, booking.getPackage().getId());
				} else
					prepStat.setObject(9, null);

				result = prepStat.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (result == 0) {
				return hasPersisted;
			} else {
				hasPersisted = true;
				return hasPersisted;
			}
		}
		return hasPersisted;
	}
}
