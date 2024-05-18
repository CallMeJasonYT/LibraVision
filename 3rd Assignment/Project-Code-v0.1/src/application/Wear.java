package application;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wear{
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
    
    public Wear(int copyID, String urlToPhoto, String details, Date submissionDate) {
    	this.copyID = copyID;
    	this.setUrlToPhoto(urlToPhoto);
    	this.setDetails(details);
    	this.setSubmissionDate(submissionDate);
    }

	public static List<Wear> getWear(int copyID) throws SQLException {
		ResultSet rs = DBCommunicator.fetchWear(copyID);
		List<Wear> wears = new ArrayList<>();
		while (rs.next()) {
			Wear wear = new Wear(copyID, rs.getString("url"), rs.getString("details"), rs.getDate("submission_date"));
			wears.add(wear);
        }
        return wears;
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
		DBCommunicator.insertDBWear(wear);
	}
    
}
