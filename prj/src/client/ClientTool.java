package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import master.Order;

public class ClientTool {
	private static int port = 20000;
	static boolean result = false;
	public static Scanner s;
	public static InetAddress inet;
	public static Socket socket;
	public static ObjectInput in;
	public static ObjectOutputStream out;
	
	public ClientTool() throws IOException {
		inet = InetAddress.getByName("192.168.0.243");
		s = new Scanner(System.in);
		socket = new Socket(inet, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	
	public static int Home() throws IOException{
		s = new Scanner(System.in);
		out = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.print("1.회원가입 or 2.로그인 or 3.종료 : ");
		int choice = s.nextInt();
		if(choice==3) exit();
		out.writeInt(choice); out.flush();
		return choice;
	}
	public static boolean ClientRegisterLogin(int clientChoice) throws IOException{
		//준비물 준비
		s = new Scanner(System.in);
		inet = InetAddress.getByName("192.168.0.243");
		socket = new Socket(inet, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush();
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush();
		
		if(clientChoice==1) { //회원가입일 경우
			System.out.print("전화번호  : ");
			out.writeUTF(s.nextLine()); out.flush(); //phoneNumber를 server에 넘김
			System.out.print("주소 : ");
			out.writeUTF(s.nextLine()); out.flush(); //address를 server에 넘김
			result = in.readBoolean();
			if(result==true) {
				System.out.println("회원가입 성공");
				return result;
			}else {
				System.out.println("회원가입 실패");
				return result;
			}
		}
		if(result==true) {
			System.out.println("로그인 성공");
			return result;
		}else {
			System.out.println("로그인 실패");
			return result;
		}
	}
	public static void ClientMain() {
		System.out.println("1.내 정보 보기 or 2.내 주문 보기");
	}
	public static int ClientInput() {
		s = new Scanner(System.in);
		return s.nextInt();
	}
	public static Order myOrder() throws ClassNotFoundException, IOException {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		Member my = (Member)in.readObject();
		Order myOrder = new Order(my);
		myOrder.orderMain();
		out.writeObject(myOrder); out.flush();
		System.out.println("주문 완료");
		return myOrder;
	}
	public static void exit() throws IOException {
		s.close();
		in.close();
		out.close();
		System.out.println("종료");
		System.exit(0);
	}
}
