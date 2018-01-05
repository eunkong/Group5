package deliveryman;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import master.Order;
import server.ReceiptManager;

class DeliverymanGUI extends JDialog {
	
	private static Socket socket ;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	{
		try {
			socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	final static int DELIVERYMAN = 3;
	static boolean deliveryState;
	
	private JPanel bg = new JPanel();

	public static String[] state = { "", "�ֹ��Ϸ�", "�丮��", "�丮�Ϸ�", "�����", "��޿Ϸ�" };

	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�����̵�", "�� �ּ�", "�� ����ó", "��� ����" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	/*
	 * private JLabel jb1 = new JLabel(columnNames[0]); private JLabel jb2 = new
	 * JLabel(columnNames[1]); private JLabel jb3 = new JLabel(columnNames[2]);
	 * private JLabel jb4 = new JLabel(columnNames[3]); private JLabel jb5 = new
	 * JLabel(columnNames[4]); private JLabel jb6 = new JLabel(columnNames[5]);
	 */
	private JLabel[] jb = new JLabel[columnNames.length];
	private JLabel[] show = new JLabel[columnNames.length];
	private JButton btStart = new JButton("��޽���");
	private JButton btFinish = new JButton("��޿Ϸ�");
	private JButton btBack = new JButton("����");

	public DeliverymanGUI(Frame mw, boolean modal) {
		super(mw, modal); // â ���� ���� �ʿ�
		design();
		event();
		menu();
		
		setTitle("�ֹ�����");
		setSize(600, 800);
		// setLocation(100, 40);
		// setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true); // �׻���
		setLocationRelativeTo(mw);
		// setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		try {
			out.writeInt(DELIVERYMAN); out.flush();  //��޸��̶�� ������ ������ �ѱ�
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(bg); // bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);
		for (int i = 0; i < columnNames.length; i++) {
			jb[i] = new JLabel(columnNames[i]);
			jb[i].setBounds(50, 100 + i * 80, 150, 20);
			jb[i].setFont(new Font("", Font.BOLD, 15));
			bg.add(jb[i]);

			show[i] = new JLabel("");
			show[i].setBounds(200, 100 + i * 80, 100, 20);
			show[i].setFont(new Font("", Font.PLAIN, 14));
			bg.add(show[i]);
		}
		btStart.setBounds(100, 600, 90, 50);
		btFinish.setBounds(255, 600, 90, 50);
		btBack.setBounds(410, 600, 90, 50);

		bg.add(btStart);
		bg.add(btFinish);
		btFinish.setEnabled(false);
		bg.add(btBack);

		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		basicPrint();

	}

	private void event() {
		btStart.addActionListener(e->{
			btStart.setEnabled(false);
			btFinish.setEnabled(true);
			try {
				out.writeInt(0); out.flush(); //������ ��ް�������(0) ����
				JOptionPane.showMessageDialog(null,"����غ�Ϸ�!", "", JOptionPane.INFORMATION_MESSAGE);
				Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
				if(orderlist==null) {
					JOptionPane.showMessageDialog(null, "����Ұ� ������", "", JOptionPane.INFORMATION_MESSAGE); 
					System.exit(0);
				}
				Iterator<Long> iterator = orderlist.keySet().iterator();
				Long num = iterator.next();
				Order order = orderlist.get(num);
				show[0].setText(num.toString()); //�ֹ���ȣ
				show[1].setText(order.getOrdertime()); //�ֹ��ð�
				show[2].setText(order.getMember().getId()); //�����̵�
				show[3].setText(order.getMember().getAddress());//���ּ�
				show[4].setText(order.getMember().getPhoneNumber());//������ó
				show[5].setText(state[order.getOrderState()]);//�ֹ�����
				out.writeBoolean(true); out.flush(); //�����(true) ����
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		});
		btFinish.addActionListener(e->{
			btFinish.setEnabled(false);
			btStart.setEnabled(true);
			try {
				deliveryState = true;
				out.writeBoolean(deliveryState); out.flush(); //��޿Ϸ� ���¸� ������ �ѱ�
				JOptionPane.showMessageDialog(null,"��޿Ϸ�!", "", JOptionPane.INFORMATION_MESSAGE);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		});
		
		btBack.addActionListener(e->{
			System.exit(0);
		});
		
	}

	private void menu() {
	}

	private void basicPrint() {

	}
	
	public static void main(String[] args) {
		JDialog jg = new DeliverymanGUI(new Frame(), true);
		jg.setVisible(true);
	}
}
