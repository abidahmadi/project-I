package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;


import dao.RideDao;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import model.Booking;
import model.Ride;
import model.RideSearcher;
import model.User;
import serviceUtility.Utilities;

public class RideDaoImpl implements RideDao{

	@Override //post ride
	public int postRide(Ride m) {
		int i=0;
		try{
			Connection con=Utilities.connect();     
			String sql = "insert into Ride(Driver_ID, `From`, `To`, Date, time, Fare, Seat, Status) values(?, ?, ?, ?, ?, ?, ?,?)";
			PreparedStatement pss = con.prepareStatement(sql);
			
			pss.setInt(1, m.getUser_ID());
			pss.setString(2, m.getFrom());
			pss.setString(3, m.getTo());
			pss.setString(4, m.getDate());
			pss.setString(5, m.getTime());
			pss.setDouble(6, m.getFare());
			pss.setInt(7, m.getSeat());
			pss.setString(8, "Offline");
			
			i =pss.executeUpdate();
			
		}catch(SQLException e){e.printStackTrace();
		System.out.println("error is here at postRide Dao class");}
		
		return i;
	}
	@Override
	public Ride getPostingByRideID(int m) {
		Ride c = new Ride();
		Connection con = Utilities.connect();
		try{
				String sql = "Select * FROM Ride WHERE Ride_ID= '"+m+"'";
				PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
				
		while(rs.next()){
			c.setRide_ID(rs.getInt("Ride_ID"));
			c.setUser_ID(rs.getInt("Driver_ID"));
			c.setFrom(rs.getString("From"));
			c.setTo(rs.getString("To"));
			c.setDate(rs.getString("Date"));
			c.setTime(rs.getString("Time"));
			c.setSeat(rs.getInt("Seat"));
			c.setFare(rs.getDouble("Fare"));
			c.setEarning(rs.getDouble("Earning"));
			c.setSeatsBooked(rs.getInt("Seat_Booked"));
		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return c;
	}
	@Override
	public int updateRide(Ride m) {
		int i = 0;
		//updat ride 
		if(m.getStatus() == null){
		
			try{
			Connection con = Utilities.connect();
			int id = m.getRide_ID();
			
			String query = "Update Ride set `From`= ?, `To`=?, Date=?, Time=?, Seat=?, Fare=? WHERE Ride_ID='"+id+"'";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,m.getFrom());
			ps.setString(2, m.getTo());
			ps.setString(3, m.getDate());
			ps.setString(4, m.getTime());
			ps.setInt(5, m.getSeat());
			ps.setDouble(6, m.getFare());
			
			i = ps.executeUpdate();
		
			}catch(SQLException e){e.printStackTrace();}
		// update status 
		}else {
			Connection con = Utilities.connect();
			int driverID = m.getUser_ID();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());
			
			try{
				String qry = "Update Ride set Status= ? WHERE Driver_ID=? and Date>='"+curDate+"'";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setString(1, m.getStatus());
				ps.setInt(2, m.getUser_ID());
				
				i = ps.executeUpdate();
				
			}catch(SQLException e){e.printStackTrace();}
			
		}
		
		return i;
	}
	@Override
	public int deleteRide(Ride m) {
		int i=0;
		try{
			Connection con = Utilities.connect();
			int id = m.getRide_ID();
			String query = "Delete FROM Ride WHERE Ride_ID='"+id+"'";
			PreparedStatement ps = con.prepareStatement(query);
			
			i = ps.executeUpdate();
		}catch(SQLException e){e.printStackTrace();}
		return i;
	}
	@Override // return number of rides that match the search ride criteria
	public int findRide(RideSearcher m) {
		int foundRide = 0;
		try{
			Connection con = Utilities.connect();
			int uid = m.getUser_ID();
			PreparedStatement cntps = con.prepareStatement("Select count(*) AS countRow From Ride Where `From` = ? and `To`=? and Date=? and Driver_ID<>? and Seat>0 and Status=? ");
			cntps.setString(1, m.getFrom());
			cntps.setString(2, m.getTo());
			cntps.setString(3, m.getDate());
			cntps.setInt(4, m.getUser_ID());
			cntps.setString(5, m.getStatus());
			ResultSet rs = cntps.executeQuery();
			while(rs.next()){
				foundRide = rs.getInt("countRow");
			}
		}catch(SQLException e){e.printStackTrace(); System.out.println("this is method findRides in RidDaoImpl");}
			
		return foundRide;
	}
	@Override
	public Ride[] searchRide(RideSearcher m, int arrayLen) {
		int count = 0;
		Ride[] c=null;
		try{
			Connection con = Utilities.connect();
		
			String query = "Select * FROM Ride WHERE `From` = ? and `To`=? and Date=? and Driver_ID<>? and Seat>0 and Status=?";

			PreparedStatement ps = con.prepareStatement(query);
		
			ps.setString(1, m.getFrom());
			ps.setString(2, m.getTo());
			ps.setString(3, m.getDate());
			ps.setInt(4,m.getUser_ID());
			ps.setString(5, m.getStatus());
			
			ResultSet rs = ps.executeQuery();
			c = new Ride[arrayLen];
		
			int i=0;
			while(rs.next()){
				
					c[i] = new Ride();
					c[i].setRide_ID(rs.getInt("Ride_ID"));
					c[i].setUser_ID(rs.getInt("Driver_ID"));
					c[i].setFrom(rs.getString("From"));
					c[i].setTo(rs.getString("To"));
					c[i].setDate(rs.getString("Date"));
					c[i].setTime(rs.getString("Time"));
					c[i].setSeat(rs.getInt("Seat"));
					c[i].setFare(rs.getDouble("Fare"));
					//c[i].setUser_ID(rs.getInt("Driver_ID"));
					i=i+1;
			}
		}catch(SQLException e){e.printStackTrace(); System.out.println("Match not found");}

		return c;
	}

