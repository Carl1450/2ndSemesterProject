package Control;

import java.sql.Date;
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
		return campsiteDAO.getAvailableCampsites(startDate, endDate);
		
	}

	public boolean reserveCampsite(Campsite campsite, Date startDate, Date endDate, Employee employee) {
		return campsiteDAO.reserveCampsite(campsite, startDate, endDate, employee);
	}
}
