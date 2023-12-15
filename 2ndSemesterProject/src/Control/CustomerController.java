package Control;

import java.util.List;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
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

	public boolean saveCustomerToDB(String name, String address, String phoneNumber, String email, int zipCode,
			String city) {
		return customerDAO.saveCustomer(name, address, phoneNumber, email, zipCode, city);

	}

	public boolean updateCustomer(String name, String address, String phoneNumber, String email, String city,
			int zipcode) {
		return customerDAO.updateCustomer(name, address, phoneNumber, email, city, zipcode);
	}

	public boolean deleteCustomer(String phoneNumber) {
		return customerDAO.deleteCustomer(phoneNumber);
	}
}