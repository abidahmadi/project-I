package model;

public class BaseSearchCriteria {

		private String pickup_location;
		private String drop_off;
		private int distance;
		private int riderID;
		
		
		
		public String getPickup_location() {
			return pickup_location;
		}
		public void setPickup_location(String pickup_location) {
			this.pickup_location = pickup_location;
		}
		public String getDrop_off() {
			return drop_off;
		}
		public void setDrop_off(String drop_off) {
			this.drop_off = drop_off;
		}
		public int getDistance() {
			return distance;
		}
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public int getRiderID() {
			return riderID;
		}
		public void setRiderID(int riderID) {
			this.riderID = riderID;
		}
		
		
		
}
