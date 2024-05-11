package application;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
	private String name;
	private String address;
	private String city;
	private String telephone;
	private Date openDate;

    public Library() {
        // Default constructor, possibly needed for FXML loading
    }

    public static List<LocalDate> getOpenDates() {
    	List<Date> sqlDates = new ArrayList<>();
    	sqlDates.add(Date.valueOf("2024-05-11"));
    	sqlDates.add(Date.valueOf("2024-05-12"));
    	sqlDates.add(Date.valueOf("2024-05-13"));
    	sqlDates.add(Date.valueOf("2024-05-14"));
    	sqlDates.add(Date.valueOf("2024-05-15"));
    	sqlDates.add(Date.valueOf("2024-05-16"));
    	sqlDates.add(Date.valueOf("2024-05-17"));
    	sqlDates.add(Date.valueOf("2024-05-18"));
    	sqlDates.add(Date.valueOf("2024-05-19"));
    	sqlDates.add(Date.valueOf("2024-05-20"));
        List<LocalDate> openDates = new ArrayList<>();
        for (Date date : sqlDates) {
        	LocalDate localDate = date.toLocalDate();
        	openDates.add(localDate);
        }
        
        return openDates;
    }
}
