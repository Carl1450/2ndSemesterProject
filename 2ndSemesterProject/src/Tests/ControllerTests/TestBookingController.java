package Tests.ControllerTests;

import Control.BookingController;
import Model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBookingController {

    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        mockCustomer = null;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TS_2_TC_1_find_customer_by_phone_number_with_valid_number() {
        // Arrange
        BookingController SUT = new BookingController();

        String validPhoneNumber = "+45 12345678";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(validPhoneNumber);

        // Assert
        assertEquals(mockCustomer, result);
    }

    @Test
    void TS_2_TC_2_find_customer_by_phone_number_with_invalid_number() {
        // Arrange
        BookingController SUT = new BookingController();

        String invalidPhoneNumber = "";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(invalidPhoneNumber);

        // Assert
        assertNull(result);
    }

    @Test
    void TS_2_TC_3_find_customer_by_phone_number_with_null() {
        // Arrange
        BookingController SUT = new BookingController();

        String nullPhoneNumber = null;

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(nullPhoneNumber);

        // Assert
        assertNull(result);
    }
}
