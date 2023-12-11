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
import Model.Customer;
import Model.Employee;
import Model.Order;
import Model.OrderLine;
import Model.Price;
import Model.Product;

public class TestOrderDAO {

    @BeforeEach
    void setUp() {
    	deleteMockDataFromDatabase();
    	insertMockDataInDatabase();
    }
    
    void insertMockDataInDatabase() {
    	String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";
    	
    	String mockProductInsertQuery = "INSERT INTO Product (barcode, name, stockNO) VALUES (1001, '', 50);";
    	
    	String mockCustomerInsertQuery = "INSERT INTO Customer (id, fname, lname, email, phoneno, addressId) VALUES "
                 + "(1, 'Jens', 'Hansen', 'jens.hansen@example.com', '+45 12345678', 1);";
    	
    	String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
                + "VALUES (1, '', '', '', '', '', '', '', 1);";
    	
    	String mockOrderInsertQuery = "INSERT INTO [Order] (id, date, totalPrice, customerId, employeeId) "
    			+ "VALUES (1, '2023-01-01', 100, 1, 1);";
    	
    	String mockOrderLineInsertQuery = "INSERT INTO OrderLine (orderId, productId, quantity) VALUES (1 ,1001, 5);";
    	

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
        Price price = new Price(100, date);
        float totalPrice = 100;
        Employee employee = new Admin(1, "", "", "", "",
                "", "");
        Customer customer = new Customer(1, null, null, null, null);
        Product product = new Product(1001, null, 50, price);
        OrderLine orderLine = new OrderLine(product, 5);
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
      

        Order mockOrder = new Order(orderLines, date, totalPrice, employee, customer);

        // Act
        Boolean result = SUT.saveOrder(mockOrder);

        // Assert
        assertTrue(result);
    }
}
