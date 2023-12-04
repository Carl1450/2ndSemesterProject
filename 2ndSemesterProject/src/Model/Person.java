package Model;

public abstract class Person {
	private String name;
	private String address;
	private String phoneNumber;
	private String email;

	public Person(String name, String address, String phoneNumber, String email) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}

}
