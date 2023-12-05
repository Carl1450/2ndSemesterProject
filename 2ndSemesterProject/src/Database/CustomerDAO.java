package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.*;

public class CustomerDAO {

	ConnectionEnvironment env;

	private String findCustomerByPhoneNumberQ = "SELECT cust.id, cust.fname, cust.lname, cust.email, [address].street, [address].streetno, [address].zipcode, city.city "
			+ "FROM customer cust "
			+ "LEFT JOIN [address] ON cust.addressId = [address].id "
			+ "LEFT JOIN city ON [address].zipcode = city.zipcode "
			+ "WHERE phoneNo = ?";
	
	private DBConnection connectionDB;

	public CustomerDAO() {
		env = ConnectionEnvironment.PRODUCTION;
	}

	public CustomerDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {

		Customer customer = null;

		ResultSet rs = null;

		try (Connection connection = DBConnection.getInstance(env).getConnection()) {
			PreparedStatement prepStat = connection.prepareStatement(findCustomerByPhoneNumberQ);
			prepStat.setString(1, phoneNumber);
			rs = prepStat.executeQuery();

			if (rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String street = rs.getString("street");
				String streetno = rs.getString("streetno");
				String zipcode = rs.getString("zipcode");
				String city = rs.getString("city");
				String email = rs.getString("email");
				String name = fname + " " + lname;
				String address = street + " " + streetno + " " + city + " " + zipcode;
				
				customer = new Customer(name, address, phoneNumber, email);
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
		}

		return rowsAffected > 0;
	}
}
