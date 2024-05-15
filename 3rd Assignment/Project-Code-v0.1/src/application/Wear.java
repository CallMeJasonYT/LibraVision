package application;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Wear extends Book{
	private int copyID;
	private String username;
	private String urlToPhoto;
	private String details;
	private Date submissionDate;

    public Wear() {}

    public Wear(int copyID, String username, String urlToPhoto, String details, Date submissionDate) {
    	this.copyID = copyID;
    	this.setUsername(username);
    	this.setUrlToPhoto(urlToPhoto);
    	this.setDetails(details);
    	this.setSubmissionDate(submissionDate);
    }

	public static List<Wear> getWear(int copyID) {
		//Search in DB Based on copyID
		List<Wear> wear = new ArrayList<>();
		//Wear w1 = new Wear(134234234, "Test User", "/misc/wornBook.jpg", "It has been damaged in the outer side", Date.valueOf("2024-05-12"));
		//Wear w2 = new Wear(134234234, "Test User2", "/misc/wornBook.jpg", "It has been damaged in the page 23", Date.valueOf("2024-05-13"));
		//Wear w3 = new Wear(111111, "Test User3", "/misc/wornBook.jpg", "It has been damaged in the outer side2", Date.valueOf("2024-05-13"));
		//wear.add(w1);
		//wear.add(w2);
		//wear.add(w3);
        return wear;
    }

	public int getCopyID() {
		return copyID;
	}

	public void setCopyID(int copyID) {
		this.copyID = copyID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrlToPhoto() {
		return urlToPhoto;
	}

	public void setUrlToPhoto(String urlToPhoto) {
		this.urlToPhoto = urlToPhoto;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public static void insertWear(Wear wear) {
		//insertDBWear(wear);
		//System.out.println(wear.getUrlToPhoto());
	}
    
}
