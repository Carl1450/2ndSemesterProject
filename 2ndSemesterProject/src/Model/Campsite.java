package Model;


public abstract class Campsite {
	private String section;
	private String route;
	private int siteNumber;
	private Price price;
	
	public Campsite(String section, String route, int siteNumber, Price price) {
		this.price = price;
		this.section = section;
		this.route = route;
		this.siteNumber = siteNumber;
	}

}
