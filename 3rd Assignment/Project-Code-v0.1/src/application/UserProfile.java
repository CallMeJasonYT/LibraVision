package application;
import java.util.List;

public class UserProfile{
	private String username;
	private List<String> authors;
	private List<String> genres;
	private Integer pages;
	private List<String> interests;

    public UserProfile() {}

    public UserProfile(String username, List<String> authors, List<String> genres, Integer pages, List<String> interests) {
    	this.setUsername(username);
    	this.setAuthors(authors);
    	this.setGenres(genres);
    	this.setPages(pages);
    	this.setInterests(interests);
    }
	
	public static List<Book> insertProfile(UserProfile userProf) {
		//DBCommunicator.insertDBProfile(userProf);
		return Book.getAIBooks(userProf);
	}

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