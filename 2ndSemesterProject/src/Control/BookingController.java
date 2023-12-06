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
    private CampsiteController campsiteController;
    private Booking currentBooking;
    private CustomerController customerController;

    public BookingController() {
        customerController = new CustomerController();
        campsiteController = new CampsiteController();

        BookingDAO bookingDAO = new BookingDAO();
    }

    public Booking startBooking(Employee employee) {
        currentBooking = new Booking();
        return currentBooking;

    }

    public List<Campsite> getAvailableCampsites(String startDate, String endDate) {
        return campsiteController.getAvailableCampsites(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    public void addCampsiteToBooking(Campsite campsite) {
        currentBooking.setCampsite(campsite);
    }

    public void addCustomerToBooking(Customer customer) {
        currentBooking.setCustomer(customer);
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerController.findCustomerByPhoneNumber(phoneNumber);

    }

    public Booking getCurrentBooking() {
        return currentBooking;

    }

    public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
        return campsiteController.reserveCampsite(campsite, startDate, endDate, employee);
    }

    public boolean saveBooking(Booking booking) {
        // TODO remember to relese/invalidate the reservation in the DB made from reserve campsite
        return bookingDAO.saveBooking(booking);
    }


    public boolean handlePayment(float amount) {
        return amount > 0;

    }

}
