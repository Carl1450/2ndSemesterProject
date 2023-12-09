package Tests.ControllerTests;

import Control.BookingController;
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

        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Insert mock data for City and Address (needed for Customer and Employee)
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        // Insert mock data for Customer and Employee
        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES (1, 'Jens', 'Larsen', 'jens.larsen@email.com', '+45 12345678', 1);";
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, password, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 'password1', 1), (2, 'Bo', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901235', 'password2', 1);";

        // Insert mock data for Campsite
        String mockCampsiteInsertQuery =
                "INSERT INTO Campsite (section, road, siteNo, [type]) VALUES " +
                        "('Nord', 'Egevej', 1, 'Cabin'), " +
                        "('Syd', 'Bøgevej', 2, 'Pitch'), " +
                        "('Vest', 'Ahornvej', 3, 'Cabin');";

        // Insert mock data for Booking
        String mockBookingInsertQuery =
                "INSERT INTO Booking (id, startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteSiteNo, packageId) VALUES " +
                        "(1, '2023-01-01', '2023-01-07', 500.0, 2, 1, 1, 1, 1, NULL), " +
                        "(2, '2023-02-01', '2023-02-07', 550.0, 2, 0, 1, 1, 2, NULL), " +
                        "(3, '2023-03-01', '2023-03-07', 600.0, 1, 1, 1, 1, 3, NULL), " +
                        "(4, '2023-04-01', '2023-04-07', 650.0, 3, 1, 1, 1, 1, NULL), " +
                        "(5, '2023-05-01', '2023-05-07', 700.0, 2, 2, 1, 1, 2, NULL);";

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(mockCityInsertQuery);

            statement.executeUpdate("SET IDENTITY_INSERT [Address] ON");
            statement.executeUpdate(mockAddressInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Address] OFF");

            statement.executeUpdate("SET IDENTITY_INSERT Customer ON");
            statement.executeUpdate(mockCustomerInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT Customer OFF");

            statement.executeUpdate("SET IDENTITY_INSERT Employee ON");
            statement.executeUpdate(mockEmployeeInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT Employee OFF");

            statement.executeUpdate(mockCampsiteInsertQuery);

            statement.executeUpdate("SET IDENTITY_INSERT Booking ON");
            statement.executeUpdate(mockBookingInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT Booking OFF");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    private void deleteMockDataFromDatabase() {
        String deleteMockDataQuery = "DELETE FROM Booking; DELETE FROM Campsite; DELETE FROM Employee; DELETE FROM Customer; DELETE FROM [Address]; DELETE FROM City; DELETE FROM Reservation;";

        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteMockDataQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReserveAndSaveBookingForSameEmployee() {
        // Arrange
        Employee employee = new Employee(1, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);
        Package packageDeal = null;

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite, packageDeal);

        BookingController SUT = new BookingController(employee);

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
        Employee employee = new Employee(1, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);

        // Two different campsites
        Campsite campsite1 = new Pitch(1, "", "", pitchPrice, 1000);
        Campsite campsite2 = new Pitch(2, "", "", pitchPrice, 1000);

        // Two different bookings
        Booking booking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee, campsite1, null);
        Booking booking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee, campsite2, null);

        BookingController SUT = new BookingController(employee);

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
        Employee employee1 = new Employee(1, "", "", "", "", "", "");
        Employee employee2 = new Employee(2, "", "", "", "", "", "");
        Date startDate = Date.valueOf("2023-07-01");
        Date endDate = Date.valueOf("2023-07-07");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate);

        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking booking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee1, campsite, null);
        Booking booking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer, employee2, campsite, null);

        BookingController bookingController1 = new BookingController(employee1);
        BookingController bookingController2 = new BookingController(employee2);

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
        Employee employee1 = new Employee(1, "", "", "", "", "", "");
        Employee employee2 = new Employee(2, "", "", "", "", "", "");
        Date startDate1 = Date.valueOf("2023-07-01");
        Date endDate1 = Date.valueOf("2023-07-07");
        Date startDate2 = Date.valueOf("2023-07-08"); // Start immediately after endDate1
        Date endDate2 = Date.valueOf("2023-07-14");
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "", "", "", "");
        Price pitchPrice = new Price(500, startDate1);

        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking booking1 = new Booking(startDate1, endDate1, price, amountOfAdults, amountOfChildren, customer, employee1, campsite, null);
        Booking booking2 = new Booking(startDate2, endDate2, price, amountOfAdults, amountOfChildren, customer, employee2, campsite, null);

        BookingController bookingController1 = new BookingController(employee1);
        BookingController bookingController2 = new BookingController(employee2);

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
    void TS_1_TC_2_null_booking_is_not_persisted_in_database() { // Arrange
        // Arrange
        Employee mockEmployee = new Employee(1, null, null, null, null, null, null);

        BookingController SUT = new BookingController(mockEmployee);

        Booking mockBooking = null;

        // Act
        SUT.setCurrentBooking(mockBooking);

        Boolean result = SUT.saveBooking();

        // Assert
        assertFalse(result);
    }
}
