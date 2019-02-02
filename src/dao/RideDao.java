package dao;

import java.util.ArrayList;

import model.Booking;
import model.Ride;
import model.RideSearcher;

public interface RideDao {
	
	public int postRide(Ride m);
	
	public int updateRide(Ride m);
	
	public int delayRide(String time, int rideId);
	
	public int deleteRide(Ride m);
	
	public Ride getPostingByRideID(int m);
	
	public int findRide(RideSearcher m);
	
	public  Ride[] searchRide(RideSearcher m, int noOfRide);
	
	public int booking(Booking m);
	
	public Booking[] getBookings(int driverID);
	public Booking[] myBookings(int passengerID);
	
	public int cancelRide(Ride m);
	
	public  Ride[] canelationReport(int driverId, String dt, int rowN);

}
