package Control;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;

import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Model.Booking;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

public class BookingController {
	private BookingDAO bookingDAO;
	private CampsiteController campsiteController;
	private Booking currentBooking;
	private CustomerController customerController;
	private Employee currentEmployee;

	private ConnectionEnvironment env;

	public BookingController(Employee employee, ConnectionEnvironment env) {
		this.env = env;

		customerController = new CustomerController(env);
		campsiteController = new CampsiteController(env);

		bookingDAO = new BookingDAO(env);

		currentEmployee = employee;

	}

	public Booking startBooking() {
		currentBooking = new Booking(currentEmployee);
		return currentBooking;
	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		Customer customer = customerController.findCustomerByPhoneNumber(phoneNumber);
		if (customer != null) {
			currentBooking.setCustomer(customer);
		}
		return customer;
	}

	public List<Campsite> getAvailableCampsites(String startDate, String endDate, boolean searchForCabin,
			boolean searchForPitch) {
		Date startDateFormatted = Date.valueOf(startDate);
		Date endDateFormatted = Date.valueOf(endDate);
		return campsiteController.getAvailableCampsites(startDateFormatted, endDateFormatted, searchForCabin,
				searchForPitch);
	}

	public void addCampsiteToBooking(Campsite campsite, String startDate, String endDate, boolean includeFee) {
		Date startDateFormatted = Date.valueOf(startDate);
		Date endDateFormatted = Date.valueOf(endDate);
		if (reserveCampsite(campsite, startDateFormatted, endDateFormatted)) {
			currentBooking.setCampsite(campsite);

			long stayAmountOfDays = getDaysDifference(startDateFormatted, endDateFormatted);

			float totalPrice = campsite.getPrice().getPrice() * stayAmountOfDays;
			if (includeFee) {
				totalPrice += campsite.getFee();
			}
			currentBooking.setTotalPrice(totalPrice);

			currentBooking.setStartDate(startDateFormatted);
			currentBooking.setEndDate(endDateFormatted);
		}

	}

	private long getDaysDifference(Date date1, Date date2) {

		java.time.LocalDate localDate1 = date1.toLocalDate();
		java.time.LocalDate localDate2 = date2.toLocalDate();

		long daysDifference = ChronoUnit.DAYS.between(localDate1, localDate2);
		if (daysDifference == 0) { 
			daysDifference = 1;
		}

		return daysDifference;
	}

	public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate) {
		return campsiteController.reserveCampsite(campsite, startDate, endDate, currentEmployee);

	}

	public boolean cancelReservationOfCampsite() {

		boolean successfullCancel;

		Campsite campsite = currentBooking.getCampsite();
		Date startDate = currentBooking.getStartDate();
		Date endDate = currentBooking.getEndDate();

		if (campsite == null || startDate == null || endDate == null || currentEmployee == null) {
			successfullCancel = false;
		} else {
			successfullCancel = campsiteController.cancelReservationOfCampsite(campsite, startDate, endDate,
					currentEmployee);
		}

		return successfullCancel;

	}

	public boolean saveBooking() {
		boolean success = false;

		if (validateBooking(currentBooking)) {
			success = bookingDAO.saveBooking(currentBooking);
		}

		return success;
	}

	private boolean validateBooking(Booking booking) {
		boolean validBooking = true;

		if (booking == null) {
			validBooking = false;
		} else {
			if (booking.getEndDate() == null || booking.getStartDate() == null
					|| booking.getEndDate().before(booking.getStartDate())) {
				return false;
			}

			if (booking.getTotalPrice() <= 0) {
				validBooking = false;
			}

			if (booking.getCustomer() == null) {
				validBooking = false;
			}

			if (booking.getEmployee() == null) {
				validBooking = false;
			}

			if (booking.getCampsite() == null) {
				validBooking = false;
			}

			if (booking.getAmountOfAdults() < 1) {
				validBooking = false;
			}

			if (booking.getAmountOfChildren() < 0) {
				validBooking = false;
			}
		}

		return validBooking;
	}

	public void cancelBooking() {
		cancelReservationOfCampsite();
		currentBooking = null;
	}

	public void setAmountOfPeople(int amountOfAdults, int amountOfChildren) {
		currentBooking.setAmountOfAdults(amountOfAdults);
		currentBooking.setAmountOfChildren(amountOfChildren);
	}

	public boolean handlePayment(float amount) {
		return amount > 0;

	}

	public Booking getCurrentBooking() {
		return currentBooking;

	}

	public void setCurrentBooking(Booking booking) {
		this.currentBooking = booking;
	}
}
