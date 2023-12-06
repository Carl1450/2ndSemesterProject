package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Employee;

public class EmployeeDAO {

	ConnectionEnvironment env;
	private String findEmployeeByIdQ = "SELECT emp.id, emp.fname, emp.lname, emp.password FROM Employee emp WHERE id = ?";
	private DBConnection connectionDB;

	public EmployeeDAO() {
		env = ConnectionEnvironment.PRODUCTION;
		connectionDB = DBConnection.getInstance(env);
	}

	public EmployeeDAO(ConnectionEnvironment env) {
		this.env = env;
		connectionDB = DBConnection.getInstance(env);

	}
	

		
	
	public Employee findEmployeeById(int id) {
		Employee employee = null;

		try (Connection connection = DBConnection.getInstance(env).getConnection()) {
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
