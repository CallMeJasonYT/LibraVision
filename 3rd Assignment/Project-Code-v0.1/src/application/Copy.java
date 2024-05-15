package application;
import java.util.ArrayList;
import java.util.List;

public class Copy extends Book{
	private int copyID;
	private String edition;

    public Copy() {
        // Default constructor, possibly needed for FXML loading
    }

    public Copy(String title, String isbn, int copyID, String edition) {
    	super(title, isbn);
    	this.copyID = copyID;
    	this.edition = edition;
    }

	public static List<Copy> searchCopy(List<Integer> copyIDs) {
		Copy c1 = new Copy("Test Title", "34234234", 134234234, "1st");
		Copy c2 = new Copy("Test Tttet", "2333", 111111, "1st");
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

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
    
}
