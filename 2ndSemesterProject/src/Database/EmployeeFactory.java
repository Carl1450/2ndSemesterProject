package Database;

import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeFactory {

    public static Employee getEmployee(ResultSet resultSet) {
        Employee employee = null;
        try {
            int id = resultSet.getInt("id");

            String role = resultSet.getString("role");

            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");

            String street = resultSet.getString("street");
            String streetno = resultSet.getString("streetno");
            String zipcode = resultSet.getString("zipcode");
            String city = resultSet.getString("city");

            String phoneNumber = resultSet.getString("phoneno");

            String password = resultSet.getString("password");

            String email = resultSet.getString("email");

            String name = fname + " " + lname;
            String address = street + " " + streetno + " " + city + " " + zipcode;

            System.out.println(role.toLowerCase());

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
