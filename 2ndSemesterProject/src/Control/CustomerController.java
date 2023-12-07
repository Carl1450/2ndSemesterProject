package Control;

import Database.CustomerDAO;
import Model.Customer;

public class CustomerController {

	private CustomerDAO customerDAO;

	public CustomerController() {
		this.customerDAO = new CustomerDAO();

	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		return customerDAO.findCustomerByPhoneNumber(phoneNumber);

	}

	public boolean saveCustomerToDB(String name, String address, String phoneNumber, String email) {
		return customerDAO.saveCustomer (name, address, phoneNumber, email);
		
	}
}
