package cook;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import master.Menu;
import master.Order;

class Window01 extends JFrame {
	private static Socket socket;
	private File ipFile = new File("files", "ip.txt"); //ip����
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	{
		try {
			BufferedReader inFile = new BufferedReader(new FileReader(ipFile)); //ip���� �ҷ����� ��� ����
			Socket socket = new Socket(InetAddress.getByName(inFile.readLine()), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	final static int COOK = 2;
	public static String[] state = { "", "�ֹ��Ϸ�", "�丮��", "�丮�Ϸ�", "�����", "��޿Ϸ�" };
	private static Map<Long, Order> orderlist;
	private static boolean cookFinish, cookStart;

	private JPanel bg = new JPanel();
	private JPanel menuPanel = new JPanel();

	private JLabel lbCook = new JLabel("<�丮��>");
	private JLabel lb[] = new JLabel[5];
	private String lbString[] = new String[] { "�ֹ� ����", "�ֹ� ��ȣ", "�ֹ� �ð�", "�� ���̵�", "�ֹ� �޴�" };
	private JLabel lbOrderState = new JLabel();
	private JLabel lbOrderNum = new JLabel();
	private JLabel lbOrderTime = new JLabel();
	private JLabel lbId = new JLabel();

	private JButton btCookStart = new JButton("�丮 �غ�");
	private JButton btCookFinish = new JButton("�丮 �Ϸ�");
	private JButton btBack = new JButton("���ư���");

	private String columnNames[] = { "�޴���", "����", "����(��)" };

	private DefaultTableModel m;
	// DefaultTableModel -> JTable -> JScrollPane
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
		design();
		event();
		menu();

		setTitle("");
		setSize(660, 800);
		setLocation(450, 50);
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		try {
			out.writeInt(COOK);
			out.flush(); // ������ �丮��(2) ����
			cookFinish = false;
			cookStart = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(bg);// ��� ��ġ
		bg.setLayout(null);

		menuPanel.setBounds(150, 350, 450, 280);
		bg.add(menuPanel);

		lbCook.setFont(new Font("����", Font.PLAIN, 40));
		lbCook.setBounds(250, 10, 205, 84);
		bg.add(lbCook);

		for (int i = 0; i < lbString.length; i++) {
			lb[i] = new JLabel(lbString[i]);
			lb[i].setText(lbString[i]);
			lb[i].setBounds(50, 100 + i * 60, 80, 30);
			bg.add(lb[i]);
		}

		lbOrderState.setBounds(210, 100, 150, 30);
		bg.add(lbOrderState);

		lbOrderNum.setBounds(210, 160, 150, 30);
		bg.add(lbOrderNum);

		lbOrderTime.setBounds(210, 220, 150, 30);
		bg.add(lbOrderTime);

		lbId.setBounds(210, 280, 150, 30);
		bg.add(lbId);

		btCookStart.setBounds(70, 670, 110, 55);
		bg.add(btCookStart);

		btCookFinish.setBounds(270, 670, 110, 55);
		btCookFinish.setEnabled(false);
		bg.add(btCookFinish);

		btBack.setBounds(470, 670, 110, 55);
		bg.add(btBack);

		menuPanel.add(jScollPane);
		jTable.setRowHeight(20); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(370, 250)); // ������
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		m = (DefaultTableModel) jTable.getModel(); // Ʋ �����

		btCookStart.addActionListener(e -> {
			btCookStart.setEnabled(false);
			btCookFinish.setEnabled(true);
			btBack.setEnabled(false);

			try {
				orderlist = (Map<Long, Order>) in.readObject(); // �ֹ��� �ޱ�
				if (orderlist != null) {
					Iterator<Long> iterator = orderlist.keySet().iterator();
					Long num = iterator.next();
					Order order = orderlist.get(num);

					lbOrderState.setText(state[order.getOrderState()]); // �ֹ�����
					lbOrderNum.setText(num.toString());// �ֹ���ȣ
					lbOrderTime.setText(order.getOrdertime());// �ֹ��ð�
					lbId.setText(order.getMember().getId());// �����̵�
					for (Iterator<Menu> iterator2 = order.getOrderIdx().keySet().iterator(); iterator2.hasNext();) {
						Menu menu = iterator2.next();
						int numb = order.getOrderIdx().get(menu);
						m.insertRow(m.getRowCount(), new Object[] { menu.getName(), numb, menu.getPrice() * numb });// �޴�
					}
				} else {
					JOptionPane.showMessageDialog(null, "�丮�Ұ� ������", "", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			cookStart = true;
			cookFinish = false;
			try {
				out.writeBoolean(cookStart);
				out.flush(); // �丮���� ���� ������ ������ �ѱ�
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("�丮����!");
		});

		btCookFinish.addActionListener(e -> {
			btCookFinish.setEnabled(false);
			btCookStart.setEnabled(true);
			btBack.setEnabled(true);
			cookStart = false;
			cookFinish = true;
			try {
				out.writeBoolean(cookFinish);
				out.flush(); // �丮�Ϸ� ���� ������ ������ �ѱ�
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (jTable.getRowCount() != 0) {
				m.removeRow(0);
			}
			jTable.updateUI();
			lbOrderState.setText(""); // �ֹ�����
			lbOrderNum.setText("");// �ֹ���ȣ
			lbOrderTime.setText("");// �ֹ��ð�
			lbId.setText("");// �����̵�
			JOptionPane.showMessageDialog(null, "�丮�� �Ϸ�Ǿ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
		});
		btBack.addActionListener(e -> {
			System.exit(0);
		});
	}

	private void menu() {
	}
}

public class CookGui {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}
