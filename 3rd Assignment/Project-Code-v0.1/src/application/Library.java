package application;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
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

    public static List<LocalDate> getOpenDates(String libName) throws SQLException {
        ResultSet rs = DBCommunicator.fetchOpenDates(libName);
        List<LocalDate> closedDates = new ArrayList<>();
        while (rs.next()) {
            closedDates.add(rs.getDate("closed").toLocalDate());
        }

        List<LocalDate> openDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now().plusDays(1);
        LocalDate endDate = currentDate.plusDays(13);

        while (!currentDate.isAfter(endDate)) {
            if (!closedDates.contains(currentDate) && isWorkingDay(currentDate)) {
                openDates.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return openDates;
    }

    private static boolean isWorkingDay(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;
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
