package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import master.MenuSFM;
import master.Order;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int port = 20000;
		InetAddress inet = InetAddress.getByName("192.168.0.243");
		Socket socket = new Socket(inet, port);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		//in 만들때 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//하면 더이상 진행되지 않음. (왜..?)
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		System.out.println("전송준비");
		Scanner s = new Scanner(System.in);
		boolean registerResult = false;
		
		while(true) {
			System.out.print("1.회원가입 or 2.로그인 or 3.종료 : ");
			int menu = s.nextInt();
			if(menu==3) break; //종료
			s.nextLine();
			out.writeInt(menu); out.flush();
			
			System.out.print("ID : ");
			out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
			System.out.print("PWD : ");
			out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
			if(menu==1) { //회원가입일 경우
				System.out.print("전화번호  : ");
				out.writeUTF(s.nextLine()); out.flush(); //phoneNumber를 server에 넘김
				System.out.print("주소 : ");
				out.writeUTF(s.nextLine()); out.flush(); //address를 server에 넘김
				registerResult = in.readBoolean();
				if(registerResult==true) {
					System.out.println("회원가입 성공");
					continue;
				}else {
					System.out.println("회원가입 실패");
					continue;
				}
			}
			
			MenuSFM.menuLoad(); //메뉴판 읽기
			MenuSFM.menuPrintConsole(); //메뉴판 출력
			//로그인 성공
			Member my = (Member)in.readObject();
			
			if(my==null) {
				System.out.println("로그인 실패");
				System.exit(0);
			} else {
				System.out.println("로그인 성공");
			}
			
			//주문하기
			Order myOrder = new Order(my);
			myOrder.orderMain();
			out.writeObject(myOrder); out.flush(); //주문 객체 전송
			System.out.println("주문 완료");
		}
		s.close();
		in.close();
		out.close();
		System.out.println("종료");
		
		//정보받기
	}
}
