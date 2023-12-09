package Control;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Database.CampsiteDAO;
import Model.Campsite;
import Model.Employee;

public class CampsiteController {
	private CampsiteDAO campsiteDAO;
	
	public CampsiteController() {
		campsiteDAO = new CampsiteDAO();
	}
	
	public List<Campsite> getAvailableCampsites(Date startDate, Date endDate) {
		List<Campsite> campsiteList = new ArrayList<>();

		if (validateDates(startDate, endDate)) {
			campsiteList = campsiteDAO.getAvailableCampsites(startDate, endDate);
		}
		return campsiteList;
		
	}

	public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		return campsiteDAO.reserveCampsite(campsite, startDate, endDate, employee);
	}

	private boolean validateDates(Date startDate, Date endDate) {
		boolean validDates = true;
		if (endDate.before(startDate)) {
			validDates = false;
		}
		return validDates;
	}
}
