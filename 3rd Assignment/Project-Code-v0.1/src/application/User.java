package application;

public class User{
	private String type;
	private String fullname;
	private String username;
	private Integer age;
	private String password;
	private String email;
	private String telephone;

    public User() {}

    public User(String type, String fullname, String username, Integer age, String password, String email, String telephone) {
    	this.type = type;
    	this.fullname = fullname;
    	this.username = username;
    	this.age = age;
    	this.password = password;
    	this.email = email;
    	this.telephone = telephone;
    }
    
    public User(String type, String fullname, String username, String password, String email, String telephone) {
    	this.type = type;
    	this.fullname = fullname;
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.telephone = telephone;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public static void insertNewUser(User user) {
		//DBCommunicator.insertDBUser(user);
	}
	
}