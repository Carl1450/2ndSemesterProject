package Control;

import java.util.List;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
import Model.Address;
import Model.Customer;

public class CustomerController {

    private CustomerDAO customerDAO;
    private Customer customer;

    private ConnectionEnvironment env;

    private List<Customer> allCustomers;

    public CustomerController(ConnectionEnvironment env) {
        this.env = env;
        this.customerDAO = new CustomerDAO(env);

    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerDAO.findCustomerByPhoneNumber(phoneNumber);

    }

    public List<Customer> findAllCustomers(String phoneNumber, boolean retrieveNewData) {
        if (retrieveNewData || allCustomers == null) {
            allCustomers = customerDAO.getAllCustomers(phoneNumber);
        }
        return allCustomers;
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

    public boolean saveCustomerToDB(String name, String phoneNumber, String email, String address, int zipCode,
                                    String city) {

        String firstName = getFirstNameFrom(name);

        String lastName = getLastNameFrom(name);


        String streetName = getStreetFromAdressString(address);

        int streetNo = getStreetNumberFromAdressString(address);

        return customerDAO.saveCustomer(firstName, lastName, phoneNumber, email, streetName, streetNo, zipCode, city);
    }

    public boolean updateCustomer(Customer oldCustomer, String name, String email, String phoneNumber, String address, int zipCode, String city) {

        String street = getStreetFromAdressString(address);
        int streetNo = getStreetNumberFromAdressString(address);

        Customer updatedCustomer = new Customer(oldCustomer.getCustomerId(), name, phoneNumber, email, new Address(oldCustomer.getAddress().getId(), street, streetNo, zipCode, city));

        return customerDAO.updateCustomer(updatedCustomer);
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

    public boolean deleteCustomer(int customerId) {
        return customerDAO.deleteCustomer(customerId);
    }
}