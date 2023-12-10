package Tests.DatbaseTests;

import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Model.Cabin;
import Model.Campsite;
import Model.Employee;
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
        deleteMockDataFromDatabase();
        insertMockDataInDatabase();
    }

    private void insertMockDataInDatabase() {

        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";
        String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES (1, 'Jens', 'Larsen', 'jens.larsen@email.com', '+45 12345678', 1);";
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, password, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 'password1', 1), (2, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Manager', '0101901234', 'password2', 1);";
        String mockCampsiteInsertQuery = "INSERT INTO Campsite (section, road, siteNo, [type]) VALUES ('Nord', 'Egevej', 1, 'Pitch'), ('Syd', 'Bøgevej', 2, 'Pitch'), ('Vest', 'Ahornvej', 3, 'Cabin');";
        String mockBookingInsertQuery = "INSERT INTO Booking (id, startDate, endDate, totalPrice, amountOfAdults, amountOfChildren, customerId, employeeId, campsiteSiteNo) VALUES (1, '2023-01-01', '2023-01-07', 500.0, 2, 1, 1, 1, 1), (2, '2023-02-01', '2023-02-07', 550.0, 2, 0, 1, 1, 2), (3, '2023-03-01', '2023-03-07', 600.0, 1, 1, 1, 1, 3), (4, '2023-04-01', '2023-04-07', 650.0, 3, 1, 1, 1, 1), (5, '2023-05-01', '2023-05-07', 700.0, 2, 2, 1, 1, 2);";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        try {
            DBConnection.startTransaction(connection);

            DBConnection.executeUpdate(connection, mockCityInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockCustomerInsertQuery, "Customer");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.executeUpdate(connection, mockCampsiteInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockBookingInsertQuery, "Booking");

            DBConnection.commitTransaction(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DBConnection.closeConnection(connection);
        }

    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    private void deleteMockDataFromDatabase() {
        String deleteQuery = "DELETE FROM Booking; DELETE FROM Reservation; DELETE FROM Campsite; DELETE FROM Employee; DELETE FROM Customer; DELETE FROM [Address]; DELETE FROM City;";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        DBConnection.executeUpdate(connection, deleteQuery);

    }


    @Test
    void TS_3_TC_1_campsites_are_returned_when_given_valid_dates() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);

        String startDateString = "2023-12-01";
        String endDateString = "2023-12-07";

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

        String startDateString = "2023-01-01";
        String endDateString = "2023-12-31";

        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testReserveCampsite_NoCrash() {
        // Arrange

        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);
        Campsite campsite = new Cabin(1, null, null, null, 0, 0);
        Date startDate = Date.valueOf("2023-11-01");
        Date endDate = Date.valueOf("2023-11-03");

        Employee mockEmployee = new Employee(1, null, null, null, null, null, null);

        // Act
        boolean result = SUT.reserveCampsite(campsite, startDate, endDate, mockEmployee);

        // Assert
        assertTrue(result);

    }


    @Test
    public void test_reserve_campsite_returns_false_when_it_cannot_reserve() {
        // Arrange
        CampsiteDAO mockCampsiteDAO = new CampsiteDAO(ConnectionEnvironment.TESTING);
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);
        Campsite campsite = new Cabin(1, null, null, null, 0, 0);
        Date startDate = Date.valueOf("2023-11-01");
        Date endDate = Date.valueOf("2023-11-03");

        Employee mockEmployee1 = new Employee(1, null, null, null, null, null, null);
        Employee mockEmployee2 = new Employee(2, null, null, null, null, null, null);

        // Act
        mockCampsiteDAO.reserveCampsite(campsite, startDate, endDate, mockEmployee1);

        boolean result = SUT.reserveCampsite(campsite, startDate, endDate, mockEmployee2);

        // Assert
        assertFalse(result);

    }

    @Test
    public void test_cancel_reservation_works() {
        // Arrange
        CampsiteDAO mockCampsiteDAO = new CampsiteDAO(ConnectionEnvironment.TESTING);
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);
        Campsite campsite = new Cabin(1, null, null, null, 0, 0);
        Date startDate = Date.valueOf("2023-11-01");
        Date endDate = Date.valueOf("2023-11-03");

        Employee mockEmployee1 = new Employee(1, null, null, null, null, null, null);
        Employee mockEmployee2 = new Employee(2, null, null, null, null, null, null);

        // Act
        boolean firstReservationSuccess = mockCampsiteDAO.reserveCampsite(campsite, startDate, endDate, mockEmployee1);

        boolean secondReservationSuccess = SUT.reserveCampsite(campsite, startDate, endDate, mockEmployee2);

        boolean cancellationSuccess = mockCampsiteDAO.cancelReservationOfCampsite(campsite, startDate, endDate, mockEmployee1);

        boolean thirdReservationSuccess = SUT.reserveCampsite(campsite, startDate, endDate, mockEmployee2);

        // Assert
        assertTrue(firstReservationSuccess);
        assertFalse(secondReservationSuccess);
        assertTrue(cancellationSuccess);
        assertTrue(thirdReservationSuccess);

    }

    @Test
    public void test_cancel_reservation_returns_false_when_nothing_is_cancelled() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(ConnectionEnvironment.TESTING);
        Campsite campsite = new Cabin(1, null, null, null, 0, 0);
        Date startDate = Date.valueOf("2023-11-01");
        Date endDate = Date.valueOf("2023-11-03");

        Employee mockEmployee = new Employee(1, null, null, null, null, null, null);

        // Act


        boolean cancellationSuccess = SUT.cancelReservationOfCampsite(campsite, startDate, endDate, mockEmployee);


        // Assert
        assertFalse(cancellationSuccess);

    }
}



