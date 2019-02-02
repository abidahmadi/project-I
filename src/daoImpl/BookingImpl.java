package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql. Statement;


import dao.RideDao;
import model.Booking;
import model.Ride;
import model.RideSearcher;
import serviceUtility.Utilities;

public class BookingImpl implements RideDao{

	@Override
	public int postRide(Ride m) {
		// TODO Auto-generated method stub
		return 0;
	}
	//performs two task based on the if statement
	@Override
	public int updateRide(Ride m) {
		Connection con = Utilities.connect();
		int i = 0;
		String from = m.getFrom();
	if(from.equals("None")){//updates ride in the ride table after cancellation
			String qry = "Update Ride SET Earning=?, Seat=?, Seat_Booked=? WHERE Ride_ID=?";
			try{ 
				PreparedStatement ps2 = con.prepareStatement(qry);
				ps2.setDouble(1, m.getEarning());
				ps2.setInt(2, m.getSeat());
				ps2.setInt(3, m.getSeatsBooked());
				ps2.setInt(4, m.getRide_ID());
				i = ps2.executeUpdate();
				System.out.println("this is updateing drivers earning:"+ i);
			}catch(SQLException h){h.printStackTrace();}
	} else {//updates ride in the ride table after booking is placed
		try{
			int id = m.getRide_ID();
			
			String query = "Update Ride set `From`= ?, `To`=?, Date=?, Time=?, Seat=?, Fare=?, Seat_Booked=?, Earning=? WHERE Ride_ID='"+id+"'";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,m.getFrom());
			ps.setString(2, m.getTo());
			ps.setString(3, m.getDate());
			ps.setString(4, m.getTime());
			ps.setInt(5, m.getSeat());
			ps.setDouble(6, m.getFare());
			ps.setInt(7, m.getSeatsBooked());
			ps.setDouble(8, m.getEarning());
			
			i = ps.executeUpdate();
			
		}catch(SQLException e){e.printStackTrace();}
	}
		return i;
	}

	@Override
	public int deleteRide(Ride m) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Ride getPostingByRideID(int m) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int findRide(RideSearcher m) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Ride[] searchRide(RideSearcher m, int noOfRide) {
		// TODO Auto-generated method stub
		return null;
	}
	// this method was used in BookingController class where passengers cancel booking
	@Override
	public int booking(Booking m) {
		int i = 0;

		String qry = "Update Booking SET Seat_Booked= ?, Fare=?, Status =? WHERE Booking_ID=?";
		Connection con = Utilities.connect();
		try{
			PreparedStatement ps = con.prepareStatement(qry);
			ps.setInt(1, m.getBookdSeat());
			ps.setDouble(2, m.getTotalFare());
			ps.setString(3, m.getStatus());
			ps.setInt(4, m.getBkingID());
			i = ps.executeUpdate();
		}catch(SQLException c){c.printStackTrace(); System.out.println("This error is from BookingImpl class method booking.");}
		return i;
	}

	@Override
	public Booking[] getBookings(int driverID) {
		Booking [] bookings = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = dateFormat.format(new Date());
		int maxSize = 0;
		String qry = "Select * FROM Booking WHERE Driver_ID=? and Ride_Date>=? and (Status='OnTime' or Status = 'Delayed')";
		String query = "Select count(*) AS rowNo FROM Booking WHERE Driver_ID='"+driverID+"' and (Status='OnTime' or Status = 'Delayed') and Ride_Date>='"+curDate+"'";
		try{
			Connection con = Utilities.connect();
				//count number of bookings and assign it as max length of Booking []array.
						Statement st = con.createStatement();
						ResultSet rst = st.executeQuery(query);
						while(rst.next()){
							 maxSize =rst.getInt("rowNo");
						}
			}catch(SQLException e)
			{e.printStackTrace(); System.out.println("This error is from cheking number of bookings available. getBooking()");}
					
		try{
			Connection con = Utilities.connect();
						PreparedStatement ps = con.prepareStatement(qry);
						ps.setInt(1, driverID);
						ps.setString(2, curDate);
						ResultSet rs = ps.executeQuery();
						bookings = new Booking[maxSize];
						int i = 0;
						while(rs.next()){
							bookings[i]= new Booking();
							bookings[i].setBkingID(rs.getInt("Booking_ID"));
							bookings[i].setRideID(rs.getInt("Ride_ID"));
							bookings[i].setDriver_Id(rs.getInt("Driver_ID"));
							bookings[i].setPassenger_Id(rs.getInt("Passenger_ID"));
							bookings[i].setFrom(rs.getString("From"));
							bookings[i].setTo(rs.getString("To"));
							bookings[i].setDate(rs.getString("Ride_Date"));
							bookings[i].setTime(rs.getString("Time"));
							bookings[i].setFare(rs.getDouble("Fare"));
							bookings[i].setBookdSeat(rs.getInt("Seat_Booked"));
							bookings[i].setStatus(rs.getString("Status"));
							i++;
							}
						}catch(SQLException e)
						{e.printStackTrace(); System.out.println("the error is here in the getBooking method at riderdaoimpl");}
		return bookings;
	}
	// in ride page passenger chechs his/her bookings
	@Override
	public Booking[] myBookings(int passengerID) {
		Booking [] bookings = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = dateFormat.format(new Date());
		int maxSize = 0;
		String qry = "Select * FROM Booking WHERE Passenger_ID=? and (Status='OnTime' or Status = 'Delayed') and Ride_Date>=?";
		
		String query = "Select count(*) AS rowNo FROM Booking WHERE Passenger_ID='"+passengerID+"' and (Status='OnTime' or Status = 'Delayed') and Ride_Date>='"+curDate+"'";
		
		try{
			Connection con = Utilities.connect();
				//count number of bookings and assign it as max length of Booking []array.
						Statement st = con.createStatement();
						ResultSet rst = st.executeQuery(query);
						while(rst.next()){
							 maxSize =rst.getInt("rowNo");
							
						}
			}catch(SQLException e)
			{e.printStackTrace(); System.out.println("This error is from cheking number of bookings available. getBooking()");}
					
		try{
			Connection con = Utilities.connect();
						PreparedStatement ps = con.prepareStatement(qry);
						ps.setInt(1, passengerID);
						ps.setString(2, curDate);
						ResultSet rs = ps.executeQuery();
						bookings = new Booking[maxSize];
		
						int i = 0;
						
						while(rs.next()){
							
							bookings[i]= new Booking();
							bookings[i].setBkingID(rs.getInt("Booking_ID"));
							bookings[i].setRideID(rs.getInt("Ride_ID"));
							bookings[i].setDriver_Id(rs.getInt("Driver_ID"));
							bookings[i].setPassenger_Id(rs.getInt("Passenger_ID"));
							bookings[i].setFrom(rs.getString("From"));
							bookings[i].setTo(rs.getString("To"));
							bookings[i].setDate(rs.getString("Ride_Date"));
							bookings[i].setTime(rs.getString("Time"));
							bookings[i].setTotalFare(rs.getDouble("Fare"));
							bookings[i].setFare(rs.getDouble("FarePerPerson"));
							bookings[i].setBookdSeat(rs.getInt("Seat_Booked"));
							bookings[i].setStatus(rs.getString("Status"));
							
							i++;
							}
						}catch(SQLException e)
						{e.printStackTrace(); System.out.println("the error is here in the getBooking method at riderdaoimpl");}
		return bookings;
	}
	@Override
	public int delayRide(String time, int rideId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int cancelRide(Ride m) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public Ride[] canelationReport(int driverId, String dt, int rowN) {
		// TODO Auto-generated method stub
		return null;
	}
}
	


