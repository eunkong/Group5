package server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import master.Order;

//�ֹ���ȣ ���� Ŭ����
public class OrderNumber {
	private static Calendar cal = Calendar.getInstance();
	private static String date=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);
	private static String numberStart = "0001";	//�ֹ� ���� ��ȣ, ���� �α⵵������ �ֹ���ȣ �ڸ��� ���氡��
	/**
	 * ���ں� �ֹ���ȣ�� �����ϴ� �޼ҵ�
	 * @return orderNum
	 * ���ڳ� �⵵�� ����Ǹ� numberStart������ ����
	 */
	public static Long getOrderNumber(){
		try {
			Map<Long, Order> map = ReceiptManager.loadDatabase();			//�����ֹ��� ���ٸ�?
			List<Long> list = new ArrayList<>(map.keySet());
			Collections.sort(list);
			Long lastNumber = list.get(list.size()-1);
			
			String lastDate = lastNumber.toString().substring(0, 6);
			if(date.equals(lastDate)) {	//�ֱ� �ֹ���ȣ�� ���� ��¥��
				Long newNumber = lastNumber+1;
				return newNumber;
			}else {	//�ֹ���ȣ�� ������ �ƴϸ�
				String newNumber = date+numberStart;
				return Long.parseLong(newNumber);
			}
		}catch(Exception e) {
			String newNumber = date+numberStart;
			return Long.parseLong(newNumber);
		}
	}
	/**
	 * ����ð� ��ȯ�ϴ� �޼ҵ�
	 * @return
	 */

	
}