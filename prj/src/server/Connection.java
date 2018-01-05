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
import java.util.TreeMap;

import client.Member;
import master.Order;

//다중IP연결 쓰레드 생성 클래스

public class Connection extends Thread {
	private static Calendar cal;
	private static String time;

	private static final int GUEST = 1;
	private static final int CHEF = 2;
	private static final int DELIVERYMAN = 3;

	public static final int REGISTER = 1;
	public static final int LOGIN = 2;

	public static final int DELIVERY = 0; // 배달맨 접속
	public static final int ORDERCOMPLETE = 1;
	public static final int COOKING = 2;
	public static final int COOKINGCOMPLETE = 3;
	public static final int DELIVERING = 4;
	public static final int COMPLETE = 5;
	
	boolean loginFlag = false; 

	private static List<Connection> list = new ArrayList<>();

	/**
	 * 연결추가 메소드
	 * 
	 * @param Connection
	 *            객체
	 */
	public static void add(Connection c) {
		list.add(c);
	}

	/**
	 * 연결삭제 메소드
	 * 
	 * @param Connection
	 *            객체
	 */
	public static void remove(Connection c) {
		list.remove(c);
	}

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean flag = true;

	/**
	 * 통로 폐쇄 메소드 try-catch로 예외발생시 폐쇄
	 */
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
		}
		remove(this);
	}

	/**
	 * Connection 생성자
	 * 
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException {
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
			int user = in.readInt(); // 클라이언트의 종류 확인
			if (user == GUEST) {
				System.out.println("[test] 고갴입장~"); // test
				guestAccess();
			}
			if (user == CHEF) {
				System.out.println("[test] 쉪입장~"); // test
				chefAccess();
			}
			if (user == DELIVERYMAN) {
				System.out.println("[test] 배달아저씨입장~"); // test
				deliveryAccess();
			}

		} catch (Exception e) {
			// 고객이 종료 선택시 socket접속 끊기기에 예외발생
			kill();
		}
	}

	/**
	 * 배달맨에게 주문 할당하는 메소드
	 * 
	 * @throws IOException
	 *             요리완료된 주문을 하나씩 할당한다.
	 */
	public void deliveryAccess() throws IOException {
		while(true) {
			in.readInt();	//배달가능여부 받는다.
			
			System.out.println("배달인  배달 가능합니다.");
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<>(map);
			Iterator<Long> iterator = tmap.keySet().iterator();		//오래된 주문부터 처리
			
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = tmap.get(num);
				if(order.getOrderState()==COOKINGCOMPLETE) {	//요리 완료된거를 배달갈꺼다
					//가게가 배달을 넘긴다.
					System.out.println("배달 고객 정보 넘김");	//test
					Map<Long, Order> map1 = new HashMap();
					map1.put(num, order);
					out.writeObject(map1);	//재구성한 맵을 넘긴다.
					out.flush();
					boolean delivery_start = in.readBoolean(); //배달시작 입력 받음	
					order.setOrderState(DELIVERING);	//배달중으로 바꾼다.
					System.out.println(order.getOrderState()+"배달 중이다.");	//test
					ReceiptManager.saveDatabase(num, order);
					boolean delivery_complete = in.readBoolean(); //배달완료시 입력 받음		
					System.out.println(delivery_complete?"배달완료":"배달미완료");
					order.setOrderState(COMPLETE);	//배달완료로 바꾼다.
					System.out.println(order.getOrderState());  //test
					ReceiptManager.saveDatabase(num, order);
					break;	//하나하면 빠져나간다.
				}
			}
			if(!iterator.hasNext())
				out.writeObject(null);	//요리완료인 주문이 없으면 null을 전송한다.
		}
		
	}


