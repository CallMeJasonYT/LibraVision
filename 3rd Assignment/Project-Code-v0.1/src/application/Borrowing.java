package application;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Borrowing {
	private Copy copy;
	private String username;
	private Date borrowingStart;
	private Date borrowingEnd;
	
	public Borrowing(Copy copy, String username, Date borrowingStart, Date borrowingEnd) {
        this.copy = copy;
        this.username = username;
        this.borrowingStart = borrowingStart;
        this.borrowingEnd = borrowingEnd;
    }

    public Borrowing() {}
    
    public static List<Borrowing> getCurBorrowings(String username) throws SQLException{
    	List<Borrowing> curBorrowings = new ArrayList<>();
    	ResultSet rs = DBCommunicator.fetchCurBorrowings(username);
    	while(rs.next()) {
    		Copy copy = new Copy(rs.getString("title"), rs.getString("book_id"), rs.getInt("copy_id"), rs.getString("url"));
    		Borrowing borrowing = new Borrowing(copy, username, rs.getDate("borrowing_start"), rs.getDate("borrowing_finish"));
    		curBorrowings.add(borrowing);
    	}

		return curBorrowings;
    }
    
    public static List<Borrowing> getBorrowings(String username) throws SQLException{
    	List<Borrowing> curBorrowings = new ArrayList<>();
    	ResultSet rs = DBCommunicator.fetchBorrowings(username);
    	while(rs.next()) {
    		Copy copy = new Copy(rs.getString("title"), rs.getString("book_id"), rs.getInt("copy_id"), rs.getString("url"));
    		Borrowing borrowing = new Borrowing(copy, username, rs.getDate("borrowing_start"), rs.getDate("borrowing_finish"));
    		curBorrowings.add(borrowing);
    	}

		return curBorrowings;
    }
    
    public static void insertBorrowing(List<Borrowing> borrowings) {
    	DBCommunicator.insertDBBorrowing(borrowings);
    }
    
    /*public static List<Borrowing> getBorrowingHistory(String username) {
    	//ResultSet rs = DBCommunicator.
    	//List<Borrowing> borrowingHist = new ArrayList<>();
    	//Copy c1 = new Copy("Test Book", "12312312", 1111);
    	//Copy c2 = new Copy("Test Book2", "23123123", 2222);
    	//Borrowing b1 = new Borrowing(c1, "Test Member", Date.valueOf("2024-05-08"), Date.valueOf("2024-05-12"));
    	//Borrowing b2 = new Borrowing(c2, "Test Member", Date.valueOf("2024-05-10"), Date.valueOf("2024-05-15"));
    	//borrowingHist.add(b1);
    	//borrowingHist.add(b2);

		//return borrowingHist;
    	
    }*/

	public Date getBorrowingStart() {
		return borrowingStart;
	}

	public void setBorrowingStart(Date borrowingStart) {
		this.borrowingStart = borrowingStart;
	}

	public Date getBorrowingEnd() {
		return borrowingEnd;
	}

	public void setBorrowingEnd(Date borrowingEnd) {
		this.borrowingEnd = borrowingEnd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	
	public static void updateBorrowing(Borrowing bor) {
		//DBCommunicator.updateDBBorrowing(bor);
	}
}
