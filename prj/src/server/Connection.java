package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.Member;
import master.Order;

//����IP���� ������ ���� Ŭ����

public class Connection extends Thread{
	private static Calendar cal;
	private static String time;
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;

	public static final int DELIVERY=0;	//��޸� ����
	public static final int ORDERCOMPLETE = 1;
	public static final int COOKING = 2;
	public static final int COOKINGCOMPLETE = 3;
	public static final int DELIVERING=4;
	public static final int COMPLETE=5;
	
	
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
	 * try-catch�� ���ܹ߻��� ���
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
	 * ��޸ǿ��� �ֹ� �Ҵ��ϴ� �޼ҵ�
	 * @throws IOException
	 * �丮�Ϸ�� �ֹ��� �ϳ��� �Ҵ��Ѵ�.
	 */
	public void delivery() throws IOException {
		System.out.println("�����  ��� �����մϴ�.");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		Iterator<Long> iterator = map.keySet().iterator();
		
		while(iterator.hasNext()) {
			Long num = iterator.next();
			Order order = map.get(num);
			if(order.getOrderState()==COOKINGCOMPLETE) {	//�丮 �Ϸ�ȰŸ� ��ް�����
				//���԰� ����� �ѱ��.
				System.out.println("��� �� ���� �ѱ�");	//test
				Map<Long, Order> map1 = new HashMap();
				map1.put(num, order);
				out.writeObject(map1);	//�籸���� ���� �ѱ��.
				out.flush();
				order.setOrderState(DELIVERING);	//��������� �ٲ۴�.
				System.out.println(order.getOrderState()+"��� ���̴�.");	//test
				ReceiptManager.saveDatabase(num, order);
				return;	//�ϳ��ϸ� ����������.
			}
		}
		out.writeObject(null);	//�丮�Ϸ��� �ֹ��� ������ null�� �����Ѵ�.
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
				//
				//��޸� ��ް���
				if(select == DELIVERY) {
					delivery();
					continue;	//��
				}
				
				//ȸ������
				if(select == REGISTER) {
					String id = in.readUTF();
					String pw = in.readUTF();
					String phone = in.readUTF();
					String address = in.readUTF();
					boolean result = MemberManager.register(id, pw, phone, address);
					out.writeBoolean(result); out.flush();
					continue;		//ȸ�������ϸ� 
				}	
			
				//�α���
					String id_login = in.readUTF();
					String pw_login = in.readUTF();
					Member member = MemberManager.login(id_login, pw_login);
					if(member==null) {
						out.writeObject(null); out.flush();
						continue;} 	//�α��� ������ ������ ��ü ����
					else {
						out.writeObject(member); out.flush(); 	//�α��� ������ ������ ��ü ����
					}
					
				
				//�ֹ����ް� �ֹ����� ����
					while(true) {
							int memberSelect = in.readInt();	//������ 2.�ֹ��ϱ�, 3.�ֹ�����, 4. �α׾ƿ� ���� (1.�� ���)
							System.out.println("member���� : "+memberSelect);
							if(memberSelect==2) {	//2.�ֹ��ϱ� ���ý�
								//�ֹ����� ����.
								Order order = (Order) in.readObject();
								cal = Calendar.getInstance();
								time = (cal.get(Calendar.YEAR)-2000)+"�� "+(1+cal.get(Calendar.MONTH))+"�� "+cal.get(Calendar.DAY_OF_MONTH)+"��  "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
								order.setOrdertime(time);
								Long orderNumber = OrderNumber.getOrderNumber();
								ReceiptManager.saveDatabasePrint(orderNumber, order);
								
								//�ֹ��� �ް� ���Կ��� ��ư ������ �ֹ����¸� �丮������ ����
								Thread.sleep(3000);	//�ӽ� 5�� �� �丮��
								order.setOrderState(COOKING);
								System.out.println(order.getOrderState()+" �丮��");	//test
								ReceiptManager.saveDatabase(orderNumber, order);
								
								Thread.sleep(5000);	//�ӽ� 5�� �� �丮�Ϸ�
								order.setOrderState(COOKINGCOMPLETE);
								System.out.println(order.getOrderState()+" �丮���ߴ�~");	//test
								ReceiptManager.saveDatabase(orderNumber, order);
								
							}	
							if(memberSelect==3) {
								out.writeObject(ReceiptManager.getPerReceipt(id_login));//ȸ���� �ֹ�����
								out.flush();
								System.out.println("test : �ֹ����� �Ѱ��.");	//test
							}
							if(memberSelect==4)
								break;
					}
					
					
			}
		}catch(Exception e) {
			//���� ���� ���ý� socket���� ����⿡ ���ܹ߻�
			kill();
		}
	}
}