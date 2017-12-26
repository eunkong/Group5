package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.Member;


public class Connection extends Thread{
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	
	
	//�ܺε����� : Connection ��ü�� �����ϴ� ����� static ���·� ����
	private static List<Connection> list = new ArrayList<>();
	public static void add(Connection c) {
		list.add(c);
	}
	public static void remove(Connection c) {
		list.remove(c);
	}
	
	//���ε�����
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean flag = true;
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		}catch(Exception e) {	}
		remove(this);//���� ������
	}
	
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.setDaemon(true);
		this.start();
	}
	
	public void run() {
		try {
			while(flag) {
				int select = in.readInt();
				System.out.println("���� = "+ select);
				
				if(select == REGISTER) {
					String id = in.readUTF();
					String pw = in.readUTF();
					String phone = in.readUTF();
					String address = in.readUTF();
					System.out.println(id);	//Test
					System.out.println(pw);
					System.out.println(phone);
					System.out.println(address);
					boolean result = MemberManager.register(id, pw, phone, address);
					System.out.println(result);
					out.writeBoolean(result); out.flush();
			
				//�α���
				}else if(select == LOGIN) {
					String id = in.readUTF();
					String pw = in.readUTF();
					Member member = MemberManager.login(id, pw);
					System.out.println(member.getAddress());
					out.writeObject(member); out.flush(); 	//�α��� ������ ������ ��ü ����
					System.out.println("��ü���ۿϷ�");
				}
			}
		}catch(Exception e) {
			kill();
		}
	}
}