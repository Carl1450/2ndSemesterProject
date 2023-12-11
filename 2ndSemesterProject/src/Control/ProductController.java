package Control;

import Database.ConnectionEnvironment;
import Database.ProductDAO;
import Model.Product;

public class ProductController {
	
	private ProductDAO productDAO;

    private ConnectionEnvironment env;
    public ProductController(ConnectionEnvironment env) {
        this.env = env;
        productDAO = new ProductDAO(env);
    }
    
    public Product findProductByBarcode(int barcode) {
    	return productDAO.findProductByBarcode(barcode);
    }
    
    public boolean saveProduct(int barcode, String name, int stockNumber) {
    	return productDAO.saveProduct(barcode, name, stockNumber);
    }

}
