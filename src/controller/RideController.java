package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RideDao;
import dao.UserDaoInter;
import daoImpl.RideDaoImpl;
import daoImpl.UserDaoImpl;
import model.Ride;
import model.RideSearcher;
import serviceUtility.Utilities;

@WebServlet("/ControllerRide")
public class RideController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionDetector = request.getParameter("act");
		HttpSession session = request.getSession();
		String uname = session.getAttribute("usern").toString();
		UserDaoInter u= new UserDaoImpl();
		int User_ID = u.getUserID(uname);
		
	if("Post".equals(actionDetector)){
		// collects info from view
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		int seat = Utilities.getIntParameter(request, "seat",-1);
		double fares = Utilities.getDoubleParameter(request, "fare",-1);
		
		if (from==null || from.trim().equals("") || to ==null || to.trim().equals("") || date==null || date.trim().equals("") || time ==null ||time.trim().equals("")|| seat<=0 || fares <=0 ){
			request.setAttribute("msg", "All fields are mandatory.");
			request.getRequestDispatcher("Ride.jsp").forward(request, response);
		}else{
			
			//*pass data to the class model object, but first use getUserID from DAO to fetch driver id who has posted the ride
			//we want to know who has posted the ride so to relate the posting to the driver/user id
//					
					
			//2**pass data to the class model object		
			Ride m = new Ride();
			m.setFrom(from);
			m.setTo(to);
			m.setDate(date);
			m.setTime(time);
			m.setFare(fares);
			m.setSeat(seat);
			m.setUser_ID(User_ID);
			
			//3. call method in DAO class to insert data in a table
			
			RideDao d = new RideDaoImpl();
			int i = d.postRide(m);
			
			if(i!=0){
			request.setAttribute("PostSuccessfromRideControler"," SUCCESSFULL: Your offer has been posted.");
			request.getRequestDispatcher("/Ride.jsp").forward(request, response);
			}else{
				request.setAttribute("postFailedFrmRideController", "Failed. Something went wrong.");
				request.getRequestDispatcher("Ride.jsp").forward(request, response);
			}	
		}
		
	}else if("Search".equals(actionDetector)){
			
			String frm = request.getParameter("from");
			String to = request.getParameter("to");
			String dt = request.getParameter("date");
			int uid = Utilities.getIntParameter(request, "userid", -1);
			
			if(frm ==null || frm.equals("") || to == null || to.equals("") || dt == null || dt.equals("")){
				request.setAttribute("msg", "All fields are mandatory.");
				request.getRequestDispatcher("/SearchRide.jsp").forward(request, response);
			}
			// step 2: pass search criteria to model class
			else{ 
				RideSearcher c = new RideSearcher();
					
					c.setFrom(frm);
					c.setTo(to);
					c.setDate(dt);
					c.setUser_ID(uid);
					c.setStatus("Online");// excludes the offline drivers
				
			// step 3: call method findRide to find if match ride exists. it returns the no of rides available
					RideDao rd = new RideDaoImpl();
					int numberOfRideAvailable = rd.findRide(c);
					
					if(numberOfRideAvailable ==0){
						//HttpSession session = request.getSession();
						session.setAttribute("NoRideAvailmsg", "There is no ride available now. Try again later.");
						request.getRequestDispatcher("SearchRide.jsp").forward(request, response);

						}
					else{
							Ride[] search =rd.searchRide(c, numberOfRideAvailable );	
							request.setAttribute("searchList", search);
							request.getRequestDispatcher("SearchResult.jsp").forward(request, response);										
						}
			}
		}
	
	else if("Offline".equals(actionDetector)){
//		
		//step 2: pass data to model object
		Ride m = new Ride();
		m.setStatus(request.getParameter("act"));
		m.setUser_ID(User_ID);
	// step:3 invoke dao to update ride status
		RideDao x = new RideDaoImpl();
		int i = x.updateRide(m);
	if(i !=0){
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dtFormat.format(new Date());
		// checks if there is any booking request
		String qry = "Select count(*) as rowNo FROM Booking WHERE Driver_ID='"+User_ID+"' and (Status='OnTime' or Status = 'Delayed') and Ride_Date >='"+date+"'";
		int rowCount = Utilities.countRowNo(qry);
		// rowCount is the number of bookings and 0 means no bookings
		if(rowCount==0){
				request.setAttribute("OnOfflinemsg", "OFFLINE");
				request.setAttribute("noOffer", "Looks like you have ZERO ride request.");
				request.getRequestDispatcher("Driver.jsp").forward(request, response);
			} else {
				request.setAttribute("OnOfflinemsg", "OFFLINE");
				request.setAttribute("bookingStatus", "You have "+ rowCount+ " booking request. Check Available Pickups.");
				request.getRequestDispatcher("Driver.jsp").forward(request, response);
			}
	}else {
		request.setAttribute("noOffer", "You do not have any ride offer, Please post rides!");
		request.getRequestDispatcher("Driver.jsp").forward(request, response);
			}
	}else if("Online".equals(actionDetector)){
//		
		//step 2: pass data to model object
		Ride m = new Ride();
		m.setStatus(request.getParameter("act"));
		m.setUser_ID(User_ID);
	// step:3 invoke dao to update ride status
		RideDao x = new RideDaoImpl();
		int i = x.updateRide(m);
		if(i !=0){
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dtFormat.format(new Date());
			// checks if there is any booking request
			String qry = "Select count(*) as rowNo FROM Booking WHERE Driver_ID='"+User_ID+"' and (Status='OnTime' or Status = 'Delayed') and Ride_Date >='"+date+"'";
			int rowCount = Utilities.countRowNo(qry);
			if(rowCount==0){
					request.setAttribute("OnOfflinemsg", "ONLINE");
					request.setAttribute("noOffer", "Looks like you have ZERO ride request.");
					request.getRequestDispatcher("Driver.jsp").forward(request, response);
			} else {request.setAttribute("OnOfflinemsg", "ONLINE");
					request.setAttribute("bookingStatus", "You have "+ rowCount+ " booking request. Check Available Pickups.");
					request.getRequestDispatcher("Driver.jsp").forward(request, response);
			}
			
		}else {
					request.setAttribute("noOffer", "You do not have any ride offer, Please post rides!");
					request.getRequestDispatcher("Driver.jsp").forward(request, response);
			}
	}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
