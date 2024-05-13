package application;

public class User{
	private String username;
	private int points;

    public User() {
        // Default constructor, possibly needed for FXML loading
    }

    public User(String username, int points) {
    	this.username = username;
    	this.points = points;
    }
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void updatePoints(int points) {
		//DBComunicator.updateDBpoints(points)
		this.points = points;
	}
	
    public static User userExist(String username) {
    	//if Resultset returns empty return new empty User else return new User
    	//fetchUser(username);
    	int points = 5;
    	return new User(username, points);
    }
}
