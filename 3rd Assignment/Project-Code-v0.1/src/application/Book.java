package application;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Book {
	private String title;
	private String author;
	private List<String> genres; // Assuming genres are a list of strings
	private double rating;
	private int borrowedCount;
	private String description;
	private int pageNum;
	private String isbn;
	private Date relDate;
	private int availCopy;

    public Book() {
        // Default constructor, possibly needed for FXML loading
    }
    public Book(String title, String isbn) {
    	this.title = title;
    	this.setIsbn(isbn);
    }

    public Book(String title, String author, List<String> genres, double rating, int borrowedCount, Date relDate,
    		String description, int pageNum, String isbn, int availCopy) {
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
    }

	public static List<Book> fetchBooks() {
        // Assuming you have a method to fetch books from a database
        List<Book> books = new ArrayList<>();
        // Mock data for demonstration
        books.add(new Book("1984", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, "0", 20));
        books.add(new Book("1985", "George Orwell", List.of("romance", "adventure"), 4.2, 22, null, "null", 0, "0", 0));
        return books;
    }
    
    public static Book fetchBookDet(Book book) {
    	book.setDescription("This is a test Desasdfjkhasdjh kfaskjhdfgkbsvcdgfky bascgdfkuyasegf backseyugfcbvkuaysgfykbagcseyfukacgsbeuykfgacsykubfgceasykugfacbwyusegfacbwsefbuacsfgucsegfbcasgkefcbasefasebfcasukebycfaksuegcyfabukcription");
    	book.setPageNum(122);
    	book.setIsbn("1231231231");
    	book.setRelDate(Date.valueOf("2022-02-11"));
    	return book;
    }
    
 // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
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

    // Method to get genres as a formatted string
    public String getGenresFormatted() {
        return String.join(", ", genres);
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
	
	public Date getRelDate() {
		return relDate;
	}
	
    public void setRelDate(Date relDate) {
    	this.relDate = relDate;
	}

	public int getAvailCopy() {
		return availCopy;
	}

	public void setAvailCopy(int availCopy) {
		this.availCopy = availCopy;
	}

    
}
