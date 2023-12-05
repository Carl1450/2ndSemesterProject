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

    public Customer getCustomer() {
    	return customer;
    }
    
    public void setCampsite (Campsite campsite) {
    	this.campsite = campsite;
    	
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
}
