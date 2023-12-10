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
        String findEmployeeByIdQ = "SELECT emp.id, emp.fname, emp.lname, emp.password, emp.[role], emp.phoneno, emp.email, " +
                "[address].street, [address].streetno, [address].zipcode, city.city " +
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

}
