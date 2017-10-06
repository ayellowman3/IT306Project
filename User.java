
public class User {

    private static int count;

    private String email;
    private String password;

    public User(){
        this.email = "";
        this.password = "";
        count++;
    }

    public User(String email, String password){
        this();
        this.email = email;
        this.password = password;
    }

	/**
	* Returns value of count
	* @return
	*/
	public static int getCount() {
		return count;
	}

	/**
	* Returns value of email
	* @return
	*/
	public String getEmail() {
		return this.email;
	}

	/**
	* Returns value of password
	* @return
	*/
	public String getPassword() {
		return this.password;
	}

	/**
	* Sets new value of email
	* @param
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	* Sets new value of password
	* @param
	*/
	public void setPassword(String password) {
		this.password = password;
	}
}
