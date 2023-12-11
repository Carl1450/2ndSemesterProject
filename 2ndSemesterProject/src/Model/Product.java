package Model;

public class Product {
	private int barcode;
	private int stockNumber;
	private String name;
	private Price price;
	
	public Product(int barcode, String name, int stockNumber, Price price) {
		this.barcode = barcode;
		this.stockNumber = stockNumber;
		this.name = name;
		this.price = price;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public int getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(int stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Price getPrice() {
		return price;
	}
	
	public void setPrice(Price price) {
        this.price = price;
    }
	
	
}
