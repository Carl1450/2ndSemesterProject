package Control;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
import Model.Customer;

import java.sql.Connection;

public class CustomerController {

	private CustomerDAO customerDAO;

	private ConnectionEnvironment env;

	public CustomerController(ConnectionEnvironment env) {
		this.env = env;
		this.customerDAO = new CustomerDAO(env);

	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		return customerDAO.findCustomerByPhoneNumber(phoneNumber);

	}

	public boolean saveCustomerToDB(String name, String address, String phoneNumber, String email) {
		return customerDAO.saveCustomer (name, address, phoneNumber, email);
		
	}
}
