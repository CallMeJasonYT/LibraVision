package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Book> fetchBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg"));
        books.add(new Book("1985", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 0, "/misc/book1.jpg"));
        return books;
    }
    
    public static Book fetchBookDet(Book book) {
    	book.setDescription("This is a test Desasdfjkhasdjh kfaskjhdfgkbsvcdgfky bascgdfkuyasegf");
    	book.setPageNum(122);
    	book.setIsbn("1231231231");
    	book.setRelDate(2022);
    	return book;
    }
    
    public static List<Book> getBooksByTitle(List<String> bookTitles){
    	//fetchBooksByTitle(bookTitles);
    	List<Book> books = new ArrayList<>();
    	books.add(new Book("1984", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 20, "/misc/book1.jpg"));
        books.add(new Book("1985", List.of("George Orwell"), List.of("romance", "adventure"), 4.2, 22, "null", 0, "0", 2002, 0, "/misc/book1.jpg"));
    	return books;
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
