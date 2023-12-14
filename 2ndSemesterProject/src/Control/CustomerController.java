package Control;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
import Model.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerController {

	private CustomerDAO customerDAO;
	private Customer customer;

	private ConnectionEnvironment env;

	public CustomerController(ConnectionEnvironment env) {
		this.env = env;
		this.customerDAO = new CustomerDAO(env);

	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		return customerDAO.findCustomerByPhoneNumber(phoneNumber);

	}

	public boolean saveCustomerToDB(String name, String address, String phoneNumber, String email, int zipCode,
			String city) {
		return customerDAO.saveCustomer(name, address, phoneNumber, email, zipCode, city);

	}

	public void updateCustomer(Customer customer) {
		try {
			customerDAO.updateCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomer(String phoneNumber) {
		customerDAO.deleteCustomer(phoneNumber);
	}
}