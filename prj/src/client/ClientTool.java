package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

import master.Menu;
import master.MenuSFM;
import master.Order;
import server.ReceiptManager;

public class ClientTool {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static final int END = 3;
	public static boolean registerResult=false;
	public Member my;
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInput in;
	public Scanner s;
	final static int CLIENT = 1;
	
	public ClientTool() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		s = new Scanner(System.in);
		out.writeInt(CLIENT); out.flush(); //Ŭ���̾�Ʈ��� ������ ������ �ѱ�
		//in ���鶧 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//�ϸ� ���̻� ������� ����. (��..?)
	}
	
	public void clientHome() throws IOException, ClassNotFoundException {
		while(true) {
			System.out.print("1.ȸ������ or 2.�α��� or 3.���� : ");
			int choice = s.nextInt();
			s.nextLine();
			out.writeInt(choice); out.flush();
			
			switch(choice) {
			case REGISTER: //register();
				continue;
			case LOGIN: 
				continue;
			case END: end(); break; //����
			default: System.out.println("��ȣ ����"); continue;
			}
			break;
			
		}
	}

	private void loginHome() throws IOException, ClassNotFoundException {
		while(true) {
			System.out.println("1.������ or 2.�ֹ��ϱ� or 3.�ֹ����� 4.�α׾ƿ�");
			int choice = s.nextInt();
			s.nextLine();
			switch(choice) {
			case 1: my.printInfo(); continue;
			case 2: 
				out.writeInt(choice); out.flush();
//				order(); continue;
			case 3: 
				out.writeInt(choice); out.flush();
				myorderlist();
				continue;
			case 4: 
				out.writeInt(choice); out.flush();
				return;
			default: System.out.println("��ȣ ����");
			}
		}
	}
	public void logout() throws IOException{
			out.writeInt(4); out.flush(); //������ �α׾ƿ�(4) ������ �ѱ�
		
	}

	public Map<Long, Order> myorderlist() throws ClassNotFoundException, IOException {
		out.writeInt(3); out.flush(); //������ �ֹ�����(3)�� �ѱ�
		Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
		return orderlist;
		/*
		if(orderlist==null) {
			System.out.println("�ֹ� ������ �����ϴ�");
		}else if(orderlist.size()==0){
			System.out.println("0");
		}else {
			ReceiptManager.printReceipt(orderlist);
		}
		*/
	}

	public void order(Map<Menu, Integer> m) throws IOException {
//		MenuSFM.menuLoad(); //�޴��� �б�
//		MenuSFM.menuPrintConsole(); //�޴��� ���
		//�ֹ��ϱ�
		Order myOrder = new Order(my);
		
		out.writeInt(2); out.flush(); //������ �ֹ��ϱ�(2)�� �ѱ�
		myOrder.order(m);
		System.out.println(myOrder.getPriceSum());
		System.out.println(myOrder.getMember());
		for (Menu menu : myOrder.getOrderIdx().keySet()) {
			System.out.println(menu.getName());
			System.out.println(myOrder.getOrderIdx().get(menu));
			System.out.println();
		}
		out.writeBoolean(true); out.flush();
		out.writeObject(myOrder); out.flush(); //�ֹ� ��ü ����
		
		System.out.println("�ֹ� �Ϸ�");
	}

	public Member login(String id, String pwd) throws IOException, ClassNotFoundException {
		out.writeInt(2); out.flush(); //������ �α���(2)�� �ѱ�
		out.writeUTF(id); out.flush(); //id�� server�� �ѱ�
		out.writeUTF(pwd); out.flush(); //pwd�� server�� �ѱ�
		
		my = (Member)in.readObject();
		
		return my;
//		return my!=null;
		
		/*if(my!=null) {
			System.out.println("�α��� ����");
			return true;
		}else {
			System.out.println("�α��� ����");
			return false;
		}*/
	}

	public boolean register(String id, String pwd, String phone, String address) throws IOException, ClassNotFoundException {
		out.writeInt(1); out.flush(); //������ ȸ������(1)�� �ѱ�
		out.writeUTF(id); out.flush(); //id�� server�� �ѱ�
		out.writeUTF(pwd); out.flush(); //pwd�� server�� �ѱ�
		out.writeUTF(phone); out.flush(); //phoneNumber�� server�� �ѱ�
		out.writeUTF(address); out.flush(); //address�� server�� �ѱ�
		return registerResult = in.readBoolean();
	}

	private void end() throws IOException {
		out.close();
		in.close();
		s.close();
		System.exit(0);
	}
}
