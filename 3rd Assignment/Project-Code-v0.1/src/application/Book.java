package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book {
	private String title;
	private List<String> author;
	private List<String> genres;
	private double rating;
	private int borrowedCount;
	private String description;
	private int pageNum;
	private String isbn;
	private int relDate;
	private int availCopy;
	private String urlToPhoto;

    public Book() {}
    
    public Book(String title, String isbn) {
    	this.title = title;
    	this.setIsbn(isbn);
    }
    
    public Book(String title, String isbn, String urlToPhoto) {
    	this.title = title;
    	this.setIsbn(isbn);
    	this.urlToPhoto = urlToPhoto;
    }
    
    public Book(String title, List<String> author, List<String> genres, double rating, int borrowedCount, String url) {
    	this.title = title;
        this.author = author;
        this.genres = genres;
        this.rating = rating;
        this.borrowedCount = borrowedCount;
        this.setUrlToPhoto(url);
    }
    
    public Book(String title, String isbn, List<String> author, List<String> genres, double rating, int borrowedCount, String url) {
    	this.title = title;
    	this.isbn = isbn;
        this.author = author;
        this.genres = genres;
        this.rating = rating;
        this.borrowedCount = borrowedCount;
        this.setUrlToPhoto(url);
    }

    public Book(String title, List<String> author, List<String> genres, double rating, int borrowedCount, String description, int pageNum, String isbn, int relDate, int availCopy, String urlToPhoto) {
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.rating = rating;
        this.borrowedCount = borrowedCount;
        this.setAvailCopy(availCopy);
        this.setDescription(description);
        this.setPageNum(pageNum);
        this.setIsbn(isbn);
        this.setRelDate(relDate);
        this.setUrlToPhoto(urlToPhoto);
    }

	public static List<Book> getBooks() throws SQLException {
	    List<ResultSet> rsList = DBCommunicator.fetchBookSearch();
		Map<String, Book> bookMap = new HashMap<>();

	    for (ResultSet rs : rsList) {
	    	while(rs.next()) {
		        String title = rs.getString("title");
		        String authorName = rs.getString("author_name");
		        String genreName = rs.getString("genre_name");
		        double rating = rs.getDouble("rating");
		        int borCount = rs.getInt("bor_count");
		        String url = rs.getString("url");
	
		        if (bookMap.containsKey(title)) {
		            Book existingBook = bookMap.get(title);
		            if (!existingBook.getAuthor().contains(authorName)) {
		                existingBook.getAuthor().add(authorName);
		            }
		            if (!existingBook.getGenres().contains(genreName)) {
		                existingBook.getGenres().add(genreName);
		            }
		        } else {
		            Book newBook = new Book(title, new ArrayList<>(List.of(authorName)), new ArrayList<>(List.of(genreName)), rating, borCount, url);
		            bookMap.put(title, newBook);
		        }
		    }
	    }
	    return new ArrayList<>(bookMap.values());
    }
	
	public static List<Book> getCatBooks(int catID) throws SQLException {
		List<ResultSet> rsList = DBCommunicator.fetchCatBooks(catID);
		Map<String, Book> bookMap = new HashMap<>();

	    for (ResultSet rs : rsList) {
	    	while(rs.next()) {
		        String title = rs.getString("title");
		        String authorName = rs.getString("author_name");
		        String genreName = rs.getString("genre_name");
		        double rating = rs.getDouble("rating");
		        String url = rs.getString("url");
	
		        if (bookMap.containsKey(title)) {
		            Book existingBook = bookMap.get(title);
		            if (!existingBook.getAuthor().contains(authorName)) {
		                existingBook.getAuthor().add(authorName);
		            }
		            if (!existingBook.getGenres().contains(genreName)) {
		                existingBook.getGenres().add(genreName);
		            }
		        } else {
		            Book newBook = new Book(title, new ArrayList<>(List.of(authorName)), new ArrayList<>(List.of(genreName)), rating, 0, url);
		            bookMap.put(title, newBook);
		        }
		    }
	    }
	    return new ArrayList<>(bookMap.values());
	}
    
    public static Book fetchBookDet(Book book) throws SQLException {
    	ResultSet rs = DBCommunicator.fetchBookDet(book);
    	while(rs.next()) {
    		book.setIsbn(rs.getString("book_id"));
    		book.setPageNum(rs.getInt("page_num"));
    		book.setRelDate(rs.getInt("release_date"));
    		book.setDescription(rs.getString("description"));
    		book.setAvailCopy(rs.getInt("available_copies"));
    	}
    	return book;
    }
    
    public static List<Book> getBooksByTitle(List<String> bookTitles) throws SQLException{
    	List<ResultSet> rsList = DBCommunicator.fetchBooksByTitle(bookTitles);
    	Map<String, Book> bookMap = new HashMap<>();

	    for (ResultSet rs : rsList) {
	    	while(rs.next()) {
	    		String isbn = rs.getString("book_id");
		        String title = rs.getString("title");
		        String authorName = rs.getString("author_name");
		        String genreName = rs.getString("genre_name");
		        double rating = rs.getDouble("rating");
		        String url = rs.getString("url");
	
		        if (bookMap.containsKey(title)) {
		            Book existingBook = bookMap.get(title);
		            if (!existingBook.getAuthor().contains(authorName)) {
		                existingBook.getAuthor().add(authorName);
		            }
		            if (!existingBook.getGenres().contains(genreName)) {
		                existingBook.getGenres().add(genreName);
		            }
		        } else {
		            Book newBook = new Book(title, isbn, new ArrayList<>(List.of(authorName)), new ArrayList<>(List.of(genreName)), rating, 0, url);
		            bookMap.put(title, newBook);
		        }
		    }
	    }
	    return new ArrayList<>(bookMap.values());
    }
    
	public static List<Book> getAIBooks(UserProfile userprof){
		//getBooks() in database;
		List<Book> aiGenBooks = new ArrayList<>();
		aiGenBooks.add(new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 5, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg"));
		aiGenBooks.add(new Book("1985", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 0, "/misc/book1.jpg"));
        
		return aiGenBooks;
	}
	
	public static void insertBooks(List<Book> books) {
		//insertDBBook(books);
	}
    
    // Getters
    public String getTitle() {
        return title;
    }

    public List<String> getAuthor() {
        return author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public String getGenresFormatted() {
        return String.join(", ", genres);
    }
    
    public String getAuthorsFormatted() {
        return String.join(", ", author);
    }

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getRelDate() {
		return relDate;
	}
	
    public void setRelDate(int relDate) {
    	this.relDate = relDate;
	}

	public int getAvailCopy() {
		return availCopy;
	}

	public void setAvailCopy(int availCopy) {
		this.availCopy = availCopy;
	}

    public static List<String> booksNeeded(List<String> isbns) throws SQLException {
    	List<ResultSet> rsList = DBCommunicator.fetchReqBooks(isbns);
    	List<String> isbnsRequired = new ArrayList<>();
    	for(ResultSet rs : rsList) {
    		while(rs.next()) {
    			isbnsRequired.add(rs.getString("book_id"));
    		}
    	}
       	return isbnsRequired;
    }

	public String getUrlToPhoto() {
		return urlToPhoto;
	}

	public void setUrlToPhoto(String urlToPhoto) {
		this.urlToPhoto = urlToPhoto;
	}
}
