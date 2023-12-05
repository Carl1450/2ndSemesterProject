package Control;

import Model.Employee;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Database.EmployeeDAO;

public class EmployeeController {

	private EmployeeDAO employeeDAO;

	public Employee findEmployeeById(int id) {
		return employeeDAO.findEmployeeById(id);
	}

	public boolean validateLogin(int id, String password) {
		Employee employee = findEmployeeById(id);

		if (employee != null) {
			String enteredPassword = hashPassword(password);
			if (enteredPassword.equals(employee.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	public String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();

			for (byte b : hashedPassword) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
