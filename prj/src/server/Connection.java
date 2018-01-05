package server;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import client.Member;
import master.Order;

//다중IP 연결 생성 클래스(Thread상속)
public class Connection extends Thread {
	
	private static Date d;
	private static Format f = new SimpleDateFormat("yy년 MM월 dd일 HH:mm:ss");
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
	
	private static List<Connection> list = new ArrayList<>();

	/**
	 * 연결추가 메소드
	 * @param Connection
	 *            객체
	 */
	public static void add(Connection c) {
		list.add(c);
	}

	
	/**
	 * 연결삭제 메소드
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
			// 고객이 종료 선택시 예외발생
			kill();
		}
	}

	
	/**
	 * 배달부 접근 처리 메소드
	 * @throws IOException
	 * 요리완료된 주문을 하나씩 할당, 입력에 따른 배달상태 변화.
	 */
	public void deliveryAccess() throws IOException {
		while(true) {
			in.readInt();	//배달가능여부 입력 받음
			System.out.println("[test] 배달인  배달 가능합니다.");
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<>(map);
			Iterator<Long> iterator = tmap.keySet().iterator();		//오래된 주문부터 처리
			
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = tmap.get(num);
				if(order.getOrderState()==COOKINGCOMPLETE) {	//요리 완료된 주문
					System.out.println("[test] 배달 고객 정보 넘김");	//test
					Map<Long, Order> map1 = new HashMap();
					map1.put(num, order);
					out.writeObject(map1);	//재구성한 맵을 배달부에게 전송
					out.flush();
					boolean delivery_start = in.readBoolean(); //배달시작 입력 받음	
					order.setOrderState(DELIVERING);	//주문상태 변경(배달중)
					System.out.println(order.getOrderState()+"[test] 배달 중이다.");	//test
					ReceiptManager.saveDatabase(num, order);
					boolean delivery_complete = in.readBoolean(); //배달완료 입력 받음		
					System.out.println(delivery_complete?"[test] 배달완료":"배달미완료");
					order.setOrderState(COMPLETE);	//주문상태 변경(배달완료)
					System.out.println("[test] "+order.getOrderState());  //test
					ReceiptManager.saveDatabase(num, order);
					break;	//하나하면 빠져나간다.
				}
			}
			if(!iterator.hasNext())
				out.writeObject(null);	//요리완료인 주문이 없으면 null 전송
		}
		
	}


	/**
	 * 요리사 접근 처리 메소드
	 * @throws IOException
	 * 주문완료 상태 주문 전달, 요리가능(완료)여부 입력 받고 상태 변화
	 */
	private void chefAccess() throws IOException {
		System.out.println("요리사 접속");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		TreeMap<Long, Order> tmap = new TreeMap<>(map);
		Iterator<Long> iterator = tmap.keySet().iterator();		//오래된 주문부터 처리

		while (iterator.hasNext()) { 
			Map<Long, Order> map1 = new HashMap();
			Long num = iterator.next();
			Order order = tmap.get(num);
			if (order.getOrderState() == ORDERCOMPLETE) { 	//요리해야할 주문
				map1.put(num, order);
				
				out.writeObject(map1); //요리해야할 주문 하나 전송
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
				out.writeObject(null); // 요리사에게 전달할 주문이 없을 때 null 전송
				out.flush();
	}
	

	/**
	 * 고객 접속 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * 로그인, 회원가입, 로그인 이후 접속 관련 처리
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
			}
			else {
				out.writeObject(member);
				out.flush(); // 로그인 성공시 고객정보 객체 전송
			}

			//고객 접속 메인선택 처리 메소드
			guestMainSelect(id_login);
		}

	}

	/**
	 * 고객접속창 메인선택 처리 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * 로그인시 연결되는 고객메인창의 선택 처리
	 * 고객 접속 메소드에서 호출 활용
	 */

	private void guestMainSelect(String id_login) throws IOException, ClassNotFoundException {
		while (true) {
			int memberSelect = in.readInt(); // [1. 내정보  2.주문하기, 3.주문내역, 4. 로그아웃] 전달 선택 입력 받음.
			System.out.println("member선택 : " + memberSelect);
			
			if(memberSelect==1) {		// 1.내정보
				
				System.out.println("[test] memberSelect 1일때 : " + memberSelect);
				
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
			
			if (memberSelect == 2) { 	// 2.주문하기
				boolean result = in.readBoolean();
				System.out.println("[test] : "+result);
				Order order = (Order) in.readObject();
				System.out.println("[test] : "+order.toString());
				d = new Date();
				time = f.format(d);
				order.setOrdertime(time);
				Long orderNumber = OrderNumber.getOrderNumber();
				ReceiptManager.saveDatabasePrint(orderNumber, order);
			}
			
			if (memberSelect == 3) {	// 3. 주문내역
				out.writeObject(ReceiptManager.getPerReceipt(id_login));
				out.flush();
				System.out.println("[test] : 주문내역 넘겼다."); // test
			}
			if (memberSelect == 4) {	// 4. 로그아웃
				break;
			}
		}
		
	}
}