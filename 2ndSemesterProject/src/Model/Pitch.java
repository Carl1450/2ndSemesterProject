package Model;

public class Pitch extends Campsite{

	private float fee;
	
	public Pitch(String section, String route, int siteNumber, Price price, float fee) {
		super(section, route, siteNumber, price);
		
		this.fee = fee;
		
	}

}
