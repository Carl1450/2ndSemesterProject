package Tests.DatbaseTests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

	
	@BeforeEach
	void setUp() {
		Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();
		String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";
		String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

		String mockEmployeeInsertQuery = "INSERT INTO [Employee] (id, fname, lname, email, phoneno, role, cprno, Password, addressid) "
				+ "VALUES (1,'kurt', 'Den' ,'ansat@mail.com', '+45 22258181','admin', 11198-4575, 'password1','1');";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(mockCityInsertQuery);
			statement.executeUpdate("SET IDENTITY_INSERT [Address] ON");
			statement.executeUpdate(mockAddressInsertQuery);
			statement.executeUpdate("SET IDENTITY_INSERT [Address] OFF");
			statement.executeUpdate("SET IDENTITY_INSERT [Employee] ON");
			statement.executeUpdate(mockEmployeeInsertQuery);
			statement.executeUpdate("SET IDENTITY_INSERT [Employee] OFF");

			} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error setting up mock data", e);
		}
		
	}

	

	@Test
	void TS_4_TC_1_find_Employee_by_password_with_valid_number() {
		// Arrange
		TestEmployeeDAO SUT = new TestEmployeeDAO();
		String Password = "password1";
		
		// Act
		boolean result = SUT.findEmployeeByPassword(Password);
		
			 Password = String.valueOf(Password);
			
		// Assert
		assertNotNull(result);
	}

	@Test
	void TS_4_TC_1_find_Employee_by_password_with_invalid_number() {
		// Arrange
		TestEmployeeDAO SUT = new TestEmployeeDAO();
		String Password =""; 
		
		 boolean result = SUT.findEmployeeByPassword(Password);
 
		// Assert
		assertFalse(result) ;
	}

	private boolean findEmployeeByPassword(String Password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Test
	void TS_4_TC_1_find_Employee_by_password_with_number_null() {
		// Arrange
		TestEmployeeDAO SUT = new TestEmployeeDAO();
		String Password = null;

		// Act
		boolean result = SUT.findEmployeeByPassword(Password);
		
		// Assert
		assertFalse(result);
	}

	@AfterEach
	void tearDown1() {
		Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

		String deleteMockDataQuery = " DELETE FROM Employee;  DELETE FROM Address; DELETE FROM City";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(deleteMockDataQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterEach
	void tearDown() {
		Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

		// Delete in reverse order of creation
		String deleteMockDataQuery = "DELETE FROM password WHERE password = 1; "
				+ "DELETE FROM [password] WHERE password = 1; ";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(deleteMockDataQuery);
		} catch (SQLException e) {
			
		}
	}
}
