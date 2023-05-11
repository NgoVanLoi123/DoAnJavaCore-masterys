package handle;

import entity.Account;

import entity.Picture;
import view.MenuHome;
import view.MenuMain;
import entity.Order;

import static entity.User.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;



public class CartHandle {
    PictureHandle pictureHandle = new PictureHandle();
    private String address;
    private ArrayList<Picture> pictures;
    private String numberP;
    private int status;
    private int choose;
    PreparedStatement statement = null;
	ResultSet rs = null;
	Connection conn = null;
    public CartHandle() {
        pictures = new ArrayList<>();
       
    }

    //kiểm tra id có tồn tại trong giỏ hàng không
    public boolean checkIdCart(int id) {
        for (Picture picture : pictures) {
            if (picture.getId() == id) {
                return true;
            }
        }
        return false;

    }

    public void addCart(Scanner sc) {
        System.out.println("Nhập id sản phẩm cần thêm vào:");
        int id = Integer.parseInt(sc.nextLine());
        Picture picture = pictureHandle.checkIdPicture(id);
        if (picture == null) {
            System.out.println("Không tồn tại id" + id + " trong cửa hàng! Vui lòng nhập lại ");
        } else {
            if (!checkIdCart(id)) {
                picture.setQuantity(1);
                pictures.add(picture);
                System.out.println("Thêm giỏ hàng thành công");
            } else {
                for (Picture pictureCart : pictures) {
                    if (pictureCart.getId() == id) {
                   
                        pictureCart.setQuantity(pictureCart.getQuantity() + 1);
                        System.out.println("Thêm giỏ hàng thành công");
                        break;
                    }
                }
            }
        }
    }
    public void Buy(Scanner sc)  {
        LocalDate localDate = LocalDate.now();// lấy thời gian hiện tại
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");//định dạng về string    
        System.out.println("Nhập id sản phẩm muốn mua:");
        int id = Integer.parseInt(sc.nextLine());
        Picture picture = pictureHandle.checkIdPicture(id);
        if (picture == null) {
            System.out.println("Không tồn tại id" + id + " trong cửa hàng! Vui lòng nhập lại ");
        } else {
            if (!checkIdCart(id)) {
            	System.out.println("Nhập địa chỉ nhận hàng :");
            	address=sc.nextLine();
            	System.out.println("Nhập số điện thoại nhận hàng :");
            	numberP=sc.nextLine();
            	System.out.println("1.Thanh toán qua thẻ 2.thanh toán khi nhận hàng :");
            	choose=Integer.parseInt(sc.nextLine());
            	
            	switch (choose) {
				case 1: {
					 status=1;
					break;
				}
				case 2: {
					 status=2;
					break;
				}
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + status);
				}
            	System.out.println("Nhập username:");
                String username = sc.nextLine();
                System.out.println("Nhập password:");
                String password = sc.nextLine();
                LogInHandle logInHandle=new LogInHandle();
                Account account = logInHandle.checkLogin(username, password);
                picture.setQuantity(1);
                CartHandle cartHandle=new CartHandle();
                try {
					cartHandle.addBill( account.getUserName(), address, picture.getTitle(), numberP,dtf.format(localDate), 1, picture.getPrice(), status);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
                System.out.println("Đặt mua thành công");
            } 
        }
    }
    public void deleteOrder( String id) {

        String sql = "delete  FROM bill where id=? ";
        try {
            conn = new ConnectDB().getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("xóa thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }
    public void EditOrder(String username,String address,String sdt,
                         String id) {
        String sql="UPDATE `bill` SET `username` = ?, `address` = ?,\n" +
                "                 `numberphone` = ? where id=?";
        try {
            conn=new ConnectDB().getConnection();//kết nối db
            statement=conn.prepareStatement(sql);//truyển query sang mysql
            statement.setString(1, username);//tham số truyền
            statement.setString(2, address);
            statement.setString(3, sdt);
            statement.setString(4, id);
            statement.executeUpdate();//thực hiện
        } catch (ClassNotFoundException e) {// bắt ngoại lệ nếu không tìm thấy lớp connect

            e.printStackTrace();
        } catch (SQLException e) {//bắt ngoại lệ liên quan sql

            e.printStackTrace();
        }
    }
    public List<Order> CheckOrder(String username) throws ClassNotFoundException {
  		List<Order> listorder = new ArrayList<>();
  		String sql = "SELECT * FROM qlbantranh.bill where username=?";//truy vấn bằng tên đăng nhập
  		try {
  			conn = new ConnectDB().getConnection();
  			statement = conn.prepareStatement(sql);
			statement.setString(1, username);
  			rs = statement.executeQuery();
  			while (rs.next()) {
  				listorder.add(new Order(	//trả về danh sách đơn hàng
  						rs.getInt(1), 
  						rs.getString(2), 
  						rs.getString(3),
  						rs.getString(4),
  						rs.getString(5),
  						rs.getString(6),
  						rs.getInt(7),
  						rs.getDouble(8),
  						rs.getInt(9)
  						));
  						
  			}
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		return listorder;

  	}
    public List<Order> getListoder() throws ClassNotFoundException {
        List<Order> listorder = new ArrayList<>();
        String sql = "SELECT * FROM qlbantranh.bill ";//truy vấn bằng tên đăng nhập
        try {
            conn = new ConnectDB().getConnection();
            statement = conn.prepareStatement(sql);

            rs = statement.executeQuery();
            while (rs.next()) {
                listorder.add(new Order(	//trả về danh sách đơn hàng
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getDouble(8),
                        rs.getInt(9)
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listorder;

    }
    public List<Order> getMoneyofDays(String day) throws ClassNotFoundException {
        List<Order> listorder = new ArrayList<>();
        String sql = "SELECT * FROM qlbantranh.bill where dateOrder=? ";//truy vấn bằng tên đăng nhập
        try {
            conn = new ConnectDB().getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,day);
            rs = statement.executeQuery();
            while (rs.next()) {
                listorder.add(new Order(	//trả về danh sách đơn hàng
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getDouble(8),
                        rs.getInt(9)
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listorder;

    }
    // hàm thêm vào cơ sở dữ liệu
    public void addBill( String username,String address,String numberphone,String dateOder,
    		 String namePicture,
    		 int quantity,
    		 double totalmoney,
    		 int status) throws SQLException {
		String sql="insert into bill( username,address,namepicture,numberphone,dateOrder,quantity,price" +
                ",status) values (?,?,?,?,?,?,?,?)";
		   try {
			conn=new ConnectDB().getConnection();
			statement=conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, address);
			statement.setString(3, numberphone);
			statement.setString(4, dateOder);
			statement.setString(5, namePicture);
			statement.setInt(6, quantity);
			statement.setDouble(7, totalmoney);
			statement.setInt(8, status);
		
			statement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
	}
    //hàm update sản phẩm
    public void updateQuantity(Scanner sc) {
        System.out.println("Nhập id bức tranh cần thay đổi số lượng: ");
        int id = Integer.parseInt(sc.nextLine());
        if (!checkIdCart(id)) {
            System.out.println("ID:" + id + " không tồn tại trong giỏ hàng");
        } else {
            boolean checkRemove=false;
            for (Picture pictureCart : pictures) {
                if (pictureCart.getId() == id) {
                    System.out.println("Số lượng hiện tại là:" + pictureCart.getQuantity());
                    int newQuantity;
                    do {
                        System.out.println("Mời bạn cập nhật số lượng mới:");
                        newQuantity = Integer.parseInt(sc.nextLine());
                        if (newQuantity < 0) {
                            System.out.println("Yêu cầu nhập số lượng >=0");
                        } else {
                            if (newQuantity > 100) {
                                System.out.println("Mặt hàng hiện tại chỉ còn 100 bức tranh");
                            } else if (newQuantity > 0) {
                                pictureCart.setQuantity(newQuantity);
                                break;
                            } else if (newQuantity == 0) {
                               checkRemove=true;
                                break;
                            }
                        }
                    } while (newQuantity < 0);

                }

            }
            if(checkRemove){
                pictures.removeIf(pictureCart->pictureCart.getId()==id);
            }
        }
    }

    //tính tổng tiền
    public double totalPrice() {
        double totalMoney = 0;
        for (Picture picture : pictures) {
            totalMoney += picture.getPrice() * picture.getQuantity();
        }
        return totalMoney;
    }

    //hàm xóa sản phẩm
    public void removePicture(Scanner sc) {
        System.out.println("Nhập id của bức tranh cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        if (!checkIdCart(id)) {
            System.out.println("ID:" + id + " không tồn tại trong giỏ hàng");
        } else {
            for (Picture pictureCart : pictures) {
                if (pictureCart.getId() == id) {
                    pictures.remove(pictureCart);
                    break;
                }
            }
        }
    }

    public void displayCart(Scanner sc) {
        if (pictures == null || (pictures != null && pictures.isEmpty())) {
            System.out.println("Giỏ hàng của bạn đang trống.");
        } else {
            for (Picture picture : pictures) {
                System.out.println("id: " + picture.getId());
                System.out.println("tên: " + picture.getTitle());
                System.out.println("giá: " + picture.getPrice());
                System.out.println("số lượng: " + picture.getQuantity());
                System.out.println("tổng tiền: " + picture.getQuantity() * picture.getPrice());
            }
            System.out.println("1. Cập nhật số lượng");
            System.out.println("2. Xóa");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                updateQuantity(sc);
            } else if (choice == 2) {
                removePicture(sc);
            }
        }
    }
    public void displayBill(Scanner sc) {
        if (pictures == null || (pictures != null && pictures.isEmpty())) {
            System.out.println("Đơn hàng của bạn đang trống.");
        } else {
            for (Picture picture : pictures) {
            	 System.out.println("Địa chỉ giao: " + address);
                 System.out.println("số điện thoại: " + numberP);
                System.out.println("id: " + picture.getId());
                System.out.println("tên: " + picture.getTitle());
                System.out.println("giá: " + picture.getPrice());
                System.out.println("số lượng: " + picture.getQuantity());
               if(status==1) {
            	   System.out.println("Đã thanh toán và chờ nhận hàng " );
               }
               if(status==2) {
            	   System.out.println("Chờ nhận hàng " );
               }
            }
            System.out.println("1. Cập nhật địa chỉ nhận hàng");
            System.out.println("2. Cập nhật số điện thoại");
            System.out.println("3. Thoát");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                address=sc.nextLine();
            } else if (choice == 2) {
                numberP=sc.nextLine();
            }
           else if (choice == 3) {
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

        }
        }
    }

    public Picture add(Scanner sc) {
        System.out.println("Nhập id sản phẩm cần thêm vào:");
        int id = Integer.parseInt(sc.nextLine());

        Picture pictureToAdd = pictureHandle.checkIdPicture(id);

        if (pictureToAdd == null) {
            System.out.println("Không tồn tại id " + id + " trong cửa hàng! Vui lòng nhập lại.");
            return null;
        }

        for (Picture picture : pictures) {
            if (picture.getId() == id) {
                if (picture.getQuantity() == 0) {
                    picture.setQuantity(1);
                    System.out.println("Thêm sản phẩm vào giỏ hàng thành công!");
                } else {
                    picture.setQuantity(picture.getQuantity() + 1);
                    System.out.println("Tăng số lượng sản phẩm trong giỏ hàng thành công!");
                }
                return picture;
            }
        }

        pictureToAdd.setQuantity(1);
        pictures.add(pictureToAdd);
        System.out.println("Thêm sản phẩm vào giỏ hàng thành công!");
        return pictureToAdd;
    }

}
