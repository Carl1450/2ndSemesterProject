package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import Model.Customer;
import Model.Employee;

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

	public List<Customer> getAllCustomers() {

		List<Customer> customers = new ArrayList<>();

		String findAllCustomersQuery = "SELECT cust.id, cust.fname, cust.lname, cust.phoneno, cust.email, "
				+ "[address].street, [address].streetno, [address].zipcode, city.city " + "FROM Customer cust "
				+ "LEFT JOIN [address] ON cust.addressId = [address].id "
				+ "LEFT JOIN city ON [address].zipcode = city.zipcode ";

		try (Connection connection = DBConnection.getConnection(env);
				PreparedStatement preparedStatement = connection.prepareStatement(findAllCustomersQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				String street = resultSet.getString("street");
				String streetno = resultSet.getString("streetno");
				String zipcode = resultSet.getString("zipcode");
				String city = resultSet.getString("city");
				String phoneNumber = resultSet.getString("phoneno");
				String email = resultSet.getString("email");

				String name = fname + " " + lname;
				String address = street + " " + streetno + " " + city + " " + zipcode;

				Customer customer = new Customer(id, name, address, phoneNumber, email);
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
	}

	public boolean saveCustomer(String name, String address, String phoneNumber, String email, int zipCode,
			String city) {

		Connection conn = DBConnection.getConnection(env);
		String insertCustomerQ = "INSERT INTO Customer( fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?, ?);";

		int rowsAffected = 0;
		String[] splitName = name.split("\\s+");
		String[] splitAddress = address.split("\\s+");

		try {
			boolean savedCity = saveCity(city, zipCode);

			if (!savedCity) {
				updateCity(city, zipCode);
			}

			int addressId = saveAddress(splitAddress[0], Integer.parseInt(splitAddress[1]), zipCode);

			PreparedStatement preparedStatement = conn.prepareStatement(insertCustomerQ);
			preparedStatement.setNString(1, splitName[0]);
			preparedStatement.setNString(2, splitName[1]);
			preparedStatement.setNString(3, email);
			preparedStatement.setNString(4, phoneNumber);
			preparedStatement.setInt(5, addressId);

			rowsAffected = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);

		return rowsAffected > 0;
	}

	private boolean saveCity(String city, int zipcode) {
		int rowsAffected = 0;

		String insertCityQ = "INSERT INTO City( city, zipcode) VALUES (?, ?);";

		Connection connection = DBConnection.getConnection(env);

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insertCityQ);
			preparedStatement.setNString(1, city);
			preparedStatement.setInt(2, zipcode);

			rowsAffected = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getSQLState().equals("23000") && e.getErrorCode() == 2627) {
				System.err.println("Primary key violation: The record with the specified primary key already exists.");
			} else {
				e.printStackTrace();
			}
		}

		DBConnection.closeConnection(connection);

		return rowsAffected > 0;

	}

	private int saveAddress(String street, int streetno, int zipcode) {
		int rowsAffected = 0;

		int addressID = -1;

		String insertAddressQ = "INSERT INTO [Address](street, streetno, zipcode) VALUES (?, ?, ?);";

		Connection connection = DBConnection.getConnection(env);

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insertAddressQ,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setNString(1, street);
			preparedStatement.setInt(2, streetno);
			preparedStatement.setInt(3, zipcode);

			rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						addressID = generatedKeys.getInt(1);

					} else {
						System.err.println("Failed to retrieve the generated ID for the address.");
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);

		}

		return addressID;

	}

	public boolean updateCustomer(String name, String address, String phoneNumber, String email, String city,
			int zipcode) {

		Connection conn = DBConnection.getConnection(env);
		String updateCustomerQ = "UPDATE Customer SET fname = ?, lname = ?, email = ?, phoneno = ?, addressId = ? WHERE phoneno = ?;";

		int rowsAffected = 0;
		String[] splitName = name.split("\\s+");
		String[] splitAddress = address.split("\\s+");

		try {
			// Update or create city
			boolean updatedCity = updateCity(city, zipcode);

			if (!updatedCity) {
				saveCity(city, zipcode);
			}

			// Update or create address
			String street = splitAddress[0];
			int streetno = Integer.parseInt(splitAddress[1]);

			int addressId = saveAddress(street, streetno, zipcode);

			PreparedStatement preparedStatement = conn.prepareStatement(updateCustomerQ);
			preparedStatement.setNString(1, splitName[0]);
			preparedStatement.setNString(2, splitName[1]);
			preparedStatement.setNString(3, email);
			preparedStatement.setNString(4, phoneNumber);
			preparedStatement.setInt(5, addressId);
			preparedStatement.setNString(6, phoneNumber);

			rowsAffected = preparedStatement.executeUpdate();

			DBConnection.closeConnection(conn);

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			DBConnection.closeConnection(conn);
			return false;
		}

	}

	private boolean updateCity(String city, int zipcode) {
		Connection connection = DBConnection.getConnection(env);
		String updateCityQ = "UPDATE City SET city = ?, zipcode = ? WHERE zipcode = ?;";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateCityQ);
			preparedStatement.setNString(1, city);
			preparedStatement.setInt(2, zipcode);
			preparedStatement.setInt(3, zipcode);

			int rowsAffected = preparedStatement.executeUpdate();

			DBConnection.closeConnection(connection);

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			DBConnection.closeConnection(connection);
			return false;
		}
	}

	public boolean deleteCustomer(String phoneNumber) {

		String deleteCustomerQ = "DELETE FROM Customer WHERE phoneNo = ?";
		int rowsAffected = 0;

		try (Connection connection = DBConnection.getConnection(env);
				PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerQ)) {

			preparedStatement.setString(1, phoneNumber);
			rowsAffected = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rowsAffected > 0) {
			System.out.println("Customer succesfully deleted. ");
		} else {
			System.out.println("Failed to delete customer. ");
		}
		
		return rowsAffected > 0;

	}
}
