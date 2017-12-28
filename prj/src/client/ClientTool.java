package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import master.MenuSFM;
import master.Order;

public class ClientTool {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static final int END = 3;
	public static boolean registerResult=false;
	public static Member my;
	
	public static void clientHome() {
		//in 만들때 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//하면 더이상 진행되지 않음. (왜..?)
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			while(true) {
				my = null;
				System.out.print("1.회원가입 or 2.로그인 or 3.종료 : ");
				int menu = s.nextInt();
//				if(menu==END) break; //종료
				s.nextLine();
				out.writeInt(menu); out.flush();
				
				switch(menu) {
				case 1: 
					System.out.print("ID : ");
					out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
					System.out.print("PWD : ");
					out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
					System.out.print("전화번호  : ");
					out.writeUTF(s.nextLine()); out.flush(); //phoneNumber를 server에 넘김
					System.out.print("주소 : ");
					out.writeUTF(s.nextLine()); out.flush(); //address를 server에 넘김
					registerResult = in.readBoolean(); //회원가입 성공여부를 server에 받음
					if(registerResult==true) {
						System.out.println("회원가입 성공");
						break;
					}else {
						System.out.println("회원가입 실패");
						break;
					}
				case 2:
					System.out.print("ID : ");
					out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
					System.out.print("PWD : ");
					out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
					break;
				case 3:
					break;
				default: System.out.println("잘못 입력하셨습니다");
				}
//				System.out.print("ID : ");
//				out.writeUTF(s.nextLine()); out.flush(); //id를 server에 넘김
//				System.out.print("PWD : ");
//				out.writeUTF(s.nextLine()); out.flush(); //pwd를 server에 넘김
				if(menu==REGISTER) { //회원가입일 경우
					System.out.print("전화번호  : ");
					out.writeUTF(s.nextLine()); out.flush(); //phoneNumber를 server에 넘김
					System.out.print("주소 : ");
					out.writeUTF(s.nextLine()); out.flush(); //address를 server에 넘김
					registerResult = in.readBoolean(); //회원가입 성공여부를 server에 받음
					if(registerResult==true) {
						System.out.println("회원가입 성공");
						continue;
					}else {
						System.out.println("회원가입 실패");
						continue;
					}
				}
				
				try {
					my = (Member)in.readObject();
					if(my!=null) {
						System.out.println("로그인 성공");
						MenuSFM.menuLoad(); //메뉴판 읽기
						MenuSFM.menuPrintConsole(); //메뉴판 출력
						//주문하기
						Order myOrder = new Order(my);
						myOrder.orderMain();
						out.writeObject(myOrder); out.flush(); //주문 객체 전송
						System.out.println("주문 완료");
					}
				} catch (Exception e) {
					System.out.println("로그인 실패");
					continue;
				}
			}
			
		} catch(Exception e) {
			System.err.println("오류!");
			e.printStackTrace();
		}
		
	}
}
