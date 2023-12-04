package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.*;

public class CustomerDAO {
	private String findCustomerByPhoneNumberQ = "Select id, fname, lname, email from customer where phoneNo = ?";
	private DBConnection connectionDB;

	public CustomerDAO() {
	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {

		Customer customer = null;

		ResultSet rs = null;

		int customerId = -1;

		Connection connection = null;

		try {
			connection = connectionDB.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(findCustomerByPhoneNumberQ);
			prepStat.setString(1, phoneNumber);
			rs = prepStat.executeQuery();

			if (rs.next()) {
				customerId = rs.getInt("id");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	public boolean saveCustomer(String name, String address, String phoneNumber, String email) {

		Connection conn = connectionDB.getConnection();
		String insertOrderQ = "INSERT INTO customer( fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?);";
		int rowsAffected = 0;
		String[] splitName = name.split("\\s+");
		try {
			// Save customer info
			PreparedStatement prepStat = conn.prepareStatement(insertOrderQ);
			prepStat.setString(1, splitName[0]);
			prepStat.setString(2, splitName[1]);
			prepStat.setString(3, email);
			prepStat.setString(4, phoneNumber);
			prepStat.setString(5, address);

			rowsAffected = prepStat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources (Connection, PreparedStatement, etc.) here if necessary
		}

		return rowsAffected > 0;
	}

}
