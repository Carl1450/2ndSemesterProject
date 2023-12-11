package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Product;

public class ProductDAO {

	private ConnectionEnvironment env;

	public ProductDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public Product findProductByBarcode(int barcode) {
		Product foundProduct = null;

		String findProductByBarcodeQ = "SELECT prod.barcode, prod.name, prod.stockNO, [Price].price"
				+ "from Product prod left join [Price] on prod.barcode = [Price].productId where prod.barcode = ?";

		try (Connection connection = DBConnection.getConnection(env)) {
			PreparedStatement prepStatement = connection.prepareStatement(findProductByBarcodeQ);
			prepStatement.setInt(1, barcode);
			ResultSet rs = prepStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				int stockNumber = rs.getInt("stockNO");
				int price = rs.getInt("price");

				foundProduct = new Product(barcode, name, stockNumber);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundProduct;
	}

}
