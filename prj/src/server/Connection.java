package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import client.Member;
import master.Order;

//다중IP연결 쓰레드 생성 클래스

public class Connection extends Thread{
	private static Calendar cal;
	private static String time;
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	
	private static List<Connection> list = new ArrayList<>();
	
	/**
	 * 연결추가 메소드
	 * @param Connection 객체
	 */
	public static void add(Connection c) {
		list.add(c);
	}
	
	/**
	 * 연결삭제 메소드
	 * @param Connection 객체
	 */
	public static void remove(Connection c) {
		list.remove(c);
	}
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean flag = true;
	
	/**
	 * 통로 폐쇄 메소드
	 */
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		}catch(Exception e) {	}
		remove(this);
	}
	
	/**
	 * Connection 생성자
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.setDaemon(true);
		this.start();
	}
	
	/**
	 * 상속받은 Thread클래스의 run()메소드 재정의
	 * 
	 */
	public void run() {
		try {
			while(flag) {
				int select = in.readInt();
				System.out.println("선택 = "+ select);
				
				if(select == REGISTER) {
					String id = in.readUTF();
					String pw = in.readUTF();
					String phone = in.readUTF();
					String address = in.readUTF();
					System.out.println(id);	//Test
					System.out.println(pw);
					System.out.println(phone);
					System.out.println(address);
					boolean result = MemberManager.register(id, pw, phone, address);
					out.writeBoolean(result); out.flush();
			
				//로그인
				}else if(select == LOGIN) {
					Member member=null;
					String id = in.readUTF();
					System.out.println(id);
					String pw = in.readUTF();
					System.out.println(pw);
					member = MemberManager.login(id, pw);
					if(member==null) {
						out.writeObject(null); out.flush();
						continue;} 	//로그인 성공시 고객정보 객체 전송
					else {
						out.writeObject(member); out.flush(); 	//로그인 성공시 고객정보 객체 전송
					}
					
					//주문정보 받음.
					Order order = (Order) in.readObject();
					cal = Calendar.getInstance();
					time = (cal.get(Calendar.YEAR)-2000)+"년 "+(1+cal.get(Calendar.MONTH))+"월 "+cal.get(Calendar.DAY_OF_MONTH)+"일  "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
					System.out.println(time);	//test
					order.setOrdertime(time);
					ReceiptStorage.saveDatabase(OrderNumber.getOrderNumber(), order);
				}
			}
		}catch(Exception e) {
			kill();
		}
	}
}