package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import Model.Price;
import Model.Product;

public class ProductDAO {

	private ConnectionEnvironment env;

	public ProductDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public Product findProductByBarcode(int barcode) {
		Product foundProduct = null;

		String findProductByBarcodeQ = "SELECT prod.barcode, prod.name, prod.stockNO, [Price].price, [Price].effectiveDate"
				+ " FROM Product prod LEFT JOIN [Price] ON prod.barcode = [Price].productId WHERE prod.barcode = ?";

		try (Connection connection = DBConnection.getConnection(env)) {
			PreparedStatement prepStatement = connection.prepareStatement(findProductByBarcodeQ);
			prepStatement.setInt(1, barcode);
			ResultSet rs = prepStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				int stockNumber = rs.getInt("stockNO");
				float price = rs.getFloat("price");
				Date effectiveDate = rs.getDate("effectiveDate");
				
				Price productPrice = new Price(price, effectiveDate);

				foundProduct = new Product(barcode, name, stockNumber, productPrice);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundProduct;
	}
	
	public boolean saveProduct(int barcode, String name, int stockNumber) {
		
		Connection conn = DBConnection.getConnection(env);
		String insertProductQ = "INSERT INTO Product(barcode, name, stockNO) VALUES (?, ?, ?);";
		int rowsAffected = 0;
		
		try {
			PreparedStatement prepStat = conn.prepareStatement(insertProductQ);
            prepStat.setInt(1, barcode);
            prepStat.setString(2, name);
            prepStat.setInt(3, stockNumber);
            
            rowsAffected = prepStat.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		return rowsAffected > 0;
	}

}
