package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import master.MenuSFM;
import master.Order;

public class ClientTool {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static final int END = 3;
	public static boolean registerResult=false;
	public static Member my;
	
	public static void clientHome() {
		//in ���鶧 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//�ϸ� ���̻� ������� ����. (��..?)
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner s = new Scanner(System.in);){
			while(true) {
				my = null;
				System.out.print("1.ȸ������ or 2.�α��� or 3.���� : ");
				int menu = s.nextInt();
//				if(menu==END) break; //����
				s.nextLine();
				out.writeInt(menu); out.flush();
				
				switch(menu) {
				case 1: 
					System.out.print("ID : ");
					out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
					System.out.print("PWD : ");
					out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
					System.out.print("��ȭ��ȣ  : ");
					out.writeUTF(s.nextLine()); out.flush(); //phoneNumber�� server�� �ѱ�
					System.out.print("�ּ� : ");
					out.writeUTF(s.nextLine()); out.flush(); //address�� server�� �ѱ�
					registerResult = in.readBoolean(); //ȸ������ �������θ� server�� ����
					if(registerResult==true) {
						System.out.println("ȸ������ ����");
						break;
					}else {
						System.out.println("ȸ������ ����");
						break;
					}
				case 2:
					System.out.print("ID : ");
					out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
					System.out.print("PWD : ");
					out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
					break;
				case 3:
					break;
				default: System.out.println("�߸� �Է��ϼ̽��ϴ�");
				}
//				System.out.print("ID : ");
//				out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
//				System.out.print("PWD : ");
//				out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
				if(menu==REGISTER) { //ȸ�������� ���
					System.out.print("��ȭ��ȣ  : ");
					out.writeUTF(s.nextLine()); out.flush(); //phoneNumber�� server�� �ѱ�
					System.out.print("�ּ� : ");
					out.writeUTF(s.nextLine()); out.flush(); //address�� server�� �ѱ�
					registerResult = in.readBoolean(); //ȸ������ �������θ� server�� ����
					if(registerResult==true) {
						System.out.println("ȸ������ ����");
						continue;
					}else {
						System.out.println("ȸ������ ����");
						continue;
					}
				}
				
				try {
					my = (Member)in.readObject();
					if(my!=null) {
						System.out.println("�α��� ����");
						MenuSFM.menuLoad(); //�޴��� �б�
						MenuSFM.menuPrintConsole(); //�޴��� ���
						//�ֹ��ϱ�
						Order myOrder = new Order(my);
						myOrder.orderMain();
						out.writeObject(myOrder); out.flush(); //�ֹ� ��ü ����
						System.out.println("�ֹ� �Ϸ�");
					}
				} catch (Exception e) {
					System.out.println("�α��� ����");
					continue;
				}
			}
			
		} catch(Exception e) {
			System.err.println("����!");
			e.printStackTrace();
		}
		
	}
}
