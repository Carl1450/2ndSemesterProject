package GUI;

import Model.Campsite;
import Model.Task;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

	private List<Task> data;

	private static final String[] COL_NAMES = { "Priority", "Description", "Deadline", "Receptionist" };

	public TaskTableModel(List<Task> tasks) {
		super();

		if (tasks != null) {
			this.data = tasks;
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
		Task task = data.get(rowIndex);
		String res = "";

		if (task != null) {
			switch (columnIndex) {
			case 0:
				res = Integer.toString(task.getPriority());
				break;
			case 1:
				res = task.getDescription();
				break;
			case 2:
				res = task.getDeadline().toString();
				break;
			case 3:
				res = task.getReceptionist().getName();
				break;
			default:
				res = "Unknown";
			}
		} else {
			res = "N/A";
		}

		return res;
	}

	public Task getTask(int index) {
		return data.get(index);
	}

	public void setData(List<Task> data) {
		this.data = data;
		super.fireTableDataChanged();
	}

}
