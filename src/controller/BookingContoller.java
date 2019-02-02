package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serviceUtility.*;
import dao.RideDao;
import dao.UserDaoInter;
import daoImpl.BookingImpl;
import daoImpl.RideDaoImpl;
import daoImpl.UserDaoImpl;
import model.Booking;
import model.Ride;

@WebServlet("/BookingContoller")
public class BookingContoller extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String actionDetector = request.getParameter("act");
		//Canceling ride by driver
		if("Cancel Ride".equals(actionDetector)){
			int rideId = Utilities.getIntParameter(request, "rideId", -1);
			String from = request.getParameter("frm");
			String dis = request.getParameter("to");
			
			Ride g = new Ride();
			g.setRide_ID(rideId);
			g.setStatus("Cancelled");
			
			
			RideDao j = new RideDaoImpl();
			int i = j.cancelRide(g);
			
		
			Booking[] emails = j.myBookings(rideId);
			String frm = emails[0].getFrom();
			String to = emails[0].getTo();
			String dt = emails[0].getDate();
			
			int arrayLen = emails.length;
			String[] email = new String[arrayLen];
			for(int n = 0; n<arrayLen; n++){
				email[n] = emails[n].getEmailAdd();
				}
				//send email. set up email message
				
				String sub = "Ride Ride Canclled";
				String msg = "Your ride from " + frm + " to " + to + " on "+dt+ " has been canceled.";
				
			if(i!=0){	
				MultiEmail.sendEmailToAll(email, sub, msg);
				request.setAttribute("msg", "Ride_ID " + rideId + " Cancelled and passengers have been notified.");
				request.getRequestDispatcher("Driver.jsp").forward(request, response);
			} else {request.setAttribute("msg", "Ride_ID " + rideId + " has not been Cancelled. Something went wrong try again.");
			request.getRequestDispatcher("Driver.jsp").forward(request, response);}
		}
		//updates delayed rides
		if("Update".equals(actionDetector)){
			int rideId = Utilities.getIntParameter(request, "rideId", -1);
			String time = request.getParameter("time");
			String delayHours = request.getParameter("delayHours");
			
			RideDao j = new RideDaoImpl();
			int i = j.delayRide(time, rideId);
			
		
			Booking[] emails = j.myBookings(rideId);
			String frm = emails[0].getFrom();
			String to = emails[0].getTo();
			String dt = emails[0].getDate();
			
			int arrayLen = emails.length;
			String[] email = new String[arrayLen];
			for(int n = 0; n<arrayLen; n++){
				email[n] = emails[n].getEmailAdd();
				}
				//send email. set up email message
				
				String sub = "Ride Delayed";
				String msg = "Your ride from " + frm + " to " + to + " on "+dt+ " has been delayed for " + delayHours + " hrs. the new pickup time is " + time;
				
			if(i!=0){	
				MultiEmail.sendEmailToAll(email, sub, msg);
				request.setAttribute("msg", "Ride_ID " + rideId + " delayed and passengers have been notified.");
				request.getRequestDispatcher("Driver.jsp").forward(request, response);
			} else {request.setAttribute("msg", "Ride_ID " + rideId + " has not been delayed. Something went wrong try again.");
			request.getRequestDispatcher("Driver.jsp").forward(request, response);}
		}
		else if("Book Now".equals(actionDetector)){
			// get your userid(you are the passenger here, bcz you are logged in)  
			  HttpSession session = request.getSession();
			  String uname = session.getAttribute("usern").toString();
			  UserDaoInter u = new UserDaoImpl();
			  //get passenger userID
			  int passengerID = u.getUserID(uname);
			  String [] passengerDetail = u.getUserById(passengerID);
			  String passEmail = passengerDetail[2];			 
			  
			 int ridID = Utilities.getIntParameter(request, "Ride_ID", -1);
			 int driverID = Utilities.getIntParameter(request, "Driver_ID", -1);
			 
			 String frm = request.getParameter("frm");
			
			 String to = request.getParameter("to");
			 String dt = request.getParameter("dt");
			 String time = request.getParameter("tm");
			 int bookedSeat = Utilities.getIntParameter(request, "bkseat", -1);
			 double totalfare = Utilities.getDoubleParameter(request, "totalf", -1);
			 //store orignal fare person on the booking table
			double farePerPerson = Utilities.getDoubleParameter(request, "fr", -1);
			 //step 2. pass data and create a booking model class object
			 
			 Booking c = new Booking();
			 c.setRideID(ridID);
			 c.setDriver_Id(driverID);
			 c.setPassenger_Id(passengerID);
			 c.setFrom(frm);
			 c.setTo(to);
			 c.setDate(dt);
			 c.setTime(time);
			 c.setBookdSeat(bookedSeat);
			 c.setTotalFare(totalfare);
			 c.setFare(farePerPerson);
			 c.setEmailAdd(passEmail);
			 
			 // step 3: pass data to RideDao and ask Ridedao to store data into Booking table
			 // first, lets get driver's profile info
			//getting drivers name and phone number
			 String userName =null;
			 int phoneNumber =0;
				Connection con = Utilities.connect();
			try{
				String sql = "Select * From User WHERE User_ID=?";
				PreparedStatement pss = con.prepareStatement(sql);
				pss.setInt(1, c.getDriver_Id());
				ResultSet rs = pss.executeQuery();
				
			if (rs.next()){
				 userName = rs.getString("Full_Name");
				phoneNumber = rs.getInt("Phone");
			}
			}catch(SQLException e){ e.printStackTrace();}	
			//step 3:
			//create a ride object in order to update the new seat
			Ride x = new Ride(); 
			x.setRide_ID(ridID);
			x.setFrom(frm);
			x.setTo(to);
			x.setDate(dt);
			x.setTime(time); 
			x.setFare(Utilities.getDoubleParameter(request, "fr", -1)); 
			x.setSeatsBooked(bookedSeat);
			//getting number of currently available seat from ride table and earnings
			RideDao g = new RideDaoImpl();
			 Ride k = g.getPostingByRideID(ridID);
			int seatold = k.getSeat();
			int seatBooked = k.getSeatsBooked();
			int updateseat = seatold - bookedSeat;
			
			int updateSeatBooked = seatBooked + bookedSeat;
			
			x.setSeat(updateseat);
			x.setSeatsBooked(updateSeatBooked);
			
			//update earning
			double earning = k.getEarning();
			double updatearing = earning + totalfare;
			x.setEarning(updatearing);
			RideDao w = new BookingImpl();
			//now dao saves data into booking table
			 int i = g.booking(c);
			 if(i!=0){
				 //update the number of seats on the ride table
				 // and add the number of booked seats on the ride table
				w.updateRide(x);
				// booking confirmation page
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
						"Transitional//EN\">\n"
						+ "<html><head><title>"+"Booking Confirmation"+"</title>"+
						
						"<link rel=\"stylesheet\" type=\"text/css\" href=\"/AhmadiCIS4665/css/stylesheet.css\">"
						+ "</head>"
						+ "<body  style=\"align:center\" fcolor =\"white\" bgcolor=\"blue\">"+
						"<font color=\"white\">"+
						
							"<Form action=\"Forwarder\" method=\"post\">"+
							"<ul>" +
							"<li><input type=\"submit\" class=\"button1\" name=\"act\" value=\"Home\">"+
							"<li><input type=\"submit\" class=\"button2\" name=\"act\" value=\"Search Ride\">"+
							"</Form>"+
							"<Form action=\"BookingStatus\" method=\"post\">"+
							"<li><input type=\"submit\" class=\"button3\" name=\"act\" value=\"My Booking\">"+
							"</Form>"+
							"<Form action=\"Forwarder\" method=\"post\">"+
								"<li style=\"float:right\"><input type=\"submit\" class=\"button4\" name=\"act\" value=\"Signout\">"+
									"</ul>"+
									"</Form>"+
									"<div style=\"border: 1px solid white; width:50%; margin-left:0; margin-top:0; margin-bottom:2px; height:265px; padding-left:30px; background:#52a046\">"+
									"<p> Confirmation: You successfully booked a ride</p>"+"<p> Please contact the driver.</p>"+
									"<p>Driver Name:</p>" +userName +
									"<p>Driver Phone:</p>" +phoneNumber+ "</div>"+
						"</body>");
		
			 }else {
				 response.sendRedirect("SearchRide.jsp");
			 }
		 }
