package Control;

import java.sql.Date;
import java.util.List;

import Database.CampsiteDAO;
import Model.Campsite;

public class CampsiteController {
	private CampsiteDAO campsiteDAO;
	
	public CampsiteController() {
		campsiteDAO = new CampsiteDAO();
	}
	
	public List<Campsite> getAvailebleCampsites(Date startDate, Date endDate) {
		return campsiteDAO.getAvailableCampsites(startDate, endDate);
		
	}
}
