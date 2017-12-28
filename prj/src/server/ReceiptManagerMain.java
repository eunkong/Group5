package server;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import master.Order;

//영수증 관리, 추후 UI 구현 예정.
public class ReceiptManagerMain {
	public static void main(String[] args) {
//		Map<Long,Order> map1 = ReceiptStorage.loadDatabase();
//		Set<Long> set1 = map1.keySet();
//		Iterator<Long> iterator1	= set1.iterator();
//		while(iterator1.hasNext()) {
//			Long orderNum = iterator1.next();
//			Order order = map1.get(orderNum);
//			ReceiptStorage.printReceipt(orderNum, order);
//		}
//		
//		System.out.println();
//		System.out.println();
		
		
		//일자
//		Map<Long,Order> map = ReceiptStorage.getPeriodReceipt("171228");
//		Set<Long> set = map.keySet();
//		Iterator<Long> iterator	= set.iterator();
//		while(iterator.hasNext()) {
//			Long orderNum = iterator.next();
//			Order order = map.get(orderNum);
//			ReceiptStorage.printReceipt(orderNum, order);
//		}
		
		//기간
//		Map<Long,Order> map = ReceiptStorage.getPeriodReceipt("171228","171230");
//		Set<Long> set = map.keySet();
//		Iterator<Long> iterator	= set.iterator();
//		while(iterator.hasNext()) {
//			Long orderNum = iterator.next();
//			Order order = map.get(orderNum);
//			ReceiptStorage.printReceipt(orderNum, order);
//		}
		
		//아이디
		Map<Long,Order> map = ReceiptStorage.getPerReceipt("master");
		Set<Long> set = map.keySet();
		Iterator<Long> iterator	= set.iterator();
		while(iterator.hasNext()) {
			Long orderNum = iterator.next();
			Order order = map.get(orderNum);
			ReceiptStorage.printReceipt(orderNum, order);
		}
		
		
		
	}
}
