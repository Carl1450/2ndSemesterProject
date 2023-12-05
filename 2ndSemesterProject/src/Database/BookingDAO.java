package Database;

import Model.Booking;

public class BookingDAO {

    ConnectionEnvironment env;

    public BookingDAO() {
        env = ConnectionEnvironment.PRODUCTION;
    }

    public BookingDAO(ConnectionEnvironment env) {
        this.env = env;
    }
    public boolean saveBooking(Booking booking) {
        return false;
    }

}
