package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import Model.*;

public class CustomerDAO {

	private ConnectionEnvironment env;

	public CustomerDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {

		String findCustomerByPhoneNumberQ = "SELECT cust.id, cust.fname, cust.lname, cust.email, [address].street, [address].streetno, [address].zipcode, city.city "
				+ "FROM customer cust " + "LEFT JOIN [address] ON cust.addressId = [address].id "
				+ "LEFT JOIN city ON [address].zipcode = city.zipcode " + "WHERE phoneNo = ?";

		Customer customer = null;

		try (Connection connection = DBConnection.getConnection(env)) {
			PreparedStatement prepStat = connection.prepareStatement(findCustomerByPhoneNumberQ);
			prepStat.setString(1, phoneNumber);
			ResultSet rs = prepStat.executeQuery();

			if (rs.next()) {

				int id = rs.getInt("id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String street = rs.getString("street");
				String streetno = rs.getString("streetno");
				String zipcode = rs.getString("zipcode");
				String city = rs.getString("city");
				String email = rs.getString("email");
				String name = fname + " " + lname;
				String address = street + " " + streetno + " " + city + " " + zipcode;

				customer = new Customer(id, name, address, phoneNumber, email);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	public boolean saveCustomer(String name, String address, String phoneNumber, String email) {

		Connection conn = DBConnection.getConnection(env);
		String insertCustomerQ = "INSERT INTO customer( fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?);";
		int rowsAffected = 0;
		String[] splitName = name.split("\\s+");
		try {
			// Save customer info
			PreparedStatement prepStat = conn.prepareStatement(insertCustomerQ);
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

	public void updateCustomerByPhoneNumber(String oldPhoneNumber, String newName, String newAddress, String newPhoneNumber,
			String newEmail) {

		StringBuilder updateCustomerQ = new StringBuilder("UPDATE Customer SET ");
		int rowsAffected = 0;

		List<String> updateColumns = new ArrayList<>();
		List<String> updateValues = new ArrayList<>();

		if (newName != null) {
			updateColumns.add("fname");
			updateValues.add(newName.split("\\s+")[0]);

			updateColumns.add("lname");
			updateValues.add(newName.split("\\s+")[1]);
		}

		if (newAddress != null) {
			updateColumns.add("addressId");
			updateValues.add(newAddress);
		}

		if (newPhoneNumber != null) {
			updateColumns.add("phoneNo");
			updateValues.add(newPhoneNumber);
		}

		if (newEmail != null) {
			updateColumns.add("email");
			updateValues.add(newEmail);
		}

		for (int i = 0; i < updateColumns.size(); i++) {
			updateCustomerQ.append(updateColumns.get(i)).append(" = ?");
			if (i < updateColumns.size() - 1) {
				updateCustomerQ.append(", ");
			}
		}

		updateCustomerQ.append(" WHERE phoneNo = ?");

		try (Connection connection = DBConnection.getConnection(env)) {
			PreparedStatement preparedStatement = connection.prepareStatement(updateCustomerQ.toString());

			int parameterIndex = 1;

			for (String value : updateValues) {
				preparedStatement.setString(parameterIndex++, value);
			}

			preparedStatement.setString(parameterIndex, oldPhoneNumber);
			
			rowsAffected = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(rowsAffected > 0) {
			System.out.println("Customer information succcesfully updated. ");
		} else {
			System.out.println("Failed to update customer information. ");
		}

	}
}
