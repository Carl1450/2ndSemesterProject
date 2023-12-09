package Tests.DatbaseTests;

import Model.Customer;
import Model.Employee;
import Model.Pitch;
import Model.Price;
import Model.Campsite;
import Database.BookingDAO;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Model.Booking;
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
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Insert a mock city
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        // Insert a mock address that references the mock city
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        // Insert a mock customer that references the mock address
        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES "
                + "(1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";

        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
                + "VALUES (1, '', '', '', '', '', '', '', 1);";

        String mockCampsiteInsertQuery = "INSERT INTO Campsite (section, road, siteno, type) "
                + "VALUES ('', '', 1, '');";

        String mockPriceInsertQuery = "INSERT INTO Price (id, price, effectiveDate, campsiteSiteNo) " + "VALUES (1, 0, '2023-01-01', 1);";

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(mockCityInsertQuery);

            statement.executeUpdate("SET IDENTITY_INSERT [Address] ON");
            statement.executeUpdate(mockAddressInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Address] OFF");

            statement.executeUpdate("SET IDENTITY_INSERT [Customer] ON");
            statement.executeUpdate(mockCustomerInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Customer] OFF");

            statement.executeUpdate("SET IDENTITY_INSERT [Employee] ON");
            statement.executeUpdate(mockEmployeeInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Employee] OFF");

            statement.executeUpdate(mockCampsiteInsertQuery);

            statement.executeUpdate("SET IDENTITY_INSERT [Price] ON");
            statement.executeUpdate(mockPriceInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT [Price] OFF");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        String deleteMockDataQuery = "DELETE FROM Price; DELETE FROM Booking; DELETE FROM Campsite; DELETE FROM Employee; DELETE FROM Customer; DELETE FROM Address; DELETE FROM City";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteMockDataQuery);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TS_1_TC_1_valid_booking_is_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO();

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, null, null, null, null);
        Employee employee = new Employee(1, "", "", "", "",
                "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);
        Package packageDeal = null;

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite, packageDeal);

        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        // Assert
        assertTrue(result);
    }


    @Test
    void TS_1_TC_3_invalid_value_booking_is_not_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO();

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = null; //A null customer would not work in the program, nor the database
        Employee employee = new Employee(1, "", "", "", "",
                "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);
        Package packageDeal = null;

        Booking mockBooking = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite, packageDeal);


        // Act
        Boolean result = SUT.saveBooking(mockBooking);

        // Assert
        assertFalse(result);

    }

    @Test
    void TS_1_TC_4_conflicting_booking_is_not_persisted_in_database() {
        // Arrange
        BookingDAO SUT = new BookingDAO();

        Date startDate = new Date(1000);
        Date endDate = new Date(50000000);
        float price = 5000;
        int amountOfAdults = 2;
        int amountOfChildren = 1;
        Customer customer = new Customer(1, null, null, null, null);
        Employee employee = new Employee(1, "", "", "", "",
                "", "");
        Price pitchPrice = new Price(500, startDate);
        Campsite campsite = new Pitch(1, "", "", pitchPrice, 1000);
        Package packageDeal = null;

        Booking mockBooking1 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite, packageDeal);

        Booking mockBooking2 = new Booking(startDate, endDate, price, amountOfAdults, amountOfChildren, customer,
                employee, campsite, packageDeal);
        // Act
        Boolean result1 = SUT.saveBooking(mockBooking1);
        Boolean result2 = SUT.saveBooking(mockBooking2);

        // Assert
        assertTrue(result1);
        assertFalse(result2);
    }
}