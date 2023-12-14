package Control;

import Database.ConnectionEnvironment;
import Model.Employee;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Database.EmployeeDAO;
import Model.Janitor;

public class EmployeeController {

    private ConnectionEnvironment env;
    private EmployeeDAO employeeDAO;

    public EmployeeController(ConnectionEnvironment env) {
        this.env = env;
        employeeDAO = new EmployeeDAO(env);
    }

    public Employee findEmployeeById(int id) {
        return employeeDAO.findEmployeeById(id);
    }

    public List<Janitor> getAllJanitors() {
        return employeeDAO.getAllJanitors();
    }

    public boolean validateLogin(Employee employee, String password) {

        if (employee != null) {
            String enteredPasswordHash = hashPassword(password);
            if (enteredPasswordHash.equals(employee.getPassword())) {
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

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public boolean saveEmployee() {

        String employeeName = "";
        String address = "";
        String phoneNumber = "";
        String email = "";
        int employeeZipcode = 0;

        return false;
//        return employeeDAO.saveEmployee();
    }

}
