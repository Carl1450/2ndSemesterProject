package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Price;
import Model.Product;

public class ProductTableModel extends AbstractTableModel {

    private List<Product> data;
    private static final String[] COL_NAMES = { "Product Name", "Barcode", "Quantity", "Price" };

    public ProductTableModel(List<Product> products) {
        super();

        if (products != null) {
            this.data = products;
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
        Product product = data.get(rowIndex);
        String res = "";
        if (product != null) {
            switch (columnIndex) {
                case 0:
                    int barcode = product.getBarcode();
                    res = String.valueOf(barcode);
                    break;
                case 1:
                    String name = product.getName();
                    res = name;
                    break;
                case 2:
                    int stockNumber = product.getStockNumber();
                    res = String.valueOf(stockNumber);
                    break;
                case 3:
                    Price price = product.getPrice();
                    res = String.valueOf(price.getPrice());
                    break;
                default:
                    res = "Unknown";
            }
        } else {
            res = "N/A";
        }
        return res;

    }

    public Product getProduct(int index) {
        return data.get(index);
    }

    public void setData(List<Product> data) {
        this.data = data;
        super.fireTableDataChanged();
    }

    public List<Product> getData(){
        return data;
    }
}
