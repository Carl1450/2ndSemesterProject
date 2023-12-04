package Model;


public abstract class Campsite {
	private String section;
	private String road;
	private int siteNumber;
	private Price price;
	
	public Campsite(String section, String road, int siteNumber, Price price) {
		this.price = price;
		this.section = section;
		this.road = road;
		this.siteNumber = siteNumber;
	}

}
