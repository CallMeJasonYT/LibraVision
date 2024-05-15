package application;
import java.sql.Date;
import java.util.List;

public class Donation{
	private String username;
	private Date donDate;
	private String isbn;
	private int bookNum;

    public Donation() {
        // Default constructor, possibly needed for FXML loading
    }

    public Donation(String username, Date donDate, String isbn, int bookNum) {
    	this.setUsername(username);
    	this.setDonDate(donDate);
    	this.setIsbn(isbn);
    	this.setBookNum(bookNum);
    }

	public static void insertDonation(List<Donation> donations) {
		//insertDBDonation(donations)
		for (Donation don : donations) {
			System.out.println(don.getIsbn());
		}
    }

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