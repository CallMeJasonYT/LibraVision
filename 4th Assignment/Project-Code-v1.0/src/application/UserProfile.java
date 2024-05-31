package application;
import java.sql.SQLException;
import java.util.List;

public class UserProfile{
	private String username;
	private List<String> authors;
	private List<String> genres;
	private Integer pages;
	private List<String> interests;

	//Constructors
    public UserProfile() {}

    public UserProfile(String username, List<String> authors, List<String> genres, Integer pages, List<String> interests) {
    	this.setUsername(username);
    	this.setAuthors(authors);
    	this.setGenres(genres);
    	this.setPages(pages);
    	this.setInterests(interests);
    }
	
	// Method to insert user profile into the database and get AI-generated book suggestions
	public static List<Book> insertProfile(UserProfile userProf) throws SQLException {
		List<Book> aiGenBooks = Book.getAIBooks(userProf);
		DBCommunicator.insertDBProfile(userProf);
		return aiGenBooks;
	}

	//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}
	
}