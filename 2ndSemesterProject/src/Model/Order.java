package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Order {
	
	private ArrayList<OrderLine> orderLines;
	private Timestamp date;
	private float totalPrice;
	private Employee employee;
	private Customer customer;
	private String formattedDate;

	public Order(ArrayList<OrderLine> orderLines, float totalPrice, Employee employee, Customer customer) {
		this.orderLines = orderLines;
		this.totalPrice = totalPrice;
		this.employee = employee;
		this.customer = customer;
		this.date = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formattedDate = dateFormat.format(date);
	}
	
	public Order(Employee employee) {
		this.employee = employee;
		this.orderLines = new ArrayList<>();
		this.date = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formattedDate = dateFormat.format(date);
	}
	
	public String getFormattedDate() {
		return formattedDate;
	}
	
	public void addOrderLine(OrderLine orderLine) {
		orderLines.add(orderLine);
	}
	
	public ArrayList<OrderLine> getOrderLines(){
		return orderLines;
	}

	public Timestamp getDate() {
		return date;
	}

	public float getTotalPrice() {
		return calculateTotalPrice();
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public float calculateTotalPrice() {
		for(OrderLine orderLine : orderLines) {
			float singlePrice = orderLine.getProduct().getPrice().getPrice();
			totalPrice += singlePrice;
		}
		return totalPrice;
		
	}
}
