package view;

import entity.Account;
import entity.Picture;
import handle.CartHandle;
import handle.PictureHandle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTopicPicture {


    public void menu(Scanner sc) {
        PictureHandle pictureHandle=new PictureHandle();
        MenuPictureDetails menuPictureDetails=new MenuPictureDetails();
        int choice;
        do{
            System.out.println("1. Xem chi tiết tranh");
           
            System.out.println("2. Quay lại danh mục tranh");
            choice =Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    System.out.println("Nhập id tranh bạn cần xem chi tiết");
                    int id=Integer.parseInt(sc.nextLine());
                    pictureHandle.showPictureDetail(id);
                    menuPictureDetails.menu(sc);
                    break;
             
                case 2:
                    MenuListPicture menuListPicture=new MenuListPicture();
                    menuListPicture.menu(sc);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại");
                    break;
            }
        }while(choice!=0);


    }
}
