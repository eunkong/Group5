package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.Member;
import master.Menu;
import master.Order;

public class Connection extends Thread{
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	
	private static List<Connection> list = new ArrayList<>();
	public static void add(Connection c) {
		list.add(c);
	}
	public static void remove(Connection c) {
		list.remove(c);
	}
	
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
		remove(this);
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
					
					//�ֹ����� ����.
					Order order = (Order) in.readObject();
					System.out.println(order.getPriceSum()+" �޾Ҵ�!!!");
					Map<Menu, Integer> orderMap = order.getOrderIdx();
					System.out.println(orderMap.isEmpty()+"�����޾Ҵ�!!");

					
					
					ReceiptStorage.saveDatabase(OrderNumber.getOrderNumber(), order);
					
					
				}
			}
		}catch(Exception e) {
			kill();
		}
	}
}