package server;

import java.util.HashMap;
import java.util.Map;

import client.Member;
import master.Order;

public class ReceiptTest {
	public static void main(String[] args) {
//		ReceiptManager.printReceipt(ReceiptManager.getPeriodReceipt("171229"));		
//		ReceiptManager.printReceipt(ReceiptManager.getPeriodReceipt("171227", "171230"));
//		ReceiptManager.printReceipt(ReceiptManager.getPerReceipt("master"));
//		ReceiptManager.printReceipt(ReceiptManager.loadDatabase());
		Map<Long, Order> map = new HashMap<>();
		Order order = new Order(new Member());
		map.put(102003L, order);
		map.put(102001L, new Order(new Member()));
		ReceiptManager.printReceipt(102003L, order);
	}
}
