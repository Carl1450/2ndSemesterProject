package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
	
	private ArrayList<OrderLine> orderLines;
	private Date date;
	private float totalPrice;
	private Employee employee;
	private Customer customer;

	public Order(ArrayList<OrderLine> orderLines, Date date, float totalPrice, Employee employee, Customer customer) {
		this.orderLines = orderLines;
		this.date = date;
		this.totalPrice = totalPrice;
		this.employee = employee;
		this.setCustomer(customer);
	}
	
	public Order(Employee employee) {
		this.employee = employee;
	}
	
	public void addOrderLine(OrderLine orderLine) {
		orderLines.add(orderLine);
	}
	
	public ArrayList<OrderLine> getOrderLines(){
		return orderLines;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public float getTotalPrice() {
		return totalPrice;
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
	
	
}
