package application;

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
		//DBComunicator.updateDBpoints(member)
	}
	
    public static Member userExist(String username) {
    	//if Resultset returns empty return new empty Member else return new Member
    	//fetchMember(username);
    	int points = 5;
    	return new Member(username, points);
    }
    
}
