package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.Member;
import master.Order;

//다중IP연결 쓰레드 생성 클래스

public class Connection extends Thread{
	private static Calendar cal;
	private static String time;
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;

	public static final int DELIVERY=0;	//배달맨 접속
	public static final int ORDERCOMPLETE = 1;
	public static final int COOKING = 2;
	public static final int COOKINGCOMPLETE = 3;
	public static final int DELIVERING=4;
	public static final int COMPLETE=5;
	
	
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
	 * try-catch로 예외발생시 폐쇄
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
	 * 배달맨에게 주문 할당하는 메소드
	 * @throws IOException
	 * 요리완료된 주문을 하나씩 할당한다.
	 */
	public void delivery() throws IOException {
		System.out.println("배달인  배달 가능합니다.");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		Iterator<Long> iterator = map.keySet().iterator();
		
		while(iterator.hasNext()) {
			Long num = iterator.next();
			Order order = map.get(num);
			if(order.getOrderState()==COOKINGCOMPLETE) {	//요리 완료된거를 배달갈꺼다
				//가게가 배달을 넘긴다.
				System.out.println("배달 고객 정보 넘김");	//test
				Map<Long, Order> map1 = new HashMap();
				map1.put(num, order);
				out.writeObject(map1);	//재구성한 맵을 넘긴다.
				out.flush();
				order.setOrderState(DELIVERING);	//배달중으로 바꾼다.
				System.out.println(order.getOrderState()+"배달 중이다.");	//test
				ReceiptManager.saveDatabase(num, order);
				return;	//하나하면 빠져나간다.
			}
		}
		out.writeObject(null);	//요리완료인 주문이 없으면 null을 전송한다.
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
				//
				//배달맨 배달가능
				if(select == DELIVERY) {
					delivery();
					continue;	//헤까림
				}
				
				//회원가입
				if(select == REGISTER) {
					String id = in.readUTF();
					String pw = in.readUTF();
					String phone = in.readUTF();
					String address = in.readUTF();
					boolean result = MemberManager.register(id, pw, phone, address);
					out.writeBoolean(result); out.flush();
					continue;		//회원가입하면 
				}	
			
				//로그인
					String id_login = in.readUTF();
					String pw_login = in.readUTF();
					Member member = MemberManager.login(id_login, pw_login);
					if(member==null) {
						out.writeObject(null); out.flush();
						continue;} 	//로그인 성공시 고객정보 객체 전송
					else {
						out.writeObject(member); out.flush(); 	//로그인 성공시 고객정보 객체 전송
					}
					
				
				//주문서받고 주문정보 저장
					while(true) {
							int memberSelect = in.readInt();	//고객에게 2.주문하기, 3.주문내역, 4. 로그아웃 전달 (1.은 대기)
							System.out.println("member선택 : "+memberSelect);
							if(memberSelect==2) {	//2.주문하기 선택시
								//주문정보 받음.
								Order order = (Order) in.readObject();
								cal = Calendar.getInstance();
								time = (cal.get(Calendar.YEAR)-2000)+"년 "+(1+cal.get(Calendar.MONTH))+"월 "+cal.get(Calendar.DAY_OF_MONTH)+"일  "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
								order.setOrdertime(time);
								Long orderNumber = OrderNumber.getOrderNumber();
								ReceiptManager.saveDatabasePrint(orderNumber, order);
								
								//주문서 받고 가게에서 버튼 누르면 주문상태를 요리중으로 변경
								Thread.sleep(3000);	//임시 5초 뒤 요리들어감
								order.setOrderState(COOKING);
								System.out.println(order.getOrderState()+" 요리중");	//test
								ReceiptManager.saveDatabase(orderNumber, order);
								
								Thread.sleep(5000);	//임시 5초 뒤 요리완료
								order.setOrderState(COOKINGCOMPLETE);
								System.out.println(order.getOrderState()+" 요리다했다~");	//test
								ReceiptManager.saveDatabase(orderNumber, order);
								
							}	
							if(memberSelect==3) {
								out.writeObject(ReceiptManager.getPerReceipt(id_login));//회원의 주문내역
								out.flush();
								System.out.println("test : 주문내역 넘겼다.");	//test
							}
							if(memberSelect==4)
								break;
					}
					
					
			}
		}catch(Exception e) {
			//고객이 종료 선택시 socket접속 끊기기에 예외발생
			kill();
		}
	}
}