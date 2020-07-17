package utils;

import java.util.Date;

public class FlightDetails {
	public FlightDetails() {
		
	}
	public FlightDetails(Date arrv, Date dept, long duration,int price, int index) {
		this.arrv = arrv;
		this.dept = dept;
		this.durationInMinutes = duration;
		this.price = price;
		this.indexOfFlight = index;
	}
	public Date getArrv() {
		return arrv;
	}
	public Date getDept() {
		return dept;
	}
	public long getDurationInMinutes() {
		return durationInMinutes;
	}
	public int getPrice() {
		return price;
	}
	public int getIndexOfFlight() {
		return indexOfFlight;
	}
	private Date arrv = new Date();
	public void setArrv(Date arrv) {
		this.arrv = arrv;
	}
	public void setDept(Date dept) {
		this.dept = dept;
	}
	public void setDurationInMinutes(long durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setIndexOfFlight(int indexOfFlight) {
		this.indexOfFlight = indexOfFlight;
	}
	private Date dept = new Date();
	private long durationInMinutes;
	private int price;
	private int indexOfFlight;	
}


