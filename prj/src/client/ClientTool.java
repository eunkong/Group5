package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import master.Order;

public class ClientTool {
	private static int port = 20000;
	static boolean result = false;
	public static Scanner s;
	public static InetAddress inet;
	public static Socket socket;
	public static ObjectInput in;
	public static ObjectOutputStream out;
	
	public ClientTool() throws IOException {
		inet = InetAddress.getByName("192.168.0.243");
		s = new Scanner(System.in);
		socket = new Socket(inet, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	
	public static int Home() throws IOException{
		s = new Scanner(System.in);
		out = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.print("1.ȸ������ or 2.�α��� or 3.���� : ");
		int choice = s.nextInt();
		if(choice==3) exit();
		out.writeInt(choice); out.flush();
		return choice;
	}
	public static boolean ClientRegisterLogin(int clientChoice) throws IOException{
		//�غ� �غ�
		s = new Scanner(System.in);
		inet = InetAddress.getByName("192.168.0.243");
		socket = new Socket(inet, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		System.out.print("ID : ");
		out.writeUTF(s.nextLine()); out.flush();
		System.out.print("PWD : ");
		out.writeUTF(s.nextLine()); out.flush();
		
		if(clientChoice==1) { //ȸ�������� ���
			System.out.print("��ȭ��ȣ  : ");
			out.writeUTF(s.nextLine()); out.flush(); //phoneNumber�� server�� �ѱ�
			System.out.print("�ּ� : ");
			out.writeUTF(s.nextLine()); out.flush(); //address�� server�� �ѱ�
			result = in.readBoolean();
			if(result==true) {
				System.out.println("ȸ������ ����");
				return result;
			}else {
				System.out.println("ȸ������ ����");
				return result;
			}
		}
		if(result==true) {
			System.out.println("�α��� ����");
			return result;
		}else {
			System.out.println("�α��� ����");
			return result;
		}
	}
	public static void ClientMain() {
		System.out.println("1.�� ���� ���� or 2.�� �ֹ� ����");
	}
	public static int ClientInput() {
		s = new Scanner(System.in);
		return s.nextInt();
	}
	public static Order myOrder() throws ClassNotFoundException, IOException {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		Member my = (Member)in.readObject();
		Order myOrder = new Order(my);
		myOrder.orderMain();
		out.writeObject(myOrder); out.flush();
		System.out.println("�ֹ� �Ϸ�");
		return myOrder;
	}
	public static void exit() throws IOException {
		s.close();
		in.close();
		out.close();
		System.out.println("����");
		System.exit(0);
	}
}
