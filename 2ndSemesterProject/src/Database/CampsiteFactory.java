package Database;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;

import java.sql.*;

public class CampsiteFactory {

    public static Campsite getCampsite(ResultSet resultSet) {

        Campsite campsite = null;

        try {

            String type = resultSet.getNString("type");

            switch (type.toLowerCase()) {
                case "cabin":
                    campsite = createCabin(resultSet);
                    break;
                case "pitch":
                    campsite = createPitch(resultSet);
                    break;
            }

        } catch (SQLException e) {
        }


        return campsite;
    }


    private static Campsite createCabin(ResultSet resultSet) {

        Campsite cabin = null;

        try {
            int siteNo = resultSet.getInt("siteNo");
            String section = resultSet.getNString("section");
            String road = resultSet.getNString("road");

            float priceAmount = resultSet.getFloat("price");
            Date effectiveDate = resultSet.getDate("effectiveDate");

            int maxPeople = resultSet.getInt("maxPeople");
            float deposit = resultSet.getFloat("deposit");

            cabin = new Cabin(siteNo, section, road, new Price(priceAmount, effectiveDate), maxPeople, deposit);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cabin;
    }

    private static Campsite createPitch(ResultSet resultSet) {
        Campsite pitch = null;

        try {
            int siteNo = resultSet.getInt("siteNo");
            String section = resultSet.getNString("section");
            String road = resultSet.getNString("road");

            float priceAmount = resultSet.getFloat("price");
            Date effectiveDate = resultSet.getDate("effectiveDate");

            float fee = resultSet.getFloat("fee");

            pitch = new Pitch(siteNo, section, road, new Price(priceAmount, effectiveDate), fee);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pitch;

    }


}
