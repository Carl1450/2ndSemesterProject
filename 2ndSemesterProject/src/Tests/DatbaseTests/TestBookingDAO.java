package Tests.DatbaseTests;

import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.TESTING);

        Booking mockBooking = new Booking();

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        //Assert
        assertTrue(result);
    }

    @Test
    void TS_1_TC_2_null_booking_is_not_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.TESTING);

        Booking mockBooking = null;

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        //Assert
        assertFalse(result);
    }
}
