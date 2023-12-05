package Model;

public class Customer extends Person {

	private int customerId;
	
	public Customer(int customerId, String name, String address, String phoneNumber, String email ) {
		super(name, address, phoneNumber, email);
		this.customerId = customerId;
	}
	
	public int getCustomerId() {
		return customerId;
	}
}
