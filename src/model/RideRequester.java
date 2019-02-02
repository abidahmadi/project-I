package model;

public class RideRequester {
	
	private int driverID;
	private int riderID;
	private String riderName;
	private String pickup_Location;
	private String destination;
	private String rideRequest;
	private String status;
	private String driverName;
	private int bookingID;
	
	
	
	
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRideRequest() {
		return rideRequest;
	}
	public void setRideRequest(String rideRequest) {
		this.rideRequest = rideRequest;
	}
	public int getDriverID() {
		return driverID;
	}
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	public int getRiderID() {
		return riderID;
	}
	public void setRiderID(int riderID) {
		this.riderID = riderID;
	}
	public String getRiderName() {
		return riderName;
	}
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public String getPickup_Location() {
		return pickup_Location;
	}
	public void setPickup_Location(String pickup_Location) {
		this.pickup_Location = pickup_Location;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	

}
