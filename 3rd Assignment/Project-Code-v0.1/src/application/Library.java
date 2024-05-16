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

    public Library() {}
    
    public Library(String name, String address, String city, String telephone, Date openDate) {
    	this.name = name;
    	this.address = address;
    	this.city = city;
    	this.telephone = telephone;
    	this.openDate = openDate;
    }

    public static List<LocalDate> getOpenDates() {
    	//fetchOpenDates();
    	List<Date> sqlDates = new ArrayList<>();
    	sqlDates.add(Date.valueOf("2024-05-11"));
    	sqlDates.add(Date.valueOf("2024-05-12"));
    	sqlDates.add(Date.valueOf("2024-05-13"));
    	sqlDates.add(Date.valueOf("2024-05-14"));
    	sqlDates.add(Date.valueOf("2024-05-15"));
    	sqlDates.add(Date.valueOf("2024-05-16"));
    	sqlDates.add(Date.valueOf("2024-05-17"));
    	sqlDates.add(Date.valueOf("2024-05-20"));
    	sqlDates.add(Date.valueOf("2024-05-21"));
    	sqlDates.add(Date.valueOf("2024-05-22"));
    	sqlDates.add(Date.valueOf("2024-05-23"));
    	sqlDates.add(Date.valueOf("2024-05-24"));
        List<LocalDate> openDates = new ArrayList<>();
        for (Date date : sqlDates) {
        	LocalDate localDate = date.toLocalDate();
        	openDates.add(localDate);
        }
        
        return openDates;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
}
