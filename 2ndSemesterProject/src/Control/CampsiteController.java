package Control;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Database.CampsiteDAO;
import Database.ConnectionEnvironment;
import Model.Campsite;
import Model.Employee;
import Model.Product;

public class CampsiteController {
	private CampsiteDAO campsiteDAO;
	 private List<Campsite> allCampsites;

	private ConnectionEnvironment env;

	public CampsiteController(ConnectionEnvironment env) {
		this.env = env;
		campsiteDAO = new CampsiteDAO(env);
		allCampsites = new ArrayList<>();
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
	
	public List<Campsite> findCampsitesStartingWith(String siteNoStart, boolean retrieveNewData) {
        if (allCampsites.isEmpty() || retrieveNewData) {
            allCampsites = campsiteDAO.getCampsites();
        }

        List<Campsite> campsitesStartingWith = new ArrayList<>();


        for (Campsite campsite : allCampsites) {
            String siteNoString = Integer.toString(campsite.getSiteNumber()).trim();
            if (siteNoString.equals("") || siteNoString.startsWith(siteNoStart)) {
                campsitesStartingWith.add(campsite);
            }
        }
        return campsitesStartingWith;
    }
	
	public boolean siteNumberExists(int siteNumber) {
	    List<Campsite> campsites = getCampsites();
	    for (Campsite campsite : campsites) {
	        if (campsite.getSiteNumber() == siteNumber) {
	            return true; 
	        }
	    }
	    return false; 
	}
	
	public List<Campsite> getCampsites(){
		return campsiteDAO.getCampsites();
	}
	
	public boolean saveCampsite(int siteNo, String section, String road, String type, float fee, float price) {
		return campsiteDAO.saveCampsite(siteNo, section, road, type, fee, price);
	}
	
	public boolean saveCabin(int siteNo, int maxPeople, float deposit) {
		return campsiteDAO.saveCabin(siteNo, maxPeople, deposit);
	}
	
	public boolean savePitch(int siteNo) {
		return campsiteDAO.savePitch(siteNo);
	}
	

	public boolean updateCampsite(int siteNo, String section, String road, String type, float fee, float price) {
		return campsiteDAO.updateCampsite(siteNo, section, road, type, fee, price);
	}
	
	public boolean updateCabin(int siteNo, int maxPeople, float deposit) {
		return campsiteDAO.updateCabin(siteNo, maxPeople, deposit);
	}
	
	public boolean updatePitch(int siteNo) {
		return campsiteDAO.updatePitch(siteNo);

	}
	
	public boolean deleteCampsite(int siteNo) {
		return campsiteDAO.deleteCampsite(siteNo);
	}
}
