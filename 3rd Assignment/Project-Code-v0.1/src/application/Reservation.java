package application;
import java.sql.Date;

public class Reservation {
	private Book book;
	private String username;
	private Date datetime;
	private Date creationDate;
	
	public Reservation(Book book, String username, Date datetime, Date creationDate) {
    	this.setBook(book);
    	this.setUsername(username);
    	this.setDatetime(datetime);
    	this.setCreationDate(creationDate);
    }
	
	public Reservation() {}

    public static void insertRes(Reservation res) {
    	DBCommunicator.insertDBRes(res);
    }

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
