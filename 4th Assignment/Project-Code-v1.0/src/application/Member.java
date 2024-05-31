package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member extends User{
	private int points;

	//Constructors
    public Member() {}

    public Member(String username, int points) {
    	super(username);
    	this.points = points;
    }
	
	//Getters and Setters
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	//Update in the db
	public void updatePoints(Member member) {
		DBCommunicator.updateDBpoints(member);
	}
	
	// Method to check if a member exists in the database and return the member object
    public static Member getMember(String username) throws SQLException {
    	ResultSet rs = DBCommunicator.fetchMember(username);
    	Member member = new Member();
    	while(rs.next()) {
    		member.setUsername(rs.getString("username"));
    		member.setPoints(rs.getInt("points"));
    	}
    	return member;
    }
}
