package Tests.DatbaseTests;

import Database.BookingDAO;
import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Database.InvalidDateException;
import Model.Booking;
import Model.Campsite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestCampsiteDAO {


    @BeforeEach
    void setUp() {
        // Initialize resources or perform any necessary setup
    }

    @AfterEach
    void tearDown() {
        // Clean up resources or perform any necessary teardown
    }

    @Test
    void TS_3_TC_1_campsites_are_returned_when_given_valid_dates() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO();  // TODO make it so that CampsiteDAO can take the ConnectionEnvironment.TESTING

        // Reformatted date strings
        String startDateString = "2023-01-01";
        String endDateString = "2023-01-07";

        // Convert string directly to java.sql.Date
        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void TS_3_TC_2_empty_list_is_returned_when_there_are_no_available_campsites() {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO();  // TODO make it so that CampsiteDAO can take the ConnectionEnvironment.TESTING

        // Reformatted date strings
        String startDateString = "2023-01-01";
        String endDateString = "2023-12-31";

        // Convert string directly to java.sql.Date
        Date startDate = Date.valueOf(startDateString);
        Date endDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate);

        // Assert
        assertEquals(0, result.size());
    }

    @ParameterizedTest
    @CsvSource({
            "abc, cba",
            "'NULL', 'NULL'" // Using 'NULL' as a placeholder for null
    })
    void TS_4_TC_1_method_throws_InvalidDateException_when_given_invalid_dates(String startDateString, String endDateString) {
        // Arrange
        CampsiteDAO SUT = new CampsiteDAO(); // TODO: Adjust CampsiteDAO for testing environment

        // Act & Assert
        assertThrows(InvalidDateException.class, () -> {
            Date startDate = (startDateString != null && startDateString.equalsIgnoreCase("NULL")) ? Date.valueOf(startDateString)  : null;
            Date endDate = (endDateString != null && endDateString.equalsIgnoreCase("NULL")) ? Date.valueOf(endDateString) : null;
            SUT.getAvailableCampsites(startDate, endDate);
        });
    }


}
