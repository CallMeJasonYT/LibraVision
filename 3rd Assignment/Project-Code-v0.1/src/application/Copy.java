package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Copy extends Book{
	private int copyID;

    public Copy() {}

    public Copy(String title, String isbn, int copyID, String url) {
    	super(title, isbn, url);
    	this.copyID = copyID;
    }
    
    public Copy(String title, String isbn) {
    	super(title, isbn);
    }

	public static List<Copy> searchCopy(Integer copyID) throws SQLException {
		ResultSet rs = DBCommunicator.fetchCopy(copyID);
		List<Copy> copies = new ArrayList<>();
		while (rs.next()) {
            Copy copy = new Copy(rs.getString("title"), rs.getString("book_id"), rs.getInt("copy_id"), rs.getString("url"));
            copies.add(copy);
        }
        return copies;
    }

	public int getCopyID() {
		return copyID;
	}

	public void setCopyID(int copyID) {
		this.copyID = copyID;
	}

	public static void insertCopies(List<Copy> copies, List<Integer> amounts) {
		//insertDBCopies(copies, amounts);
	}
}
