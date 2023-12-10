package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Employee;

public class EmployeeDAO {

    private ConnectionEnvironment env;


    public EmployeeDAO(ConnectionEnvironment env) {
        this.env = env;

    }


    public Employee findEmployeeById(int id) {
        String findEmployeeByIdQ = "SELECT id, fname, lname, password FROM Employee WHERE id = ?";

        Employee employee = null;

        try (Connection connection = DBConnection.getConnection(env)) {
            PreparedStatement prepStatement = connection.prepareStatement(findEmployeeByIdQ);
            prepStatement.setInt(1, id);
            ResultSet rs = prepStatement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String password = rs.getString("password");
                employee = new Employee(id, fname, lname, null, null, null, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

}
