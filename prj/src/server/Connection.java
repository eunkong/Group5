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
import java.util.TreeMap;

import client.Member;
import master.Order;

//����IP���� ������ ���� Ŭ����

public class Connection extends Thread {
	private static Calendar cal;
	private static String time;

	private static final int GUEST = 1;
	private static final int CHEF = 2;
	private static final int DELIVERYMAN = 3;

	public static final int REGISTER = 1;
	public static final int LOGIN = 2;

	public static final int DELIVERY = 0; // ��޸� ����
	public static final int ORDERCOMPLETE = 1;
	public static final int COOKING = 2;
	public static final int COOKINGCOMPLETE = 3;
	public static final int DELIVERING = 4;
	public static final int COMPLETE = 5;
	
	boolean loginFlag = false; 

	private static List<Connection> list = new ArrayList<>();

	/**
	 * �����߰� �޼ҵ�
	 * 
	 * @param Connection
	 *            ��ü
	 */
	public static void add(Connection c) {
		list.add(c);
	}

	/**
	 * ������� �޼ҵ�
	 * 
	 * @param Connection
	 *            ��ü
	 */
	public static void remove(Connection c) {
		list.remove(c);
	}

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean flag = true;

	/**
	 * ��� ��� �޼ҵ� try-catch�� ���ܹ߻��� ���
	 */
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
		}
		remove(this);
	}

	/**
	 * Connection ������
	 * 
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException {
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
			int user = in.readInt(); // Ŭ���̾�Ʈ�� ���� Ȯ��
			if (user == GUEST) {
				System.out.println("[test] ��Z����~"); // test
				guestAccess();
			}
			if (user == CHEF) {
				System.out.println("[test] ������~"); // test
				chefAccess();
			}
			if (user == DELIVERYMAN) {
				System.out.println("[test] ��޾���������~"); // test
				deliveryAccess();
			}

		} catch (Exception e) {
			// ���� ���� ���ý� socket���� ����⿡ ���ܹ߻�
			kill();
		}
	}

	/**
	 * ��޸ǿ��� �ֹ� �Ҵ��ϴ� �޼ҵ�
	 * 
	 * @throws IOException
	 *             �丮�Ϸ�� �ֹ��� �ϳ��� �Ҵ��Ѵ�.
	 */
	public void deliveryAccess() throws IOException {
		while(true) {
			in.readInt();	//��ް��ɿ��� �޴´�.
			
			System.out.println("�����  ��� �����մϴ�.");
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<>(map);
			Iterator<Long> iterator = tmap.keySet().iterator();		//������ �ֹ����� ó��
			
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = tmap.get(num);
				if(order.getOrderState()==COOKINGCOMPLETE) {	//�丮 �Ϸ�ȰŸ� ��ް�����
					//���԰� ����� �ѱ��.
					System.out.println("��� �� ���� �ѱ�");	//test
					Map<Long, Order> map1 = new HashMap();
					map1.put(num, order);
					out.writeObject(map1);	//�籸���� ���� �ѱ��.
					out.flush();
					boolean delivery_start = in.readBoolean(); //��޽��� �Է� ����	
					order.setOrderState(DELIVERING);	//��������� �ٲ۴�.
					System.out.println(order.getOrderState()+"��� ���̴�.");	//test
					ReceiptManager.saveDatabase(num, order);
					boolean delivery_complete = in.readBoolean(); //��޿Ϸ�� �Է� ����		
					System.out.println(delivery_complete?"��޿Ϸ�":"��޹̿Ϸ�");
					order.setOrderState(COMPLETE);	//��޿Ϸ�� �ٲ۴�.
					System.out.println(order.getOrderState());  //test
					ReceiptManager.saveDatabase(num, order);
					break;	//�ϳ��ϸ� ����������.
				}
			}
			if(!iterator.hasNext())
				out.writeObject(null);	//�丮�Ϸ��� �ֹ��� ������ null�� �����Ѵ�.
		}
		
	}


