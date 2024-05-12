package application;

import java.util.ArrayList;
import java.util.List;

public class User{
	private String username;
	private int points;

    public User() {
        // Default constructor, possibly needed for FXML loading
    }

    public User(String username, int points) {
    	this.username = username;
    	this.points = points;
    }

	public static List<Book> fetchBooks() {
        // Assuming you have a method to fetch books from a database
        List<Book> books = new ArrayList<>();
        // Mock data for demonstration
        books.add(new Book("1984", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, 0, 20));
        books.add(new Book("1985", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, 0, 0));
        return books;
    }
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void updatePoints(int points) {
		//DBComunicator.updateDBpoints(points)
		this.points = points;
	}
	
    
}
