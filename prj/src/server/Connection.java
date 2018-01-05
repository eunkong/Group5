package server;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import client.Member;
import master.Order;

//����IP ���� ���� Ŭ����(Thread���)
public class Connection extends Thread {
	
	private static Date d;
	private static Format f = new SimpleDateFormat("yy�� MM�� dd�� HH:mm:ss");
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
	
	private static List<Connection> list = new ArrayList<>();

	/**
	 * �����߰� �޼ҵ�
	 * @param Connection
	 *            ��ü
	 */
	public static void add(Connection c) {
		list.add(c);
	}

	
	/**
	 * ������� �޼ҵ�
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
			// ���� ���� ���ý� ���ܹ߻�
			kill();
		}
	}

	
	/**
	 * ��޺� ���� ó�� �޼ҵ�
	 * @throws IOException
	 * �丮�Ϸ�� �ֹ��� �ϳ��� �Ҵ�, �Է¿� ���� ��޻��� ��ȭ.
	 */
	public void deliveryAccess() throws IOException {
		while(true) {
			in.readInt();	//��ް��ɿ��� �Է� ����
			System.out.println("[test] �����  ��� �����մϴ�.");
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<>(map);
			Iterator<Long> iterator = tmap.keySet().iterator();		//������ �ֹ����� ó��
			
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = tmap.get(num);
				if(order.getOrderState()==COOKINGCOMPLETE) {	//�丮 �Ϸ�� �ֹ�
					System.out.println("[test] ��� �� ���� �ѱ�");	//test
					Map<Long, Order> map1 = new HashMap();
					map1.put(num, order);
					out.writeObject(map1);	//�籸���� ���� ��޺ο��� ����
					out.flush();
					boolean delivery_start = in.readBoolean(); //��޽��� �Է� ����	
					order.setOrderState(DELIVERING);	//�ֹ����� ����(�����)
					System.out.println(order.getOrderState()+"[test] ��� ���̴�.");	//test
					ReceiptManager.saveDatabase(num, order);
					boolean delivery_complete = in.readBoolean(); //��޿Ϸ� �Է� ����		
					System.out.println(delivery_complete?"[test] ��޿Ϸ�":"��޹̿Ϸ�");
					order.setOrderState(COMPLETE);	//�ֹ����� ����(��޿Ϸ�)
					System.out.println("[test] "+order.getOrderState());  //test
					ReceiptManager.saveDatabase(num, order);
					break;	//�ϳ��ϸ� ����������.
				}
			}
			if(!iterator.hasNext())
				out.writeObject(null);	//�丮�Ϸ��� �ֹ��� ������ null ����
		}
		
	}


	/**
	 * �丮�� ���� ó�� �޼ҵ�
	 * @throws IOException
	 * �ֹ��Ϸ� ���� �ֹ� ����, �丮����(�Ϸ�)���� �Է� �ް� ���� ��ȭ
	 */
	private void chefAccess() throws IOException {
		System.out.println("�丮�� ����");
		Map<Long, Order> map = ReceiptManager.loadDatabase();
		TreeMap<Long, Order> tmap = new TreeMap<>(map);
		Iterator<Long> iterator = tmap.keySet().iterator();		//������ �ֹ����� ó��

		while (iterator.hasNext()) { 
			Map<Long, Order> map1 = new HashMap();
			Long num = iterator.next();
			Order order = tmap.get(num);
			if (order.getOrderState() == ORDERCOMPLETE) { 	//�丮�ؾ��� �ֹ�
				map1.put(num, order);
				
				out.writeObject(map1); //�丮�ؾ��� �ֹ� �ϳ� ����
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
				out.writeObject(null); // �丮�翡�� ������ �ֹ��� ���� �� null ����
				out.flush();
	}
	

	/**
	 * �� ���� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * �α���, ȸ������, �α��� ���� ���� ���� ó��
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
			}
			else {
				out.writeObject(member);
				out.flush(); // �α��� ������ ������ ��ü ����
			}

			//�� ���� ���μ��� ó�� �޼ҵ�
			guestMainSelect(id_login);
		}

	}

	/**
	 * ������â ���μ��� ó�� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * �α��ν� ����Ǵ� ������â�� ���� ó��
	 * �� ���� �޼ҵ忡�� ȣ�� Ȱ��
	 */

	private void guestMainSelect(String id_login) throws IOException, ClassNotFoundException {
		while (true) {
			int memberSelect = in.readInt(); // [1. ������  2.�ֹ��ϱ�, 3.�ֹ�����, 4. �α׾ƿ�] ���� ���� �Է� ����.
			System.out.println("member���� : " + memberSelect);
			
			if(memberSelect==1) {		// 1.������
				
				System.out.println("[test] memberSelect 1�϶� : " + memberSelect);
				
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
			
			if (memberSelect == 2) { 	// 2.�ֹ��ϱ�
				boolean result = in.readBoolean();
				System.out.println("[test] : "+result);
				Order order = (Order) in.readObject();
				System.out.println("[test] : "+order.toString());
				d = new Date();
				time = f.format(d);
				order.setOrdertime(time);
				Long orderNumber = OrderNumber.getOrderNumber();
				ReceiptManager.saveDatabasePrint(orderNumber, order);
			}
			
			if (memberSelect == 3) {	// 3. �ֹ�����
				out.writeObject(ReceiptManager.getPerReceipt(id_login));
				out.flush();
				System.out.println("[test] : �ֹ����� �Ѱ��."); // test
			}
			if (memberSelect == 4) {	// 4. �α׾ƿ�
				break;
			}
		}
		
	}
}