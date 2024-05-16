package application;

public class Member{
	private String username;
	private int points;

    public Member() {
        // Default constructor, possibly needed for FXML loading
    }

    public Member(String username, int points) {
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
	
    public static Member userExist(String username) {
    	//if Resultset returns empty return new empty Member else return new Member
    	//fetchMember(username);
    	int points = 5;
    	return new Member(username, points);
    }
}
