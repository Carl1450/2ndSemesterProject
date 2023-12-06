package Model;

public class Employee extends Person {
	
	private String cpr;
	private int id;
	private String password;
	
	public Employee(int id, String name, String address, String phoneNumber, String email, String cpr, String password) {
		super(name, address, phoneNumber, email); 
		this.setId(id);
		this.setCpr(cpr);
	}

	public String getCpr() {
		return cpr;
	}


	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
