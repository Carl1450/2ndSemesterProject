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


    public boolean saveProduct(int barcode, String name, int stockNumber) {
        return productDAO.saveProduct(barcode, name, stockNumber);
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
