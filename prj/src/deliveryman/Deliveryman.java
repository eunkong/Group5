package deliveryman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import master.Order;
import server.ReceiptManager;

public class Deliveryman {
	final static int DELIVERYMAN = 3;
	static boolean deliveryState;
	public static void main(String[] args){
		System.out.println("<<��޸�>>");
		File ipFile = new File("files", "ip.txt"); //ip����
		try(BufferedReader inFile = new BufferedReader(new FileReader(ipFile)); //ip���� �ҷ����� ��� ����
				Socket socket = new Socket(InetAddress.getByName(inFile.readLine()), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			out.writeInt(DELIVERYMAN); out.flush(); //��޸��̶�� ������ ������ �ѱ�
			
			while(true) {
				deliveryState = false;
				System.out.print("��ް��ɿ��� : (0�̸� ����) / (1�̸� ����)");
				int state = s.nextInt(); //��޸ǿ��� ��� ���ɿ��� �Է¹ޱ�
				if(state==1) break;
				if(state!=0) continue;
				out.writeInt(state); out.flush(); //���� ����
				System.out.println("��� ���� ���� �Ϸ�");
				s.nextLine();
				
				Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
				if(orderlist==null) {
					System.out.println("����� ����� �����ϴ�"); break;
				}
				System.out.println("������");
				Iterator<Long> iterator = orderlist.keySet().iterator();
				while(iterator.hasNext()) {
					Long num = iterator.next();
					Order order = orderlist.get(num);
					ReceiptManager.printReceipt(num, order);
					break;
				}
				
				Thread.sleep(3000);
				deliveryState = true;
				out.writeBoolean(deliveryState); out.flush(); //��޿Ϸ� ���¸� ������ �ѱ�
				System.out.println("��޿Ϸ�!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
