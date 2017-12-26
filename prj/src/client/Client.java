package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static final int REGISTER = 1;
	private static final int LOGIN = 2;
	public static void main(String[] args) throws IOException {
		int port = 20000;
		InetAddress inet = InetAddress.getByName("192.168.0.243");
		Socket socket = new Socket(inet, port);
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		System.out.println("로그인하세요");
//		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
//		System.out.println("전송준비");
		Scanner s = new Scanner(System.in);
		
		while(true) {
			System.out.print("1.회원가입 or 2.로그인 or 3.종료 : ");
			int menu = s.nextInt();
			s.nextLine();
			if(menu==3) break;
			out.writeInt(menu);
			out.flush();
			System.out.println("int 전송");
			
			System.out.print("ID : ");
			String id = s.nextLine();
			System.out.print("PWD : ");
			String pwd = s.nextLine();
			
			Member master = new Member(id, pwd);
			out.writeObject(master);
			out.flush();
			System.out.println("master 전송");
			break;
			
//			boolean result = in.readBoolean();
//			System.out.println("결과 : " + result);
			
//			if(result==false) continue;
		}
		s.close();
//		in.close();
		out.close();
		System.out.println("종료");
		
		//정보받기
	}
}
