package Model;

public abstract class Employee extends Person {

    private String cpr;
    private int id;
    private String password;

    public Employee(int id, String name, Address address, String phoneNumber, String email, String cpr,
                    String password) {
        super(name, phoneNumber, email, address);
        this.id = id;
        this.cpr = cpr;
        this.password = password;
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