	@Override
	public int booking(Booking m) {
		// post bookings into booking table
		int i =0;
		try{
			Connection con = Utilities.connect();
			String qry = "INSERT into Booking (Ride_ID, Driver_ID, Passenger_ID, `From`, `To`, Ride_Date, Time, Seat_Booked, FarePerPerson, Fare, Email_Address) values(?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(qry);
			
			ps.setInt(1, m.getRideID());
			ps.setInt(2, m.getDriver_Id());
			ps.setInt(3, m.getPassenger_Id());
			ps.setString(4, m.getFrom());
			ps.setString(5, m.getTo());
			ps.setString(6, m.getDate());
			ps.setString(7, m.getTime());
			ps.setInt(8, m.getBookdSeat());
			ps.setDouble(9, m.getFare());
			ps.setDouble(10, m.getTotalFare());
			ps.setString(11, m.getEmailAdd());
			
			i = ps.executeUpdate();
		}catch(SQLException e){e.printStackTrace();
		System.out.println("error is here at booking in RideDaoImpl classe");}
		
		return i;
	}
	@Override
	public Booking[] getBookings(int driverID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Booking[] myBookings(int passengerID) {
		//here passengerID = rideID;
		Booking[] psgrs =null;
		String count = "Select count(*) AS rowNo FROM Booking WHERE Ride_ID='"+passengerID+"' AND Status<>'Cancelled'";
		int recordNo = Utilities.countRowNo(count);
		
		Connection con = Utilities.connect();
		String qry = "SELECT * FROM Booking WHERE Ride_ID='"+passengerID+"' AND Status<>'Cancelled'";
		psgrs = new Booking[recordNo];
		try{
			PreparedStatement ps = con.prepareStatement(qry);
			ResultSet rs = ps.executeQuery();
			int i = 0;
			while(rs.next()){
				psgrs[i] = new Booking();
				
				psgrs[i].setEmailAdd(rs.getString("Email_Address"));
				System.out.println(psgrs[i]);
				psgrs[i].setFrom(rs.getString("From"));
				psgrs[i].setTo(rs.getString("To"));
				psgrs[i].setDate(rs.getString("Ride_Date"));
				i++;
				
			}
		}catch(SQLException g){g.printStackTrace(); System.out.println("error here at myBookings() in RideDaoImpl getting passenger emails"); }
		
		
		return psgrs;
	}
	@Override
	public int delayRide(String time, int rideId) {
		int i =0;
		Connection con = Utilities.connect();
		String qryRide = "Update Ride SET Time =? WHERE Ride_ID = ?";
		String qryBook = "Update Booking SET Time =? WHERE Ride_ID = ?";
		String sqlBooking = "Update Booking SET Status = 'Delayed' WHERE Ride_ID='"+rideId+"' and Status = 'OnTime'";
		try{
			PreparedStatement ps = con.prepareStatement(qryRide);
			ps.setString(1, time);
			ps.setInt(2, rideId);
			i = ps.executeUpdate();
			ps.close();
		}catch(SQLException e){e.printStackTrace();System.out.println("error at delayRideDaoImp update Ride table time");}
		
		try{
			PreparedStatement psBook = con.prepareStatement(qryBook);
			psBook.setString(1, time);
			psBook.setInt(2, rideId);
			psBook.executeUpdate();
			psBook.close();
		}catch(SQLException ec){ec.printStackTrace(); System.out.println("error at delayRideDaoImp update booking table time");}
		
		try{
			PreparedStatement psB = con.prepareStatement(sqlBooking);
			psB.executeUpdate();
			psB.close();
			con.close();
		}catch(SQLException eg){eg.printStackTrace(); System.out.println("error at delayRideDaoImp update booking table Status");}
		
		return i;
	}
	@Override
	public int cancelRide(Ride m) {
		int i =0;
		Connection con = Utilities.connect();
		
		try{
			String qry = "Update Ride set Cancelation= ? WHERE Ride_ID=?";
			PreparedStatement ps = con.prepareStatement(qry);
			ps.setString(1, m.getStatus());
			ps.setInt(2, m.getRide_ID());
			
			i = ps.executeUpdate();
			
		}catch(SQLException e){e.printStackTrace();}
		
		return i;
	}
	@Override
	public  Ride[] canelationReport(int driverId, String dt, int rowN)  {
		
		Ride[] c=null;
		try{
			Connection con = Utilities.connect();
			String query = "Select * FROM Ride WHERE  Driver_ID=? and Date>='"+dt+"' and  Cancelation='Cancelled' ";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, driverId);
			ResultSet rs = ps.executeQuery();
			c = new Ride[rowN];
			int i=0;
			while(rs.next()){
				
					c[i] = new Ride();
					c[i].setRide_ID(rs.getInt("Ride_ID"));
					c[i].setUser_ID(rs.getInt("Driver_ID"));
					c[i].setFrom(rs.getString("From"));
					c[i].setTo(rs.getString("To"));
					c[i].setDate(rs.getString("Date"));
					c[i].setTime(rs.getString("Time"));
					c[i].setSeat(rs.getInt("Seat"));
					c[i].setFare(rs.getDouble("Fare"));
					c[i].setStatus(rs.getString("Cancelation"));
					i=i+1;
			}
		}catch(SQLException e){e.printStackTrace(); System.out.println("Match not found");}

		return c;
	}
	
}
