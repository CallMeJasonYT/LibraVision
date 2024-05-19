package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member extends User{
	private int points;

    public Member() {}

    public Member(String username, int points) {
    	super(username);
    	this.points = points;
    }
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void updatePoints(Member member) {
		DBCommunicator.updateDBpoints(member);
	}
	
    public static Member memberExist(String username) throws SQLException {
    	ResultSet rs = DBCommunicator.fetchMember(username);
    	Member member = new Member();
    	while(rs.next()) {
    		member.setUsername(rs.getString("username"));
    		member.setPoints(rs.getInt("points"));
    	}
    	return member;
    }
}
