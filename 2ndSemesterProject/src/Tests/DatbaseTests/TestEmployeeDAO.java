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
        deleteMockData();
        insertMockData();
    }

    void insertMockData() {
        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        String mockAddressInsertQuery = "INSERT INTO [Address] (id, street, streetno, zipcode) VALUES (1, 'Bredgade', 30, 1000);";

        String mockCityInsertQuery = "INSERT INTO City (zipCode, city) VALUES (1000, 'Copenhagen');";

        String mockEmployeeInsertQuery = "INSERT INTO [Employee] (id, fname, lname, email, phoneno, role, cprno, Password, addressid) "
                + "VALUES (1,'kurt', 'Den' ,'ansat@mail.com', '+45 22258181','admin', 11198-4575, 'password1','1');";


        try {
            DBConnection.startTransaction(connection);

            DBConnection.executeUpdate(connection, mockCityInsertQuery);

            DBConnection.executeUpdateWithIdentityInsert(connection, mockAddressInsertQuery, "[Address]");

            DBConnection.executeUpdateWithIdentityInsert(connection, mockEmployeeInsertQuery, "Employee");

            DBConnection.commitTransaction(connection);

        } catch (SQLException e) {
            try {
                DBConnection.rollbackTransaction(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection);
        }


    }

    @AfterEach
    void tearDown() {
        deleteMockData();

    }

    void deleteMockData() {
        String deleteMockDataQuery = "DELETE FROM Employee; DELETE FROM [Address]; DELETE FROM [City];";

        Connection connection = DBConnection.getConnection(ConnectionEnvironment.TESTING);

        DBConnection.executeUpdate(connection, deleteMockDataQuery);

        DBConnection.closeConnection(connection);
    }

    @Test
    void TS_4_TC_1_find_Employee_by_id_with_valid_id() {

        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);

        // Act
        Employee result = SUT.findEmployeeById(1);

        // Assert
        assertNotNull(result);
    }

    @Test
    void TS_4_TC_1_find_Employee_by_id_with_invalid_id() {

        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);

        Employee result = SUT.findEmployeeById(2);

        // Assert
        assertNull(result);
    }

    @Test
    void TS_4_TC1_find_Employee_by_id_with_id_null() {

        // Arrange
        EmployeeDAO SUT = new EmployeeDAO(ConnectionEnvironment.TESTING);

        // Act
        Employee result = SUT.findEmployeeById(0);

        // Assert
        assertNull(result);

    }
}
