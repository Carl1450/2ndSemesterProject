package Tests.DatbaseTests;

import Database.CustomerDAO;
import Model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomerDAO {
    @Test
    void TS_2_TC_1_find_customer_by_phone_number_with_valid_number() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO();
        String phoneNumber = "+45 12345678";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(result);
    }

    @Test
    void TS_2_TC_2_find_customer_by_phone_number_with_invalid_number() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO();
        String phoneNumber = "";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNull(result);
    }

    @Test
    void TS_2_TC_3_find_customer_by_phone_number_with_null() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO();
        String phoneNumber = null;

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNull(result);
    }

}
