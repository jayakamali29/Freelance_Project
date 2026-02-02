package com.freelance.bean;
public class Project {
	private int projectID;
	private String clientUserID;
	private String projectTitle;
	private String projectDescription;
	private java.math.BigDecimal budgetMin;
	private java.math.BigDecimal budgetMax;
	private java.sql.Date postedDate;
	private String status;
	private Integer awardedBidID;
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getClientUserID() {
		return clientUserID;
	}
	public void setClientUserID(String clientUserID) {
		this.clientUserID = clientUserID;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public java.math.BigDecimal getBudgetMin() {
		return budgetMin;
	}
	public void setBudgetMin(java.math.BigDecimal budgetMin) {
		this.budgetMin = budgetMin;
	}
	public java.math.BigDecimal getBudgetMax() {
		return budgetMax;
	}
	public void setBudgetMax(java.math.BigDecimal budgetMax) {
		this.budgetMax = budgetMax;
	}
	public java.sql.Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(java.sql.Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAwardedBidID() {
		return awardedBidID;
	}
	public void setAwardedBidID(Integer awardedBidID) {
		this.awardedBidID = awardedBidID;
	}
	
	

}