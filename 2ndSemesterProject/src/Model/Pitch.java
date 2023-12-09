package Model;

public class Pitch extends Campsite {

    private float fee;

    public Pitch(int siteNumber, String section, String road, Price price, float fee) {
        super(siteNumber, section, road, price);

        this.fee = fee;

    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

}
