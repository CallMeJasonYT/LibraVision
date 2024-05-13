package application;
import java.sql.Date;
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

    public Borrowing() {
        // Default constructor, possibly needed for FXML loading
    }
    
    public static List<Borrowing> getBorrowings(){
    	//Results
    	List<Borrowing> curBorrowings = new ArrayList<>();
    	//Copy c1 = new Copy("Test Book", 12312312, 11, "1st");
    	//Copy c2 = new Copy("Test Book2", 23123123, 2222, "2nd");
    	//Borrowing b1 = new Borrowing(c1, "Test User", Date.valueOf("2024-05-08"), Date.valueOf("2024-05-12"));
    	//Borrowing b2 = new Borrowing(c2, "Test User", Date.valueOf("2024-05-10"), Date.valueOf("2024-05-15"));
    	//curBorrowings.add(b1);
    	//curBorrowings.add(b2);

		return curBorrowings;
    }
    
    public static void insertBorrowing(List<Borrowing> borrowings) {
    	//insertDBBorrowing(borrowings)
    	for(Borrowing bor : borrowings) {
    		System.out.println(bor.getBorrowingStart());
    		System.out.println(bor.getBorrowingEnd());
    		System.out.println(bor.getUsername());
    		System.out.println(bor.getCopy().getCopyID());
    	}
    }

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
	
	public void updateBorrowing(Borrowing bor, Date date) {
		//DBCommunicator.updateDBBorrowing(bor.getCopy(), date);
		bor.setBorrowingEnd(date);
	}
}
