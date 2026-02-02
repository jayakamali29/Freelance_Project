package com.freelance.bean;

public class Bid {
	private int bidID;
	private int projectID;
	private String
	freelancerUserID;
	private java.math.BigDecimal
	bidAmount;
	private int deliveryDays;
	private String coverLetter;
	private java.sql.Date
	bidDate;
	private String bidStatus;
	public int getBidID() {
		return bidID;
	}
	public void setBidID(int bidID) {
		this.bidID = bidID;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getFreelancerUserID() {
		return freelancerUserID;
	}
	public void setFreelancerUserID(String freelancerUserID) {
		this.freelancerUserID = freelancerUserID;
	}
	public java.math.BigDecimal getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(java.math.BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}
	public int getDeliveryDays() {
		return deliveryDays;
	}
	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}
	public String getCoverLetter() {
		return coverLetter;
	}
	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}
	public java.sql.Date getBidDate() {
		return bidDate;
	}
	public void setBidDate(java.sql.Date bidDate) {
		this.bidDate = bidDate;
	}
	public String getBidStatus() {
		return bidStatus;
	}
	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}
	
	

}