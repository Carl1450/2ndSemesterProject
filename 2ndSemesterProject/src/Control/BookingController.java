package Control;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Database.BookingDAO;
import Model.Booking;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

public class BookingController {
	private BookingDAO bookingDAO;
	private CampsiteController campsiteCon;
	private Booking currentBooking;
	private CustomerController customerController;

	public BookingController() {

		BookingDAO bookingDAO = new BookingDAO();
		ArrayList<Date> getAvailableCampsites = new ArrayList<>();

	}

	public Booking startBooking(Employee employee) {
		currentBooking = new Booking();
		return currentBooking;

	}

	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate) {

		return campsiteCon.getAvailebleCampsites(startDate, endDate);
	}

	public void addCampsiteToBooking(Campsite campsite) {
		currentBooking.setCampsite(campsite);
	}

	public void addCustomerToBooking(Customer customer) {

	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {

		return customerController.findCustomerByPhoneNumber(phoneNumber);

	}

	public Booking getCurrentBooking() {
		return currentBooking;

	}

	public boolean saveBooking(Booking booking) {
		return false;

	}

	public boolean handlePayment(float amount) {
		return false;

	}

}
