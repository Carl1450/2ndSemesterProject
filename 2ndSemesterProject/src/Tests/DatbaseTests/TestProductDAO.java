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

        String insertQuery = "INSERT INTO Product (barcode, name, stockNO) VALUES (1, 'Test', 10);";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        DBConnection.executeUpdate(connection, insertQuery);

        DBConnection.closeConnection(connection);
    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    void deleteMockDataFromDatabase() {

        String insertQuery = "DELETE FROM Product;";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        DBConnection.executeUpdate(connection, insertQuery);

        DBConnection.closeConnection(connection);

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
    
    @Test
    void test_null_returned_when_barcode_is_empty() {
    	// Arrange
    	ProductDAO SUT = new ProductDAO(ConnectionEnvironment.TESTING);
    	int emptyBarcode = 0;
    	
    	// Act
    	Product result = SUT.findProductByBarcode(emptyBarcode);
    	
    	// Assert
    	assertNull(result);
    }

}
