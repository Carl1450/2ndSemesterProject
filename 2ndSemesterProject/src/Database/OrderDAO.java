package Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Order;
import Model.OrderLine;

public class OrderDAO {

    private ConnectionEnvironment env;
    String insertOrderQ = "INSERT INTO Order(date, totalPrice, employeeId) VALUES (?, ?, ?);";
    String insertOrderLineQ = "INSERT INTO OrderLine(orderId, productId, quantity) VALUES (?, ?, ?);";
    
    public OrderDAO(ConnectionEnvironment env) {
        this.env = env;
        
    }
    
    public boolean saveOrder(Order order) throws SQLException {
    	Connection connection = DBConnection.getConnection(env);
    	try(PreparedStatement prepStat = connection.prepareStatement(insertOrderQ)) {
    		prepStat.setDate(1, order.getDate());
    		prepStat.setFloat(2, order.getTotalPrice());
    		prepStat.setInt(3, order.getEmployee().getId());
    		
    		int result = prepStat.executeUpdate();
    		
    		boolean orderInserted = false;
    		if(result > 0) {
    			orderInserted = true;
    			//saveOrderlines(order.getOrderLines());
    		}
            return orderInserted;
    	}    	
    }
    
    public boolean saveOrderLines(Connection connection, ArrayList<OrderLine> orderLines) {
    	
		return false;
    	
    }
}
