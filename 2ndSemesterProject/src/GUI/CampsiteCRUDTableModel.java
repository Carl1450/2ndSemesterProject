package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Cabin;
import Model.Campsite;
import Model.Pitch;
import Model.Price;

public class CampsiteCRUDTableModel extends AbstractTableModel {
	private List<Campsite> data;
	private static final String[] COL_NAMES = { "Type", "Site Number", "Section", "Road", "Fee", "Price", "Max People",
			"Deposit" };

	public CampsiteCRUDTableModel(List<Campsite> campsites) {
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
				int siteNumber = c.getSiteNumber();
				res = (siteNumber != 0) ? String.valueOf(siteNumber) : "N/A";
				break;

			case 2:
				String section = c.getSection();
				res = (section != null) ? String.valueOf(section) : "N/A";
				break;

			case 3:
				String road = c.getRoad();
				res = (road != null) ? String.valueOf(road) : "N/A";
				break;

			case 4:
				float fee = c.getFee();
				res = (fee != 0) ? String.valueOf(fee) : "N/A";

				break;

			case 5:
				Price price = c.getPrice();
				res = (price != null) ? String.valueOf(price.getPrice()) : "N/A";
				break;

			case 6:
				if (c instanceof Cabin) {
					int maxPeople = ((Cabin) c).getMaxPeople();
					res = (maxPeople != 0) ? String.valueOf(maxPeople) : "N/A";
				}
				break;

			case 7:
				if (c instanceof Cabin) {
					float deposit = ((Cabin) c).getDeposit();
					res = (deposit != 0) ? String.valueOf(deposit) : "N/A";
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

	public Campsite getCampsite(int index) {
		return data.get(index);
	}

	public void setData(List<Campsite> data) {
		this.data = data;
		super.fireTableDataChanged();
	}

}
