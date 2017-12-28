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
import java.util.Set;

import master.Menu;
import master.Order;

//영수증 파일 관리 클래스
public class ReceiptStorage {
		public static final File db = new File("files", "receiptlist.db");


	/**
	 * 전체 영수증 파일 불러오는 메소드		
	 * @return Map<String, Order> 영수증 전체
	 */
		public static Map<Long, Order> loadDatabase() {
			try(
				ObjectInputStream in = new ObjectInputStream(
															new BufferedInputStream(
																new FileInputStream(db)));
			){
				@SuppressWarnings("unchecked")
				Map<Long, Order> map = (Map<Long, Order>) in.readObject();
				return map;
			}
			catch(Exception e) {
				System.out.println("최초주문");
				return new HashMap<>();
			}
		}
		
	/**
	 * 주문번호, 주문정보를 파일에 입력하는 메소드
	 * 추가적으로 입력정보 콘솔 출력
	 * @param orderNum
	 * @param order
	 */
		public static void saveDatabase(Long orderNum, Order order) {
			try(
					ObjectOutputStream out = new ObjectOutputStream(
																new BufferedOutputStream(
																	new FileOutputStream(db)));
			){
				Map<Long, Order> map = new HashMap<>();
				map.put(orderNum, order);
				printReceipt(orderNum, order);
				out.writeObject(map);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	/**
	 * 주문번호와 주문정보를 받아 콘솔에 영수증 출력하는 메소드
	 * @param orderNum 주문번호
	 * @param order 주문정보
	 * private 선언 : 내부 사용
	 */
	private static void printReceipt(Long orderNum, Order order) {
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
		 * @param id
		 * @return Map<주문번호, Order>
		 * 매개변수로 받은 id와 일치하는 영수증 추출
		 */
		public static Map<Long, Order> getPerReceipt(String id){
			 Map<Long, Order> allreceipt = loadDatabase();
			 Set<Long> set = allreceipt.keySet();
			 Iterator<Long> iterator = set.iterator();
			 Map<Long, Order> map = new HashMap<>();
			 while(iterator.hasNext()) {
				 Long orderNum = iterator.next();
				 Order order = allreceipt.get(orderNum);
				 if(id.equals(order.getMember().getId()))	
						map.put(orderNum, order);		
			 }
			 return map;
		}
		

		/**
		 * 입력받은 날짜 영수증 가져오는 메소드
		 * @param date(String)
		 * @return Map<주문번호, Order>
		 */
		public static Map<Long, Order> getPeriodReceipt(String date){
			 Map<Long, Order> allreceipt = loadDatabase();
			 Set<Long> set = allreceipt.keySet();
			 Iterator<Long> iterator = set.iterator();
			 Map<Long, Order> map = new HashMap<>();
			 while(iterator.hasNext()) {
				 Long orderNum = iterator.next();
				 Order order = allreceipt.get(orderNum);
				 String orderDate = orderNum.toString().substring(0, 6);
				 if(date.equals(orderDate))	
						map.put(orderNum, order);		
			 }
			 return map;
		}
		
		/**
		 * 입력받은 기간 영수증 가져오는 메소드
		 * @param start, end
		 * @return Map<주문번호, Order>
		 */
		public static Map<Long, Order> getPeriodReceipt(String start, String end){
			 Map<Long, Order> allreceipt = loadDatabase();
			 Set<Long> set = allreceipt.keySet();
			 Iterator<Long> iterator = set.iterator();
			 Map<Long, Order> map = new HashMap<>();
			 while(iterator.hasNext()) {
				 Long orderNum = iterator.next();
				 Order order = allreceipt.get(orderNum);
				 String orderDate = orderNum.toString().substring(0, 6);
				 Long orderDateLong = Long.parseLong(orderDate);
				 Long startLong = Long.parseLong(start);
				 Long endLong = Long.parseLong(end);
				 if(orderDateLong<=endLong&&orderDateLong>=startLong)	
						map.put(orderNum, order);		
			 }
			 return map;
		}
		
		
		

}
