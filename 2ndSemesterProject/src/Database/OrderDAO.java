package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Order;
import Model.OrderLine;
import Model.Product;

public class OrderDAO {

	private ConnectionEnvironment env;
	String insertOrderQ = "INSERT INTO [Order](date, totalPrice, customerId, employeeId) VALUES (?, ?, ?, ?);";
	String insertOrderLineQ = "INSERT INTO OrderLine(orderId, productId, quantity) VALUES (?, ?, ?);";

	public OrderDAO(ConnectionEnvironment env) {
		this.env = env;
	}

	public boolean saveOrder(Order order) throws SQLException {
		Connection connection = DBConnection.getConnection(env);
		
		try (PreparedStatement prepStat = connection.prepareStatement(insertOrderQ, PreparedStatement.RETURN_GENERATED_KEYS)) {
			prepStat.setDate(1, order.getDate());
			prepStat.setFloat(2, order.getTotalPrice());
			prepStat.setInt(3, order.getCustomer().getCustomerId());
			prepStat.setInt(4, order.getEmployee().getId());

			int result = prepStat.executeUpdate();

			boolean orderInserted = false;
			if (result > 0) {
				orderInserted = true;
				try (ResultSet generatedKeys = prepStat.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int orderId = generatedKeys.getInt(1);
						saveOrderLines(order.getOrderLines(), orderId);
					}
				}
			}
			return orderInserted;
		}
	}

	public boolean saveOrderLines(ArrayList<OrderLine> orderLines, int orderId) throws SQLException {
		try (Connection connection = DBConnection.getConnection(env);) {
			PreparedStatement prepStat = connection.prepareStatement(insertOrderLineQ);

			boolean orderLineInserted = false;

			for (OrderLine orderLine : orderLines) {
				prepStat.setInt(1, orderId);
				prepStat.setInt(2, orderLine.getProduct().getBarcode());
				prepStat.setInt(3, orderLine.getQuantity());
				
				int result = prepStat.executeUpdate();

				if (result > 0) {
					orderLineInserted = true;
				}
			}
			return orderLineInserted;
		}

	}
}
