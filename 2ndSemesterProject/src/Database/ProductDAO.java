package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

	public List<Product> getAllProducts() {

		List<Product> products = new ArrayList<>();

		String findAllProductsQuery = "SELECT prod.barcode, prod.name, prod.stockNO, [Price].price, [Price].effectiveDate"
				+ " FROM Product prod LEFT JOIN [Price] ON prod.barcode = [Price].productId";

		Connection connection = DBConnection.getConnection(env);

        try {

			ResultSet resultSet = DBConnection.executeQuery(connection, findAllProductsQuery);
			while (resultSet.next()) {
				products.add(buildProduct(resultSet));
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
	}

	private Product buildProduct(ResultSet resultSet) {
		Product product = null;

        try {
            int barcode = resultSet.getInt("barcode");
			String name = resultSet.getNString("name");
			int stockNumber = resultSet.getInt("stockNo");

			float priceFloat = resultSet.getFloat("price");
			Date effectiveDate = resultSet.getDate("effectiveDate");

			Price price = new Price(priceFloat, effectiveDate);
			product = new Product(barcode, name, stockNumber, price);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		return product;
    }


	public boolean saveProduct(int barcode, String name, int stockNumber, float price) {
		
		Connection conn = DBConnection.getConnection(env);

		String insertProductQuery = "INSERT INTO Product(barcode, name, stockNO) VALUES (?, ?, ?);";

		String insertPriceQuery = "INSERT INTO Price (price, productId) Values (?, ?);";

		String insertProductAndPriceQuery = insertProductQuery + insertPriceQuery;

		int rowsAffected = 0;
		
		try {
			PreparedStatement prepStat = conn.prepareStatement(insertProductAndPriceQuery);
            prepStat.setInt(1, barcode);
            prepStat.setString(2, name);
            prepStat.setInt(3, stockNumber);

			prepStat.setFloat(4, price);
			prepStat.setInt(5, barcode);
            
            rowsAffected = prepStat.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
        }

		DBConnection.closeConnection(conn);

		return rowsAffected > 0;
	}

	public boolean updateProduct(int oldBarcode, int newBarcode, String newName, int newStockNumber) {

		String updateProductQuery = "UPDATE Product SET barcode = ?, name = ?, stockNo = ? WHERE barcode = ?;";

		int result = 0;

		Connection connection = DBConnection.getConnection(env);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateProductQuery);
			preparedStatement.setInt(1, newBarcode);
			preparedStatement.setNString(2, newName);
			preparedStatement.setInt(3, newStockNumber);
			preparedStatement.setInt(4, oldBarcode);

			result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		DBConnection.closeConnection(connection);

		return result > 0;
    }

	public boolean deleteProduct(Product product) {

		String updateProductQuery = "DELETE FROM Product WHERE barcode = ?;";

		int result = 0;

		Connection connection = DBConnection.getConnection(env);

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateProductQuery);
			preparedStatement.setInt(1, product.getBarcode());

			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		DBConnection.closeConnection(connection);

		return result > 0;
	}

}
