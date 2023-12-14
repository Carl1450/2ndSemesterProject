package Control;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Model.Campsite;
import Model.Employee;

public class CampsiteController {
	private CampsiteDAO campsiteDAO;

	private ConnectionEnvironment env;

	public CampsiteController(ConnectionEnvironment env) {
		this.env = env;
		campsiteDAO = new CampsiteDAO(env);
	}
	
	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate, boolean searchForCabin, boolean searchForPitch) {
		List<Campsite> campsiteList = new ArrayList<>();

		if (validateDates(startDate, endDate)) {
			campsiteList = campsiteDAO.getAvailableCampsites(startDate, endDate, searchForCabin, searchForPitch);
		}
		return campsiteList;
		
	}

	public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		return campsiteDAO.reserveCampsite(campsite, startDate, endDate, employee);
	}

	public boolean cancelReservationOfCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		return campsiteDAO.cancelReservationOfCampsite(campsite, startDate, endDate, employee);
	}

	private boolean validateDates(Date startDate, Date endDate) {
		boolean validDates = true;
		if (endDate.before(startDate)) {
			validDates = false;
		}
		return validDates;
	}
	
	public List<Campsite> getCampsites(){
		return campsiteDAO.getCampsites();
	}
	
	public boolean saveCampsite(int siteNo, String section, String road, String type, float price) {
		return campsiteDAO.saveCampsite(siteNo, section, road, type, price);
	}
	
	public boolean saveCabin(int siteNo, int maxPeople, float deposit) {
		return campsiteDAO.saveCabin(siteNo, maxPeople, deposit);
	}
	
	public boolean savePitch(int siteNo, float fee) {
		return campsiteDAO.savePitch(siteNo, fee);
	}
	
	public boolean updateCampsite(int siteNo, String section, String road, String type, int maxPeople,
			float deposit, float fee, Date effectiveDate, float price) {
		return campsiteDAO.updateCampsiteBySiteNo(maxPeople, section, road, type, maxPeople, deposit, fee, price);
	}
	
	public boolean deleteCampsite(int siteNo) {
		return campsiteDAO.deleteCampsite(siteNo);
	}
}
