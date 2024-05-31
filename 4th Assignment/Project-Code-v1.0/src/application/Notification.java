package application;

public class Notification {
	private Book book;
	private String username;
	
	//Constructors
	public Notification(String username, Book book) {
    	this.setBook(book);
    	this.setUsername(username);
    }
	
	public Notification() {}

    public static void insertNotification(Notification notif) {
    	DBCommunicator.insertDBNotification(notif);
    }

	//Getters and Setters
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
}
