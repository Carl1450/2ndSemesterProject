package Model;

public class Pitch extends Campsite{

	private float fee;
	
	public Pitch(String section, String road, int siteNumber, Price price, float fee) {
		super(section, road, siteNumber, price);
		
		this.fee = fee;
		
	}

}
