package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import client.Member;

public class Server {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static File target = new File("files", "menus.db");
	public static void main(String[] args) throws IOException, ClassNotFoundException {

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
				Member m = (Member) in.readObject();
				boolean result = MemberManager.register(m.getId(), m.getPwd());
				out.writeInt(REGISTER); out.flush();
				out.writeBoolean(result); out.flush();
			//�α���
			}else if(select == LOGIN) {
				Member m = (Member) in.readObject();
				boolean result = MemberManager.login(m.getId(), m.getPwd());
				System.out.println(result);	//�α��� �������� üũ
				out.writeBoolean(result); out.flush();
				
				//�α��� �����ϸ�
				if(result) {
				
				}
			}	
			
			
			
			
			
			
			
			
			
			
			
			
			
		
		}
		
	}
}
