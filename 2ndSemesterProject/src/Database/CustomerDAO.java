package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Address;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import Model.Customer;
import Model.Employee;

public class CustomerDAO {

    private ConnectionEnvironment env;

    public CustomerDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {

        String findCustomerByPhoneNumberQ = "SELECT cust.id as customerId, cust.fname, cust.lname, cust.email, [address].id as addressId, [address].street, [address].streetno, [address].zipcode, city.city "
                + "FROM customer cust " + "LEFT JOIN [address] ON cust.addressId = [address].id "
                + "LEFT JOIN city ON [address].zipcode = city.zipcode " + "WHERE phoneNo = ?";

        Customer customer = null;

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement prepStat = connection.prepareStatement(findCustomerByPhoneNumberQ);
            prepStat.setString(1, phoneNumber);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {

                int customerId = rs.getInt("customerId");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");

                String email = rs.getString("email");
                String name = fname + " " + lname;

                int addressId = rs.getInt("addressId"); // Corrected the field name
                String street = rs.getNString("street");
                int streetno = rs.getInt("streetno");
                int zipCode = rs.getInt("zipcode");
                String city = rs.getNString("city");

                Address address = new Address(addressId, street, streetno, zipCode, city);

                customer = new Customer(customerId, name, phoneNumber, email, address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public List<Customer> getAllCustomers(String searchPhoneNumber) {

        List<Customer> customers = new ArrayList<>();

        String findAllCustomersQuery = "SELECT cust.id as customerId, cust.fname, cust.lname, cust.phoneno, cust.email, "
                + "[address].id as addressId, [address].street, [address].streetno, [address].zipcode, city.city " + "FROM Customer cust "
                + "LEFT JOIN [address] ON cust.addressId = [address].id "
                + "LEFT JOIN city ON [address].zipcode = city.zipcode "
                + "WHERE cust.phoneno LIKE ?";

        try (Connection connection = DBConnection.getConnection(env);
             PreparedStatement preparedStatement = connection.prepareStatement(findAllCustomersQuery)) {

            preparedStatement.setString(1, "%" + searchPhoneNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int customerId = resultSet.getInt("customerId");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");

                String phoneNumber = resultSet.getString("phoneno");

                String email = resultSet.getString("email");
                String name = fname + " " + lname;

                int addressId = resultSet.getInt("addressId"); // Corrected the field name
                String street = resultSet.getNString("street");
                int streetno = resultSet.getInt("streetno");
                int zipCode = resultSet.getInt("zipcode");
                String city = resultSet.getNString("city");

                Address address = new Address(addressId, street, streetno, zipCode, city);

                Customer customer = new Customer(customerId, name, phoneNumber, email, address);

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public boolean saveCustomer(String firstName, String lastName, String phoneNumber, String email, String streetName, int streetNo, int zipCode, String city) {


        Connection conn = DBConnection.getConnection(env);
        String insertCustomerQ = "INSERT INTO Customer(fname, lname, email, phoneno, addressId) VALUES (?, ?, ?, ?, ?);";

        int rowsAffected = 0;


        try {
            boolean savedCity = saveCity(city, zipCode);

            if (!savedCity) {
                updateCity(city, zipCode);
            }

            int addressId = saveAddress(streetName, streetNo, zipCode);

            PreparedStatement preparedStatement = conn.prepareStatement(insertCustomerQ);
            preparedStatement.setNString(1, firstName);
            preparedStatement.setNString(2, lastName);
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

    public boolean updateCustomer(Customer updatedCustomer) {

        Connection connection = DBConnection.getConnection(env);
        String updateCustomerQuery = "UPDATE Customer SET fname = ?, lname = ?, email = ?, phoneno = ?, addressId = ? WHERE id = ?;";

        int rowsAffected = 0;

        Address address = updatedCustomer.getAddress();

        int addressId = address.getId();

        try {
            DBConnection.startTransaction(connection);

            boolean updatedCity = updateCity(address.getCity(), address.getZipCode());

            if (!updatedCity) {
                saveCity(address.getCity(), address.getZipCode());
            }

            boolean updatedAddress = updateAddress(address.getId(), address.getStreet(), address.getStreetNo(), address.getZipCode());

            if (!updatedAddress) {
                addressId = saveAddress(address.getStreet(), address.getStreetNo(), address.getZipCode());
            }

            PreparedStatement preparedStatement = connection.prepareStatement(updateCustomerQuery);
            preparedStatement.setNString(1, updatedCustomer.getFirstName());
            preparedStatement.setNString(2, updatedCustomer.getLastName());
            preparedStatement.setNString(3, updatedCustomer.getEmail());
            preparedStatement.setNString(4, updatedCustomer.getPhoneNumber());
            preparedStatement.setInt(5, addressId);
            preparedStatement.setInt(6, updatedCustomer.getCustomerId());

            rowsAffected = preparedStatement.executeUpdate();

            DBConnection.commitTransaction(connection);
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DBConnection.closeConnection(connection);
        }

        return rowsAffected > 0;
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

    private boolean updateAddress(int addressId, String street, int streetNo, int zipCode) {
        Connection connection = DBConnection.getConnection(env);
        String updateCityQ = "UPDATE [Address] SET street = ?, streetNo = ?, zipcode = ? WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateCityQ);
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, streetNo);
            preparedStatement.setInt(3, zipCode);
            preparedStatement.setInt(4, addressId);

            int rowsAffected = preparedStatement.executeUpdate();

            DBConnection.closeConnection(connection);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.closeConnection(connection);
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {

        String deleteCustomerQ = "DELETE FROM Customer WHERE id = ?";
        int rowsAffected = 0;

        try (Connection connection = DBConnection.getConnection(env);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerQ)) {

            preparedStatement.setInt(1, customerId);
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
