package application;
import java.sql.Date;

public class BookReview {
	private String isbn;
	private Date submissionDate;
	private double stars;
	private String username;
	private String details;
	
    public BookReview() {}
	
	public BookReview(String isbn, Date submissionDate, double stars, String username, String details) {
        this.setIsbn(isbn);
        this.setSubmissionDate(submissionDate);
        this.setStars(stars);
        this.setUsername(username);
        this.setDetails(details);
    }
    
    public static void insertBookRev(BookReview review) {
    	DBCommunicator.insertDBBookRev(review);
    }

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public double getStars() {
		return stars;
	}

	public void setStars(double stars) {
		this.stars = stars;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
