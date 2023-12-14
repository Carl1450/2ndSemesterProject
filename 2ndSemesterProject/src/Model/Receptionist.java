package Model;

public class Receptionist extends Employee {

	public Receptionist(int id, String name, String address, String phoneNumber, String email, String cpr, String password) {
		super(id, name, address, phoneNumber, email, cpr, password);
	}

	@Override
	public String toString() {
		return "Receptionist";
	}

}
