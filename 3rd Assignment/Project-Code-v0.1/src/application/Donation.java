package application;
import java.sql.Date;
import java.util.List;

public class Donation{
	private String username;
	private Date donDate;
	private String isbn;
	private int bookNum;

	//Constructors
    public Donation() {}

    public Donation(String username, Date donDate, String isbn, int bookNum) {
    	this.setUsername(username);
    	this.setDonDate(donDate);
    	this.setIsbn(isbn);
    	this.setBookNum(bookNum);
    }

	//Inserts the donation in the db
	public static void insertDonation(List<Donation> donations) {
		DBCommunicator.insertDBDonation(donations);
    }

	//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDonDate() {
		return donDate;
	}

	public void setDonDate(Date donDate) {
		this.donDate = donDate;
	}

	public int getBookNum() {
		return bookNum;
	}

	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}