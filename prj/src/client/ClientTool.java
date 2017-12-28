package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import master.MenuSFM;
import master.Order;

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
	
	ClientTool() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		s = new Scanner(System.in);
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
			case REGISTER: register();
				continue;
			case LOGIN: 
				if(login()) {
					loginHome();
					break;
				}else {
					continue;
				}
			case END: end(); break; //종료
			default: System.out.println("번호 오류"); continue;
			}
			break;
			
		}
	}

	private void loginHome() throws IOException {
		System.out.println("1.내정보 or 2.주문하기 or 3.주문내역");
		int choice = s.nextInt();
		s.nextLine();
		switch(choice) {
		case 1: my.printInfo(); break;
		case 2: order(); break;
		case 3: break;
		default: System.out.println("번호 오류");
		}
	}

	private void order() throws IOException {
		MenuSFM.menuLoad(); //메뉴판 읽기
		MenuSFM.menuPrintConsole(); //메뉴판 출력
		//주문하기
		Order myOrder = new Order(my);
		myOrder.orderMain();
		out.writeObject(myOrder); out.flush(); //주문 객체 전송
		System.out.println("주문 완료");
	}

	private boolean login() throws IOException, ClassNotFoundException {
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
		
		my = (Member)in.readObject();
		
		if(my!=null) {
			System.out.println("로그인 성공");
			loginHome();
//			MenuSFM.menuLoad(); //메뉴판 읽기
//			MenuSFM.menuPrintConsole(); //메뉴판 출력
//			//주문하기
//			Order myOrder = new Order(my);
//			myOrder.orderMain();
//			out.writeObject(myOrder); out.flush(); //주문 객체 전송
//			System.out.println("주문 완료");
			return true;
		}else {
			System.out.println("로그인 실패");
			return false;
		}
	}

	private void register() throws IOException, ClassNotFoundException {
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
		System.out.print("전화번호  : ");
		out.writeUTF(s.nextLine()); out.flush(); //phoneNumber를 server에 넘김
		System.out.print("주소 : ");
		out.writeUTF(s.nextLine()); out.flush(); //address를 server에 넘김
		registerResult = in.readBoolean();
		if(registerResult==true) {
			System.out.println("회원가입 성공");
		}else {
			System.out.println("회원가입 실패");
		}
	}

	private void end() throws IOException {
		out.close();
		in.close();
		s.close();
		System.exit(0);
	}
}
