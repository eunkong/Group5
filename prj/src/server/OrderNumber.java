package server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import master.Order;

//주문번호 생성 클래스
public class OrderNumber {
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);
	private static String numberStart = "0001";	//주문 시작 번호, 가게 인기도에따라 주문번호 자리수 변경가능
	/**
	 * 일자별 주문번호를 생성하는 메소드
	 * @return orderNum
	 * 일자나 년도가 변경되면 numberStart번부터 시작
	 */
	public static Long getOrderNumber(){
		try {
			Map<Long, Order> map = ReceiptStorage.loadDatabase();			//최초주문이 없다면?
			List<Long> list = new ArrayList<>(map.keySet());
			Collections.sort(list);
			Long lastNumber = list.get(list.size()-1);
			System.out.println("최근주문번호 : "+lastNumber); //test
			
			String lastDate = lastNumber.toString().substring(0, 6);
			if(date.equals(lastDate)) {	//최근 주문번호가 오늘 날짜면
				Long newNumber = lastNumber+1;
				return newNumber;
			}else {	//주문번호가 오늘이 아니면
				System.out.println("날짜 바꼈다!");
				String newNumber = date+numberStart;
				System.out.println("새번호 체크 : "+newNumber);
				System.out.println("새번호 Long 체크  : "+Long.parseLong(newNumber));
				return Long.parseLong(newNumber);
			}
		}catch(Exception e) {
			System.out.println("최초주문번호 생성!");
			String newNumber = date+numberStart;
			return Long.parseLong(newNumber);
		}
	}
	/**
	 * 현재시간 반환하는 메소드
	 * @return
	 */

	
}