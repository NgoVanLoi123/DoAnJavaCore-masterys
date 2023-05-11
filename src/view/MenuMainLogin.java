package view;

import entity.Account;
import exception.InvalidEmailException;
import exception.InvalidPasswordException;
import handle.FormatHandle;
import handle.LogInHandle;
import handle.RegisterHandle;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuMainLogin {
	 FormatHandle formatHandle=new FormatHandle();
    RegisterHandle registerHandle= new RegisterHandle();
   
    public void Menu(Scanner sc,ArrayList<Account> accountArrayList){
        int option=0;
        try{
            System.out.println("1- Đăng nhập");
            System.out.println("2- Đăng kí");
            System.out.println("Mời bạn lựa chọn:");
            option =Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Bạn phải nhập 1 số trong menu");
            this.Menu(sc,accountArrayList);
        }
        switch(option){
            case 1:
                //đăng nhập
                Account account=new Account();
                LogInHandle logInHandle=new LogInHandle();
                logInHandle.LogIn(sc,account);
                break;
            case 2:
                //đăng kí
            	 boolean check =false;
                System.out.println("Nhập username");
                String username= sc.nextLine();
                System.out.println("Nhập email");
                String email =sc.nextLine();
                try{//kiểm tra form nhập từ người dùng
                    if(!formatHandle.validateEmail(email)){
                        throw new InvalidEmailException("Email không hợp lệ!");
                    }
                    else{
                        check=true;
                    }
                }catch(InvalidEmailException e){
                    System.out.println(e.getMessage()+"Vui lòng nhập lại");
                    email =sc.nextLine();
                }
                System.out.println("Nhập password");
                String password =sc.nextLine();
               
               
                	  
                    try{
                        if(!formatHandle.validatePassword(password)){
                            throw new InvalidPasswordException("Password không mạnh!");
                        }else{
                            check =true;
                        }

                    }catch(InvalidPasswordException e){
                        System.out.println(e.getMessage()+" Vui lòng nhập lại");
                        password =sc.nextLine();
                    }

              
                int role=0; 
                System.out.println("Lựa chọn quyền truy cập 1.Người Dùng 2.Người Quản Trị");
                int chooseRole=sc.nextInt();
                switch (chooseRole) {
				case 1: {
					role=1;
					break;
				}
				case 2: {
					role=2;
					break;
				}
				default:
					throw new IllegalArgumentException("Lựa Chọn Không Hợp Lệ: " + chooseRole);
				}
		
			try {
				account = registerHandle.CheckAccountSingup(username);
				if(account==null) { registerHandle.SignUp(username, email, password,role);
               
                System.out.println("Đăng kí thành công");
                this.Menu(sc,accountArrayList);
                }
               
               
                  
                else{
                    System.out.println("Đăng kí thất bại do tài khoản này đã tồn tại");
                    this.Menu(sc,accountArrayList);
                }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
                break;
        }
    }
}

