package Tests.ControllerTests;

import Control.TaskController;
import Database.ConnectionEnvironment;
import Database.DBConnection;
import Model.Employee;
import Model.Janitor;
import Model.Receptionist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskController {

    @BeforeEach
    void setUp() {
        deleteMockDataFromDatabase();
        insertMockDataInDatabase();
    }

    void insertMockDataInDatabase() {
        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";
        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";
        String mockEmployeeInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, [role], cprNo, password, addressId) VALUES (1, 'Anne', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Receptionist', '0101901234', 'password1', 1), (2, 'Bo', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Janitor', '0101901235', 'password2', 1), (3, 'Poul', 'Nielsen', 'anne.nielsen@email.com', '+45 87654321', 'Janitor', '0101901235', 'password2', 1);";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        try {
            DBConnection.startTransaction(connection);

            DBConnection.executeUpdate(connection, mockCityInsertQuery);
            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");
            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.commitTransaction(connection);

            DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            try {
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        deleteMockDataFromDatabase();
    }

    void deleteMockDataFromDatabase() {
        String deleteQuery = "DELETE FROM Task; DELETE FROM Booking; DELETE FROM Employee; DELETE FROM [Address]; DELETE FROM City;";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        try {
            DBConnection.executeUpdate(connection, deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBConnection.closeConnection(connection);
    }


    @Test
    void test_finds_all_janitors() {
        // Arrange
        TaskController SUT = new TaskController(ConnectionEnvironment.TESTING);

        // Act
        List<Janitor> janitors = SUT.getAllJanitors();

        // Assert
        assertEquals(2, janitors.size());
        for (Employee employee: janitors) {
            assertInstanceOf(Janitor.class, employee);
        }
    }

    @Test
    void test_create_and_save_task_whole_process() {
        // Arrange
        TaskController SUT = new TaskController(ConnectionEnvironment.TESTING);

        String description = "Test";
        int priority = 1;
        Date deadline = Date.valueOf("2023-01-01");
        Employee receptionist = new Receptionist(1, null, null, null, null, null, null) ;
        Janitor janitor = new Janitor(2, null, null, null, null, null, null);

        // Act
        SUT.createTask(description, priority, receptionist, deadline);
        SUT.assignJanitorToTask(janitor);

        Boolean result = SUT.finishTask();

        // Assert
        assertTrue(result);
    }

    @Test
    void test_cant_save_invalid_task() {
        // Arrange
        TaskController SUT = new TaskController(ConnectionEnvironment.TESTING);

        String description = null;
        int priority = -1;
        Date deadline = null;
        Employee receptionist = null;
        Janitor janitor = null;

        // Act
        SUT.createTask(description, priority, receptionist, Date.valueOf("2023-01-01"));
        SUT.assignJanitorToTask(janitor);

        Boolean result = SUT.finishTask();

        // Assert
        assertFalse(result);
    }
}
