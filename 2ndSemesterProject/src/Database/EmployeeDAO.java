package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Address;
import Model.Employee;
import Model.Janitor;

public class EmployeeDAO {

    private ConnectionEnvironment env;


    public EmployeeDAO(ConnectionEnvironment env) {
        this.env = env;

    }


    public Employee findEmployeeById(int id) {
        String findEmployeeByIdQ = "SELECT emp.id AS employeeId, emp.fname, emp.lname, emp.password, emp.[role], emp.phoneno, emp.email, " +
                "[address].id AS addressId, [address].street, [address].streetno, [address].zipcode, city.city " +
                "FROM Employee emp " +
                "LEFT JOIN [address] ON emp.addressId = [address].id " +
                "LEFT JOIN city ON [address].zipcode = city.zipcode " +
                "WHERE emp.id = ?";

        Employee employee = null;

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement prepStatement = connection.prepareStatement(findEmployeeByIdQ);
            prepStatement.setInt(1, id);
            ResultSet rs = prepStatement.executeQuery();

            if (rs.next()) {
                employee = EmployeeFactory.getEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        String findAllEmployeesQuery = "SELECT emp.id as employeeId, emp.fname, emp.lname, emp.password, emp.[role], emp.phoneno, emp.email, " +
                "[address].id as addressId, [address].street, [address].streetno, [address].zipcode, city.city " +
                "FROM Employee emp " +
                "LEFT JOIN [address] ON emp.addressId = [address].id " +
                "LEFT JOIN city ON [address].zipcode = city.zipcode ";

        Connection connection = DBConnection.getConnection(env);
        ResultSet resultSet = DBConnection.executeQuery(connection, findAllEmployeesQuery);

        try {
            while (resultSet.next()) {
                employees.add(EmployeeFactory.getEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


    public int saveEmployee(String firstName, String lastName, String phoneNumber, String email, String street, int streetNo, int zipCode,
                            String city, String role, String cprNo, String password) {

        int rowsAffected = 0;

        int employeeId = -1;

        String insertEmployee = "INSERT INTO Employee (fname, lname, email, phoneno, addressId, [role], cprNo, [password]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DBConnection.getConnection(env);


        try {
            DBConnection.startTransaction(connection);

            boolean savedCity = saveCity(city, zipCode);


            int addressId = saveAddress(street, streetNo, zipCode);

            PreparedStatement preparedStatement = connection.prepareStatement(insertEmployee, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNString(1, firstName);
            preparedStatement.setNString(2, lastName);
            preparedStatement.setNString(3, email);
            preparedStatement.setNString(4, phoneNumber);
            preparedStatement.setInt(5, addressId);
            preparedStatement.setNString(6, role);
            preparedStatement.setNString(7, cprNo);
            preparedStatement.setNString(8, password);

            rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employeeId = generatedKeys.getInt(1);

                    } else {
                        System.err.println("Failed to retrieve the generated ID for the address.");
                    }
                }
            }

            DBConnection.commitTransaction(connection);


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

        return employeeId;

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

    public boolean UpdateEmployee(Employee updatedEmployee) {
        int rowsAffected = 0;

        String updateEmployeeQuery = "UPDATE Employee SET fname = ?, lname = ?, email = ?, phoneno = ?, AddressId = ?, [role] = ? WHERE id = ?;";

        Connection connection = DBConnection.getConnection(env);

        Address address = updatedEmployee.getAddress();

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

            PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeQuery);
            preparedStatement.setNString(1, updatedEmployee.getFirstName());
            preparedStatement.setNString(2, updatedEmployee.getLastName());
            preparedStatement.setNString(3, updatedEmployee.getEmail());
            preparedStatement.setNString(4, updatedEmployee.getPhoneNumber());
            preparedStatement.setInt(5, addressId);
            preparedStatement.setNString(6, updatedEmployee.toString());
            preparedStatement.setInt(7, updatedEmployee.getId());

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


    public boolean deleteEmployee(int employeeId) {
        String deleteCustomerQ = "DELETE FROM Employee WHERE id = ?";
        int rowsAffected = 0;

        try (Connection connection = DBConnection.getConnection(env);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerQ)) {

            preparedStatement.setInt(1, employeeId);
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rowsAffected > 0;

    }


    public List<Janitor> getAllJanitors() {
        List<Janitor> janitors = new ArrayList<>();

        String findAllJanitorsQuery = "SELECT emp.id as employeeId, emp.fname, emp.lname, emp.password, emp.[role], emp.phoneno, emp.email, " +
                "[address].id as addressId, [address].street, [address].streetno, [address].zipcode, city.city " +
                "FROM Employee emp " +
                "LEFT JOIN [address] ON emp.addressId = [address].id " +
                "LEFT JOIN city ON [address].zipcode = city.zipcode " +
                "WHERE emp.[role] = 'Janitor';";

        Connection connection = DBConnection.getConnection(env);

        ResultSet resultSet = DBConnection.executeQuery(connection, findAllJanitorsQuery);


        try {
            while (resultSet.next()) {
                janitors.add((Janitor) EmployeeFactory.getEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return janitors;
    }

}
