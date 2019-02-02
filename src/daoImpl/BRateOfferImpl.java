package daoImpl;

import java.sql.*;

import dao.BRateOfferDao;
import model.BaseRateOffer;
import model.BaseSearchCriteria;
import serviceUtility.Utilities;

public class BRateOfferImpl implements BRateOfferDao {

	@Override // going online- driver
	public int postOffer(BaseRateOffer m) {
		int i =0;
		Connection con = Utilities.connect();
		try{
			String qry = "Insert into BaseRateOffer(Driver_ID, Current_Loc, Max_Distance, Request) values(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(qry);
			ps.setInt(1, m.getDriverId());
			ps.setString(2, m.getCurrentLoc());
			ps.setInt(3, m.getMaxDistance());
			ps.setString(4, m.getRideReq());
			
			i = ps.executeUpdate();
		}catch(SQLException e){e.printStackTrace();
		System.out.println("error is here at BRateOfferDao postOffer method");}
		return i;
	}

	@Override // going offline- driver
	public int deleteOffcer(String a) {
		int i =0;
		Connection con = Utilities.connect();
		try{
			PreparedStatement ps = con.prepareStatement(a);
			i = ps.executeUpdate();
		}catch(SQLException e){e.printStackTrace();
		System.out.println("error is here at BRateOfferDao postOffer method");}
		return i;
	}
	@Override
	public BaseRateOffer[] searchRide(BaseSearchCriteria m, int maxSize) {
		BaseRateOffer[] rideResult = null;
		String qry = "Select * FROM BaseRateOffer WHERE Current_Loc=? and Max_Distance>=? and Driver_ID<>? and Status<>'Booked' ORDER BY Offer_ID ASC";
		Connection con = Utilities.connect();
		try{
			PreparedStatement ps = con.prepareStatement(qry);
			ps.setString(1, m.getPickup_location());
			ps.setInt(2, m.getDistance());
			ps.setInt(3, m.getRiderID());
			
			ResultSet rs = ps.executeQuery();
			rideResult = new BaseRateOffer[maxSize];
			int i = 0;
			while(rs.next()){
				rideResult[i] = new BaseRateOffer();
				rideResult[i].setDriverId(rs.getInt("Driver_ID"));
				rideResult[i].setMaxDistance(rs.getInt("Max_Distance"));
				rideResult[i].setOfferId(rs.getInt("Offer_ID"));
				rideResult[i].setCurrentLoc(rs.getString("Current_Loc"));
				rideResult[i].setDestination(m.getDrop_off());
				i++;
			}
			ps.close();
			con.close();
		}catch(SQLException e){e.printStackTrace();
		System.out.println("error is here at BRateOfferDao searchRide method");}

		return rideResult;
	}

	@Override
	public int postDeleteBooking(String m) {
		int i =0;
		Connection con = Utilities.connect();
		try{
			PreparedStatement ps = con.prepareStatement(m);
			i = ps.executeUpdate();
		}catch(SQLException e){e.printStackTrace();}
		return i;
	}
}
