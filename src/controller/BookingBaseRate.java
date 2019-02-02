package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import dao.BRateOfferDao;
import dao.UserDaoInter;
import daoImpl.BRateOfferImpl;
import daoImpl.UserDaoImpl;
import datastructure.Queue;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import model.BaseRateOffer;
import model.BaseSearchCriteria;
import model.BookingBRate;
import model.RideRequester;
import serviceUtility.SendEmail;
import serviceUtility.Utilities;


@WebServlet("/BookingBaseRate")
public class BookingBaseRate extends HttpServlet {
	
	private static final int NULL = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//below string values are used by if clause to invoke the right method
				String actionRecognizer = request.getParameter("act");
				//getting username from session and using it to get userid from User table
				HttpSession session = request.getSession();
				String userName = session.getAttribute("usern").toString();
				String riderName = session.getAttribute("name").toString();
				UserDaoInter c = new UserDaoImpl();
				//this riderId can be driverId too. depends on whoever is calling the method 
				int riderId = c.getUserID(userName);
				
				
				//step 1: reading form
		if("Search".equals(actionRecognizer)){
				String address = request.getParameter("pickup");
				String[] parts = address.split(",");
				String pickup_location = parts[parts.length-3];
				
				String destination = request.getParameter("destination");
				int distance = Utilities.getIntParameter(request, "distance", -1);
				
				//step 2: pass data to model object
				BaseSearchCriteria m = new BaseSearchCriteria();
				m.setPickup_location(pickup_location);
				m.setDrop_off(destination);
				m.setDistance(distance);
				m.setRiderID(riderId);
				
				//step 3: pass data to dao to look for ride but first lets add these details into booking table for later use
				 
				String qry ="Select count(*) AS rowNo FROM BaseRateOffer WHERE Driver_ID<>'"+ riderId+"' and Current_Loc='"+pickup_location+"' and Max_Distance>='"+distance+"' and Status<>'Booked'";
				int maxArraySize = Utilities.countRowNo(qry);
				
				if(maxArraySize == 0){
					request.setAttribute("noRide", "No Ride available that match your search.");
					request.getRequestDispatcher("SearchBROffer.jsp").forward(request, response);
				} else{
					Queue n = Utilities.creatQue(m, maxArraySize);
					BaseRateOffer show = n.remove();
					
					request.setAttribute("showRide", show);
					request.getRequestDispatcher("SearchBROffer.jsp").forward(request, response);
				}	
		}
		//this method is invoked by rider
		else if("Accept".equals(actionRecognizer)){
			int driverID = Utilities.getIntParameter(request, "drvrID", -1);
			
			RideRequester m = new RideRequester();
				m.setDriverID(driverID);
				m.setRiderID(riderId);
				m.setRiderName(riderName);
				m.setRideRequest("OneRequest");//driver check status based on this keyword
				m.setStatus("Booked");//makes this offer invisible to other riders
			int requestConfirmation = Utilities.cancelOrRequestRide(m);
			
			//add data to bookingBRate table
			String pickup_location = request.getParameter("currentLoc");
			String destination = request.getParameter("destination");
			
			BRateOfferDao h = new BRateOfferImpl();
			 String query = "INSERT into BookingBRate (Driver_ID, Rider_ID, Rider_Name, Pickup_Location, Destination, Status) values('"+driverID+"','"+riderId+"','"+riderName+"', '"+pickup_location+"','"+destination+"', 'Waiting')";
			 h.postDeleteBooking(query);
			if(requestConfirmation !=0){
				String waiting= "wiatforResponse";
				request.setAttribute("requestConf", waiting);
				request.getRequestDispatcher("SearchBROffer.jsp").forward(request, response);
			}
		}
		//driver accepting request from rider
		else if("Accept Request".equals(actionRecognizer)){
			int driverId = riderId;//here rider is driverid driver invokes the method. it can be rider if rider use is using this page
			Utilities.acceptingRequest(driverId);
			Connection con = Utilities.connect();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String curDate = dateFormat.format(new Date());
			Date cur= null;
			try{
				String qry = "Select * FROM BaseRateOffer WHERE Driver_ID=?";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setInt(1, driverId);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					String currLoc = rs.getString("Current_Loc");
					Date dg = rs.getTime("Time_Posted");
					int mDist = rs.getInt("Max_Distance");
					String rideReq = rs.getString("Request");
					
				try {
						cur = dateFormat.parse(curDate);
						
					} catch (ParseException e) {
						System.out.println("error here at parse curDate to date");
					}
					long diff = cur.getTime() - dg.getTime();
					BaseRateOffer b = new BaseRateOffer();
					b.setOnline(true);
					b.setCurrentLoc(currLoc);
					b.setMaxDistance(mDist);
					b.setForMin(diff);
					b.setRideReq(rideReq);
					b.setDriverId(rs.getInt("Driver_ID"));
					b.setRiderId(rs.getInt("Rider_ID"));
					b.setRiderName(rs.getString("Rider_Name"));
					request.setAttribute("listB", b);
					request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
				} else {
					BaseRateOffer b = new BaseRateOffer();
					b.setCurrentLoc("none");
					request.setAttribute("listB", b);
					request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
				}
			}catch(SQLException e){System.out.println("error is here at base ride offer first try catch block");}
		}
		
			//this method is invoked by rider
		else if("Cancel".equals(actionRecognizer)){
			int driverID = Utilities.getIntParameter(request, "drvrID", -1);
			
			RideRequester m = new RideRequester();
				m.setDriverID(driverID);
				m.setRiderID(riderId);
				m.setRiderName(riderName);
				m.setRideRequest("None");
				m.setRiderID(NULL);
				m.setRiderName(null);
				m.setStatus("Unbooked");
			int requestConfirmation = Utilities.cancelOrRequestRide(m);
			//delete record from BookingBRate table
			BRateOfferDao h = new BRateOfferImpl();
			 String query = "Delete FROM BookingBRate WHERE Rider_ID ='"+riderId+"'";
			 h.postDeleteBooking(query);
			if(requestConfirmation !=0){
				request.setAttribute("noRide", "Request Cancelled");
				request.getRequestDispatcher("SearchBROffer.jsp").forward(request, response);
			}
		}
		else if("Decline".equals(actionRecognizer)){
			int driverID = Utilities.getIntParameter(request, "drvrID", -1);
			int riderID = Utilities.getIntParameter(request, "riderID", -1);
			String riderNam = request.getParameter("riderName");
			String pickUpLoc = request.getParameter("currLoc");
			
			RideRequester m = new RideRequester();
				m.setDriverID(driverID);
				m.setRiderID(riderID);
				m.setRiderName(riderNam);
				m.setRideRequest("None");
				m.setRiderID(NULL);
				m.setRiderName(null);
				m.setStatus("Unbooked");
			int requestConfirmation = Utilities.cancelOrRequestRide(m);
			//delete record from BookingBRate table
			BRateOfferDao h = new BRateOfferImpl();
			 String query = "Delete FROM BookingBRate WHERE Rider_ID ='"+riderID+"'";
			 h.postDeleteBooking(query);
			 String [] riderInfo = c.getUserById(riderID);
			 String riderEmail = riderInfo[2];
			 
			 SendEmail.sendEmailNow(riderEmail, "Ride Cancelled", "We apologize to inform you that your ride from "+pickUpLoc+" has been declined by the driver. Please search again there are other drivers willing to pick you up.");
			if(requestConfirmation !=0){
				request.setAttribute("noRide", "You cancelled accepted ride this will affect your acceptance rate. Passenger was notified by email.");
				request.getRequestDispatcher("HomePage.jsp").forward(request, response);
			}
		}
		else if("Base Rate Ride".equals(actionRecognizer) || "Refresh".equals(actionRecognizer)){
			BookingBRate statResult = Utilities.checkRequestStatus(riderId);
			request.setAttribute("showB", statResult);
			request.getRequestDispatcher("SearchBROffer.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
