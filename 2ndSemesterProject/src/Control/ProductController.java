package Control;

import Database.ConnectionEnvironment;
import Database.ProductDAO;
import Model.Product;

public class ProductController {
	
	private ProductDAO productDAO;

    private ConnectionEnvironment env;
    public ProductController(ConnectionEnvironment env) {
        this.env = env;
    }
    
    public Product findProductByBarcode(int barcode) {
    	return productDAO.findProductByBarcode(barcode);
    }

}
