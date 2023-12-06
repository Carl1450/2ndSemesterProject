package GUI;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import Model.*;

public class CampsiteTableModel extends AbstractTableModel{
	
	private List<Campsite> data;
	private static final String[] COL_NAMES = {"Type", "Section", "Road", "Site Number", "Price"};
	
	public CampsiteTableModel(List<Campsite> campsites) {
		super();
		
		if(campsites != null) {
			this.data = campsites;
		}
		else {
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
		Campsite c = data.get(rowIndex);
		String res = "";
		switch(columnIndex) {
		case 0:
			//Type stuff
			break;
			
		case 1:
			//Section stuff
			break;
			
		case 2:
			//Road stuff
			break;
			
		case 3:
			//Site Number stuff
			break;
			
		case 4:
			//price
			break;
			
		default:
			res = "Unknown";
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
