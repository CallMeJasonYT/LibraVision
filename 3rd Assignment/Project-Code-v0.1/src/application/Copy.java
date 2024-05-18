package application;
import java.util.ArrayList;
import java.util.List;

public class Copy extends Book{
	private int copyID;

    public Copy() {}

    public Copy(String title, String isbn, int copyID) {
    	super(title, isbn);
    	this.copyID = copyID;
    }
    
    public Copy(String title, String isbn) {
    	super(title, isbn);
    }

	public static List<Copy> searchCopy(List<Integer> copyIDs) {
		Copy c1 = new Copy("Test Title", "34234234", 134234234);
		Copy c2 = new Copy("Test Tttet", "2333", 111111);
		List<Copy> copies = new ArrayList<>();
		for(int copyID : copyIDs) {
			if(copyID == c1.getCopyID()) {
				copies.add(c1);
			}else if (copyID == c2.getCopyID()){
				copies.add(c2);
			}
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
