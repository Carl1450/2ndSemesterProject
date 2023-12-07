package Tests.DatbaseTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Database.ConnectionEnvironment;
import Database.DBConnection;
import Database.EmployeeDAO;
import Model.Employee;

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
    void TS_4_TC_1_find_Employee_by_id_with_valid_id() {
        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);
        String id = "1";
        int idInt = Integer.parseInt(id);

        // Act
        Employee result = SUT.findEmployeeById(idInt);

        id = String.valueOf(id);

        // Assert
        assertNotNull(result);
    }

    @Test
    void TS_4_TC_1_find_Employee_by_id_with_invalid_id() {
        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);
        int id = 2;

        Employee result = SUT.findEmployeeById(id);

        // Assert
        assertNull(result);
    }

    @Test
    void TS_4_TC_1_find_Employee_by_id_with_id_null() {
        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);
        int id = 0;

        // Act
        Employee result = SUT.findEmployeeById(id);

        // Assert
        assertNull(result);
    }

    @AfterEach
    void tearDown() {
        Connection connection = DBConnection.getInstance(ConnectionEnvironment.TESTING).getConnection();

        // Delete in reverse order of creation
        String deleteMockDataQuery = "DELETE FROM Employee WHERE id = 1; " + "DELETE FROM [Address] WHERE id = 1; "
                + "DELETE FROM [City] WHERE zipcode = 1000; ";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteMockDataQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}


