package Control;

import java.sql.SQLException;

import Database.ConnectionEnvironment;
import Database.OrderDAO;
import Model.Employee;
import Model.Order;
import Model.OrderLine;
import Model.Product;

public class OrderController {

    private ConnectionEnvironment env;
    private OrderDAO orderDAO;
    private Employee currentEmployee;
    private Order currentOrder;
    private ProductController productController;


    public OrderController(Employee employee, ConnectionEnvironment env) {
        this.env = env;
        
        productController = new ProductController(env);
        
        orderDAO = new OrderDAO(env);
        
        currentEmployee = employee;
    }
    
    public Order createOrder() {
    	currentOrder = new Order(currentEmployee);
    	return currentOrder;
    }
    
    public Product findProductByBarcode(int barcode) {
    	return productController.findProductByBarcode(barcode);
    }
    
    public OrderLine createOrderLine(Product product, int quantity) {
    	OrderLine orderLine = new OrderLine(product, quantity);
    	currentOrder.addOrderLine(orderLine);
    	return orderLine;
    }
    
    public void addOrderLine(OrderLine orderLine){
    	currentOrder.addOrderLine(orderLine);
    }
    
    public boolean saveOrder() throws SQLException {
		return orderDAO.saveOrder(currentOrder);
		
    }

}
