package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import client.Member;
import master.Menu;
import master.MenuSFM;

public class Server {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static File target = new File("files", "menus.db");
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		MenuSFM.menuLoad();
		MenuSFM.menuPrintConsole();
		
		//���Ͽ���
		Socket socket = new ServerSocket(20000).accept();
		
		//��ü������ int, boolean ��� �ѱ�
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
		
		//ȸ������, �α���
		while(true) {
			int select = in.readInt();
			System.out.println("���� = "+ select);
			//ȸ������
			if(select == REGISTER) {
//				Member m = (Member) in.readObject();
				String id = in.readUTF();
				String pw = in.readUTF();
				String phone = in.readUTF();
				String address = in.readUTF();
				System.out.println(id);
				System.out.println(pw);
				System.out.println(phone);
				System.out.println(address);
				boolean result = MemberManager.register(id, pw, phone, address);
				out.writeBoolean(result); out.flush();
			//�α���
			}else if(select == LOGIN) {
				String id = in.readUTF();
				String pw = in.readUTF();
				boolean result = MemberManager.login(id, pw);
				System.out.println(result);	//�α��� �������� üũ
				out.writeBoolean(result); out.flush();
				//�α��� �����ϸ�
				if(result) {
					System.out.println("�α��� ����");
				}
			}
			
		}
		
		
		
		
	}
}