//	/**
//	 * 요리사 접근 메소드
//	 * @throws IOException
//	 * 
//	 */
//	private void chefAccess() throws IOException {
//		System.out.println("요리사 접속");
//		while(true) {//요리사 메뉴불러오기 , 전체반복
//			Map<Long, Order> map = ReceiptManager.loadDatabase();
//			TreeMap<Long, Order> tm = new TreeMap<Long, Order>(map);		
//			Iterator<Long> iterator = tm.keySet().iterator();			//map정렬 예전 주문부터 처리
//			
//			while (iterator.hasNext()) { // 전체메뉴중에
//				Long num = iterator.next();
//				Order order = map.get(num);
//				// 쓰레드로 각각의 주문을 따로 처리
//				// 쓰레드 안에 새로운 쓰레드를 만든다.
//				
//				if (order.getOrderState() == ORDERCOMPLETE) { // 요리해야할 주문
//					Thread thread = new Thread() {
//						public void run() {
//							Map<Long, Order> map1 = new HashMap();
//							map1.put(num, order); // 요리해야할 주문 넘김
//							try {
//								out.writeObject(map1);
//								out.flush();
//								
//								if (in.readBoolean()) { // 요리중 상태 전달받음
//									order.setOrderState(COOKING);
//									ReceiptManager.saveDatabase(num, order);
//									System.out.println("[test]" + order.getOrderState() + "요리중이에요~");
//								}
//								if (in.readBoolean()) { // 요리완료 상태 전달받음
//									order.setOrderState(COOKINGCOMPLETE);
//									System.out.println("[test]" + order.getOrderState() + "요리다했다 배달가라."); // test
//									ReceiptManager.saveDatabase(num, order);
//								}
//								
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					};
//					thread.start();
//				}
//			}
//			out.writeObject(null); out.flush();	//신호, 요리해야할 주문을 다 보내고 나면 null값 보낸다. 
//			
//		}
//	}
//	


	/**
	 * 요리사 접근 메소드
	 * @throws IOException
	 * 
	 */
	private void chefAccess() throws IOException {
		System.out.println("요리사 접속");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		TreeMap<Long, Order> tmap = new TreeMap<>(map);
		Iterator<Long> iterator = tmap.keySet().iterator();		//오래된 주문부터 처리

		while (iterator.hasNext()) { // 전체메뉴중에
			Map<Long, Order> map1 = new HashMap();
			Long num = iterator.next();
			Order order = tmap.get(num);
			// 쓰레드로 각각의 주문을 따로 처리
			if (order.getOrderState() == ORDERCOMPLETE) { // 요리해야할 주문
				map1.put(num, order); // 요리해야할 주문 넘김
				
				out.writeObject(map1);
				out.flush();
				
				if (in.readBoolean()) { // 요리중 상태 전달받음
					order.setOrderState(COOKING);
					ReceiptManager.saveDatabase(num, order);
					System.out.println("[test]" + order.getOrderState() + "요리중이에요~");
				}
				if (in.readBoolean()) { // 요리완료 상태 전달받음
					order.setOrderState(COOKINGCOMPLETE);
					System.out.println("[test]" + order.getOrderState() + "요리다했다 배달가라."); // test
					ReceiptManager.saveDatabase(num, order);
				}
				
			}
		}
				out.writeObject(null); // 요리사에게 전달할 주문이 없을 때
				out.flush();

	}
	
	
	

	/**
	 * 고객 접속 메소드
	 * 
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	private void guestAccess() throws IOException, ClassNotFoundException, InterruptedException {
		while (flag) {
			int select = in.readInt();
			System.out.println("선택 = " + select);
			// 회원가입
			if (select == REGISTER) {
				String id = in.readUTF();
				String pw = in.readUTF();
				String phone = in.readUTF();
				String address = in.readUTF();
				boolean result = MemberManager.register(id, pw, phone, address);
				out.writeBoolean(result);
				out.flush();
				continue; // 회원가입하면
			}

			// 로그인
			String id_login = in.readUTF();
			String pw_login = in.readUTF();
			Member member = MemberManager.login(id_login, pw_login);
			if (member == null) {
				out.writeObject(null);
				out.flush();
				continue;
			} // 로그인 성공시 고객정보 객체 전송
			else {
				loginFlag= true;
				out.writeObject(member);
				out.flush(); // 로그인 성공시 고객정보 객체 전송
			}

			// 주문서받고 주문정보 저장
			while (true) {
				int memberSelect = in.readInt(); // 고객에게 1. 내정보  2.주문하기, 3.주문내역, 4. 로그아웃 전달 (1.은 대기)
				System.out.println("member선택 : " + memberSelect);
				
				if(memberSelect==1) {		// 1. 내정보 수정하기 선택시
					//수정할지 안할지를 알아야한다.
					System.out.println("memberSelect 1일때 : " + memberSelect);
					
					//해당 아이디의 최신 내정보 전송
					Map<String, Member> map = MemberManager.loadDatabase();
					Member memberNew = map.get(id_login);
					System.out.println("주소변경한거 보낸다. : "+memberNew.getAddress());
					out.writeObject(memberNew); out.flush();
					
					
					boolean editBoolean = in.readBoolean();
					System.out.println("[test] 수정할거야? "+editBoolean);
					
					if(!editBoolean) {		//수정 안할 경우
						continue;
					}
					if(editBoolean) {		//수정할 경우
						System.out.println("[test] 수정 시작할게요~");
						Member editMember = (Member) in.readObject();
						System.out.println("[test] 수정할 아이디 받았습니다 : "+ editMember.getId());
						System.out.println("[test] 수정할 번호 받았어요 :"+editMember.getPhoneNumber());
						System.out.println("[test] 수정할 주소도 받았다~ : "+editMember.getAddress());
						Member editComplete = MemberManager.editInfo(editMember);
						out.writeObject(editComplete);
					}
				}
				
				if (memberSelect == 2) { // 2.주문하기 선택시
					// 주문정보 받음.
					boolean result = in.readBoolean();
					System.out.println(result);
					Order order = (Order) in.readObject();
					System.out.println(order.toString());
					cal = Calendar.getInstance();
					time = (cal.get(Calendar.YEAR) - 2000) + "년 " + (1 + cal.get(Calendar.MONTH)) + "월 "
							+ cal.get(Calendar.DAY_OF_MONTH) + "일  " + cal.get(Calendar.HOUR_OF_DAY) + ":"
							+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
					order.setOrdertime(time);
					Long orderNumber = OrderNumber.getOrderNumber();
					ReceiptManager.saveDatabasePrint(orderNumber, order);

				}
				if (memberSelect == 3) {
					out.writeObject(ReceiptManager.getPerReceipt(id_login));// 회원의 주문내역
					out.flush();
					System.out.println("test : 주문내역 넘겼다."); // test
				}
				if (memberSelect == 4) {
					loginFlag = false;
					break;
				}
			}

		}

	}
}