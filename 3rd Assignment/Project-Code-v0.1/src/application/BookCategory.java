package application;

import java.util.ArrayList;
import java.util.List;

public class BookCategory {
	private List<Book> books;
	private String username;
	private String categoryName;
	private String urlToPhoto;

    public BookCategory() {}
    
    public BookCategory(List<Book> books, String username, String categoryName, String urlToPhoto) {
    	this.setBooks(books);
    	this.setUsername(username);
    	this.setCategoryName(categoryName);
    	this.setUrlToPhoto(urlToPhoto);
    }

	public static List<BookCategory> getBookCat(String username) {
		Book book1 = new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg");
		Book book2 = new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg");		
		Book book3 = new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg");
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
        List<BookCategory> bookCats = new ArrayList<>();
        bookCats.add(new BookCategory(books, username, "Test Category", "/misc/bookCategory.jpg"));

        return bookCats;
    }
	
	public static void insertBookCat(BookCategory bookCat) {
		//insertDBBookCategory(bookCat);
	}
	
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
