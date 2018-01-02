package Cook;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import master.Order;
import server.ReceiptManager;

public class Cook {
	final static int COOK = 2;
	private static boolean cookFinish, cookStart;
	private static Map<Long, Order> orderlist;
	private static int cookChoice;

	public static void main(String[] args) {
		System.out.println("<<�丮��>>");
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			out.writeInt(COOK); out.flush(); //�丮���� ������ ������ �ѱ�
			cookFinish = false;
			cookStart = false;
			
			while(true) {
				orderlist = (Map<Long, Order>)in.readObject(); //�ֹ��� �ޱ�
				System.out.println(orderlist);
				if(orderlist!=null) {
					System.out.println("������");
					Iterator<Long> iterator = orderlist.keySet().iterator();
					while(iterator.hasNext()) 
					{
						Long num = iterator.next();
						Order order = orderlist.get(num);
						ReceiptManager.printReceipt(num, order);
						break;
					}
					System.out.println("�丮���ɿ��� : (0�̸� ����)/(1�̸� ����)");
					cookChoice = s.nextInt();
					if(cookChoice == 0) { //�丮�簡 �丮�� �Ѵٸ�
						cookStart = true;
						out.writeBoolean(cookStart); out.flush(); //�丮���� ���� ������ ������ �ѱ�
						System.out.println("�丮����!");
						Thread.sleep(3000);
						cookFinish = true;
						out.writeBoolean(cookFinish); out.flush(); //�丮�Ϸ� ���� ������ ������ �ѱ�
						System.out.println("�丮�Ϸ�!");
						continue;
					} else if(cookChoice == 1) {
						break;
					} else {
						continue;
					}
					
				} else {
					System.out.println("�ֹ� ������ �������� �ʽ��ϴ�!");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
