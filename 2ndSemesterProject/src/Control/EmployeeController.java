package Control;

import Model.Employee;

import Database.EmployeeDAO;

public class EmployeeController {

	private EmployeeDAO employeeDAO;

	public Employee findEmployeeById(int id) {
		return employeeDAO.findEmployeeById(id);
	}

	public boolean validateLogin(int id, String password) {
		Employee employee = findEmployeeById(id);

		if (employee != null) {
			String enteredPassword = employeeDAO.hashPassword(password);
			if (enteredPassword.equals(employee.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
