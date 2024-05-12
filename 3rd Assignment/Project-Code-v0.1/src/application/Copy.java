package application;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Copy extends Book{
	private int copyID;
	private String edition;

    public Copy() {
        // Default constructor, possibly needed for FXML loading
    }

    public Copy(String title, int isbn, int copyID, String edition) {
    	super(title, isbn);
    	this.copyID = copyID;
    	this.edition = edition;
    }

	public static List<Book> fetchBooks() {
        // Assuming you have a method to fetch books from a database
        List<Book> books = new ArrayList<>();
        // Mock data for demonstration
        books.add(new Book("1984", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, 0, 20));
        books.add(new Book("1985", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, 0, 0));
        return books;
    }

	public int getCopyID() {
		return copyID;
	}

	public void setCopyID(int copyID) {
		this.copyID = copyID;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public String getTitle() {
		return super.getTitle();
	}
    
}
