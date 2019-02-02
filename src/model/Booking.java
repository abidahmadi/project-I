package model;

public class Booking {
	private int BkingID;
	private int rideID;
	private int driver_Id;
	private int passenger_Id;
	private String from;
	private String to;
	private String date;
	private String time;
	private int bookdSeat;
	private double fare;
	private double totalFare;
	private int nOfBooking;
	private String status;
	private String emailAdd;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getBkingID() {
		return BkingID;
	}
	public void setBkingID(int bkingID) {
		BkingID = bkingID;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public int getRideID() {
		return rideID;
	}
	public void setRideID(int rideID) {
		this.rideID = rideID;
	}
	
	public int getDriver_Id() {
		return driver_Id;
	}
	public void setDriver_Id(int driver_Id) {
		this.driver_Id = driver_Id;
	}
	public int getPassenger_Id() {
		return passenger_Id;
	}
	public void setPassenger_Id(int passenger_Id) {
		this.passenger_Id = passenger_Id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getBookdSeat() {
		return bookdSeat;
	}
	public void setBookdSeat(int bookdSeat) {
		this.bookdSeat = bookdSeat;
	}
	public double getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}
	public int getnOfBooking() {
		return nOfBooking;
	}
	public void setnOfBooking(int nOfBooking) {
		this.nOfBooking = nOfBooking;
	}
	public String getEmailAdd() {
		return emailAdd;
	}
	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
	
}