else if("Cancel".equals(actionDetector)){
		 int bookingID = Utilities.getIntParameter(request, "BkingID", -1);
		 double farePaid = Utilities.getDoubleParameter(request, "farepp", -1);
		 int sb = Utilities.getIntParameter(request, "seatBooked", -1);
		 double ppp = Utilities.getDoubleParameter(request, "farep", -1);
		 int rideID = Utilities.getIntParameter(request, "rideID", -1);
		 int drvrID = Utilities.getIntParameter(request, "driverID", -1);
		 String frm = request.getParameter("frm");
		 String to = request.getParameter("to");
		 request.setAttribute("frm", frm);
		 request.setAttribute("to", to);
		 request.setAttribute("driverID", drvrID);
		 request.setAttribute("rID", rideID);
		 request.setAttribute("sB", sb);
		 request.setAttribute("bID", bookingID);
		 request.setAttribute("fp", farePaid);
		 request.setAttribute("fpp", ppp);
		 request.getRequestDispatcher("CancelRideRequest.jsp").forward(request, response);
		 
	 }
		
		//rider cancels booking. invoked from CancelRideRuquest.jsp
else if ("Cancel Ride".equals(actionDetector)){
		int drvrID = Utilities.getIntParameter(request, "drverID", -1);
		int bookingID = Utilities.getIntParameter(request, "bookingID", -1);
		int seatBked = Utilities.getIntParameter(request, "seatBooked", -1);
		double farePaid = Utilities.getDoubleParameter(request, "farePaid", -1);
		int seatNo = Utilities.getIntParameter(request, "seatNumber", -1);
		double farePerPerson = Utilities.getDoubleParameter(request, "farePPerson", -1);
		double returnFare = seatNo*farePerPerson;
		double fareFixed = farePaid - returnFare;
		int rideID = Utilities.getIntParameter(request, "rideID", -1);
		String from = request.getParameter("frm");
		String to = request.getParameter("to");
		//update seat
		int seatFixed = seatBked - seatNo;
		//update drivers earning. get drivers current earning and subtract the cancellation fare
		//ask dao to get driver earning
		RideDao g = new RideDaoImpl();
		Ride b = g.getPostingByRideID(rideID);
		//update current driver earnings
		double drEarning = b.getEarning();
		double fixedEarning = drEarning - returnFare;
		//update seat available
		int seatAvailable = b.getSeat();
		int availableSeatFixed = seatAvailable + seatNo;
		//update number of seats booked
		int seatBooked = b.getSeatsBooked();
		int seatBookedFixed = seatBooked -seatNo;
		Ride x = new Ride();
		x.setEarning(fixedEarning);
		x.setRide_ID(rideID);
		x.setSeat(availableSeatFixed);
		x.setSeatsBooked(seatBookedFixed);
		x.setFrom("None");//this ensure the right part of method updateRide() is invoked on bookingImpl class
		RideDao w = new BookingImpl();//once the passengers seat is cancelled then update drivers earning bellow inside if statement
		
		//pass data to model- create booking object
		Booking c = new Booking();
		c.setBkingID(bookingID);
		c.setBookdSeat(seatFixed);
		c.setTotalFare(fareFixed);
		
		if(seatFixed ==0){
			c.setStatus("Cancelled");
		}else{
		c.setStatus("OnTime");
		}
		//pass booking object to DAO and ask DAO to update booking table
		RideDao a = new BookingImpl();
		int i = a.booking(c);
		UserDaoInter k =  new UserDaoImpl();
		 String [] drvrEmai = k.getUserById(drvrID);
		 System.out.println(drvrEmai[2]);
		 String driverEmail = drvrEmai[2];
		 String sub = "Pickup Cancelled";
		 String text = "One of your bookings from " + from + " to " +to +" has been cancelled.";
		if(i!=0){
			SendEmail.sendEmailNow(driverEmail, sub, text);
			w.updateRide(x); //updating drivers earnings
			request.setAttribute("bookingCancelled", "Your cancellation was successful.");
			request.getRequestDispatcher("Rider.jsp").forward(request, response);
		} else{
		request.setAttribute("cancelFailed", "Your cancellation FAILLED try again later.");
		request.getRequestDispatcher("Rider.jsp").forward(request, response);
		}
}
// SELECT. call from Select button from searchResult page.
		else {
			int num = Utilities.getIntParameter(request, "u", -1);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
	  	RideDao d = new RideDaoImpl();
	  	//get the ride offer details
	  	Ride c = d.getPostingByRideID(num);
	  	int driverid = c.getUser_ID();
	  	//getting driver profile detail
	  	UserDaoInter a = new UserDaoImpl();
	  	String[] userProf = a.getUserById(driverid);

	  	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
									"Transitional//EN\">\n"
									              
									+ "<html>"
									+ "<head>"
									+ "<title>"+"Booking"+
									"</title>"+
									
									"<link rel=\"stylesheet\" type=\"text/css\" href=\"/AhmadiCIS4665/css/stylesheet.css\">"
									
									+ "</head>"
									+ "<body  style=\"align:center\" fcolor =\"white\" bgcolor=\"blue\">"+
									"<font color=\"white\">"+
									// starts. navigation buttons
										"<Form action=\"Forwarder\" method=\"post\">"+
										"<ul>" +
										"<li><input type=\"submit\" class=\"button1\" name=\"act\" value=\"Home\">"+
										"<li><input type=\"submit\" class=\"button2\" name=\"act\" value=\"Search Ride\">"+

										"<li><input type=\"submit\" class=\"button3\" name=\"act\" value=\"My Booking\">"+

											"<li style=\"float:right\"><input type=\"submit\" class=\"button4\" name=\"act\" value=\"Signout\">"+
												"</ul>"+
												"</Form>"+
							//ends-navigation button
		"<div class =\"titleBooking\"><h1> Amazing Ride Share </h1></div>"+
									
			"<div class =\"containerbooking\">"+
						"<div class=\"formBoxBooking\"> "+
							"<Form action = \"BookingContoller\" method=\"post\">"+
								"<div class =\"left\">"+
									"<input type=\"hidden\" class=\"booking\" name= \"Ride_ID\" value="+
									c.getRide_ID() +
									">" + 
	  								"<input type=\"hidden\" class=\"booking\" name= \"Driver_ID\" value=" +
									c.getUser_ID() +
									">"+
	  								"<p>From:</p>"+"</label><input type=\"text\" class=\"booking\" name= \"frm\" value='" +
									c.getFrom() +
									"' readonly>"+
									"<p>To:</p>"+"</label><input type=\"text\" class=\"booking\" name= \"to\" value='"+
									c.getTo() + "'readonly>"+
									"<p>Date:</p>"+"</label> <input type=\"text\" class=\"booking\" name= \"dt\" value="+
	  								c.getDate() +
	  								" readonly>"+
	  								"<p>Time:</p>"+"</label><input <input type=\"text\" class=\"booking\" name= \"tm\" value=" +
	  								c.getTime() +
	  								" readonly>"+
	  								"<p>Fare/Person:</p>"+"<input type=\"text\"  id= \"fare\" name=\"fr\" value=" +
	  								c.getFare() +
	  								" readonly>" +
	  								"<p>Available Seat:</p>"+"<input type=\"text\" class=\"booking\"  name= \"seat\" value="+
	  								c.getSeat() +
	  								" readonly>" + 
	  		
	  								"<p style=\"color:yellow\">How many seats do you want?</p>"+"<input type=\"number\" class=\"booking\" name=\"bkseat\" id=\"bseat\" min=\"1\" max="+
	  								c.getSeat()+
	  								">"
	  								+
	  								"</p><p>Total"+"<input type=\"text\" id=\"total\" name=\"totalf\" readonly style=\"width: 50%;\">"+
	  								"<div class=\"subButton\"><input type=\"submit\" class=\"booknow\" name=\"act\" value=\"Book Now\"></div>"+
	  						"</div>"+
	  					"<div class =\"right\"> "+
								"<div class=\"box\">"+
									"<h3> Driver's Profile</h3>"+
									"<p>Driver's Name</p>" +
									"<input type=\"text\" name=\"uname\" value='"
									+ userProf[0]
									+ "'readonly>"+
									"<p>Phone Number</p>"+
									"<input type=\"text\" name=\"phone\" value='"+
									userProf[1] +
									"'readonly>"+
									"<p>Email Address:</p>"+
									"<input type=\"text\" name=\"pass\" value='" +
									userProf[2] +
									"'readonly> "+
									"<p style=\"padding-top: 15px;\">IMPORTANT: At this time, Amazing Ride Share does not process transactions. You have to contact your driver and discuss the pickup locations and any other concern. You will pay directly to the driver by form of cash or check. Drivers collect their fees before the trip starts.</p>" 
									+"</div>"+
								"</div>	"+
							"</div>"+
	  								"</Form>"+
							"<div class=\"subButton1\" style=\"margin-bottom:10px\">"+
	  							"<input type=\"submit\" class=\"updatTotalBking\" onclick=\"calc();\"  value=\"Update Fare\">"+
	  							"</div>"+
					"</div>"+
			"</div>"+		
						"<script>"+
"function calc(){"+
	"var s = parseFloat(document.getElementById('fare').value);"+
	"var f = parseFloat(document.getElementById('bseat').value);"+
	
	"document.getElementById('total').value=s*f;}"+
"</script>"+ 
	"</body>"
	 );
		}
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}