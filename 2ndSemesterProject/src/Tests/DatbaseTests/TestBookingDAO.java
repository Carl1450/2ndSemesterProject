package Tests.DatbaseTests;

import Model.*;
import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingDAO {

    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();
        insertMockDataInDatabase();
    }

    void insertMockDataInDatabase() {
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES "
                + "(1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";

        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
                + "VALUES (1, '', '', '', '', '', '', '', 1);";

        String mockCampsiteInsertQuery = "INSERT INTO Campsite (section, road, siteno, type, fee) "
                + "VALUES ('', '', 1, '', 100);";

        String mockPriceInsertQuery = "INSERT INTO Price (id, price, effectiveDate, campsiteSiteNo) " + "VALUES (1, 0, '2023-01-01', 1);";

        try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)){
            DBConnection.startTransaction(connection);

            DBConnection.executeUpdate(connection, mockCityInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockCustomerInsertQuery, "Customer");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.executeUpdate(connection, mockCampsiteInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockPriceInsertQuery, "Price");

            DBConnection.commitTransaction(connection);

            DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    void deleteMockDataFromDatabase() {
        String deleteMockDataQuery = "DELETE FROM Price; " +
                "DELETE FROM Booking; " +
                "DELETE FROM Campsite; " +
                "DELETE FROM Employee; " +
                "DELETE FROM Customer;" +
                "DELETE FROM Address; " +
                "DELETE FROM City";

        try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)) {
            DBConnection.executeUpdate(connection, deleteMockDataQuery);
            DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TS_1_TC_1_valid_booking_is_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.TESTING);

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, null, null, null, null);
        Employee employee = new Admin(1, "", "", "", "",
                "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite);

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        // Assert
        assertTrue(result);
    }


    @Test
    void TS_1_TC_4_conflicting_booking_is_not_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO(ConnectionEnvironment.TESTING);

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, null, null, null, null);
        Employee employee = new Admin(1, "", "", "", "",
                "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);

        Booking mockBooking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite);

        Booking mockBooking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite);
        // Act
        Boolean result1 = SUT.saveBooking(mockBooking1);
        Boolean result2 = SUT.saveBooking(mockBooking2);

        // Assert
        assertTrue(result1);
        assertFalse(result2);
    }
}