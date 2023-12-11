package Tests.ControllerTests;

import Control.CampsiteController;
import Database.ConnectionEnvironment;
import Model.Campsite;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCampsiteController {


    @Test
    void assert_empty_list_when_enddate_is_earlier_than_startdate() {
        // Arrange
        CampsiteController SUT = new CampsiteController(ConnectionEnvironment.TESTING);
        Date startDate = new Date(500);
        Date endDate = new Date(400);

        // Act
        List<Campsite> result = SUT.getAvailableCampsites(startDate, endDate, true, true);

        // Assert
        assertTrue(result.isEmpty());
    }
}
