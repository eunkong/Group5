package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import client.Member;
import master.Menu;
import master.Order;

//영수증 파일 관리 클래스
public class ReceiptStorage {
		public static final File db = new File("files", "receiptlist.db");
		
	/**
	 * 주문번호와 주문정보를 받아 콘솔에 영수증 출력하는 메소드
	 * @param orderNum 주문번호
	 * @param order 주문정보
	 * private 선언 : 내부 사용
	 */
	private static void printReceipt(String orderNum, Order order) {
			System.out.println("\t=== 짜 장 전 설   영 수 증 ===");
			System.out.println("\t주문번호 : "+ orderNum);
			System.out.println("\t고객 아이디 : "+ order.getMember().getId());
			System.out.println("\t고객 주소 : "+ order.getMember().getAddress());
			System.out.println("\t고객 연락처 : "+order.getMember().getPhoneNumber());
			System.out.println("\t=====================");
			System.out.println("\t[주문메뉴]");
			//주문메뉴 출력
			Map<Menu, Integer> orderMap = order.getOrderIdx();
			for (Iterator<Menu> iterator = orderMap.keySet().iterator(); iterator.hasNext();) {
				Menu menu= iterator.next();
				System.out.println("\t"+menu.getName()+"\t"+orderMap.get(menu)+"개\t"+menu.getPrice()*orderMap.get(menu)+"원");
			}
			System.out.println("\t=====================");
			System.out.println("\t총 가격 :\t\t"+order.getPriceSum()+"원");
	}

	
	/**
	 * 개인의 주문기록 불러오는 메소드
	 * @param map
	 * @return
	 */
//	public static Map<String, Order> getPerReceipt(Member member){
//		 Map<String, Order> allreceipt = loadDatabase();
//		for (Iterator<String> iterator = allreceipt.keySet().iterator(); iterator.hasNext();) {
//			while(iterator.hasNext()) {
//				String orderNum = iterator.next();
//				Order order = allreceipt.get(orderNum);
//				Map<String, Order> map = new HashMap<>();
//				if(member.getId().equals(order.getMember().getId()))	//매개변수로 넘겨온 member와 영수증에 있는 id가 일치하면 가져와라	
//					map.put(orderNum, arg1);		//이것의 주문번호, 주문정보 넘긴다.
//			}
//		}		 
//	}

	/**
	 * 전체 영수증 파일 불러오는 메소드		
	 * @return Map<String, Order> 영수증 전체
	 */
		public static Map<String, Order> loadDatabase() {
			try(
				ObjectInputStream in = new ObjectInputStream(
															new BufferedInputStream(
																new FileInputStream(db)));
			){
				@SuppressWarnings("unchecked")
				Map<String, Order> map = (Map<String, Order>) in.readObject();
				System.out.println("불러온거 비어있는지? : "+map.isEmpty());	//test
				return map;
			}
			catch(Exception e) {
				e.printStackTrace();
				return new HashMap<>();
			}
		}

		
	/**
	 * 주문번호, 주문정보를 파일에 입력하는 메소드
	 * 추가적으로 입력정보 콘솔 출력
	 * @param orderNum
	 * @param order
	 */
		public static void saveDatabase(String orderNum, Order order) {
			try(
					ObjectOutputStream out = new ObjectOutputStream(
																new BufferedOutputStream(
																	new FileOutputStream(db)));
			){
				Map<String, Order> map = new HashMap<>();
				map.put(orderNum, order);
				printReceipt(orderNum, order);
				out.writeObject(map);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		
		//맵을 받아 그 내용 영수를 출력하는 메소드도 필요하다.
		//날짜별 영수증 가져오는 메소드, 주문번호의 앞번호가 해당날짜로 시작하는 정보들 불러오기
		//기간별 영수증 가져오는 메소드, 오버라이딩으로 매개변수 두개일 때로 정의

}
