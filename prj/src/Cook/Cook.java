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
		System.out.println("<<�丮��>>");
		cookFinish = false;
		cookStart = false;
		/*
		 * try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"),
		 * 20000); ObjectOutputStream out = new
		 * ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new
		 * ObjectInputStream(socket.getInputStream()); Scanner s = new
		 * Scanner(System.in);){ out.writeInt(COOK); out.flush(); //�丮���� ������ ������ �ѱ�
		 */
		while (true) {
			try {
				loadOrderlist();
				System.out.println("�丮���ɿ��� : (0�̸� ����)/(1�̸� ����)");
				cookChoice = s.nextInt();
				if (cookChoice == 0) { // �丮�簡 �丮�� �Ѵٸ�
					cookingStart(); //�丮���۹�ư
					Thread.sleep(3000);
					cookingFinish(); //�丮�Ϸ��ư
					continue;
				} else if (cookChoice == 1) { //��ҹ�ư
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
		out.flush(); // �丮�Ϸ� ���� ������ ������ �ѱ�
		System.out.println("�丮�Ϸ�!");		
	}

	private static void cookingStart() throws IOException {
		cookStart = true;
		out.writeBoolean(cookStart);
		out.flush(); // �丮���� ���� ������ ������ �ѱ�
		System.out.println("�丮����!");
	}

	private static void loadOrderlist() throws ClassNotFoundException, IOException {
		orderlist = (Map<Long, Order>) in.readObject(); // �ֹ��� �ޱ�
		System.out.println(orderlist);
		if (orderlist != null) {
			loadOrderlist();
			System.out.println("������");
			Iterator<Long> iterator = orderlist.keySet().iterator();
			while (iterator.hasNext()) {
				Long num = iterator.next();
				Order order = orderlist.get(num);
				ReceiptManager.printReceipt(num, order);
				break;
			}
		} else {
			System.out.println("�ֹ� ������ �������� �ʽ��ϴ�!");
		}
	}
}
