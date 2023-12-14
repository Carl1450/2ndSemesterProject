package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Cabin;
import Model.Customer;
import Model.Pitch;
import Model.Price;

public class CustomerTableModel extends AbstractTableModel{
	private List<Customer> data;
	private static final String[] COL_NAMES = { "Name", "Phone Number", "Email", "Address", "Zip Code", "City"};

	public CustomerTableModel(List<Customer> customers) {
		super();

		if (customers != null) {
			this.data = customers;
		} else {
			this.data = new ArrayList<>();
			System.out.println("Warning: Customers list was null. Initializing with an empty list.");
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
		Customer c = data.get(rowIndex);
		String res = "";
		if (c != null) {
			switch (columnIndex) {
			case 0:
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

}
