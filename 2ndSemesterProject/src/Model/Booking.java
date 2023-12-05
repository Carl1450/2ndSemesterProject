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
	
    public Customer getCustomer() {
        return null;
    }
    
    public void setCampsite (Campsite campsite) {
    	this.campsite = campsite;
    	
    }

	public Date getStartDate() {
		return startDate;
	}
    
}
