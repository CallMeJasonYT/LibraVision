package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategory {
	private List<Book> books;
	private String username;
	private String categoryName;
	private String urlToPhoto;

	//Constructors
    public BookCategory() {}
    
    public BookCategory(List<Book> books, String username, String categoryName, String urlToPhoto) {
    	this.setBooks(books);
    	this.setUsername(username);
    	this.setCategoryName(categoryName);
    	this.setUrlToPhoto(urlToPhoto);
    }

	// Method to retrieve book categories for a specific user from the database
	public static List<BookCategory> getBookCat(String username) throws SQLException {
        List<BookCategory> bookCats = new ArrayList<>();
        ResultSet rs = DBCommunicator.fetchBookCat(username);
        while(rs.next()) {
        	bookCats.add(new BookCategory(Book.getCatBooks(rs.getInt("category_id")), username, rs.getString("category_name"), rs.getString("url")));
        }
        return bookCats;
    }

	// Method to insert a new book category into the database
	public static void insertBookCat(BookCategory bookCat) {
		DBCommunicator.insertDBBookCategory(bookCat);
	}
	
	//Getters and Setters
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getUrlToPhoto() {
		return urlToPhoto;
	}
	
	public void setUrlToPhoto(String urlToPhoto) {
		this.urlToPhoto = urlToPhoto;
	}  
}
