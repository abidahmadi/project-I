package dao;

import model.BaseRateOffer;
import model.BaseSearchCriteria;

public interface BRateOfferDao {

	
	public int postOffer(BaseRateOffer m);
	
	public int deleteOffcer(String a);
	
	public BaseRateOffer[] searchRide(BaseSearchCriteria m, int maxSize);
	
	public int postDeleteBooking(String m);
	

	
	
	
	
}
