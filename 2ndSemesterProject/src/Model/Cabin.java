package Model;

public class Cabin extends Campsite {

    private int maxPeople;
    private float deposit;

    public Cabin(int siteNumber, String section, String road, Price price, float fee, int maxPeople, float deposit) {
        super(siteNumber, section, road, price, fee);

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
