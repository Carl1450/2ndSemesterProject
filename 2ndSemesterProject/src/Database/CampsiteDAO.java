package Database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;

public class CampsiteDAO {

    ConnectionEnvironment env;

    public CampsiteDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public CampsiteDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public List<Campsite> getAvailableCampsites(Date startDate, Date endDate) {
        try {
            validateDate(startDate);
            validateDate(endDate);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        List<Campsite> campsiteList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance(env).getConnection()) {
            String query =
                    "SELECT " +
                            "c.id, c.section, c.road, c.siteNo, c.[type], " +
                            "cab.maxpeople, cab.deposit, " +
                            "p.fee, " +
                            "pr.price, pr.effectiveDate " +
                            "FROM Campsite c " +
                            "LEFT JOIN Cabin cab ON c.id = cab.id " +
                            "LEFT JOIN Pitch p ON c.id = p.id " +
                            "LEFT JOIN Price pr ON c.id = pr.campsiteId " +
                            "WHERE NOT EXISTS (" +
                            "SELECT 1 " +
                            "FROM Booking b " +
                            "WHERE b.campsiteId = c.id " +
                            "AND b.startDate <= ? " +
                            "AND b.endDate >= ?" +
                            ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, endDate);
                preparedStatement.setDate(2, startDate);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {

                        Campsite campsite = CampsiteFactory.getCampsite(resultSet);

                        campsiteList.add(campsite);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return campsiteList;
    }


    private void validateDate(Date date) throws InvalidDateException {
        if (date == null) {
            throw new InvalidDateException();
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.format(date);
        } catch (IllegalArgumentException e) {
            throw new InvalidDateException();

        }
    }

}
