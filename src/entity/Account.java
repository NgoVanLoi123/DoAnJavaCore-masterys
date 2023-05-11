package entity;

import java.time.LocalDate;

public class Account {

    private int id;
    private String userName;
    private String email;
    private String passWord;
    private int role;


    public Account() {
		super();
	}

	

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Account(int id, String userName, String email, String passWord, int role) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.passWord = passWord;
		this.role = role;
	}



	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

	public int getStatus() {
		return role;
	}

	public void setStatus(int status) {
		this.role = status;
	}

	@Override
	public String toString() {
		return "Khách hàng [id=" + id + ", userName=" + userName + ", email=" + email + ", passWord=" + passWord
				+ ", role=" + role + "]";
	}
	public void ShowKH() {
		System.out.println( "Khách hàng [id=" + id + ", userName=" + userName + ", email=" + email + ", passWord=" + passWord
				);
		if(role==1) {
			System.out.println("Vai trò: Người dùng");
		}
	}
  
}