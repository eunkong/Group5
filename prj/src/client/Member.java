package client;

import java.io.Serializable;
//
public class Member implements Serializable {
	private String id, pwd, phoneNumber, address, grade;
	private int orderCount, point;
	private String[] gradeArray = {"�Ϲ�", "VIP", "VVIP"};
	
	public Member() {
		
	}
	public Member(String id, String pwd, String phoneNumber, String address) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.grade = gradeArray[0];
		this.orderCount = 0;
		this.point = 0;
	}
	public Member(String id, String pwd, String phoneNumber, String address, String grade, int orderCount, int point) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.grade = grade;
		this.orderCount = orderCount;
		this.point = point;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public void printInfo() {
		System.out.println("ID : " + this.id);
		System.out.println("��ȭ��ȣ : " + this.phoneNumber);
		System.out.println("�ּ� : " + this.address);
		System.out.println("��� : " + this.grade);
		System.out.println("�� �ֹ� Ƚ�� : " + this.orderCount);
		System.out.println("����Ʈ : " + this.point);
	}
	
}
