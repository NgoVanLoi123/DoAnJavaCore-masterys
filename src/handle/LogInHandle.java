package handle;

import entity.Account;
import entity.User;
import view.MenuHome;
import view.MenuMain;
import view.MenuPassword;
import view.MenuChangeAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LogInHandle {
	PreparedStatement statement = null;
	ResultSet rs = null;
	Connection conn = null;
    public Account checkLogin(String username, String password) {
    	String sql = "SELECT * FROM account where username =? and password=?";
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
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
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return null;
    }
    public List<Account> findALL() {
    	List<Account> list=new ArrayList<>();
    	String sql = "SELECT * FROM account where role=1 ";
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
	
			rs = statement.executeQuery();
			while (rs.next()) {
				list.add(new Account(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5)
						
						))
				;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return list;
    }
    public Account findOne(String id) {
    	
    	String sql = "SELECT * FROM account where id=? ";
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
	        statement.setString(1, id);
			rs = statement.executeQuery();
			while (rs.next()) {
				return new Account(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5)
						
						);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return null;
    }

	//hàm cập nhật khách hàng
    public void EditUser(String username,String email,String pass,
			String role,String id) {
		String sql="UPDATE `account` SET `username` = ?, `email` = ?,\n"//truy vấn
				+ "`password` = ?, `role` = ? where id=?";
		try {
			conn=new ConnectDB().getConnection();//kết nối db
			statement=conn.prepareStatement(sql);//truyển query sang mysql
			statement.setString(1, username);//tham số truyền
			statement.setString(2, email);
			statement.setString(3, pass);
			statement.setString(4, role);
			statement.setString(5, id);
			statement.executeUpdate();//thực hiện
		} catch (ClassNotFoundException e) {// bắt ngoại lệ nếu không tìm thấy lớp connect
		
			e.printStackTrace();
		} catch (SQLException e) {//bắt ngoại lệ liên quan sql
			
			e.printStackTrace();
		}
	}
	// hàm xóa khách hàng
    public void deleteUser( String id) {
    
    	String sql = "delete  FROM account where id=? ";
		try {
			conn = new ConnectDB().getConnection();
			statement = conn.prepareStatement(sql);
	        
			statement.setString(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	
    }
    public Account checkUserName(String username, ArrayList<Account> accountArrayList) {
        for (Account x : accountArrayList) {
            if (x.getUserName().equals(username)) {
                return x;
            }
        }
        return null;
    }

    public Account checkPassWord(String password, ArrayList<Account> accountArrayList) {
        for (Account x : accountArrayList) {
            if (x.getPassWord().equals(password)) {
                return x;
            }
        }
        return null;
    }

    public Account checkEmail(String email, ArrayList<Account> accountArrayList) {
        for (Account x : accountArrayList) {
            if (x.getEmail().equals(email)) {
                return x;
            }
        }
        return null;
    }

    public Account LogIn(Scanner sc, Account account) {
        boolean check = false;
        while (!check) {
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
             account = this.checkLogin(username, password);
            if (account != null) {
                System.out.println("Chào mừng  " + account.getUserName() + "  bạn có thể thực hiện các công việc sau: ");
                MenuHome menuHome=new MenuHome();
                User.account=account;
				try {
					menuHome.menu(sc);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
//                MenuChangeAccount menuChangeAccount=new MenuChangeAccount();
//                menuChangeAccount.menu(sc,account,accountArrayList);

            } 
        }
		return account;
    }
    
}

