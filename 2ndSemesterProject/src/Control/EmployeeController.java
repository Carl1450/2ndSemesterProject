package Control;

import Database.ConnectionEnvironment;
import Database.EmployeeFactory;
import Model.Address;
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
            String enteredPasswordHash = hashInput(password);
            if (enteredPasswordHash.equals(employee.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public String hashInput(String input) {
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

        String firstName = getFirstNameFrom(name);

        String lastName = getLastNameFrom(name);

        String street = getStreetFromAdressString(address);

        int streetNo = getStreetNumberFromAdressString(address);


        String hashedCpr = hashInput(cprNo);
        String hashedPassword = hashInput(password);


        return employeeDAO.saveEmployee(firstName, lastName, phoneNumber, email, street, streetNo, Integer.parseInt(zipcode), city, role, hashedCpr, hashedPassword);

    }

    private String getFirstNameFrom(String name) {
        String[] splitName = name.split(" ");

        String firstName = "";

        for (int i = 0; i < splitName.length - 1; i++) {
            firstName += " " + splitName[i];
        }
        return firstName.trim();
    }

    private String getLastNameFrom(String name) {
        String[] splitName = name.split(" ");
        String lastName = splitName[splitName.length - 1];
        return lastName;
    }

    private String getStreetFromAdressString(String address) {
        String[] splitAddress = address.split(" ");

        String streetName = "";

        for (int i = 0; i < splitAddress.length - 1; i++) {
            streetName += " " + splitAddress[i];
        }
        return streetName.trim();
    }

    private int getStreetNumberFromAdressString(String address) {

        String[] splitAddress = address.split(" ");

        int streetNo = Integer.parseInt(splitAddress[splitAddress.length - 1]);

        return streetNo;
    }

    public boolean deleteEmployee(int employeeId) {
        return employeeDAO.deleteEmployee(employeeId);
    }

    public boolean updateEmployee(Employee oldEmployee , String name, String address, int zipCode, String city, String phoneNumber, String email, String role) {

        String street = getStreetFromAdressString(address);
        int streetNo = getStreetNumberFromAdressString(address);

        Address employeeAddress = new Address(oldEmployee.getAddress().getId(), street,streetNo, zipCode, city);

        Employee updatedEmployee = EmployeeFactory.getEmployee(oldEmployee.getId(), name, employeeAddress, phoneNumber, email, role);

        return employeeDAO.UpdateEmployee(updatedEmployee);
    }

}
