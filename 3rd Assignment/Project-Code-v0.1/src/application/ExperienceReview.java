package application;

import java.sql.Date;

public class ExperienceReview {
	private double starApp;
	private double starStaff;
	private double starBook;
	private String details;
	private String username;
	private Date submissionDate;

    public ExperienceReview() {}

    public ExperienceReview(double starApp, double starStaff, double starBook, String details, String username, Date submissionDate) {
    	this.setStarApp(starApp);
    	this.setStarStaff(starStaff);
    	this.setStarBook(starBook);
    	this.setDetails(details);
    	this.setUsername(username);
    	this.setSubmissionDate(submissionDate);
    }

	public double getStarApp() {
		return starApp;
	}

	public void setStarApp(double starApp) {
		this.starApp = starApp;
	}

	public double getStarStaff() {
		return starStaff;
	}

	public void setStarStaff(double starStaff) {
		this.starStaff = starStaff;
	}

	public double getStarBook() {
		return starBook;
	}

	public void setStarBook(double starBook) {
		this.starBook = starBook;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	public static void insertExpRev(ExperienceReview expRev) {
		DBCommunicator.insertDBExpRev(expRev);
	}
    
}
