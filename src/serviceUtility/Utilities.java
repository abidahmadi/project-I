package serviceUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSeparatorUI;

import dao.BRateOfferDao;
import daoImpl.BRateOfferImpl;
import datastructure.Queue;
import model.BaseRateOffer;
import model.BaseSearchCriteria;
import model.BookingBRate;
import model.RideRequester;

public class Utilities {
	
// parse String to int
	
public static int parseInt(String a){
	
	int b=0;
	try{
		b = Integer.parseInt(a);
		
	} catch(Exception c){c.printStackTrace();}
	return b;
}
//counts row number in a database table you need to pass desired sql
	public static int countRowNo(String qry){
		int countRo =0;
		Connection con = connect();
	
			try{
				PreparedStatement ps = con.prepareStatement(qry);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
				countRo =rs.getInt("rowNo");
				}
			}catch(SQLException g){g.printStackTrace(); System.out.println("there is no record available countRowNo in Utilities");}
	
	return countRo;
	}
	
//connects to the mysql database	
	public static Connection connect(){
		Connection con = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalJava?useSSL=false", "root", "menu");
		
		}
		catch (Exception e) {e.printStackTrace();
		}
		
		return con;
	}
	
	
	  public static String replaceNull(String val) {
		    if(val == null) {
		      return("");
		    } else {
		      return(val);
		    }
		}
	  
	public static String prompt(String param, String regularMessage,
              String warningMessage) {
		  if ((param != null) && param.trim().equals("")) {
			  return("<B><FONT COLOR=RED>" + warningMessage + "</FONT></B>");
		  } else {
			  return(regularMessage);
		  }
	  }
	public static int getIntParameter(HttpServletRequest request,
            String paramName,
            int defaultValue) {
String paramString = request.getParameter(paramName);
int paramValue;
try {
paramValue = Integer.parseInt(paramString);
} catch(Exception nfe) { // null or bad format
paramValue = defaultValue;
}
return(paramValue);
}

/** Reads param and converts to double. Default if problem. */
	
public static double getDoubleParameter(HttpServletRequest request,
                  String paramName,
                double defaultValue) {
String paramString = request.getParameter(paramName);
double paramValue;
try {
paramValue = Double.parseDouble(paramString);
} catch(Exception nfe) { // null or bad format
paramValue = defaultValue;
}
return(paramValue);
}
	//it checks if driver has any ride request.
	public static int bookingStatus(int id){
		int rowCount=0;
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dtFormat.format(new Date());
		String qry = "Select count(*) as rowNo FROM Booking WHERE Driver_ID='"+id+"' and Ride_Date >='"+date+"'";
		rowCount = countRowNo(qry);
		return rowCount;
	}
	public static Queue creatQue(BaseSearchCriteria m, int maxSize){
		Queue drivers_List = new Queue(maxSize);
		
		BRateOfferDao x = new BRateOfferImpl();
		BaseRateOffer[] dr_List = x.searchRide(m, maxSize);
		int k = dr_List.length;
		
		for(int i =0; i<dr_List.length; i++){
			drivers_List.insert(dr_List[i]);
		}
		return drivers_List;
	}
	
	public static int cancelOrRequestRide(RideRequester m){
		int i =0;
		try{
			
			String qry ="Update BaseRateOffer set Request=?, Rider_ID=?, Rider_Name=?, Status=? WHERE Driver_ID=?";
			PreparedStatement ps = connect().prepareStatement(qry);
				ps.setString(1, m.getRideRequest());
				ps.setInt(2, m.getRiderID());
				ps.setString(3, m.getRiderName());
				ps.setString(4, m.getStatus());
				ps.setInt(5, m.getDriverID());
				i = ps.executeUpdate();
			ps.close();
			connect().close();
		}catch(SQLException e){e.printStackTrace();}
		
		return i;
	}
	
	public static void acceptingRequest(int driverId){
		String qry ="Update BaseRateOffer set Request='Accepted' WHERE Driver_ID=?";
		String sql ="Update BookingBRate set Status='Accepted' WHERE Driver_ID=?";
		
		try{
					PreparedStatement ps = connect().prepareStatement(qry);
					PreparedStatement ps2 = connect().prepareStatement(sql);
					ps.setInt(1, driverId);
					ps.executeUpdate();
					
					ps2.setInt(1, driverId);
					ps2.executeUpdate();
					
					ps.close();
					ps2.close();
					connect().close();
		}catch(SQLException e){e.printStackTrace();}
	
	}
	
	public static BookingBRate checkRequestStatus(int riderId){
		BookingBRate requestStatus = null;
			String qry = "Select * FROM BookingBRate WHERE Rider_ID='"+riderId+"' AND Progress = 'ON'";
			
			try{
			PreparedStatement ps = connect().prepareStatement(qry);
			ResultSet rs = ps.executeQuery();
				while (rs.next()){
					requestStatus = new BookingBRate();
					requestStatus.setBookingID(rs.getInt("Booking_ID"));
					requestStatus.setDriverId(rs.getInt("Driver_ID"));
					requestStatus.setStatus(rs.getString("Status"));
					requestStatus.setDestination(rs.getString("Destination"));
					requestStatus.setPickupLoc(rs.getString("Pickup_Location"));
					requestStatus.setProgress(rs.getString("Progress"));
					System.out.println(rs.getString("Status"));
				}
		
		
				}catch(SQLException e){e.printStackTrace();}
					return requestStatus;
		}
}