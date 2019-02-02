package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import dao.BRateOfferDao;
import dao.UserDaoInter;
import daoImpl.BRateOfferImpl;
import daoImpl.UserDaoImpl;
import model.BaseRateOffer;
import serviceUtility.Utilities;

@WebServlet("/BaseRateRide")//this a controller class - MVC
public class BaseRateRide extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//the below to string values are used by if clause to invoke the right method
		String actionRecognizer = request.getParameter("act");
		//String w = request.getAttribute("offline").toString();
		
		HttpSession session = request.getSession();
		
		String userName = session.getAttribute("usern").toString();
		UserDaoInter c = new UserDaoImpl();
		int driverId = c.getUserID(userName);
		
		//checks where the request come from then invokes method based on that
		if("Go Online".equals(actionRecognizer)){

		//step 1: get data from view
		
		String address = request.getParameter("pickupLoc");
		
		int maxDist = Utilities.getIntParameter(request, "maxDistance", -1);
		
		
		if (address==null || address.trim().equals("") || maxDist<=0){
			BaseRateOffer m = new BaseRateOffer();
			m.setCurrentLoc("none");
			m.setMaxDistance(-1);
		
			request.setAttribute("listB", m);
			request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
		}else {
			String[] parts = address.split(",");
			String location = parts[parts.length-3];
			// step 2: pass data to model object
			BaseRateOffer m = new BaseRateOffer();
			m.setCurrentLoc(location);
			m.setDriverId(driverId);
			m.setMaxDistance(maxDist);
			m.setRideReq("None");
		
		//step 3 : ask dao to insert data into the base rate ride table
			BRateOfferDao b = new BRateOfferImpl();
			int i = b.postOffer(m);
			
			if(i !=0){
				request.setAttribute("listB", m);
				request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
			} //if data is not stored in database
			m.setCurrentLoc("none");
			m.setMaxDistance(-2);
			request.setAttribute("listB", m);
			request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
			}
		}//-----------------Go Offline below---------------------------------
		else if ("Go Offline".equals(actionRecognizer)){
			String qry = "Delete FROM BASERATEOFFER WHERE Driver_ID='"+driverId+"'";
			BRateOfferDao b = new BRateOfferImpl();
			int i = b.deleteOffcer(qry);
			BaseRateOffer w = new BaseRateOffer();
			if(i !=0){
				w.setCurrentLoc("none");
				request.setAttribute("listB", w);
				request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
			}else{
				w.setCurrentLoc("none");
				w.setMaxDistance(-2);
				request.setAttribute("listB", w);
				request.getRequestDispatcher("BaseRateRide.jsp").forward(request, response);
			}
		}
		//--------------------------------------------------
		else if("Base Rate Ride".equals(actionRecognizer) ||"Refresh".equals(actionRecognizer)){
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
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
