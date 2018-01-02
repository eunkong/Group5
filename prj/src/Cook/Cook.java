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
		System.out.println("<<요리사>>");
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			out.writeInt(COOK); out.flush(); //요리사라는 정보를 서버에 넘김
			cookFinish = false;
			cookStart = false;
			
			while(true) {
				orderlist = (Map<Long, Order>)in.readObject(); //주문서 받기
				System.out.println(orderlist);
				if(orderlist!=null) {
					System.out.println("고객정보");
					Iterator<Long> iterator = orderlist.keySet().iterator();
					while(iterator.hasNext()) 
					{
						Long num = iterator.next();
						Order order = orderlist.get(num);
						ReceiptManager.printReceipt(num, order);
						break;
					}
					System.out.println("요리가능여부 : (0이면 가능)/(1이면 종료)");
					cookChoice = s.nextInt();
					if(cookChoice == 0) { //요리사가 요리를 한다면
						cookStart = true;
						out.writeBoolean(cookStart); out.flush(); //요리시작 상태 정보를 서버에 넘김
						System.out.println("요리시작!");
						Thread.sleep(3000);
						cookFinish = true;
						out.writeBoolean(cookFinish); out.flush(); //요리완료 상태 정보를 서버에 넘김
						System.out.println("요리완료!");
						continue;
					} else if(cookChoice == 1) {
						break;
					} else {
						continue;
					}
					
				} else {
					System.out.println("주문 정보가 존재하지 않습니다!");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
