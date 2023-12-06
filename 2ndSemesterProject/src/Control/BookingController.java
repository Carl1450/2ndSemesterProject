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

    public BookingController() {
        customerController = new CustomerController();
        campsiteController = new CampsiteController();

        bookingDAO = new BookingDAO();

    }

    public Booking startBooking(Employee employee) {
        currentBooking = new Booking();
        return currentBooking;
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerController.findCustomerByPhoneNumber(phoneNumber);
        currentBooking.setCustomer(customer);
        return customer;
    }

    public List<Campsite> getAvailableCampsites(String startDate, String endDate) {
        return campsiteController.getAvailableCampsites(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    public void addCampsiteToBooking(Campsite campsite, Date startDate, Date endDate) {
        if (reserveCampsite(campsite, startDate, endDate)) {
            currentBooking.setCampsite(campsite);
        }
    }

    private boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate) {
        boolean reservationSuccess = false;

        try {
            bookingConnection = DBConnection.getInstance().getConnection();
            bookingConnection.setAutoCommit(false);

            reservationSuccess = campsiteController.reserveCampsite(campsite, startDate, endDate, currentBooking.getEmployee().getId());

            if (!reservationSuccess) {
                bookingConnection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (bookingConnection != null) {
                try {
                    bookingConnection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return reservationSuccess;
    }

    public boolean saveBooking(Booking booking) {
        boolean bookingSuccess = false;
        try {
            if (bookingConnection != null && !bookingConnection.getAutoCommit()) {
                bookingSuccess = bookingDAO.saveBooking(booking);
                if (bookingSuccess) {
                    bookingConnection.commit();
                } else {
                    bookingConnection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (bookingConnection != null) {
                try {
                    bookingConnection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            bookingSuccess = false;
        } finally {
            if (bookingConnection != null) {
                try {
                    bookingConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookingSuccess;
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
}
