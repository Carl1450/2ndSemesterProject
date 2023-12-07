package Tests.ControllerTests;

import Control.BookingController;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Model.*;
import Model.Package;
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
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, password, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 'password1', 1);";

        // Insert mock data for Campsite
        String mockCampsiteInsertQuery =
                "INSERT INTO Campsite (id, section, road, siteNo, [type]) VALUES " +
                        "(1, 'Nord', 'Egevej', 101, 'Standard'), " +
                        "(2, 'Syd', 'Bøgevej', 102, 'Deluxe'), " +
                        "(3, 'Vest', 'Ahornvej', 103, 'Standard');";

        // Insert mock data for Booking
        String mockBookingInsertQuery =
                "INSERT INTO Booking (id, startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteId, packageId) VALUES " +
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

            statement.executeUpdate("SET IDENTITY_INSERT Campsite ON");
            statement.executeUpdate(mockCampsiteInsertQuery);
            statement.executeUpdate("SET IDENTITY_INSERT Campsite OFF");

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
        Campsite campsite = new Pitch(1, "", "", 10, pitchPrice, 1000);
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
}
