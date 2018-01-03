package Cook;

import java.io.IOException;
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
	private static Socket socket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static Scanner s;

	public Cook() {
		try {
			socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			s = new Scanner(System.in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("<<요리사>>");
		cookFinish = false;
		cookStart = false;
		/*
		 * try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"),
		 * 20000); ObjectOutputStream out = new
		 * ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new
		 * ObjectInputStream(socket.getInputStream()); Scanner s = new
		 * Scanner(System.in);){ out.writeInt(COOK); out.flush(); //요리사라는 정보를 서버에 넘김
		 */
		while (true) {
			try {
				loadOrderlist();
				System.out.println("요리가능여부 : (0이면 가능)/(1이면 종료)");
				cookChoice = s.nextInt();
				if (cookChoice == 0) { // 요리사가 요리를 한다면
					cookingStart(); //요리시작버튼
					Thread.sleep(3000);
					cookingFinish(); //요리완료버튼
					continue;
				} else if (cookChoice == 1) { //취소버튼
					break;
				} else {
					continue;
				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void cookingFinish() throws IOException {
		cookFinish = true;
		out.writeBoolean(cookFinish);
		out.flush(); // 요리완료 상태 정보를 서버에 넘김
		System.out.println("요리완료!");		
	}

	private static void cookingStart() throws IOException {
		cookStart = true;
		out.writeBoolean(cookStart);
		out.flush(); // 요리시작 상태 정보를 서버에 넘김
		System.out.println("요리시작!");
	}

	private static void loadOrderlist() throws ClassNotFoundException, IOException {
		orderlist = (Map<Long, Order>) in.readObject(); // 주문서 받기
		System.out.println(orderlist);
		if (orderlist != null) {
			loadOrderlist();
			System.out.println("고객정보");
			Iterator<Long> iterator = orderlist.keySet().iterator();
			while (iterator.hasNext()) {
				Long num = iterator.next();
				Order order = orderlist.get(num);
				ReceiptManager.printReceipt(num, order);
				break;
			}
		} else {
			System.out.println("주문 정보가 존재하지 않습니다!");
		}
	}
}
