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
            connection.setAutoCommit(false);

            String mockCampsiteInsertQuery = "INSERT INTO Campsite (section, road, siteNo, [type]) VALUES ('Nord', 'Egevej', 1, 'Pitch'), ('Vest', 'Ahornvej', 2, 'Cabin');";
            String mockPitchInsertQuery = "INSERT INTO Pitch (siteNo, fee) VALUES (1, 100);";
            String mockCabinInsertQuery = "INSERT INTO Cabin (siteNo, maxpeople, deposit) VALUES (2, 10, 1000);";

            String mockPriceInsertQuery = "INSERT INTO Price (id, price, effectiveDate, campsiteSiteNo) VALUES (1, 50, '2023-01-01', 1), (2, 50, '2023-01-01', 2);";

            try (Statement statement = connection.createStatement()) {


                statement.executeUpdate(mockCampsiteInsertQuery);

                statement.executeUpdate(mockCabinInsertQuery);

                statement.executeUpdate(mockPitchInsertQuery);

                statement.executeUpdate("SET IDENTITY_INSERT Price ON");
                statement.executeUpdate(mockPriceInsertQuery);
                statement.executeUpdate("SET IDENTITY_INSERT Price OFF");
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
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
            connection.setAutoCommit(false);

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

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void test_campsiteFactory_returns_expected_object() {
        // Arrange
        String selectCampsitesQuery = "SELECT c.siteNo, c.section, c.road, c.siteNo, c.[type], cab.maxpeople, cab.deposit, p.fee, pr.price, pr.effectiveDate " +
                "FROM Campsite c " +
                "LEFT JOIN Cabin cab ON c.siteNo = cab.siteNo " +
                "LEFT JOIN Pitch p ON c.siteNo = p.siteNo " +
                "LEFT JOIN Price pr ON c.siteNo = pr.campsiteSiteNo ";

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
        assertEquals(1, pitchResult.getSiteNumber(), "Pitch SiteNumber was incorrect");
        assertEquals("Egevej", pitchResult.getRoad(), "Pitch road was incorrect");
        assertEquals(50, pitchResult.getPrice().getPrice(), "Pitch price was incorrect");
        assertEquals("Nord", pitchResult.getSection(), "Pitch section was incorrect");
        assertEquals(100, pitchResult.getFee(), "Pitch fee was incorrect");

        // Assertions for cabinResult
        assertEquals(2, cabinResult.getSiteNumber(), "Cabin SiteNumber was incorrect");
        assertEquals("Ahornvej", cabinResult.getRoad(), "Cabin road was incorrect");
        assertEquals(50, cabinResult.getPrice().getPrice(), "Cabin price was incorrect");
        assertEquals("Vest", cabinResult.getSection(), "Cabin section was incorrect");
        assertEquals(1000, cabinResult.getDeposit(), "Cabin deposit was incorrect");
        assertEquals(10, cabinResult.getMaxPeople(), "Cabin max people was incorrect");


    }


    @Test
    void test_campsiteFactory_returns_null_when_no_campsite_are_given() {
        // Arrange
        String selectCampsitesQuery = "SELECT c.siteNo, c.section, c.road, c.siteNo, c.[type], cab.maxpeople, cab.deposit, p.fee, pr.price, pr.effectiveDate " +
                "FROM Campsite c " +
                "LEFT JOIN Cabin cab ON c.siteNo = cab.siteNo " +
                "LEFT JOIN Pitch p ON c.siteNo = p.siteNo " +
                "LEFT JOIN Price pr ON c.siteNo = pr.campsiteSiteNo " +
                "WHERE c.siteNo = -1";


        Object campsiteResult = new Object();

        // Act
        try {
            Statement statement = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectCampsitesQuery);

            resultSet.next();
            campsiteResult = CampsiteFactory.getCampsite(resultSet);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertNull(campsiteResult);


    }

}
