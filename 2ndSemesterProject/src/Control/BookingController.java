package Control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.BookingDAO;
import Database.DBConnection;
import Model.Booking;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

public class BookingController {
    private BookingDAO bookingDAO;
    private CampsiteController campsiteController;
    private Booking currentBooking;
    private CustomerController customerController;

    private Connection bookingConnection;

    private Employee currentEmployee;

    public BookingController(Employee employee) {
        customerController = new CustomerController();
        campsiteController = new CampsiteController();

        bookingDAO = new BookingDAO();

        currentEmployee = employee;

    }

    public Booking startBooking() {
        currentBooking = new Booking(currentEmployee);
        return currentBooking;
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerController.findCustomerByPhoneNumber(phoneNumber);
        currentBooking.setCustomer(customer);
        return customer;
    }

    public List<Campsite> getAvailableCampsites(String startDate, String endDate) {
        Date startDateFormatted = Date.valueOf(startDate);
        Date endDateFormatted = Date.valueOf(endDate);
        return campsiteController.getAvailableCampsites(startDateFormatted, endDateFormatted);
    }

    public void addCampsiteToBooking(Campsite campsite, String startDate, String endDate) {
        Date startDateFormatted = Date.valueOf(startDate);
        Date endDateFormatted = Date.valueOf(endDate);
        if (reserveCampsite(campsite, startDateFormatted, endDateFormatted)) {
            currentBooking.setCampsite(campsite);
            currentBooking.setTotalPrice(campsite.getPrice().getPrice());
            currentBooking.setStartDate(startDateFormatted);
            currentBooking.setEndDate(endDateFormatted);
        }
    }

    public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate) {
        return campsiteController.reserveCampsite(campsite, startDate, endDate, currentEmployee);

    }

    public boolean cancelRerservationOfCampsite() {

        return true;
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
            if (booking.getEndDate() == null || booking.getStartDate() == null || booking.getEndDate().before(booking.getStartDate())) {
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
        try {
            if (bookingConnection != null && !bookingConnection.getAutoCommit()) {
                bookingConnection.rollback();
                bookingConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
