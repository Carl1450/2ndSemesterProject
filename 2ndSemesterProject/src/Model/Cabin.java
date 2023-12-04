package Model;

public class Cabin extends Campsite{
	
	private int maxPeople;
	private float deposit;

	public Cabin(String section, String road, int siteNumber, Price price, int maxPeople, float deposit) {
		super(section, road, siteNumber, price);
		
		this.setDeposit(deposit);
		this.setMaxPeople(maxPeople);
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

}
