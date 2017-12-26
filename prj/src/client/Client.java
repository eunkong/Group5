package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int port = 20000;
		InetAddress inet = InetAddress.getByName("192.168.0.243");
		Socket socket = new Socket(inet, port);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		//in ���鶧 ObjectInputStream(BufferedInputStream(socket.getInputStream()));
		//�ϸ� ���̻� ������� ����. (��..?)
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		System.out.println("�����غ�");
		Scanner s = new Scanner(System.in);
		boolean registerResult = false;
		
		while(true) {
			System.out.print("1.ȸ������ or 2.�α��� or 3.���� : ");
			int menu = s.nextInt();
			if(menu==3) break; //����
			s.nextLine();
			out.writeInt(menu);
			out.flush();
			// System.out.println("int ����");
			
			System.out.print("ID : ");
			out.writeUTF(s.nextLine()); out.flush(); //id�� server�� �ѱ�
			System.out.print("PWD : ");
			out.writeUTF(s.nextLine()); out.flush(); //pwd�� server�� �ѱ�
			if(menu==1) { //ȸ�������� ���
				System.out.print("��ȭ��ȣ  : ");
				out.writeUTF(s.nextLine()); out.flush(); //phoneNumber�� server�� �ѱ�
				System.out.print("�ּ� : ");
				out.writeUTF(s.nextLine()); out.flush(); //address�� server�� �ѱ�
				registerResult = in.readBoolean();
				if(registerResult==true) {
					System.out.println("ȸ������ ����");
					continue;
				}else {
					System.out.println("ȸ������ ����");
					continue;
				}
			}
			Member my = new Member();
			my = (Member)in.readObject();
			System.out.println("��ü �ޱ� ����");
			my.printInfo();
		}
		s.close();
		in.close();
		out.close();
		System.out.println("����");
		
		//�����ޱ�
	}
}
