package Model;

import java.sql.Date;

public class Price {
	
	private float price;
	private Date effectiveDate;
	
	public Price(float price, Date effectiveDate) {
		this.price = price;
		this.effectiveDate = effectiveDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
