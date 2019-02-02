package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RideDao;
import dao.UserDaoInter;
import daoImpl.RideDaoImpl;
import daoImpl.UserDaoImpl;
import model.Ride;
import serviceUtility.Utilities;

@WebServlet("/EditPosting")
public class PostingEditor extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionDetector = request.getParameter("act");
		if("Back".equals(actionDetector)){
			response.sendRedirect("Postings.jsp");
		}else if("Update".equals(actionDetector)){
		
		int rideId = Utilities.getIntParameter(request, "rideId", -1);
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		int seat = Utilities.getIntParameter(request, "seat",-1);
		double fares = Utilities.getDoubleParameter(request, "fare",-1);
		
		if (from==null || from.trim().equals("") || to ==null || to.trim().equals("") || date==null || date.trim().equals("") || time ==null ||time.trim().equals("")|| seat<=0 || fares <=0 ){
			request.setAttribute("msg", "All fields are mandatory.");
			request.getRequestDispatcher("/EditRide.jsp").forward(request, response);
		}else{
			
			//2**pass data to the class model object		
			Ride m = new Ride();
			m.setFrom(from);
			m.setTo(to);
			m.setDate(date);
			m.setTime(time);
			m.setFare(fares);
			m.setSeat(seat);
			m.setRide_ID(rideId);
			
			//3. call method in DAO class to insert data in a table
			//but first get the User_ID from the mysql and insert it into Ride table so that it should be known who posted the offer
			
			RideDao d = new RideDaoImpl();
	
			int i = d.updateRide(m);
			if(i!=0){
			request.setAttribute("updatemsgfromEditPosting"," SUCCESSFULL: Your offer has been updated.");
			request.getRequestDispatcher("/Postings.jsp").forward(request, response);
			}else{
				request.setAttribute("failedupdatefrmEditPsoting", "Update Failed. Something went wrong");
				request.getRequestDispatcher("/Postings.jsp").forward(request, response);
			}	
		}
	}else if("Delete".equals(actionDetector)){
		
		int rideId = Utilities.getIntParameter(request, "rideId", -1);
		
		String from = request.getParameter("frm");
		String to = request.getParameter("target");
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		int seat = Utilities.getIntParameter(request, "seat",-1);
		double fares = Utilities.getDoubleParameter(request, "fare",-1);
		
		if (from==null || from.trim().equals("") || to ==null || to.trim().equals("") || date==null || date.trim().equals("") || time ==null ||time.trim().equals("")|| seat<=0 || fares <=0 ){
			request.setAttribute("msg", "All fields are mandatory.");
			request.getRequestDispatcher("EditRide.jsp").forward(request, response);
		}else{
			
		//2**pass data to the class model object		
		Ride m = new Ride();
		m.setFrom(from);
		m.setTo(to);
		m.setDate(date);
		m.setTime(time);
		m.setFare(fares);
		m.setSeat(seat);
		m.setRide_ID(rideId);
		
		//3. call method in DAO class to insert data in a table
		
		RideDao d = new RideDaoImpl();
		int i = d.deleteRide(m);
		if(i!=0){
				String frm = m.getFrom();
				String target = m.getTo();
				String onDate = m.getDate();
				String ontime = m.getTime();
				request.setAttribute("messages"," SUCCESSFULL:" + "Your offer from " + frm+ " to " +target+ " on "+ onDate+ " at " + ontime+ " has been DELETED" );
				request.getRequestDispatcher("Postings.jsp").forward(request, response);
		}else{
				response.sendRedirect("Postings.jsp");
		}	
		}
		}    
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
