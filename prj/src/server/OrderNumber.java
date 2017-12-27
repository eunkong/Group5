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
//�ֹ���ȣ ���� Ŭ����
public class OrderNumber {
	public static File db = new File("files", "orderNumber.db");
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);
	private final static int ORDERMAX = 9999;	//�Ϸ� �ִ� �ֹ� Ƚ��
	
	//�Ϸ� �ٲ�� db.delete()�ؼ� ���� �����ϰ� �ٽø���
	//set�� �����ؼ� �ֹ���ȣ ��� 1~9999���� �Է��ϰ� �ֹ����� �� ���� �ϳ� �� �����´�.
	
	/**
	 * �ֹ���ȣ�� �����ϴ� �޼ҵ�
	 * @return orderNum
	 */
//	public static String getOrderNumber(){
//		//���� �ֹ���¥�� �������Ͽ� ����� ��¥�� �ٸ��ٸ� 
////		if(check) {	//���Ͽ� ����� ��¥������ �����̸�
////			setNumber();
////		}else {	//���Ͽ� ����� ��¥������ ó�� �����ϰų� ������ �ƴϸ�
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
	 * ���� �ֹ���ȣ(String) ���(1~9999)�� ���Ͽ� ���� �޼ҵ� 
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
	 * �ֹ���ȣ �ҷ����� �޼ҵ�
	 * @return Map<��¥,Set<��ȣ>>
	 * ������ ��ȣ�� set���� �����͵� ���Ͽ����� �Ⱦ�������. set�� �ǹ̰� ����.
	 */
	private static Map<String,Set<Integer>> loadDatabase() {
		try(
			ObjectInputStream in = new ObjectInputStream(
														new BufferedInputStream(
															new FileInputStream(db)));
		){
			Map<String,Set<Integer>> map  = (Map<String, Set<Integer>>) in.readObject();
			System.out.println(map.get(date)+" �ֹ���ȣ �ҷ�����");
				return map;
		}catch(Exception e) {
			e.printStackTrace();
			return new HashMap();
		}
	}

	
	//�ӽ� ���˿�
	public static String getOrderNumber() {
		
		return "1712270001";
	}
	
//��¥��ȣ�� ���� ���� �Է��ϰ�, �ҷ��µ� ����
//�����ε����� 1 �ÿ��� 
//���Ͽ� �ٽ� ����.
	
	
	/**
	 * orderNumber.db�� �ֹ���ȣ�� reset�ϴ� �޼ҵ�
	 * Map<��¥,Set<��ȣ>>�� ���� ����
	 */
	

	
	
//���Ϻҷ��ͼ�
//���� ��¥��ȣ�� ���ó�¥�� �ٸ��ٸ� 
//�������(delete) �ʿ��ϳ�?
//�ٽ� ��¥��ȣ ����
//
//
	
	
	
}
