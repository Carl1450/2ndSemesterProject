package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.OrderLine;
import Model.Price;

public class ProductTableModel extends AbstractTableModel {

	private List<OrderLine> data;
	private static final String[] COL_NAMES = { "Product Name", "Barcode", "Quantity", "Price" };

	public ProductTableModel(List<OrderLine> orderLines) {
		super();

		if (orderLines != null) {
			this.data = orderLines;
		} else {
			this.data = new ArrayList<>();
			System.out.println("Warning: Product list was null. Initializing with an empty list.");
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
		OrderLine o = data.get(rowIndex);
		String res = "";
		if (o != null) {
			switch (columnIndex) {
			case 0:
				String name = o.getProduct().getName();
				res = name;
				break;
			case 1:
				int barcode = o.getProduct().getBarcode();
				res = String.valueOf(barcode);
				break;
			case 2:
				int quantity = o.getQuantity();
				res = String.valueOf(quantity);
				break;
			case 3:
				if(o.getQuantity() > 1) {
				Price price = o.getProduct().getPrice();
				double totalPrice = price.getPrice() * o.getQuantity();
				res = String.valueOf(totalPrice); 
				} else {
					Price price = o.getProduct().getPrice();
					float singlePrice = price.getPrice();
					res = String.valueOf(singlePrice);
				}
				
				break;
			default:
				res = "Unknown";
			}
		} else {
			res = "N/A";
		}
		return res;

	}

	public OrderLine getOrderLine(int index) {
		return data.get(index);
	}

	public void setData(List<OrderLine> data) {
		this.data = data;
		super.fireTableDataChanged();
	}

}
