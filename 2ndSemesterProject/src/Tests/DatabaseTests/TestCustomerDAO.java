package Tests.DatabaseTests;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
import Database.DBConnection;
import Model.Address;
import Model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomerDAO {

    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();
        insertMockDataInDatabase();
    }

    private void insertMockDataInDatabase() {
        String insertMockCity = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        String insertMockAddress = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        String insertMockCustomer = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) "
                + "VALUES (1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        try {
            DBConnection.executeUpdate(connection, insertMockCity);
            DBConnection.executeUpdateWithIdentityInsert(connection, insertMockAddress, "[Address]");
            DBConnection.executeUpdateWithIdentityInsert(connection, insertMockCustomer, "Customer");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBConnection.closeConnection(connection);
    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    private void deleteMockDataFromDatabase() {

        String deleteMockData = "DELETE FROM Customer; DELETE FROM [Address]; DELETE FROM City;";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        try {
            DBConnection.executeUpdate(connection, deleteMockData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBConnection.closeConnection(connection);
    }

    @Test
    void TS_2_TC_1_find_customer_by_phone_number_with_valid_number() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String phoneNumber = "+45 12345678";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(result);
    }

    @Test
    void TS_2_TC_2_find_customer_by_phone_number_with_invalid_number() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String phoneNumber = "";

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNull(result);
    }

    @Test
    void TS_2_TC_3_find_customer_by_phone_number_with_null() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String phoneNumber = null;

        // Act
        Customer result = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNull(result);
    }

    @Test
    void update_Customer_by_phoneNumber_with_validInput() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String phoneNumber = "+45 12345678";
        String newEmail = "new.email@example.com";
        String newName = "Paul Johnson";

        Address address = new Address(1, "Mockaddress", 123, 9000, "Aalborg");

        Customer updatedCustomer = new Customer(1, "Paul John", phoneNumber, "old.email.example.com", address);


        // Act
        SUT.updateCustomer(updatedCustomer);
        Customer updatedCustomerInDatabase = SUT.findCustomerByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(updatedCustomer);
        assertEquals(newEmail, updatedCustomer.getEmail());
        assertEquals(newName, updatedCustomer.getName());
    }

    @Test
    void saveCustomer_SuccessfulInsertion() {
        // Arrange
        CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String firstName = "John";
        String lastName = "Doe";
        String phoneNumber = "+45 22384742";
        String email = "john.doe@example.com";
        String streetName = "Danmarksgade";
        int streetNo = 22;
        int zipCode = 9000;
        String city = "TestCity";

        // Act
        boolean result = SUT.saveCustomer(firstName, lastName, phoneNumber, email, streetName, streetNo, zipCode, city);

        // Assert
        assertTrue(result, "Customer insertion should be successful");
    }

}
