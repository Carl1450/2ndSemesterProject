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

    private List<Employee> allEmployees;

    public EmployeeController(ConnectionEnvironment env) {
        this.env = env;
        employeeDAO = new EmployeeDAO(env);
        allEmployees = new ArrayList<>();
    }

    public Employee findEmployeeById(int id) {
        return employeeDAO.findEmployeeById(id);
    }

    public List<Janitor> getAllJanitors() {
        return employeeDAO.getAllJanitors();
    }

    public boolean validateLogin(Employee employee, String password) {

        if (employee != null) {
            String enteredPasswordHash = hashinput(password);
            if (enteredPasswordHash.equals(employee.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public String hashinput(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(input.getBytes(StandardCharsets.UTF_8));
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

    public List<Employee> getAllEmployees(boolean refresh) {
        if (allEmployees.isEmpty() || refresh) {
            allEmployees = employeeDAO.getAllEmployees();
        }
        return allEmployees;
    }

    public int saveEmployee(String name, String phoneNumber, String email, String address, String zipcode, String city, String role, String cprNo, String password) {

        String hashedCpr = hashinput(cprNo);
        String hashedPassword = hashinput(password);

        return employeeDAO.saveEmployee(name, address, phoneNumber, email, Integer.parseInt(zipcode), city, role, hashedCpr, hashedPassword);

    }

    public boolean deleteEmployee(int employeeId) {
        return employeeDAO.deleteEmployee(employeeId);
    }

    public boolean updateEmployee(int employeeId, String name, String address, int zipCode, String city, String phoneNumber, String email, String role) {

        return employeeDAO.UpdateEmployee(employeeId, name, address, zipCode, city, phoneNumber, email, role);
    }

}
