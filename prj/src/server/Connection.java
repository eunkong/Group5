package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import client.Member;
import master.Order;

//����IP���� ������ ���� Ŭ����

public class Connection extends Thread{
	private static Calendar cal;
	private static String time;
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	
	private static List<Connection> list = new ArrayList<>();
	
	/**
	 * �����߰� �޼ҵ�
	 * @param Connection ��ü
	 */
	public static void add(Connection c) {
		list.add(c);
	}
	
	/**
	 * ������� �޼ҵ�
	 * @param Connection ��ü
	 */
	public static void remove(Connection c) {
		list.remove(c);
	}
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean flag = true;
	
	/**
	 * ��� ��� �޼ҵ�
	 */
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		}catch(Exception e) {	}
		remove(this);
	}
	
	/**
	 * Connection ������
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.setDaemon(true);
		this.start();
	}
	
	/**
	 * ��ӹ��� ThreadŬ������ run()�޼ҵ� ������
	 * 
	 */
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
					out.writeBoolean(result); out.flush();
			
				//�α���
				}else if(select == LOGIN) {
					Member member=null;
					String id = in.readUTF();
					System.out.println(id);
					String pw = in.readUTF();
					System.out.println(pw);
					member = MemberManager.login(id, pw);
					if(member==null) {
						out.writeObject(null); out.flush();
						continue;} 	//�α��� ������ ������ ��ü ����
					else {
						out.writeObject(member); out.flush(); 	//�α��� ������ ������ ��ü ����
					}
					
					//�ֹ����� ����.
					Order order = (Order) in.readObject();
					cal = Calendar.getInstance();
					time = (cal.get(Calendar.YEAR)-2000)+"�� "+(1+cal.get(Calendar.MONTH))+"�� "+cal.get(Calendar.DAY_OF_MONTH)+"��  "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
					System.out.println(time);	//test
					order.setOrdertime(time);
					ReceiptStorage.saveDatabase(OrderNumber.getOrderNumber(), order);
				}
			}
		}catch(Exception e) {
			kill();
		}
	}
}