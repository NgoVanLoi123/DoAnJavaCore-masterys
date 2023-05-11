package entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int id;
	private String username;
	private String address;
	private String numberphone;
	private String dateOder;
	private String namePicture;
	private int quantity;
	private double totalmoney;
	private int status;
	
	
	
	
	public Order(int id, String username, String address,String namePicture, String numberphone, String dateOder,
			int quantity, double totalmoney, int status) {
		this.id = id;
		this.username = username;
		this.address = address;
		this.numberphone = numberphone;
		this.dateOder = dateOder;
		this.namePicture = namePicture;
		this.quantity = quantity;
		this.totalmoney = totalmoney;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumberphone() {
		return numberphone;
	}
	public void setNumberphone(String numberphone) {
		this.numberphone = numberphone;
	}
	public String getDateOder() {
		return dateOder;
	}
	public void setDateOder(String dateOder) {
		this.dateOder = dateOder;
	}
	public String getNamePicture() {
		return namePicture;
	}
	public void setNamePicture(String namePicture) {
		this.namePicture = namePicture;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(double totalmoney) {
		this.totalmoney = totalmoney;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	
	}
	
	public void Check() {
		
		System.out.println("id :" +id);
		System.out.println("Tên khách hàng :"+ username);
		System.out.println("Địa chỉ:"+ address);
		System.out.println("SDT:"+ numberphone);
		System.out.println("Tên bức tranh :" +namePicture);
		System.out.println("Số lượng :" +quantity);
		System.out.println("Đơn giá :" +totalmoney);
		if(status==1) {
			System.out.println("Đã thanh toán qua thẻ");
		}
		else if(status==2) {
			System.out.println("Chờ nhận hàng và thanh toán");
		}
	}
	
    
}
