package Database;

import java.sql.Date;

import Model.Booking;
import Model.Campsite;

public class BookingDAO {

    ConnectionEnvironment env;

    public BookingDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public BookingDAO(ConnectionEnvironment env) {
        this.env = env;
    }
    public boolean saveBooking(Campsite campsite) {
        return false;
    }

	public BookingDAO(Date date, Date date2, Date date3) {
		// TODO Auto-generated method stub
	
	}

	public  BookingDAO FindcampsiteByDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public  BookingDAO saveBooking(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
