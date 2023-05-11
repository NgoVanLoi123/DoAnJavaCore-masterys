package view;

import entity.Account;
import entity.Order;
import handle.CartHandle;
import handle.PictureHandle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entity.User.account;

public class MenuPictureDetails {
    public void menu(Scanner sc) {
        CartHandle cartHandle=new CartHandle();
        int choice;
        do{
            System.out.println("1. Thêm vào giỏ hàng");
            System.out.println("2. Xem giỏ hàng");
            System.out.println("3. Kiểm tra đơn hàng");
            System.out.println("4. Mua ngay");
            System.out.println("5. Thoát");
            choice =Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    if(account==null){
                        System.out.println("Bạn cần phải đăng nhập trước khi thêm vào giỏ hàng");
                    }else{
                        cartHandle.addCart(sc);
                    }
                    break;
                case 2:
                    if(account==null){
                        System.out.println("Bạn cần phải đăng nhập trước khi xem vào giỏ hàng");
                    }else{
                        cartHandle.displayCart(sc);
                    }
                    break;
                case 3:
                    if(account==null){
                        System.out.println("Bạn cần phải đăng nhập trước khi xem vào đơn hàng");
                    }else{
                    	System.out.println("Nhập vào tên người dùng để kiểm tra đơn hàng");
                    	String username=sc.nextLine();
                        try {
                        	List<Order> list=
							cartHandle.CheckOrder(username);
                        	for(Order order:list) {
                        		System.out.println(order.toString());
                        	}
						} catch (ClassNotFoundException e) {
							
							e.printStackTrace();
						}
                    }
                    break;
                case 4:
                    if(account==null){
                        System.out.println("Bạn cần phải đăng nhập trước khi mua");
                    }else{
                        cartHandle.Buy(sc);
                    }
                    break;
                case 5:
                    if(account==null){
                        MenuMain menuMain =new MenuMain();
                        menuMain.menu(sc);
                    }else{
                        MenuHome menuHome=new MenuHome();
                        try {
                            menuHome.menu(sc);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại");
            }
        }while(choice!=3);
    }

}
