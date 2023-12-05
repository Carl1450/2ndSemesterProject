package Tests.DatbaseTests;

import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Database.InvalidDateException;
import Model.Campsite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestCampsiteDAO {


    @BeforeEach
    void setUp() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Insert mock data for City and Address (needed for Customer and Employee)
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        // Insert mock data for Customer and Employee
        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES (1, 'Jens', 'Larsen', 'jens.larsen@email.com', '+45 12345678', 1);";
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 1);";

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
            statement.executeUpdate(mockAddressInsertQuery);
            statement.executeUpdate(mockCustomerInsertQuery);
            statement.executeUpdate(mockEmployeeInsertQuery);
            statement.executeUpdate(mockCampsiteInsertQuery);
            statement.executeUpdate(mockBookingInsertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterEach
    void tearDown() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        String deleteMockDataQuery = "DELETE FROM Booking; DELETE FROM Campsite; DELETE FROM Employee; DELETE FROM Customer; DELETE FROM [Address]; DELETE FROM City;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteMockDataQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void TS_3_TC_1_campsites_are_returned_when_given_valid_dates() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);

        // Reformatted date strings
        String startDateString = "2023-12-01";
        String endDateString = "2023-12-07";

        // Convert string directly to java.sql.Date
        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate);

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void TS_3_TC_2_empty_list_is_returned_when_there_are_no_available_campsites() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);

        // Reformatted date strings
        String startDateString = "2023-01-01";
        String endDateString = "2023-12-31";

        // Convert string directly to java.sql.Date
        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate);

        // Assert
        assertEquals(0, result.size());
    }

    @ParameterizedTest
    @CsvSource({
            "abc, cba",
            "'NULL', 'NULL'" // Using 'NULL' as a placeholder for null
    })
    void TS_4_TC_1_method_throws_InvalidDateException_when_given_invalid_dates(String startDateString, String endDateString) {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);

        // Act & Assert
        assertThrows(InvalidDateException.class, () -> {
            Date startDate = (startDateString != null && startDateString.equalsIgnoreCase("NULL")) ? Date.valueOf(startDateString) : null;
            Date endDate = (endDateString != null && endDateString.equalsIgnoreCase("NULL")) ? Date.valueOf(endDateString) : null;
            SUT.getAvailableCampsites(startDate, endDate);
        });
    }


}
