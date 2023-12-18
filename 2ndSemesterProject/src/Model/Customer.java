package Model;

public class Customer extends Person {

	private int customerId;

	public Customer(int customerId, String name, String phoneNumber, String email, Address address) {
		super(name, phoneNumber, email, address);
		this.customerId = customerId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

}
