package handle;

import entity.Account;
import exception.InvalidEmailException;
import exception.InvalidPasswordException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegisterHandle {
    FormatHandle formatHandle=new FormatHandle();
    LogInHandle logInHandle =new LogInHandle();
    ArrayList<Account> accountArrayList=new ArrayList<>();
    PreparedStatement statement = null;
	ResultSet rs = null;
	Connection conn = null;
	public Account CheckAccountSingup(String username) throws ClassNotFoundException {
		String sql = "select * from account where username =? ";
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			rs = statement.executeQuery();
			while (rs.next()) {
				return new Account(
						rs.getInt(1),
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4), 
						rs.getInt(5));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	public void SignUp(String username,String email, String password,int role) throws ClassNotFoundException {
		String sql = "insert into account(username,email,password,role) values(?,?,?,?)";	
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, password);
			statement.setInt(4, role);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
}
