package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import Model.Customer;

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

	public boolean saveCustomer(String name, String address, String phoneNumber, String email, int zipCode,
			String city) {

		Connection conn = DBConnection.getConnection(env);
		String insertCustomerQ = "INSERT INTO Customer( fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?, ?);";

		int rowsAffected = 0;
		String[] splitName = name.split("\\s+");
		String[] splitAddress = address.split("\\s+");

		try {
			boolean savedCity = saveCity(city, zipCode);

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

		String insertAddressQ = "INSERT INTO Address(street, streetno, zipcode) VALUES (?, ?, ?);";

		Connection connection = DBConnection.getConnection(env);

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insertAddressQ, Statement.RETURN_GENERATED_KEYS);
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

	public void updateCustomer(Customer customer) throws SQLException {
		String updateCityQuery = "UPDATE City SET city=? WHERE zipCode=?";
		String checkCityQuery = "SELECT COUNT(*) FROM City WHERE zipCode=? AND city=?";
		String updateAddressQuery = "UPDATE Address SET street=?, streetno=?, zipcode=? WHERE zipcode=?";
		String insertAddressQuery = "INSERT INTO Address(street, streetno, zipcode) VALUES (?, ?, ?)";
		String updateCustomerQuery = "UPDATE Customer SET fname=?, lname=?, email=? WHERE phoneno=?";
		String insertCustomerQuery = "INSERT INTO Customer(fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection(env);
				PreparedStatement updateCityStmt = connection.prepareStatement(updateCityQuery);
				PreparedStatement checkCityStmt = connection.prepareStatement(checkCityQuery);
				PreparedStatement updateAddressStmt = connection.prepareStatement(updateAddressQuery);
				PreparedStatement insertAddressStmt = connection.prepareStatement(insertAddressQuery,
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatement updateCustomerStmt = connection.prepareStatement(updateCustomerQuery);
				PreparedStatement insertCustomerStmt = connection.prepareStatement(insertCustomerQuery,
						Statement.RETURN_GENERATED_KEYS)) {

			// Start a transaction
			connection.setAutoCommit(false);

			try {
				// Update city information
				String[] splitAddress = customer.getAddress().split("\\s+");
				String[] splitName = customer.getName().split("\\s+");
				checkCityStmt.setInt(1, Integer.parseInt(splitAddress[3]));
				checkCityStmt.setString(2, splitAddress[2]);
				ResultSet resultSet = checkCityStmt.executeQuery();
				resultSet.next();
				int cityCount = resultSet.getInt(1);

				// Update city information if it doesn't exist
				if (cityCount == 0) {
					updateCityStmt.setString(1, splitAddress[2]);
					updateCityStmt.setInt(2, Integer.parseInt(splitAddress[3]));
					updateCityStmt.executeUpdate();
				}

				// Commit the changes to the city information
				connection.commit();

				boolean customerExists = findCustomerByPhoneNumber(customer.getPhoneNumber()) != null;

				// Update or insert into the Address table
				if (customerExists) {
					// Customer exists, update the existing address
					updateAddressStmt.setString(1, splitAddress[0]);
					updateAddressStmt.setInt(2, Integer.parseInt(splitAddress[1]));
					updateAddressStmt.setInt(3, Integer.parseInt(splitAddress[3]));
					updateAddressStmt.setInt(4, Integer.parseInt(splitAddress[3]));
					updateAddressStmt.executeUpdate();

					// Update the customer information
					updateCustomerStmt.setString(1, splitName[0]);
					updateCustomerStmt.setString(2, splitName[1]);
					updateCustomerStmt.setString(3, customer.getEmail());
					updateCustomerStmt.setString(4, customer.getPhoneNumber());
					updateCustomerStmt.executeUpdate();

				} else {
					// Customer is new, insert a new address
					insertAddressStmt.setString(1, splitAddress[0]);
					insertAddressStmt.setInt(2, Integer.parseInt(splitAddress[1]));
					insertAddressStmt.setInt(3, Integer.parseInt(splitAddress[3]));
					insertAddressStmt.executeUpdate();

					// Retrieve the generated addressId
					ResultSet addressKeys = insertAddressStmt.getGeneratedKeys();
					if (addressKeys.next()) {
						int addressId = addressKeys.getInt(1);

						// Insert into the Customer table
						insertCustomerStmt.setString(1, splitName[0]);
						insertCustomerStmt.setString(2, splitName[1]);
						insertCustomerStmt.setString(3, customer.getEmail());
						insertCustomerStmt.setString(4, customer.getPhoneNumber());
						insertCustomerStmt.setInt(5, addressId);
						insertCustomerStmt.executeUpdate();
					}
				}

				// Commit the transaction
				connection.commit();
			} catch (SQLException e) {
				// Rollback the transaction in case of an exception
				connection.rollback();
				throw e;
			} finally {
				// Set auto-commit back to true
				connection.setAutoCommit(true);
			}
		}
	}

	public void deleteCustomer(String phoneNumber) {

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

	}
}
