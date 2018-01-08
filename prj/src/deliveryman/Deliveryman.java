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
		System.out.println("<<배달맨>>");
		File ipFile = new File("files", "ip.txt"); //ip파일
		try(BufferedReader inFile = new BufferedReader(new FileReader(ipFile)); //ip파일 불러오기 통로 생성
				Socket socket = new Socket(InetAddress.getByName(inFile.readLine()), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			out.writeInt(DELIVERYMAN); out.flush(); //배달맨이라는 정보를 서버에 넘김
			
			while(true) {
				deliveryState = false;
				System.out.print("배달가능여부 : (0이면 가능) / (1이면 종료)");
				int state = s.nextInt(); //배달맨에게 배달 가능여부 입력받기
				if(state==1) break;
				if(state!=0) continue;
				out.writeInt(state); out.flush(); //상태 전송
				System.out.println("배달 상태 전송 완료");
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
				deliveryState = true;
				out.writeBoolean(deliveryState); out.flush(); //배달완료 상태를 서버에 넘김
				System.out.println("배달완료!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
