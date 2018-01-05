package server;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
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

//receiptlist.db ������ ���� ���� Ŭ����
public class ReceiptManager {
		public static final File db = new File("files", "receiptlist.db");
		public static String[] state = {"","�ֹ��Ϸ�","�丮��","�丮�Ϸ�","�����","��޿Ϸ�"};

		/**
		 * ��ü ������ ���� �ҷ����� �޼ҵ�		
		 * @return Map<String, Order> ������ ��ü
		 */
		public static Map<Long, Order> loadDatabase() {
			try(
				ObjectInputStream in = new ObjectInputStream(
															new BufferedInputStream(
																new FileInputStream(db)));
			){
				Map<Long, Order> map = (Map<Long, Order>) in.readObject();
				return map;
			}
			catch(EOFException e) {
				e.printStackTrace();
				return new HashMap<>();
			}catch(Exception e) {
				e.printStackTrace();
				return new HashMap<>();
			}
		}
		
		
		/**
		 * �ֹ���ȣ, �ֹ������� ���Ͽ� �Է��ϴ� �޼ҵ�
		 * @param orderNum
		 * @param order
		 */
		public static void saveDatabase(Long orderNum, Order order) {
			Map<Long, Order> map = loadDatabase();	//���Ͽ� ����� map�ҷ�����
			try(
					ObjectOutputStream out = new ObjectOutputStream(
																new BufferedOutputStream(
																	new FileOutputStream(db)));

			){
					
					Iterator<Long> test = map.keySet().iterator();
					while(test.hasNext()) {
						Long num = test.next();
					}	
					
					map.put(orderNum, order);
					out.writeObject(map);	//���� �ٽý����.
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * �ֹ���ȣ, �ֹ������� ���Ͽ� �Է��� �� �ֿܼ� ���(Test)�ϴ� �޼ҵ�
		 * @param orderNum
		 * @param order
		 */
		public static void saveDatabasePrint(Long orderNum, Order order) {
			Map<Long, Order> map = loadDatabase();	//���Ͽ� ����� map�ҷ�����
			try(
					ObjectOutputStream out = new ObjectOutputStream(
																new BufferedOutputStream(
																	new FileOutputStream(db)));

			){
					map.put(orderNum, order);
					printReceipt(orderNum,order);
					out.writeObject(map);	//���� �ٽý����.
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		//
		
		/**
		 * �ֹ���ȣ�� �ֹ������� �޾� �ֿܼ� ������ ����ϴ� �޼ҵ�(Test��)
		 * @param Map<�ֹ���ȣ, Order>
		 */
		public static void printReceipt(Map<Long, Order> map) {
				Set<Long> set = map.keySet();
				Iterator<Long> iterator	= set.iterator();
				while(iterator.hasNext()) {
					Long orderNum = iterator.next();
					Order order = map.get(orderNum);					
					printReceipt(orderNum, order);
				}
		}	
		
		
		
		/**
		 * �ֹ���ȣ�� �ֹ������� �޾� �ֿܼ� ������ ����ϴ� �޼ҵ�(Test��)
		 * @param orderNum �ֹ���ȣ
		 * @param order �ֹ�����
		 */
		public static void printReceipt(Long orderNum, Order order) {
				System.out.println();
				System.out.println("\t[�ֹ�����]"+state[order.getOrderState()]);
				System.out.println("\t======= ¥ �� �� ��   �� �� �� =======");
				System.out.println("\t�ֹ���ȣ : "+ orderNum);
				System.out.println("\t�ֹ��ð� : "+ order.getOrdertime());
				System.out.println("\t�� ���̵� : "+ order.getMember().getId());
				System.out.println("\t�� �ּ� : "+ order.getMember().getAddress());
				System.out.println("\t�� ����ó : "+order.getMember().getPhoneNumber());
				System.out.println("\t==============================");
				System.out.println("\t[�ֹ��޴�]");
				//�ֹ��޴� ���
				Map<Menu, Integer> orderMap = order.getOrderIdx();
				for (Iterator<Menu> iterator = orderMap.keySet().iterator(); iterator.hasNext();) {
					Menu menu= iterator.next();
					System.out.println("\t"+menu.getName()+"\t"+orderMap.get(menu)+"��\t\t"+menu.getPrice()*orderMap.get(menu)+"��");
				}
				System.out.println("\t==============================");
				System.out.println("\t�� ���� :\t\t\t"+order.getPriceSum()+"��");
				System.out.println("\t==============================");
				System.out.println();
		}

		
		/**
		 * ������ �ֹ���� �ҷ����� �޼ҵ�
		 * @param id
		 * @return Map<�ֹ���ȣ, Order>
		 * �Ű������� ���� id�� ��ġ�ϴ� ������ ����
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
		 * �Է¹��� ��¥ ������ �������� �޼ҵ�
		 * @param date(String)
		 * @return Map<�ֹ���ȣ, Order>
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
		 * �Է¹��� �Ⱓ ������ �������� �޼ҵ�
		 * @param start, end
		 * @return Map<�ֹ���ȣ, Order>
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
		
		
		/**
		 * �Է¹��� �Ⱓ ������ �������� �޼ҵ�
		 * @param Map<�ֹ���ȣ, Order>, start, end
		 * @return Map<�ֹ���ȣ, Order>
		 */
		public static Map<Long, Order> getPeriodReceipt(Map<Long, Order> mapInput, String start, String end){
			 Set<Long> set = mapInput.keySet();
			 Iterator<Long> iterator = set.iterator();
			 Map<Long, Order> map = new HashMap<>();
			 while(iterator.hasNext()) {
				 Long orderNum = iterator.next();
				 Order order = mapInput.get(orderNum);
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
