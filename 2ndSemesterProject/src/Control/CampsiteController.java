package Control;

import java.sql.Date;
import java.util.List;

import Database.CampsiteDAO;
import Model.Campsite;

public class CampsiteController {
	CampsiteDAO campsiteDAO;
	
	public CampsiteController() {
		
	}
	
	public List<Campsite> getAvailebleCampsites(Date startDate, Date endDate) {
		return null;
		
	}
}
