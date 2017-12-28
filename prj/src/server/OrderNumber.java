package server;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import master.Order;

//주문번호 생성 클래스
public class OrderNumber {
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);

	/**
	 * 주문번호를 생성하는 메소드
	 * @return orderNum
	 */
	public static Long getOrderNumber(){
		Map<Long, Order> map = ReceiptStorage.loadDatabase();
		List<Long> list = new ArrayList<>(map.keySet());
		System.out.println("Map에 주문번호만 출력 : "+list.toString());
		Collections.sort(list);
		System.out.println("주문번호 솔트 : "+list.toString());
		Long lastNumber = list.get(list.size()-1);
		System.out.println("최근주문번호 : "+lastNumber); //test
		
		String lastDate = lastNumber.toString().substring(0, 6);
		System.out.println("최근날짜 : "+lastDate); //test
		if(date.equals(lastDate)) {	//최근 주문번호가 오늘 날짜면
			Long newNumber = lastNumber+1;
			System.out.println("오늘날짜 새번호 : "+newNumber);
			return newNumber;
		}else {	//주문번호가 오늘이 아니면
			System.out.println("날짜 바꼈다!");
			String newNumber = date+"0001L";
			System.out.println("새번호 체크 : "+newNumber);
			String newNum = newNumber.substring(0, newNumber.length()-1);	//L제거
			System.out.println("새번호 Long 체크  : "+Long.parseLong(newNum));
			return Long.parseLong(newNum);
		}
	}

	
}