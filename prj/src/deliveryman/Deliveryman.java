package deliveryman;

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
	public static void main(String[] args){
		System.out.println("<<배달맨>>");
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			while(true) {
				System.out.print("배달가능여부 : (0이면 가능)");
				int state = s.nextInt(); //배달맨에게 배달 가능여부 입력받기
				if(state!=0) continue;
				out.writeInt(state); out.flush(); //상태 전송
				s.nextLine();
				
				Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
				if(orderlist==null) {
					System.out.println("배달할 목록이 없습니다"); break;
				}
				System.out.println("고객정보");
				Iterator<Long> iterator = orderlist.keySet().iterator();
				while(iterator.hasNext()) {
					Long num = iterator.next();
					Order order = orderlist.get(num);
					ReceiptManager.printReceipt(num, order);
					break;
				}
				
				Thread.sleep(3000);
				System.out.println("배달완료!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
