package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
//주문번호 생성 클래스
public class OrderNumber {
	public static File db = new File("files", "orderNumber.db");
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);
	private final static int ORDERMAX = 9999;	//하루 최대 주문 횟수
	
	//하루 바뀌면 db.delete()해서 파일 삭제하고 다시만들어서
	//set을 구성해서 주문번호 디비에 1~9999까지 입력하고 주문들어올 때 마다 하나 씩 가져온다.
	
	/**
	 * 주문번호를 생성하는 메소드
	 * @return orderNum
	 */
//	public static String getOrderNumber(){
//		//만약 주문날짜가 기존파일에 저장된 날짜와 다르다면 
////		if(check) {	//파일에 저장된 날짜정보가 오늘이면
////			setNumber();
////		}else {	//파일에 저장된 날짜정보가 처음 생성하거나 오늘이 아니면
////			resetNumber();
////		}
//	}
	
	
//	private static String setNumber() {
//		try(
//				ObjectInputStream in = new ObjectInputStream(
//															new BufferedInputStream(
//																new FileInputStream(db)));
//				
//		){
//			String recentNum = in.readUTF().substring(6, 9);
//			int recent = Integer.parseInt(recentNum);
//			try(
//					ObjectOutputStream out = new ObjectOutputStream(
//							new BufferedOutputStream(
//									new FileOutputStream(db)));
//					){
//				Format f = new DecimalFormat("0000");
//				for(int i=recent+1;i<ORDERMAX;i++) {
//					String orderNum = f.format(i);
//					out.writeObject(date+orderNum);
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//			
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
	

	/**
	 * 오늘 주문번호(String) 모두(1~9999)를 파일에 쓰는 메소드 
	 */
	private static void resetNumber() {
		try(
				ObjectOutputStream out = new ObjectOutputStream(
															new BufferedOutputStream(
																new FileOutputStream(db)));
			){
				Format f = new DecimalFormat("0000");
				for(int i=1;i<ORDERMAX;i++) {
					String orderNum = f.format(i);
					out.writeObject(date+orderNum);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}

	/**
	 * 주문번호 불러오는 메소드
	 * @return Map<날짜,Set<번호>>
	 * 문제점 번호를 set으로 가져와도 파일에서는 안없어진다. set의 의미가 없다.
	 */
	private static Map<String,Set<Integer>> loadDatabase() {
		try(
			ObjectInputStream in = new ObjectInputStream(
														new BufferedInputStream(
															new FileInputStream(db)));
		){
			Map<String,Set<Integer>> map  = (Map<String, Set<Integer>>) in.readObject();
			System.out.println(map.get(date)+" 주문번호 불러오기");
				return map;
		}catch(Exception e) {
			e.printStackTrace();
			return new HashMap();
		}
	}

	
	//임시 점검용
	public static String getOrderNumber() {
		
		return "1712270001";
	}
	
//날짜번호를 같이 많이 입력하고, 불러온뒤 삭제
//시작인덱스를 1 늘여서 
//파일에 다시 쓴다.
	
	
	/**
	 * orderNumber.db에 주문번호를 reset하는 메소드
	 * Map<날짜,Set<번호>>로 파일 저장
	 */
	

	
	
//파일불러와서
//만약 날짜번호가 오늘날짜랑 다르다면 
//다지우고(delete) 필요하나?
//다시 날짜번호 생성
//
//
	
	
	
}
