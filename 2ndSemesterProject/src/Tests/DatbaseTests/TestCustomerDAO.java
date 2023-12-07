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
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Insert a mock city
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        // Insert a mock address that references the mock city
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        // Insert a mock customer that references the mock address
        String mockCustomerInsertQuery =
                "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES " +
                        "(1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(mockCityInsertQuery);

            statement.executeUpdate("SET IDENTITY_INSERT [Address] ON");
            statement.executeUpdate(mockAddressInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Address] OFF");

            statement.executeUpdate("SET IDENTITY_INSERT Customer ON");
            statement.executeUpdate(mockCustomerInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT Customer ON");

        } catch (SQLException e) {
            throw new RuntimeException("Error setting up mock data", e);
        }
    }

    @AfterEach
    void tearDown() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Delete in reverse order of creation
        String deleteMockDataQuery =
                "DELETE FROM Customer WHERE id = 1; " +
                        "DELETE FROM [Address] WHERE id = 1; " +
                        "DELETE FROM City WHERE zipCode = 1000;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteMockDataQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Error cleaning up mock data", e);
        }
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

}
