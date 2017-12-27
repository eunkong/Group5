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

//������ ���� ���� Ŭ����
public class ReceiptStorage {
		public static final File db = new File("files", "receiptlist.db");
		
	/**
	 * �ֹ���ȣ�� �ֹ������� �޾� �ֿܼ� ������ ����ϴ� �޼ҵ�
	 * @param orderNum �ֹ���ȣ
	 * @param order �ֹ�����
	 * private ���� : ���� ���
	 */
	private static void printReceipt(String orderNum, Order order) {
			System.out.println("\t=== ¥ �� �� ��   �� �� �� ===");
			System.out.println("\t�ֹ���ȣ : "+ orderNum);
			System.out.println("\t�� ���̵� : "+ order.getMember().getId());
			System.out.println("\t�� �ּ� : "+ order.getMember().getAddress());
			System.out.println("\t�� ����ó : "+order.getMember().getPhoneNumber());
			System.out.println("\t=====================");
			System.out.println("\t[�ֹ��޴�]");
			//�ֹ��޴� ���
			Map<Menu, Integer> orderMap = order.getOrderIdx();
			for (Iterator<Menu> iterator = orderMap.keySet().iterator(); iterator.hasNext();) {
				Menu menu= iterator.next();
				System.out.println("\t"+menu.getName()+"\t"+orderMap.get(menu)+"��\t"+menu.getPrice()*orderMap.get(menu)+"��");
			}
			System.out.println("\t=====================");
			System.out.println("\t�� ���� :\t\t"+order.getPriceSum()+"��");
	}

	
	/**
	 * ������ �ֹ���� �ҷ����� �޼ҵ�
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
//				if(member.getId().equals(order.getMember().getId()))	//�Ű������� �Ѱܿ� member�� �������� �ִ� id�� ��ġ�ϸ� �����Ͷ�	
//					map.put(orderNum, arg1);		//�̰��� �ֹ���ȣ, �ֹ����� �ѱ��.
//			}
//		}		 
//	}

	/**
	 * ��ü ������ ���� �ҷ����� �޼ҵ�		
	 * @return Map<String, Order> ������ ��ü
	 */
		public static Map<String, Order> loadDatabase() {
			try(
				ObjectInputStream in = new ObjectInputStream(
															new BufferedInputStream(
																new FileInputStream(db)));
			){
				@SuppressWarnings("unchecked")
				Map<String, Order> map = (Map<String, Order>) in.readObject();
				System.out.println("�ҷ��°� ����ִ���? : "+map.isEmpty());	//test
				return map;
			}
			catch(Exception e) {
				e.printStackTrace();
				return new HashMap<>();
			}
		}

		
	/**
	 * �ֹ���ȣ, �ֹ������� ���Ͽ� �Է��ϴ� �޼ҵ�
	 * �߰������� �Է����� �ܼ� ���
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

		
		//���� �޾� �� ���� ������ ����ϴ� �޼ҵ嵵 �ʿ��ϴ�.
		//��¥�� ������ �������� �޼ҵ�, �ֹ���ȣ�� �չ�ȣ�� �ش糯¥�� �����ϴ� ������ �ҷ�����
		//�Ⱓ�� ������ �������� �޼ҵ�, �������̵����� �Ű����� �ΰ��� ���� ����

}
