package view;

import entity.Account;
import entity.Order;
import exception.InvalidEmailException;
import exception.InvalidPasswordException;
import handle.CartHandle;
import handle.FormatHandle;
import handle.LogInHandle;
import handle.PictureHandle;
import handle.RegisterHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuHome {
    public void menu(Scanner sc) throws ClassNotFoundException {
        ArrayList<Account> accountArrayList =new ArrayList<>();
        List<Account> listaAccounts;
        PictureHandle pictureHandle =new PictureHandle();
        FormatHandle formatHandle=new FormatHandle();
        RegisterHandle registerHandle= new RegisterHandle();
        MenuMainLogin mainLogin=new MenuMainLogin();
        MenuTopicPicture menuTopicPicture=new MenuTopicPicture();
        int choice;
        do{
            System.out.println("                Tranh sơn mài Việt Nam");
            System.out.println("1. Xem danh sách tranh");
            System.out.println("2. Tìm kiếm tranh theo tên");
            System.out.println("3. Kiểm tra đơn hàng");
            System.out.println("4. Giỏ hàng");
            System.out.println("5. Thoát chương trình");
            System.out.println("6. Quản trị");
            System.out.println("Mời bạn lựa chọn:");
            choice =Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    MenuListPicture menuListPicture =new MenuListPicture();
                    menuListPicture.menu(sc);
                    break;
                case 2:
                    pictureHandle.searchByKeyword();
                    menuTopicPicture.menu(sc);
                    break;
                case 3:
                	 CartHandle cartHandle1=new CartHandle();
                	 double toltalmony=0;
                	 double single=0;
                		System.out.println("Nhập vào tên người dùng để kiểm tra đơn hàng");
                    	String username=sc.nextLine();
                        try {
                        	List<Order> list=
							cartHandle1.CheckOrder(username);
                        	for(Order order:list) {
                        		single=order.getTotalmoney();
                        		toltalmony+=single++;
                        		order.Check();
                        	}
                        	System.out.println("Tổng đơn hàng :" +toltalmony +"vnd");
						} catch (ClassNotFoundException e) {
							
							e.printStackTrace();
						}
                    break;
                case 4:
                    CartHandle cartHandle=new CartHandle();
                    cartHandle.displayCart(sc);
                    break;
                case 5:
                    MenuMainLogin menuMainLogin =new MenuMainLogin();
                    menuMainLogin.Menu(sc,accountArrayList);
                    break;
                case 6:
                	System.out.println("Đăng nhập để thao tác?");
                	System.out.println("Nhập tài khoản:");
                	String  name=sc.nextLine();
                	System.out.println("Nhập mật khẩu:");
                	String pass=sc.nextLine();
                	LogInHandle logInHandle=new LogInHandle();
                	Account account=logInHandle.checkLogin(name, pass);

                	System.out.println(account.getStatus());
                	if(account.getStatus()==2) {
                		System.out.println("1.Quản lý khách hàng");
                		System.out.println("2.Quản lý đơn hàng");
                		System.out.println("3.Báo cáo thống kê");
                		System.out.println("4.Thông tin cá nhân");
                		System.out.println("5.Đăng xuất");
                		int chose=Integer.parseInt(sc.nextLine());
                		switch (chose) {
						case 1: {
							 listaAccounts=logInHandle.findALL();
							
							for(Account account2:listaAccounts) {
								account2.ShowKH();
							}
							System.out.println("1.xóa khách hàng");
							System.out.println("2.sửa thông tin khách hàng");
							int luachon=Integer.parseInt(sc.nextLine());
							
							if(luachon==1) {
								System.out.println("Nhập id khách hàng muốn xóa");
								String id=sc.nextLine();
								logInHandle.deleteUser(id);
								System.out.println("Xóa thành công");
							}
							else if(luachon==2) {
								System.out.println("Nhập id khách hàng muốn sửa tt:");
								String id=sc.nextLine();
								Account account2=logInHandle.findOne(id);
								 if(account2!=null) {
									 System.out.println("Nhập tên sửa");
										String namefix=sc.nextLine();
										
										System.out.println("Nhập email");
										String emailfix=sc.nextLine();
										System.out.println("Nhập password");
										String passwordfix=sc.nextLine();
										System.out.println("Cấp quyền quản trị (nhập số 2) ");
										System.out.println("Cấp quyền người dùng (nhập số 1) ");
										String role=sc.nextLine();
										logInHandle.EditUser(namefix, emailfix, passwordfix, role, id);
								 }
								 else
										 
								 {
									 System.out.println("Tài khoản không tồi tại!vui lòng đăng ký");
									    //đăng kí
					            	 boolean check =false;
					                System.out.println("Nhập username");
					                String usernamedk= sc.nextLine();
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
									account = registerHandle.CheckAccountSingup(usernamedk);
									if(account==null) { registerHandle.SignUp(usernamedk, email, password,role);
					               
					                System.out.println("Đăng kí thành công");
					                mainLogin.Menu(sc,accountArrayList);
					                }
					               
					               
					                  
					                else{
					                    System.out.println("Đăng kí thất bại do tài khoản này đã tồn tại");
					                    mainLogin.Menu(sc,accountArrayList);
					                }
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 }
								
								
							}
							break;
						}
							case 2: {
								CartHandle cartHandle2=new CartHandle();

								try {
									List<Order> list=cartHandle2.getListoder();
									for(Order o:list){
										o.Check();

									}
									System.out.println("1.Xoá đơn hàng");
									System.out.println("2.sửa đơn hàng");
									int luachondh=Integer.parseInt(sc.nextLine());
									if(luachondh==1){
										System.out.println("nhập id đơn hàng muốn xóa :");
										String id=sc.nextLine();
                                      cartHandle2.deleteOrder(id);
									}
									if(luachondh==2){
										System.out.println("nhập id đơn hàng muốn cập nhật:");
										String id=sc.nextLine();
										System.out.println("nhập lại tên:");
										String nameodfix=sc.nextLine();
										System.out.println("nhập lại địa chỉ:");
										String addoderfix=sc.nextLine();
										System.out.println("nhập lại số điện thoại:");
										String sdtfix=sc.nextLine();

										cartHandle2.EditOrder(nameodfix,addoderfix,sdtfix,id);
										System.out.println("cập nhật thành công");
									}
								} catch (ClassNotFoundException e) {
									throw new RuntimeException(e);
								}
								break;
							}
							case 3:{
								CartHandle cartHandle2=new CartHandle();
								String warn="nhập ngày muốn đề xuất doanh thu dạng dd//mm/yyyy";
								System.out.println(warn);
								String day=sc.nextLine();
								double totalofday=0;
								double singleofday=0;
								List<Order> list=cartHandle2.getMoneyofDays(day);
								for (Order order:list){
									singleofday=order.getTotalmoney();
									totalofday+=singleofday++;
								}
								System.out.println("Doanh thu ngày" +day+"là"+totalofday+"VND");
								MenuHome menuHome=new MenuHome();
								menuHome.menu(sc);
								break;
							}

						default:
							throw new IllegalArgumentException("Unexpected value: " + chose);
						}

                	}

                	break;
                case 7:
                    System.out.println("Bạn đã thoát chương trình");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại");
            }
        }while(choice!=6);



    }
}

