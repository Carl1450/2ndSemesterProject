package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.*;

public class CustomerTableModel extends AbstractTableModel {
	private List<Customer> data;
	private static final String[] COL_NAMES = { "Name", "Phone Number", "Email", "Address", "City", "Zip Code" };

	public CustomerTableModel(List<Customer> customers) {
		super();

		if (customers != null) {
			this.data = customers;
		} else {
			this.data = new ArrayList<>();
		}
	}

	@Override
	public String getColumnName(int col) {
		return COL_NAMES[col];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Customer customer = data.get(rowIndex);
		Address customerAddress = customer.getAddress();
		String res = "";
		if (customer != null) {
			switch (columnIndex) {
			case 0:
				res = customer.getName();
				break;
			case 1:
				res = customer.getPhoneNumber();
				break;
			case 2:
				res = customer.getEmail();
				break;
			case 3:
				res = customerAddress.getStreet() + " " + customerAddress.getStreetNo();
				break;
			case 4:
				res = customerAddress.getCity();
				break;
			case 5:
				res = Integer.toString(customerAddress.getZipCode());
				break;

			default:
				res = "Unknown";
			}
		} else {
			res = "N/A";
		}
		return res;

	}

	public Customer getCustomer(int index) {
		return data.get(index);
	}

	public void setData(List<Customer> data) {
		this.data = data;
		super.fireTableDataChanged();
	}

	public List<Customer> getData() {
		return data;
	}

}
