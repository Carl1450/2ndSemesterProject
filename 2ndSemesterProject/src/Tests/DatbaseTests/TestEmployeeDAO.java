package Tests.DatbaseTests;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Database.ConnectionEnvironment;
import Database.DBConnection;

class TestEmployeeDAO {

	
	public class EmployeeDAO {

	    @BeforeEach
	    void setUp() {
	        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

	      
	        String mockEmployeeInsertQuery = "INSERT INTO employee (password ) VALUES ('password1');";

	       
	        try {
	            Statement statement = connection.createStatement();
	            statement.executeUpdate(mockEmployeeInsertQuery); 
	        } catch (SQLException e) {
	            throw new RuntimeException("Error setting up mock data", e);
	        }
	    }
	    
	    @AfterEach
	    void tearDown() {
	        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

	        // Delete in reverse order of creation
	        String deleteMockDataQuery =
	                "DELETE FROM password WHERE password = 1; " +
	                        "DELETE FROM [password] WHERE password = 1; " ;
	                        

	        try {
	            Statement statement = connection.createStatement();
	            statement.executeUpdate(deleteMockDataQuery);
	        } catch (SQLException e) {
	            throw new RuntimeException("Error cleaning up mock data", e);
	        }
	    }
 @Test
	    void TS_4_TC_1_find_Employee_by_password_with_valid_number() {
	        // Arrange
	        TestEmployeeDAO SUT = new TestEmployeeDAO();
	        String Password = "password1";

	        // Act
	        String result = SUT.findEmployeeByPassword(Password);

	        // Assert
	        assertNotNull(result);
	    }
	@Test
	    void TS_4_TC_1_find_Employee_by_password_with_invalid_number() {
	        // Arrange
	        TestEmployeeDAO SUT = new TestEmployeeDAO();
	        String Password = "";

	        // Act
	       String result =  SUT.findEmployeeByPassword(Password);

	        // Assert
	        assertNull(result);
	    }
	}
	@Test
    void TS_4_TC_1_find_Employee_by_password_with_number_null() {
        // Arrange
        TestEmployeeDAO SUT = new TestEmployeeDAO();
        String Password = null;

        // Act
       String result =  SUT.findEmployeeByPassword(Password);

        // Assert
        assertNull(result);
    }
	private String findEmployeeByPassword(String password) {
		// TODO Auto-generated method stub
		return password;
	}


	

}

