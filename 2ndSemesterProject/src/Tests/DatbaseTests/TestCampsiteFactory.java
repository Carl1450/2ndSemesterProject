package Tests.DatbaseTests;

import Database.*;
import Model.Cabin;
import Model.Campsite;
import Model.Employee;
import Model.Pitch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCampsiteFactory {

    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();
        insertMockDataInDatabase();
    }

    private void insertMockDataInDatabase() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        try {
            connection.setAutoCommit(false); // Start transaction

            // Define your insert queries
            String mockCampsiteInsertQuery = "INSERT INTO Campsite (id, section, road, siteNo, [type]) VALUES (1, 'Nord', 'Egevej', 101, 'Pitch'), (2, 'Vest', 'Ahornvej', 102, 'Cabin');";
            String mockPitchInsertQuery = "INSERT INTO Pitch (id, fee) VALUES (1, 100);";
            String mockCabinInsertQuery = "INSERT INTO Cabin (id, maxpeople, deposit) VALUES (2, 10, 1000);";

            String mockPriceInsertQuery = "INSERT INTO Price (id, price, effectiveDate, campsiteId) VALUES (1, 50, '2023-01-01', 1), (2, 50, '2023-01-01', 2);";

            // Execute each insert query
            try (Statement statement = connection.createStatement()) {


                statement.executeUpdate("SET IDENTITY_INSERT Campsite ON");
                statement.executeUpdate(mockCampsiteInsertQuery);
                statement.executeUpdate("SET IDENTITY_INSERT Campsite OFF");

                statement.executeUpdate(mockCabinInsertQuery);

                statement.executeUpdate(mockPitchInsertQuery);

                statement.executeUpdate("SET IDENTITY_INSERT Price ON");
                statement.executeUpdate(mockPriceInsertQuery);
                statement.executeUpdate("SET IDENTITY_INSERT Price OFF");
            }

            connection.commit(); // Commit transaction if all insertions are successful
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit behavior
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    private void deleteMockDataFromDatabase() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        try {
            connection.setAutoCommit(false); // Start transaction

            // Your delete queries
            String[] deleteQueries = {
                    "DELETE FROM Price",
                    "DELETE FROM Pitch",
                    "DELETE FROM Cabin",
                    "DELETE FROM Campsite",

            };

            try (Statement statement = connection.createStatement()) {
                for (String query : deleteQueries) {
                    statement.executeUpdate(query);
                }
            }

            connection.commit(); // Commit transaction if all deletions are successful
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit behavior
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void test_campsiteFactory_returns_expected_object() {
        // Arrange
        String selectCampsitesQuery = "SELECT c.id, c.section, c.road, c.siteNo, c.[type], cab.maxpeople, cab.deposit, p.fee, pr.price, pr.effectiveDate " +
                "FROM Campsite c " +
                "LEFT JOIN Cabin cab ON c.id = cab.id " +
                "LEFT JOIN Pitch p ON c.id = p.id " +
                "LEFT JOIN Price pr ON c.id = pr.campsiteId ";

        Pitch pitchResult = null;
        Cabin cabinResult = null;

        // Act
        try {
            Statement statement = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectCampsitesQuery);

            resultSet.next();
            pitchResult = (Pitch) CampsiteFactory.getCampsite(resultSet);
            resultSet.next();
            cabinResult = (Cabin) CampsiteFactory.getCampsite(resultSet);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Assertions for pitchResult
        assertEquals(1, pitchResult.getId(), "Pitch ID was incorrect");
        assertEquals("Egevej", pitchResult.getRoad(), "Pitch road was incorrect");
        assertEquals(50, pitchResult.getPrice().getPrice(), "Pitch price was incorrect");
        assertEquals("Nord", pitchResult.getSection(), "Pitch section was incorrect");
        assertEquals(101, pitchResult.getSiteNumber(), "Pitch site number was incorrect");
        assertEquals(100, pitchResult.getFee(), "Pitch fee was incorrect");

        // Assertions for cabinResult
        assertEquals(2, cabinResult.getId(), "Cabin ID was incorrect");
        assertEquals("Ahornvej", cabinResult.getRoad(), "Cabin road was incorrect");
        assertEquals(50, cabinResult.getPrice().getPrice(), "Cabin price was incorrect");
        assertEquals("Vest", cabinResult.getSection(), "Cabin section was incorrect");
        assertEquals(102, cabinResult.getSiteNumber(), "Cabin site number was incorrect");
        assertEquals(1000, cabinResult.getDeposit(), "Cabin deposit was incorrect");
        assertEquals(10, cabinResult.getMaxPeople(), "Cabin max people was incorrect");


    }

}
