package Database;

import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeFactory {

    public static Employee getEmployee(int employeeId, String name, Address address, String phoneNumber, String email, String role) {

        Employee employee = null;

        switch (role.toLowerCase()) {
            case "admin":
                employee = new Admin(employeeId, name, address, phoneNumber, email, null, null);
                break;
            case "receptionist":
                employee = new Receptionist(employeeId, name, address, phoneNumber, email, null, null);
                break;
            case "salesassistant":
                employee = new SalesAssistant(employeeId, name, address, phoneNumber, email, null, null);
                break;
            case "janitor":
                employee = new Janitor(employeeId, name, address, phoneNumber, email, null, null);
                break;
        }
        return employee;
    }

    public static Employee getEmployee(ResultSet resultSet) {
        Employee employee = null;
        try {
            int id = resultSet.getInt("employeeId");

            String role = resultSet.getString("role");

            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");

            int addressId = resultSet.getInt("addressId");
            String street = resultSet.getString("street");
            String streetno = resultSet.getString("streetno");
            String zipcode = resultSet.getString("zipcode");
            String city = resultSet.getString("city");

            String phoneNumber = resultSet.getString("phoneno");

            String password = resultSet.getString("password");

            String email = resultSet.getString("email");

            String name = fname + " " + lname;

            Address address = new Address(addressId, street, Integer.parseInt(streetno), Integer.parseInt(zipcode), city);

            switch (role.toLowerCase()) {
                case "admin":
                    employee = new Admin(id, name, address, phoneNumber, email, null, password);
                    break;
                case "receptionist":
                    employee = new Receptionist(id, name, address, phoneNumber, email, null, password);
                    break;
                case "salesassistant":
                    employee = new SalesAssistant(id, name, address, phoneNumber, email, null, password);
                    break;
                case "janitor":
                    employee = new Janitor(id, name, address, phoneNumber, email, null, password);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;

    }
}
