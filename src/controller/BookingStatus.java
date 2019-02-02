package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import dao.RideDao;
import dao.UserDaoInter;
import daoImpl.BookingImpl;
import daoImpl.RideDaoImpl;
import daoImpl.UserDaoImpl;
import model.Booking;
import model.Ride;
import model.User;
import serviceUtility.Utilities;

/**
 * Servlet implementation class BookingStatus
 */
@WebServlet("/BookingStatus")
public class BookingStatus extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
	//step 1: pass data to model 
		// driverN is the user who is currently sign in, it can be driver or passenger
		String driverN = session.getAttribute("usern").toString();
	//step 2: pass username to dao to get driver id and fetch booking records where driverid = driverid
		
		UserDaoInter n = new UserDaoImpl();
		int driverID = n.getUserID(driverN);
		String actionRecognizer = request.getParameter("act");
		
		if("Get Report".equals(actionRecognizer))
		{
			System.out.println("driver" +driverID);
			String date = request.getParameter("date");
			String sql = "Select count(*) AS rowNo FROM Ride WHERE Driver_ID= '"+ driverID +"' and Cancelation='Cancelled' and Date>= '"+ date +"'";
				
		int row = Utilities.countRowNo(sql);
		RideDao m = new RideDaoImpl();
		Ride [] report = m.canelationReport(driverID, date, row);
								
				
							
			request.setAttribute("list", report);
				request.getRequestDispatcher("Report.jsp").forward(request, response);
								
		}else if("Booking Status".equals(actionRecognizer) || "Cancel".equals(actionRecognizer))
{
						RideDao c = new BookingImpl();
						//int ln=1;
						
						Booking[] search =c.getBookings(driverID);
						
						request.setAttribute("list", search);
						request.getRequestDispatcher("ScheduledPickups.jsp").forward(request, response);
	  //  passenger checks his/her bookings 
} else if("My Booking".equals(actionRecognizer)){
						RideDao c = new BookingImpl();
						int ln=1;
						//driverID here is the user = passenger
						Booking[] search =c.myBookings(driverID);
						request.setAttribute("list", search);
						request.getRequestDispatcher("MyBookings.jsp").forward(request, response);
}

else if("Available Pickups".equals(actionRecognizer))
	{	
		//this is checking if there is any pickups scheduled
		//step 1: get scheduled pickups by driverId--use dao to fetch result
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = dateFormat.format(new Date());
		String sql = "Select count(*) AS rowNo FROM (Select Ride_ID, count(Seat_Booked) From Booking WHERE Driver_ID= '"+ driverID +"'  and Ride_Date>= '"+curDate+"' and (Status = 'OnTime' OR Status = 'Delayed') group by Ride_ID) AS z";
		int rowNo = Utilities.countRowNo(sql);
		
	if(rowNo==0){
				request.setAttribute("pickupsNotAvail", " Looks like there aren't any pickups available. Try checking again later.");
				request.getRequestDispatcher("Driver.jsp").forward(request, response);
			}
	else{
		//if ride request is available then do this
		String query = "SELECT Ride_ID, Driver_ID, `From`, `To`, Ride_Date, Time, count(Seat_Booked) as scheduled"+
				" FROM Booking Where Driver_ID=? and (Status = 'OnTime' OR Status = 'Delayed') and Ride_Date>=? group by Ride_ID, `From`, `To`, Ride_Date, Time ";
			Booking [] pickups=null;
			Connection con = Utilities.connect();
			
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, driverID);
			ps.setString(2, curDate);
			
			ResultSet rs = ps.executeQuery();
			pickups = new Booking[rowNo];
			int i =0;
			while(rs.next()){
				pickups[i] = new Booking();
				
				pickups[i].setRideID(rs.getInt("Ride_ID"));
				pickups[i].setDriver_Id(rs.getInt("Driver_ID"));
				pickups[i].setFrom(rs.getString("From"));
				pickups[i].setTo(rs.getString("To"));
				pickups[i].setDate(rs.getString("Ride_Date"));
				pickups[i].setTime(rs.getString("Time"));
				pickups[i].setnOfBooking(rs.getInt("scheduled"));
				i++;
			}
		
		}catch(SQLException x){x.printStackTrace();}
			request.setAttribute("pickupList", pickups);
			request.getRequestDispatcher("BookingStatus.jsp").forward(request, response);
			}
// getting passengers booking info
}else {//driver clicks on passenger information button
		//step 1: get data
		int rideID= Utilities.getIntParameter(request, "act", -1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = dateFormat.format(new Date());
		// checking how many booking does passenger have
			int maxRider = Utilities.countRowNo("Select count(*) as rowNo FROM Booking WHERE Ride_ID='"+rideID+"' and (Status='OnTime' OR Status='Delayed') and Ride_Date>= '"+curDate+"'");
			
		//step2: get passenger id from booking table using ride id
		int[] userID=null;
		
		Connection con = Utilities.connect();
	try{//getting passenger ID
		String qry = "Select Passenger_ID FROM Booking WHERE Ride_ID='"+rideID+"' and (Status='OnTime' OR Status='Delayed') AND Ride_Date>= '"+curDate+"'";
		PreparedStatement ps = con.prepareStatement(qry);
		ResultSet rs = ps.executeQuery();
			userID = new int[maxRider];
			int i = 0;
			while(rs.next()){
				userID[i] = rs.getInt("Passenger_ID");
				i++;
			}

	}catch(SQLException e){e.printStackTrace();}
	User[] search =null;
	UserDaoInter x = new UserDaoImpl();
		search= x.getUserByID(userID);
		request.setAttribute("pList", search);
		request.getRequestDispatcher("Passenger.jsp").forward(request, response);}


}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