//	/**
//	 * �丮�� ���� �޼ҵ�
//	 * @throws IOException
//	 * 
//	 */
//	private void chefAccess() throws IOException {
//		System.out.println("�丮�� ����");
//		while(true) {//�丮�� �޴��ҷ����� , ��ü�ݺ�
//			Map<Long, Order> map = ReceiptManager.loadDatabase();
//			TreeMap<Long, Order> tm = new TreeMap<Long, Order>(map);		
//			Iterator<Long> iterator = tm.keySet().iterator();			//map���� ���� �ֹ����� ó��
//			
//			while (iterator.hasNext()) { // ��ü�޴��߿�
//				Long num = iterator.next();
//				Order order = map.get(num);
//				// ������� ������ �ֹ��� ���� ó��
//				// ������ �ȿ� ���ο� �����带 �����.
//				
//				if (order.getOrderState() == ORDERCOMPLETE) { // �丮�ؾ��� �ֹ�
//					Thread thread = new Thread() {
//						public void run() {
//							Map<Long, Order> map1 = new HashMap();
//							map1.put(num, order); // �丮�ؾ��� �ֹ� �ѱ�
//							try {
//								out.writeObject(map1);
//								out.flush();
//								
//								if (in.readBoolean()) { // �丮�� ���� ���޹���
//									order.setOrderState(COOKING);
//									ReceiptManager.saveDatabase(num, order);
//									System.out.println("[test]" + order.getOrderState() + "�丮���̿���~");
//								}
//								if (in.readBoolean()) { // �丮�Ϸ� ���� ���޹���
//									order.setOrderState(COOKINGCOMPLETE);
//									System.out.println("[test]" + order.getOrderState() + "�丮���ߴ� ��ް���."); // test
//									ReceiptManager.saveDatabase(num, order);
//								}
//								
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					};
//					thread.start();
//				}
//			}
//			out.writeObject(null); out.flush();	//��ȣ, �丮�ؾ��� �ֹ��� �� ������ ���� null�� ������. 
//			
//		}
//	}
//	


	/**
	 * �丮�� ���� �޼ҵ�
	 * @throws IOException
	 * 
	 */
	private void chefAccess() throws IOException {
		System.out.println("�丮�� ����");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		TreeMap<Long, Order> tmap = new TreeMap<>(map);
		Iterator<Long> iterator = tmap.keySet().iterator();		//������ �ֹ����� ó��

		while (iterator.hasNext()) { // ��ü�޴��߿�
			Map<Long, Order> map1 = new HashMap();
			Long num = iterator.next();
			Order order = tmap.get(num);
			// ������� ������ �ֹ��� ���� ó��
			if (order.getOrderState() == ORDERCOMPLETE) { // �丮�ؾ��� �ֹ�
				map1.put(num, order); // �丮�ؾ��� �ֹ� �ѱ�
				
				out.writeObject(map1);
				out.flush();
				
				if (in.readBoolean()) { // �丮�� ���� ���޹���
					order.setOrderState(COOKING);
					ReceiptManager.saveDatabase(num, order);
					System.out.println("[test]" + order.getOrderState() + "�丮���̿���~");
				}
				if (in.readBoolean()) { // �丮�Ϸ� ���� ���޹���
					order.setOrderState(COOKINGCOMPLETE);
					System.out.println("[test]" + order.getOrderState() + "�丮���ߴ� ��ް���."); // test
					ReceiptManager.saveDatabase(num, order);
				}
				
			}
		}
				out.writeObject(null); // �丮�翡�� ������ �ֹ��� ���� ��
				out.flush();

	}
	
	
	

	/**
	 * �� ���� �޼ҵ�
	 * 
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	private void guestAccess() throws IOException, ClassNotFoundException, InterruptedException {
		while (flag) {
			int select = in.readInt();
			System.out.println("���� = " + select);
			// ȸ������
			if (select == REGISTER) {
				String id = in.readUTF();
				String pw = in.readUTF();
				String phone = in.readUTF();
				String address = in.readUTF();
				boolean result = MemberManager.register(id, pw, phone, address);
				out.writeBoolean(result);
				out.flush();
				continue; // ȸ�������ϸ�
			}

			// �α���
			String id_login = in.readUTF();
			String pw_login = in.readUTF();
			Member member = MemberManager.login(id_login, pw_login);
			if (member == null) {
				out.writeObject(null);
				out.flush();
				continue;
			} // �α��� ������ ������ ��ü ����
			else {
				loginFlag= true;
				out.writeObject(member);
				out.flush(); // �α��� ������ ������ ��ü ����
			}

			// �ֹ����ް� �ֹ����� ����
			while (true) {
				int memberSelect = in.readInt(); // ������ 1. ������  2.�ֹ��ϱ�, 3.�ֹ�����, 4. �α׾ƿ� ���� (1.�� ���)
				System.out.println("member���� : " + memberSelect);
				
				if(memberSelect==1) {		// 1. ������ �����ϱ� ���ý�
					//�������� �������� �˾ƾ��Ѵ�.
					System.out.println("memberSelect 1�϶� : " + memberSelect);
					
					//�ش� ���̵��� �ֽ� ������ ����
					Map<String, Member> map = MemberManager.loadDatabase();
					Member memberNew = map.get(id_login);
					System.out.println("�ּҺ����Ѱ� ������. : "+memberNew.getAddress());
					out.writeObject(memberNew); out.flush();
					
					
					boolean editBoolean = in.readBoolean();
					System.out.println("[test] �����Ұž�? "+editBoolean);
					
					if(!editBoolean) {		//���� ���� ���
						continue;
					}
					if(editBoolean) {		//������ ���
						System.out.println("[test] ���� �����ҰԿ�~");
						Member editMember = (Member) in.readObject();
						System.out.println("[test] ������ ���̵� �޾ҽ��ϴ� : "+ editMember.getId());
						System.out.println("[test] ������ ��ȣ �޾Ҿ�� :"+editMember.getPhoneNumber());
						System.out.println("[test] ������ �ּҵ� �޾Ҵ�~ : "+editMember.getAddress());
						Member editComplete = MemberManager.editInfo(editMember);
						out.writeObject(editComplete);
					}
				}
				
				if (memberSelect == 2) { // 2.�ֹ��ϱ� ���ý�
					// �ֹ����� ����.
					boolean result = in.readBoolean();
					System.out.println(result);
					Order order = (Order) in.readObject();
					System.out.println(order.toString());
					cal = Calendar.getInstance();
					time = (cal.get(Calendar.YEAR) - 2000) + "�� " + (1 + cal.get(Calendar.MONTH)) + "�� "
							+ cal.get(Calendar.DAY_OF_MONTH) + "��  " + cal.get(Calendar.HOUR_OF_DAY) + ":"
							+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
					order.setOrdertime(time);
					Long orderNumber = OrderNumber.getOrderNumber();
					ReceiptManager.saveDatabasePrint(orderNumber, order);

				}
				if (memberSelect == 3) {
					out.writeObject(ReceiptManager.getPerReceipt(id_login));// ȸ���� �ֹ�����
					out.flush();
					System.out.println("test : �ֹ����� �Ѱ��."); // test
				}
				if (memberSelect == 4) {
					loginFlag = false;
					break;
				}
			}

		}

	}
}