package Tests.DatbaseTests;

import Database.ConnectionEnvironment;
import Database.CustomerDAO;
import Database.DBConnection;
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
		deleteMockData();
		insertMockData();
	}

	private void insertMockData() {
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
		deleteMockData();
	}

	private void deleteMockData() {

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

		// Act
		SUT.updateCustomerByPhoneNumber(phoneNumber, newName, null, null, newEmail);
		Customer updatedCustomer = SUT.findCustomerByPhoneNumber(phoneNumber);

		// Assert
		assertNotNull(updatedCustomer);
		assertEquals(newEmail, updatedCustomer.getEmail());
		assertEquals(newName, updatedCustomer.getName());
	}

	@Test
	void update_customer_by_phoneNumber_with_invalid_phoneNumber() {
		// Arrange
		CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
		String invalidPhoneNumber = "00000";
		String newEmail = "new.email@email.com";

		// Act
		SUT.updateCustomerByPhoneNumber(invalidPhoneNumber, null, null, null, newEmail);
		Customer updatedCustomer = SUT.findCustomerByPhoneNumber(invalidPhoneNumber);

		// Assert
		assertNull(updatedCustomer);
	}
	
	@Test
    void saveCustomer_SuccessfulInsertion() {
        // Arrange
		CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String name = "John Doe";
        String address = "Danmarksgade 22";
        String phoneNumber = "+45 22384742";
        String email = "john.doe@example.com";
        int zipCode = 9000;
        String city = "TestCity";

        // Act
        boolean result = SUT.saveCustomer(name, address, phoneNumber, email, zipCode, city);

        // Assert
        assertTrue(result, "Customer insertion should be successful");
    }

    @Test
    void saveCustomer_FailedCustomerInsertion() {
        // Arrange
    	CustomerDAO SUT = new CustomerDAO(ConnectionEnvironment.TESTING);
        String name = "John Doe";
        String address = "Danmarksgade 23";
        String phoneNumber = "+45 22384742";
        String email = "invalid-email"; // Intentionally set an invalid email to trigger a failure in customer insertion
        int zipCode = 9000;
        String city = "TestCity";

        // Act
        boolean result = SUT.saveCustomer(name, address, phoneNumber, email, zipCode, city);

        // Assert
        assertFalse(result, "Customer insertion should fail");
    }

}
