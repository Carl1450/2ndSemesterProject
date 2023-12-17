package Tests.DatabaseTests;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Database.ConnectionEnvironment;
import Database.DBConnection;
import Database.TaskDAO;
import Model.Janitor;
import Model.Receptionist;
import Model.Task;

class TestTaskDAO {

    @BeforeEach
    void setUp() throws Exception {
	deleteMockDataFromDatabase();
	insertMockDataInDatabase();
    }

    void insertMockDataInDatabase() {
	String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

	String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

	String mockReceptionistInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
		+ "VALUES (1, '', '', '', '', '', '', '', 1);";

	String mockFirstJanitorInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
		+ "VALUES (2, '', '', '', '', '', '', '', 1);";

	String mockSecondJanitorInsertQuery = "INSERT INTO Employee (id, fname, lname, email, phoneno, role, cprno, password, addressid) "
		+ "VALUES (3, '', '', '', '', '', '', '', 1);";

	String mockFirstTaskInsertQuery = "INSERT INTO Task (id, priority, [description], deadline, completed, receptionistId, janitorId) "
		+ "VALUES (1, 2, 'A task', '2023-12-12', 0, 1, 2);";

	String mockSecondTaskInsertQuery = "INSERT INTO Task (id, priority, [description], deadline, completed, receptionistId, janitorId) "
		+ "VALUES (2, 4, 'A task', '2023-12-12', 0, 1, 3);";

	try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)) {
	    try {
		DBConnection.startTransaction(connection);

		DBConnection.executeUpdate(connection, mockCityInsertQuery);

		DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

		DBConnection.executeUpdateWithIdentityInsert(connection, mockReceptionistInsertQuery, "Employee");

		DBConnection.executeUpdateWithIdentityInsert(connection, mockFirstJanitorInsertQuery, "Employee");

		DBConnection.executeUpdateWithIdentityInsert(connection, mockSecondJanitorInsertQuery, "Employee");

		DBConnection.executeUpdateWithIdentityInsert(connection, mockFirstTaskInsertQuery, "Task");

		DBConnection.executeUpdateWithIdentityInsert(connection, mockSecondTaskInsertQuery, "Task");

		DBConnection.commitTransaction(connection);
	    } catch (Exception e) {
		DBConnection.rollbackTransaction(connection);
		e.printStackTrace();
	    } finally {
		DBConnection.closeConnection(connection);
	    }

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @AfterEach
    void tearDown() throws Exception {
	deleteMockDataFromDatabase();
    }

    void deleteMockDataFromDatabase() {
	String deleteMockDataQuery = "DELETE FROM Task; " + "DELETE FROM Employee;" + "DELETE FROM Address; "
		+ "DELETE FROM City";

	try (Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING)) {
	    DBConnection.executeUpdate(connection, deleteMockDataQuery);
	    DBConnection.closeConnection(connection);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Test
    void acquiresUncompletedTasksSuccesfully() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(0, false);

	// Assert
	assertTrue(listOfTasks.size() == 2);
    }

    @Test
    void acquiresSpecificJanitorTasksSuccessfully() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(2, true);
	ArrayList<Task> secondListOfTasks = SUT.getUncompletedTasks(3, true);

	// Assert
	assertTrue(listOfTasks.get(0).getPriority() == 2);
	assertTrue(secondListOfTasks.get(0).getPriority() == 4);
    }

    @Test
    void returnsNothingWhenNoTasksFound() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(1, true);

	// Assert
	assertTrue(listOfTasks.size() == 0);
    }

    @Test
    void finishesTaskSuccessfully() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	boolean result = SUT.finishTask(1);
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(0, false);

	// Assert
	assertTrue(listOfTasks.size() == 1);
	assertTrue(result);
    }

    @Test
    void returnFalseIfTaskDoesNotExist() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	boolean result = SUT.finishTask(25);
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(0, false);

	// Assert
	assertTrue(listOfTasks.size() == 2);
	assertFalse(result);
    }

    @Test
    void taskIsSavedCorrectly() {
	// Arrange
	TaskDAO SUT = new TaskDAO(ConnectionEnvironment.TESTING);

	// Act
	Date deadLine = new Date(1000);
	Date startDate = new Date(5000000);
	Receptionist receptionist = new Receptionist(1, "", null, "", "", "", "");
	Janitor janitor = new Janitor(2, "", null, "", "", "", "");

	Task task = new Task(5, "", 2, deadLine, receptionist, startDate);
	task.setJanitors(janitor);
	SUT.saveTask(task);
	ArrayList<Task> listOfTasks = SUT.getUncompletedTasks(0, false);

	// Assert
	assertTrue(listOfTasks.size() == 3);
    }
}
