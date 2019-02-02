package model;

public class BaseRateOffer {
	
	private int offerId;
	private int DriverId;
	private int riderId;
	private int maxDistance;
	private String currentLoc;
	private String destination;
	private long forMin;
	private boolean isOnline = true;
	private String rideReq;
	private String riderName;
	
	
	
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getRiderName() {
		return riderName;
	}
	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	public int getRiderId() {
		return riderId;
	}
	public void setRiderId(int riderId) {
		this.riderId = riderId;
	}
	public String getRideReq() {
		return rideReq;
	}
	public void setRideReq(String rideReq) {
		this.rideReq = rideReq;
	}
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public int getDriverId() {
		return DriverId;
	}
	public void setDriverId(int driverId) {
		DriverId = driverId;
	}
	public int getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}
	public String getCurrentLoc() {
		return currentLoc;
	}
	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}
	public long getForMin() {
		return forMin;
	}
	public void setForMin(long forMin) {
		this.forMin = forMin;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
}
