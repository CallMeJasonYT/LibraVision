package application;
import java.util.ArrayList;
import java.util.List;

public class Copy extends Book{
	private String copyID;
	private String edition;

    public Copy() {
        // Default constructor, possibly needed for FXML loading
    }

    public Copy(String title, String isbn, String copyID, String edition) {
    	super(title, isbn);
    	this.copyID = copyID;
    	this.edition = edition;
    }

	public static List<Copy> searchCopy(List<String> copyIDs) {
		Copy c1 = new Copy("Test Title", "34234234", "134234234", "1st");
		Copy c2 = new Copy("Test Tttet", "2333", "111111", "1st");
		List<Copy> copies = new ArrayList<>();
		for(String copyID : copyIDs) {
			if(copyID.equals(c1.getCopyID())) {
				copies.add(c1);
			}else if (copyID.equals(c2.getCopyID())){
				copies.add(c2);
			}
		}
        return copies;
    }

	public String getCopyID() {
		return copyID;
	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public String getTitle() {
		return super.getTitle();
	}
    
}
