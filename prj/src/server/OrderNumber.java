package server;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import master.Order;

//�ֹ���ȣ ���� Ŭ����
public class OrderNumber {
	private static Date d;
	private static Format f = new SimpleDateFormat("yyMMdd");
	private static String date;
	private static String numberStart = "0001";	//�ֹ� ���� ��ȣ, �ֹ���ȣ �ڸ��� ���氡��
	
	/**
	 * ���ں� �ֹ���ȣ�� �����ϴ� �޼ҵ�
	 * @return orderNum
	 * ���ڳ� �⵵�� ����Ǹ� numberStart������ ����
	 */
	public static Long getOrderNumber(){
			d = new Date();						//�ֹ���ȣ ���� ��, ���� ��¥
			date = f.format(d);
		try {
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			List<Long> list = new ArrayList<>(map.keySet());
			Collections.sort(list);
			Long lastNumber = list.get(list.size()-1);
			
			String lastDate = lastNumber.toString().substring(0, 6);
			if(date.equals(lastDate)) {	//�ֱ� �ֹ���ȣ�� ������
				Long newNumber = lastNumber+1;
				return newNumber;
			}else {						//�ֱ� �ֹ���ȣ�� �����ڰ� �ƴ� ���
				String newNumber = date+numberStart;
				return Long.parseLong(newNumber);
			}
		}catch(Exception e) {
			String newNumber = date+numberStart;
			return Long.parseLong(newNumber);
		}
	}

	
}