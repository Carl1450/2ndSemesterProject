package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Employee;
import Model.Product;

public class EmployeeTableModel extends AbstractTableModel {

    private List<Employee> data;
    private static final String[] COL_NAMES = {"Name", "Email", "Phone Number", "Address", "Role"};

    public EmployeeTableModel(List<Employee> employees) {
        super();

        if (employees != null) {
            this.data = employees;
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
        Employee employee = data.get(rowIndex);
        String res = "";

        // "Name", "Email", "Phone Number", "Address", "Role"
        if (employee != null) {
            switch (columnIndex) {
                case 0:
                    res = employee.getName();
                    break;
                case 1:
                    res = employee.getEmail();
                    break;
                case 2:
                    res = employee.getPhoneNumber();
                    break;
                case 3:
                    res = employee.getAddress();
                    break;
                case 4:
                    res = employee.toString();
                    break;
                default:
                    res = "Unknown";
            }
        } else {
            res = "N/A";
        }
        return res;

    }

    public Employee getEmployee(int index) {
        return data.get(index);
    }

    public void setData(List<Employee> data) {
        this.data = data;
        super.fireTableDataChanged();
    }

    public List<Employee> getData(){
        return data;
    }
}
