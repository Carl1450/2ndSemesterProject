package Database;

import Model.Product;

public class ProductDAO {

    ConnectionEnvironment env;

    public ProductDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public ProductDAO(ConnectionEnvironment env) {
        this.env = env;
    }

    public Product findProductByBarcode(int barcode) {
        Product foundProduct = null;

        // FILL OUT THE REST

        return foundProduct;
    }

}
