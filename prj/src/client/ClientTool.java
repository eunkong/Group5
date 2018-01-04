package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

import master.Menu;
import master.MenuSFM;
import master.Order;
import server.ReceiptManager;

public class ClientTool {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static final int END = 3;
	public static boolean registerResult=false;
	public Member my;
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInput in;
	public Scanner s;
	final static int CLIENT = 1;
	
	public ClientTool() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		s = new Scanner(System.in);
		out.writeInt(CLIENT); out.flush(); //클라이언트라는 정보를 서버에 넘김
		//in 만들때 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//하면 더이상 진행되지 않음. (왜..?)
	}
	
	public void clientHome() throws IOException, ClassNotFoundException {
		while(true) {
			System.out.print("1.회원가입 or 2.로그인 or 3.종료 : ");
			int choice = s.nextInt();
			s.nextLine();
			out.writeInt(choice); out.flush();
			
			switch(choice) {
			case REGISTER: //register();
				continue;
			case LOGIN: 
				continue;
			case END: end(); break; //종료
			default: System.out.println("번호 오류"); continue;
			}
			break;
			
		}
	}

	private void loginHome() throws IOException, ClassNotFoundException {
		while(true) {
			System.out.println("1.내정보 or 2.주문하기 or 3.주문내역 4.로그아웃");
			int choice = s.nextInt();
			s.nextLine();
			switch(choice) {
			case 1: my.printInfo(); continue;
			case 2: 
				out.writeInt(choice); out.flush();
//				order(); continue;
			case 3: 
				out.writeInt(choice); out.flush();
				myorderlist();
				continue;
			case 4: 
				out.writeInt(choice); out.flush();
				return;
			default: System.out.println("번호 오류");
			}
		}
	}
	public void logout() throws IOException{
			out.writeInt(4); out.flush(); //서버에 로그아웃(4) 정보를 넘김
		
	}

	public Map<Long, Order> myorderlist() throws ClassNotFoundException, IOException {
		out.writeInt(3); out.flush(); //서버에 주문내역(3)을 넘김
		Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
		return orderlist;
		/*
		if(orderlist==null) {
			System.out.println("주문 내역이 없습니다");
		}else if(orderlist.size()==0){
			System.out.println("0");
		}else {
			ReceiptManager.printReceipt(orderlist);
		}
		*/
	}

	public void order(Map<Menu, Integer> m) throws IOException {
//		MenuSFM.menuLoad(); //메뉴판 읽기
//		MenuSFM.menuPrintConsole(); //메뉴판 출력
		//주문하기
		Order myOrder = new Order(my);
		
		out.writeInt(2); out.flush(); //서버에 주문하기(2)를 넘김
		myOrder.order(m);
		System.out.println(myOrder.getPriceSum());
		System.out.println(myOrder.getMember());
		for (Menu menu : myOrder.getOrderIdx().keySet()) {
			System.out.println(menu.getName());
			System.out.println(myOrder.getOrderIdx().get(menu));
			System.out.println();
		}
		out.writeBoolean(true); out.flush();
		out.writeObject(myOrder); out.flush(); //주문 객체 전송
		
		System.out.println("주문 완료");
	}

	public Member login(String id, String pwd) throws IOException, ClassNotFoundException {
		out.writeInt(2); out.flush(); //서버에 로그인(2)을 넘김
		out.writeUTF(id); out.flush(); //id를 server에 넘김
		out.writeUTF(pwd); out.flush(); //pwd를 server에 넘김
		
		my = (Member)in.readObject();
		
		return my;
//		return my!=null;
		
		/*if(my!=null) {
			System.out.println("로그인 성공");
			return true;
		}else {
			System.out.println("로그인 실패");
			return false;
		}*/
	}

	public boolean register(String id, String pwd, String phone, String address) throws IOException, ClassNotFoundException {
		out.writeInt(1); out.flush(); //서버에 회원가입(1)을 넘김
		out.writeUTF(id); out.flush(); //id를 server에 넘김
		out.writeUTF(pwd); out.flush(); //pwd를 server에 넘김
		out.writeUTF(phone); out.flush(); //phoneNumber를 server에 넘김
		out.writeUTF(address); out.flush(); //address를 server에 넘김
		return registerResult = in.readBoolean();
	}

	private void end() throws IOException {
		out.close();
		in.close();
		s.close();
		System.exit(0);
	}
}
