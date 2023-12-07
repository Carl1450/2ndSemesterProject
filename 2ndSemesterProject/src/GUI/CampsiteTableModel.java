package GUI;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import Model.*;

public class CampsiteTableModel extends AbstractTableModel {

	private List<Campsite> data;
	private static final String[] COL_NAMES = { "Type", "Section", "Road", "Site Number", "Price" };

	public CampsiteTableModel(List<Campsite> campsites) {
		super();

		if (campsites != null) {
			this.data = campsites;
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
		Campsite c = data.get(rowIndex);
		String res = "";
		if (c != null) {
			switch (columnIndex) {
			case 0:
				if (c instanceof Cabin) {
					res = "Cabin";
				}
				if (c instanceof Pitch) {
					res = "Pitch";
				}
				break;

			case 1:
				String section = c.getSection();
				res = (section != null) ? String.valueOf(section) : "N/A";
				break;

			case 2:
				String road = c.getRoad();
				res = (road != null) ? String.valueOf(road) : "N/A";
				break;

			case 3:
				int siteNumber = c.getSiteNumber();
				res = (siteNumber != 0) ? String.valueOf(siteNumber) : "N/A";
				break;

			case 4:
				Price price = c.getPrice();
				res = (price != null) ? String.valueOf(price) : "N/A";
				break;

			default:
				res = "Unknown";
			}
		} else {
			res = "N/A";
		}
		return res;

	}

	public Campsite getCampsite(int index) {
		return data.get(index);
	}

	public void setData(List<Campsite> data) {
		this.data = data;
		super.fireTableDataChanged();
	}

}
