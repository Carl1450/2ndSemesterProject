package Model;

public class Cabin extends Campsite{
	
	private int maxPeople;
	private float deposit;

	public Cabin(String section, String road, int siteNumber, Price price, int maxPeople, float deposit) {
		super(section, road, siteNumber, price);
		
		this.deposit = deposit;
		this.maxPeople = maxPeople;
	}

}
