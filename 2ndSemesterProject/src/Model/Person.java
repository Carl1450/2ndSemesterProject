package Model;

public abstract class Person {
	private String name;
	private String phoneNumber;
	private String email;

	private Address address;

	public Person(String name, String phoneNumber, String email, Address address) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		int lastSpaceIndex = name.lastIndexOf(" ");

		if (lastSpaceIndex == -1 || lastSpaceIndex == name.length() - 1) {
			return name;
		}

		return name.substring(0, lastSpaceIndex);
	}

	public String getLastName() {
		int lastSpaceIndex = name.lastIndexOf(" ");

		if (lastSpaceIndex == -1 || lastSpaceIndex == name.length() - 1) {
			return "";
		}

		return name.substring(lastSpaceIndex + 1);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
