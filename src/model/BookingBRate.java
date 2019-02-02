package model;

public class BookingBRate {
	
	private int bookingID;
	private int driverId;
	private String status;
	private String progress;
	private String pickupLoc;
	private String destination;
	
	 
	public int getBookingID() {
		return bookingID;
		
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getPickupLoc() {
		return pickupLoc;
	}
	public void setPickupLoc(String pickupLoc) {
		this.pickupLoc = pickupLoc;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

}
