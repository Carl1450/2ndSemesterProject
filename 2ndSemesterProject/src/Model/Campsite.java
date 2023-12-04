package Model;


public abstract class Campsite {
	private String section;
	private String road;
	private int siteNumber;
	private Price price;
	
	public Campsite(String section, String road, int siteNumber, Price price) {
		this.setPrice(price);
		this.setSection(section);
		this.setRoad(road);
		this.setSiteNumber(siteNumber);
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}
