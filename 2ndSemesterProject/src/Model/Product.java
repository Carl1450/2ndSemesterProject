package Model;

public class Product {
	private int barcode;
	private int stockNumber;
	private String name;
	
	public Product(int barcode, String name, int stockNumber) {
		this.barcode = barcode;
		this.stockNumber = stockNumber;
		this.name = name;
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
	
	
}
