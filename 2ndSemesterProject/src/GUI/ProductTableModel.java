package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;
import Model.Product;

public class ProductTableModel extends AbstractTableModel {
	
	private List<Product> data;
	private static final String[] COL_NAMES = { "Name", "Barcode", "Quantity", "Price" };
	
	public ProductTableModel(List<Product> products) {
		super();

		if (products != null) {
			this.data = products;
		} else {
			this.data = new ArrayList<>();
			System.out.println("Warning: Campsites list was null. Initializing with an empty list.");
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
		Product p = data.get(rowIndex);
		String res = "";
		if (p != null) {
			switch (columnIndex) {
			case 0:
				String name = p.getName();
				res = name;
				break;
			case 1:
				int barcode = p.getBarcode();
				res = String.valueOf(barcode);
				break;
			case 2:
				int quantity = 1;
				res = String.valueOf(quantity);
				break;
			case 3:
				Price price = p.getPrice();
				res = String.valueOf(price);
				break;
			default:
				res = "Unknown";
			}
		} else {
			res = "N/A";
		}
		return res;

	}

}
