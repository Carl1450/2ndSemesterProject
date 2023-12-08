package Model;

public class Pitch extends Campsite{

	private float fee;
	
	public Pitch(int id, String section, String road, int siteNumber, Price price, float fee) {
		super(id, section, road, siteNumber, price);
		
		this.fee = fee;
		
	}
	
	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

}
