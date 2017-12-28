package server;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import master.Order;

//�ֹ���ȣ ���� Ŭ����
public class OrderNumber {
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);

	/**
	 * �ֹ���ȣ�� �����ϴ� �޼ҵ�
	 * @return orderNum
	 */
	public static Long getOrderNumber(){
		Map<Long, Order> map = ReceiptStorage.loadDatabase();
		List<Long> list = new ArrayList<>(map.keySet());
		System.out.println("Map�� �ֹ���ȣ�� ��� : "+list.toString());
		Collections.sort(list);
		System.out.println("�ֹ���ȣ ��Ʈ : "+list.toString());
		Long lastNumber = list.get(list.size()-1);
		System.out.println("�ֱ��ֹ���ȣ : "+lastNumber); //test
		
		String lastDate = lastNumber.toString().substring(0, 6);
		System.out.println("�ֱٳ�¥ : "+lastDate); //test
		if(date.equals(lastDate)) {	//�ֱ� �ֹ���ȣ�� ���� ��¥��
			Long newNumber = lastNumber+1;
			System.out.println("���ó�¥ ����ȣ : "+newNumber);
			return newNumber;
		}else {	//�ֹ���ȣ�� ������ �ƴϸ�
			System.out.println("��¥ �ٲ���!");
			String newNumber = date+"0001L";
			System.out.println("����ȣ üũ : "+newNumber);
			String newNum = newNumber.substring(0, newNumber.length()-1);	//L����
			System.out.println("����ȣ Long üũ  : "+Long.parseLong(newNum));
			return Long.parseLong(newNum);
		}
	}

	
}