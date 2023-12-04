package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;

public class CampsiteDAO {

	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate) {

		List<Campsite> campsiteList = new ArrayList<>();

		try (Connection connection = DBConnection.getInstance(ConnectionEnvironment.PRODUCTION).getConnection()) {
			String query = "SELECT  id, section, road, siteNo, type FROM Campsite WHERE siteNo NOT IN (SELECT DISTINCT b.siteNo "
					+ "FROM Booking b WHERE (b.startDate <= ? AND b.endDate >= ?))";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setDate(1, startDate);
				preparedStatement.setDate(2, endDate);
				
				try(ResultSet resultSet = preparedStatement.executeQuery()){
					while(resultSet.next()) {
						
						Campsite campsite = null;
						
						int id = resultSet.getInt(1);
						String section = resultSet.getNString(2);
						String road = resultSet.getNString(3);
						int siteNo = resultSet.getInt(4);
						String type = resultSet.getNString(5);
						
						if(type.equals("Cabin")) {
							String cabinQuery = "SELECT maxPeople, deposit FROM Cabin WHERE id = ?";
							PreparedStatement cabinPreparedStatement = connection.prepareStatement(cabinQuery);
							cabinPreparedStatement.setInt(1, id);
							ResultSet cabinResultSet = cabinPreparedStatement.executeQuery();
							
							int maxPeople = cabinResultSet.getInt(1);
							float deposit = cabinResultSet.getFloat(2);
							
							Price price = getPrice(connection, id);
							campsite = new Cabin(section, road, siteNo, price, maxPeople, deposit);
						}
						if(type.equals("Pitch")) {
							String pitchQuery = "SELECT fee FROM Pitch WHERE id = ?";
							PreparedStatement pitchPreparedStatement = connection.prepareStatement(pitchQuery);
							pitchPreparedStatement.setInt(1, id);
							ResultSet pitchResultSet = pitchPreparedStatement.executeQuery();
							
							float fee = pitchResultSet.getFloat(1);
							
							Price price = getPrice(connection, id);
							campsite = new Pitch(section, road, siteNo, price, fee);
						}
						campsiteList.add(campsite);
					}
					
				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return campsiteList;
	}
	
	private Price getPrice(Connection connection, int id) {
		Price price = null;
		try{
			String query = "SELECT price, effectiveDate, campsiteId FROM price WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			float priceAmount = resultSet.getFloat(1);
			Date effectiveDate = resultSet.getDate(2);
			price = new Price(priceAmount, effectiveDate);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return price;
		
	}

}
