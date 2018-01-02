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
	
	ClientTool() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
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
			case REGISTER: register();
				continue;
			case LOGIN: 
				if(login()) {
					loginHome();
					continue;
				}else {
					continue;
				}
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
				order(); continue;
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

	private void myorderlist() throws ClassNotFoundException, IOException {
		Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
		if(orderlist.size()==0) {
			System.out.println("�ֹ� ������ �����ϴ�");
		}else {
			ReceiptManager.printReceipt(orderlist);
		}
	}

	private void order() throws IOException {
		MenuSFM.menuLoad(); //�޴��� �б�
		MenuSFM.menuPrintConsole(); //�޴��� ���
		//�ֹ��ϱ�
		Order myOrder = new Order(my);
		myOrder.orderMain();
		
		out.writeObject(myOrder); out.flush(); //�ֹ� ��ü ����
		System.out.println("�ֹ� �Ϸ�");
	}

	private boolean login() throws IOException, ClassNotFoundException {
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
		
		my = (Member)in.readObject();
		
		if(my!=null) {
			System.out.println("�α��� ����");
//			loginHome();
//			MenuSFM.menuLoad(); //�޴��� �б�
//			MenuSFM.menuPrintConsole(); //�޴��� ���
//			//�ֹ��ϱ�
//			Order myOrder = new Order(my);
//			myOrder.orderMain();
//			out.writeObject(myOrder); out.flush(); //�ֹ� ��ü ����
//			System.out.println("�ֹ� �Ϸ�");
			return true;
		}else {
			System.out.println("�α��� ����");
			return false;
		}
	}

	private void register() throws IOException, ClassNotFoundException {
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
		System.out.print("��ȭ��ȣ  : ");
		out.writeUTF(s.nextLine()); out.flush(); //phoneNumber�� server�� �ѱ�
		System.out.print("�ּ� : ");
		out.writeUTF(s.nextLine()); out.flush(); //address�� server�� �ѱ�
		registerResult = in.readBoolean();
		if(registerResult==true) {
			System.out.println("ȸ������ ����");
		}else {
			System.out.println("ȸ������ ����");
		}
	}

	private void end() throws IOException {
		out.close();
		in.close();
		s.close();
		System.exit(0);
	}
}
