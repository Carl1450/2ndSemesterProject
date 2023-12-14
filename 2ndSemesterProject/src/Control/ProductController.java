package Control;

import Database.ConnectionEnvironment;
import Database.ProductDAO;
import Model.Product;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private ProductDAO productDAO;

    private List<Product> allProducts;

    private ConnectionEnvironment env;

    public ProductController(ConnectionEnvironment env) {
        this.env = env;
        productDAO = new ProductDAO(env);
        allProducts = new ArrayList<>();
    }

    public Product findProductByBarcode(int barcode) {
        return productDAO.findProductByBarcode(barcode);
    }

    public List<Product> findProductsStartingWith(String barcodeStart, boolean retrieveNewData) {
        if (allProducts.isEmpty() || retrieveNewData) {
            allProducts = productDAO.getAllProducts();
        }

        List<Product> productsStartingWith = new ArrayList<>();


        for (Product product : allProducts) {
            String barcodeString = Integer.toString(product.getBarcode()).trim();
            if (barcodeString.equals("") || barcodeString.startsWith(barcodeStart)) {
                productsStartingWith.add(product);
            }

        }

        return productsStartingWith;
    }


    public boolean saveProduct(String barcode, String name, String stockNumber, String price) {

        boolean success = false;
        if (!barcode.trim().equals("") || !name.trim().equals("") || !stockNumber.trim().equals("") || !price.trim().equals("")) {

            int barcodeInt = -1;
            int stockNumberInt = -1;
            float priceFloat = -1;

            try {
                barcodeInt = Integer.parseInt(barcode);
                stockNumberInt = Integer.parseInt(stockNumber);
                priceFloat = Float.parseFloat(price);
            } catch (NumberFormatException  e) {
                e.printStackTrace();
            }


            success = productDAO.saveProduct(barcodeInt, name, stockNumberInt, priceFloat);

        }


        return success;


    }

    public boolean updateProduct(int oldBarcode, String newBarcode, String newName, String newStockNumber) {
        return productDAO.updateProduct(oldBarcode, Integer.parseInt(newBarcode), newName, Integer.parseInt(newStockNumber));
    }

    public boolean deleteProduct(Product product) {

        boolean success = false;

        try {
            success = productDAO.deleteProduct(product);

        } catch (RuntimeException e) {
            System.out.println("A conflict in the database occurred, Product can not be deleted");
        }

        return success;
    }

}
