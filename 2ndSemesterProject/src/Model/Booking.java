package Model;

import java.sql.Date;

public class Booking {

    private Date startDate;
    private Date endDate;
    private float totalPrice;
    private int amountOfAdults;
    private int amountOfChildren;
    private Customer customer;
    private Employee employee;
    private Campsite campsite;
    private Package packageDeal;

    public Booking(Date startDate, Date endDate, float totalPrice, int amountOfAdults, int amountOfChildren, Customer customer, Employee employee, Campsite campsite, Package packageDeal) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.amountOfAdults = amountOfAdults;
        this.amountOfChildren = amountOfChildren;
        this.customer = customer;
        this.employee = employee;
        this.campsite = campsite;
        this.packageDeal = packageDeal;
    }

    public Booking(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

    public int getAmountOfAdults() {
        return amountOfAdults;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Campsite getCampsite() {
        return campsite;
    }

    public Package getPackage() {
        return packageDeal;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCampsite(Campsite campsite) {
        this.campsite = campsite;
    }

    public void setPackageDeal(Package packageDeal) {
        this.packageDeal = packageDeal;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;

    }
}
