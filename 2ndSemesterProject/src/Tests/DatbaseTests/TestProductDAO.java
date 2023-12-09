package Tests.DatbaseTests;

import Database.ConnectionEnvironment;
import Database.DBConnection;
import Database.ProductDAO;
import Model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestProductDAO {

    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();
        insertMockDataIntoDatabase();
    }

    void insertMockDataIntoDatabase() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        String insertQuery = "INSERT INTO Product (barcode, name, stockNO) VALUES (1, Test, 10);";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    void deleteMockDataFromDatabase() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        String insertQuery = "DELETE FROM Product;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    void test_valid_product_is_returned_when_product_is_found() {
        // Arrange
        ProductDAO SUT = new ProductDAO(ConnectionEnvironment.TESTING);
        int validBarcode = 1;

        // Act
        Product result = SUT.findProductByBarcode(validBarcode);

        // Assert
        assertNotNull(result);

    }

    @Test
    void test_null_returned_when_no_product_is_found() {
        // Arrange
        ProductDAO SUT = new ProductDAO(ConnectionEnvironment.TESTING);
        int invalidBarcode = -1;

        // Act
        Product result = SUT.findProductByBarcode(invalidBarcode);

        // Assert
        assertNull(result);
    }

}
