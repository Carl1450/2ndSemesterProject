package Tests.ControllerTests;

import Control.BookingController;
import Control.CampsiteController;
import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingController {


    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();

        insertMockDataToDatabase();

    }

    void insertMockDataToDatabase() {

        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES (1, 'Jens', 'Larsen', 'jens.larsen@email.com', '+45 12345678', 1);";
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, password, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 'password1', 1), (2, 'Bo', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901235', 'password2', 1);";

        String mockCampsiteInsertQuery =
                "INSERT INTO Campsite (section, road, siteNo, [type], fee) VALUES " +
                        "('Nord', 'Egevej', 1, 'Cabin', 100), " +
                        "('Syd', 'Bøgevej', 2, 'Pitch', 100), " +
                        "('Vest', 'Ahornvej', 3, 'Cabin', 100);";

        String mockBookingInsertQuery =
                "INSERT INTO Booking (id, startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteSiteNo) VALUES " +
                        "(1, '2023-01-01', '2023-01-07', 500.0, 2, 1, 1, 1, 1), " +
                        "(2, '2023-02-01', '2023-02-07', 550.0, 2, 0, 1, 1, 2), " +
                        "(3, '2023-03-01', '2023-03-07', 600.0, 1, 1, 1, 1, 3), " +
                        "(4, '2023-04-01', '2023-04-07', 650.0, 3, 1, 1, 1, 1), " +
                        "(5, '2023-05-01', '2023-05-07', 700.0, 2, 2, 1, 1, 2);";


        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);
        try {

            DBConnection.startTransaction(connection);


            DBConnection.executeUpdate(connection, mockCityInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockCustomerInsertQuery, "Customer");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.executeUpdate(connection, mockCampsiteInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockBookingInsertQuery, "Booking");

            DBConnection.commitTransaction(connection);
        } catch (SQLException e) {
            try {
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DBConnection.closeConnection(connection);
        }
    }


    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    private void deleteMockDataFromDatabase() {
        String deleteMockDataQuery = "DELETE FROM Booking; " +
                "DELETE FROM Campsite; " +
                "DELETE FROM Employee; " +
                "DELETE FROM Customer; " +
                "DELETE FROM [Address]; " +
                "DELETE FROM City; " +
                "DELETE FROM Reservation;";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        DBConnection.executeUpdate(connection, deleteMockDataQuery);

        DBConnection.closeConnection(connection);

    }


    @Test
    public void testReserveAndSaveBookingForSameEmployee() {
        // Arrange
        Employee employee = new Admin(1, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite);

        BookingController SUT = new BookingController(employee, ConnectionEnvironment.TESTING);

        // Act
        SUT.setCurrentBooking(mockBooking);

        boolean reservationResult = SUT.reserveCampsite(campsite, startDate, endDate);

        boolean bookingResult = SUT.saveBooking();

        // Assertion
        assertTrue(reservationResult);
        assertTrue(bookingResult);
    }

    @Test
    public void testReserveAndSaveTwoDifferentCampsitesForSameEmployee() {
        // Arrange
        Employee employee = new Admin(1, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);

        Campsite campsite1 = new Pitch(1, "", "", pitchPrice, 1000);
        Campsite campsite2 = new Pitch(2, "", "", pitchPrice, 1000);

        Booking booking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee, campsite1);
        Booking booking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee, campsite2);

        BookingController SUT = new BookingController(employee, ConnectionEnvironment.TESTING);

        // Act & Assert
        // For the first campsite
        SUT.setCurrentBooking(booking1);
        boolean reservationResult1 = SUT.reserveCampsite(campsite1, startDate, endDate);
        boolean bookingResult1 = SUT.saveBooking();
        assertTrue(reservationResult1, "First reservation should be successful");
        assertTrue(bookingResult1, "First booking should be successfully saved");

        // For the second campsite
        SUT.setCurrentBooking(booking2);
        boolean reservationResult2 = SUT.reserveCampsite(campsite2, startDate, endDate);
        boolean bookingResult2 = SUT.saveBooking();
        assertTrue(reservationResult2, "Second reservation should be successful");
        assertTrue(bookingResult2, "Second booking should be successfully saved");
    }

    @Test
    public void testConcurrentReservationAndBooking() {
        // Arrange
        Employee employee1 = new Admin(1, "", "", "", "", "", "");
        Employee employee2 = new Admin(2, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);

        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking booking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee1, campsite);
        Booking booking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee2, campsite);

        BookingController bookingController1 = new BookingController(employee1, ConnectionEnvironment.TESTING);
        BookingController bookingController2 = new BookingController(employee2, ConnectionEnvironment.TESTING);

        // Act
        boolean reservationResult1 = bookingController1.reserveCampsite(campsite, startDate, endDate);
        boolean reservationResult2 = bookingController2.reserveCampsite(campsite, startDate, endDate);

        bookingController1.setCurrentBooking(booking1);
        boolean bookingResult1 = bookingController1.saveBooking();

        bookingController2.setCurrentBooking(booking2);
        boolean bookingResult2 = bookingController2.saveBooking();


        // Assert
        assertTrue(reservationResult1);
        assertFalse(reservationResult2);
        assertTrue(bookingResult1);
        assertFalse(bookingResult2);
    }

    @Test
    public void testNonOverlappingReservationAndBooking() {
        // Arrange
        Employee employee1 = new Admin(1, "", "", "", "", "", "");
        Employee employee2 = new Admin(2, "", "", "", "", "", "");
        Date startDate1 = Date.valueOf("2023-07-01");
        Date endDate1 = Date.valueOf("2023-07-07");
        Date startDate2 = Date.valueOf("2023-07-08");
        Date endDate2 = Date.valueOf("2023-07-14");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate1);

        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking booking1 = new Booking(startDate1, endDate1, price, amountOfAdults, amountOfChildren, customer, employee1, campsite);
        Booking booking2 = new Booking(startDate2, endDate2, price, amountOfAdults, amountOfChildren, customer, employee2, campsite);

        BookingController bookingController1 = new BookingController(employee1, ConnectionEnvironment.TESTING);
        BookingController bookingController2 = new BookingController(employee2, ConnectionEnvironment.TESTING);

        // Act
        bookingController1.setCurrentBooking(booking1);
        boolean reservationResult1 = bookingController1.reserveCampsite(campsite, startDate1, endDate1);
        boolean bookingResult1 = bookingController1.saveBooking();

        bookingController2.setCurrentBooking(booking2);
        boolean reservationResult2 = bookingController2.reserveCampsite(campsite, startDate2, endDate2);
        boolean bookingResult2 = bookingController2.saveBooking();

        // Assert
        assertTrue(reservationResult1);
        assertTrue(bookingResult1);

        assertTrue(reservationResult2);
        assertTrue(bookingResult2);
    }

    @Test
    void TS_1_TC_2_null_booking_is_not_persisted_in_database() {
        // Arrange
        Employee mockEmployee = new Admin(1, null, null, null, null, null, null);

        BookingController SUT = new BookingController(mockEmployee, ConnectionEnvironment.TESTING);

        Booking mockBooking = null;

        // Act
        SUT.setCurrentBooking(mockBooking);

        Boolean result = SUT.saveBooking();

        // Assert
        assertFalse(result);
    }


    @Test
    void TS_1_TC_3_invalid_value_booking_is_not_persisted_in_database() {
        // Arrange
        Employee employee = new Admin(1, "", "", "", "",
                "", "");
        BookingController SUT = new BookingController(employee , ConnectionEnvironment.TESTING);

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = null;
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite);

        // Act
        SUT.setCurrentBooking(mockBooking);

        Boolean result = SUT.saveBooking();

        // Assert
        assertFalse(result);

    }
}
