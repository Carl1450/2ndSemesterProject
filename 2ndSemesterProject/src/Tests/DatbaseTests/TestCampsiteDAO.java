package Tests.DatbaseTests;

import Database.BookingDAO;
import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Model.Booking;
import Model.Campsite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        CampsiteDAO SUT = new CampsiteDAO();

        // Reformatted date strings
        String startDateString = "2023-01-01";
        String endDateString = "2023-01-07";

        // Convert string directly to java.sql.Date
        Date validStartDate = Date.valueOf(startDateString);
        Date validEndDate = Date.valueOf(endDateString);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(validStartDate, validEndDate);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void TS_3_TC_2_empty_list_is_returned_when_there_are_no_available_campsites() {

    }

    @Test
    void TS_4_TC_1_method_throws_InvalidDateException_when_given_invalid_dates() {

    }


}
