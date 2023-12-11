package Tests.DatbaseTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Database.ConnectionEnvironment;
import Database.DBConnection;
import Database.OrderDAO;
import Model.Admin;
import Model.Employee;
import Model.Order;
import Model.OrderLine;

public class TestOrderDAO {

    @BeforeEach
    void setUp() {
    	deleteMockDataFromDatabase();
    	insertMockDataInDatabase();
    }
    
    void insertMockDataInDatabase() {
    	String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";
    	
    	String mockProductInsertQuery = "INSERT INTO Product (barcode, name, stockNO) VALUES (1111, '', 50);";
    	
    	String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES "
                 + "(1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";
    	
    	String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
                + "VALUES (1, '', '', '', '', '', '', '', 1);";
    	
    	String mockOrderInsertQuery = "INSERT INTO [Order] (id, date, totalPrice, customerId, employeeId) "
    			+ "VALUES (1, '', '100', 1, 1);";
    	
    	String mockOrderLineInsertQuery = "INSERT INTO OrderLine (orderId, productId, quantity) VALUES (1 ,1111, 5);";
    	

    	try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)){
            DBConnection.startTransaction(connection);
            
            DBConnection.executeUpdate(connection, mockCityInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

            DBConnection.executeUpdate(connection, mockProductInsertQuery);
            
            DBConnection.executeUpdateWithIdentityInsert(connection, mockCustomerInsertQuery, "Customer");
            
            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockOrderInsertQuery, "[Order]");
            
            DBConnection.executeUpdate(connection, mockOrderLineInsertQuery);

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
        String deleteMockDataQuery = "DELETE FROM OrderLine; "
        		+ "DELETE FROM [Order]; "
        		+ "DELETE FROM Employee; "
        		+ "DELETE FROM Customer; "
        		+ "DELETE FROM Product; "
        		+ "DELETE FROM [Address]; "
        		+ "DELETE FROM City;";

        try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)) {
            DBConnection.executeUpdate(connection, deleteMockDataQuery);
            DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_that_save_order_persist_successfully() throws SQLException {
    	 // Arrange
        OrderDAO SUT = new OrderDAO(ConnectionEnvironment.TESTING);

        Date date = new Date(1000);
        float totalPrice = 5000;
        Employee employee = new Admin(1, "", "", "", "",
                "", "");
        OrderLine orderLine = new OrderLine(null, 5);
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
      

        Order mockOrder = new Order(orderLines, date, totalPrice, employee);

        // Act
        Boolean result = SUT.saveOrder(mockOrder);

        // Assert
        assertTrue(result);
    }
}
