package Model;

public class Admin extends Employee{

	public Admin(int id, String name, Address address, String phoneNumber, String email, String cpr, String password) {
		super(id, name, address, phoneNumber, email, cpr, password); 
	}

	@Override
	public String toString() {
		return "Admin";
	}

}