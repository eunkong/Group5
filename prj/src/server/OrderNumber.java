package server;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import master.Order;

//주문번호 생성 클래스
public class OrderNumber {
	private static Date d;
	private static Format f = new SimpleDateFormat("yyMMdd");
	private static String date;
	private static String numberStart = "0001";	//주문 시작 번호, 주문번호 자리수 변경가능
	
	/**
	 * 일자별 주문번호를 생성하는 메소드
	 * @return orderNum
	 * 일자나 년도가 변경되면 numberStart번부터 시작
	 */
	public static Long getOrderNumber(){
			d = new Date();						//주문번호 받을 때, 현재 날짜
			date = f.format(d);
		try {
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			List<Long> list = new ArrayList<>(map.keySet());
			Collections.sort(list);
			Long lastNumber = list.get(list.size()-1);
			
			String lastDate = lastNumber.toString().substring(0, 6);
			if(date.equals(lastDate)) {	//최근 주문번호가 오늘자
				Long newNumber = lastNumber+1;
				return newNumber;
			}else {						//최근 주문번호가 오늘자가 아닐 경우
				String newNumber = date+numberStart;
				return Long.parseLong(newNumber);
			}
		}catch(Exception e) {
			String newNumber = date+numberStart;
			return Long.parseLong(newNumber);
		}
	}

	
}