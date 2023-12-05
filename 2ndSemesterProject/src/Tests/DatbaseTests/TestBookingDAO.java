package Tests.DatbaseTests;

import Model.Customer;
import Model.Employee;
import Model.Pitch;
import Model.Price;
import Model.Campsite;
import Model.Package;
import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingDAO {
    @BeforeEach
    void setUp() {
        // Initialize resources or perform any necessary setup
    }

    @AfterEach
    void tearDown() {
        // Clean up resources or perform any necessary teardown
    }

    @Test
    void TS_1_TC_1_valid_booking_is_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.PRODUCTION);

        
        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, "Kaj Larsen", "Den Vej", "+45 12345678", "mig@mail.com");
        Employee employee = new Employee(1, "Kurt Jensen", "Den Anden Vej", "+45 87654321", "ansat@mail.com", "111198-4575", "password123");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "Section 1", "Papegøjevej", 10, pitchPrice , 1000);
        Package packageDeal = new Package(1);
        
        
        Booking mockBooking = new Booking(startDate, endDate, price
        		, amountOfAdults, amountOfChildren, customer, employee, campsite, packageDeal);

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        //Assert
        assertTrue(result);
    }

    /*
    @Test
    void TS_1_TC_2_null_booking_is_not_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.TESTING);

        Booking mockBooking = null;

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        //Assert
        assertFalse(result);
    } */
}
