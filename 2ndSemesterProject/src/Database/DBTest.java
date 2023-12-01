package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {
	
	public static void main(String[] args) {
		
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "SELECT * FROM test_table";
		try {
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
