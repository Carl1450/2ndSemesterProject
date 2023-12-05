package Model;

public class Customer extends Person {

	private int customerId;
	
	public Customer(String name, String address, String phoneNumber, String email ) {
		super( name, address, phoneNumber, email);
	}
	
	public int getCustomerId() {
		return customerId;
	}

}
