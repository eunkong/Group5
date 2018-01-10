package gui;
 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.ClientTool;
import client.Member;
import master.Order;
import server.ReceiptManager;

public class OrderInfo extends JDialog {
	private JPanel bg = new JPanel();

	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�� �ֹ�");
	private JButton bt1 = new JButton("�Ⱓ�� ��ȸ");
	private JButton bt2 = new JButton("��ü�ֹ�");
	private JButton bt3 = new JButton("���ư���");

	private Map<Long, Order> orderIdx;

	public static String[] state = { "", "�ֹ��Ϸ�", "�丮��", "�丮�Ϸ�", "�����", "��޿Ϸ�" };

	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�� �ּ�", "�� ����ó", "�ֹ� ����", "�ݾ�" };
	
	// DefaultTableModel -> JTable -> JScrollPane
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public OrderInfo(Frame owner, boolean modal) {
		super(owner, modal);
		design();
		event();
		menu();

		setTitle("");
		setSize(1200, 800);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg); //��� ��ġ
		bg.setLayout(null);

		jpanel.setBounds(47, 166, 1092, 569);
		bg.add(jpanel);

		jlabel.setFont(new Font("����", Font.PLAIN, 70));
		jlabel.setBounds(72, 60, 385, 94);
		bg.add(jlabel);

		bt1.setBounds(696, 60, 133, 70);
		bg.add(bt1);

		bt2.setBounds(841, 60, 133, 70);
		bg.add(bt2);

		bt3.setBounds(986, 60, 133, 70);
		bg.add(bt3);

		jpanel.add(jScollPane);
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // ������
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		allPrint();

	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		bt1.addActionListener(e -> {

			// ^[01][0-9][01][0-9][123][0-9]$ ���Խ� ���� ����
			String start = JOptionPane.showInputDialog("��ȸ ������ �Ⱓ�� �Է�   ex) 180101");
			if (start.equals("") || start == null)
				return;

			String finish = JOptionPane.showInputDialog("��ȸ������ �Ⱓ�� �Է���!  ex) 180101");
			if (finish.equals("") || finish == null)
				return;

			// DefaultTableModel Ʋ �����
			DefaultTableModel tp = (DefaultTableModel) jTable.getModel();
			while (tp.getRowCount() != 0) {
				tp.removeRow(0);
			}

			// ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPeriodReceipt(orderIdx, start, finish);
			Iterator<Long> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				Long num = iterator.next(); // 1���θ� �Ѵ�.
				Order order = map.get(num);
				tp.insertRow(tp.getRowCount(), new Object[] { num, order.getOrdertime(), order.getMember().getAddress(),
						order.getMember().getPhoneNumber(), state[order.getOrderState()], order.getPriceSum() });
			}
			jTable.updateUI();
		});

		bt2.addActionListener(e -> {
			allPrint();
		});

		bt3.addActionListener(e -> {
			dispose();
		});
	}

	private void allPrint() {
		DefaultTableModel tp = (DefaultTableModel) jTable.getModel();
		while (tp.getRowCount() != 0) {
			tp.removeRow(0);
		}

		try {
			orderIdx = ClientTool.getTool().myorderlist(); // ����

			if (orderIdx == null) {
				JOptionPane.showMessageDialog(null, "�ֹ� ������ �����ϴ�!", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			TreeMap<Long, Order> temp = new TreeMap<>(orderIdx);

			for (Iterator<Long> ite = temp.descendingKeySet().iterator(); ite.hasNext();) {
				long key = ite.next();
				Order orderTemp = orderIdx.get(key);
				Member memtp = orderTemp.getMember();
				tp.addRow(new Object[] { key, orderTemp.getOrdertime(), memtp.getAddress(), memtp.getPhoneNumber(),
						state[orderTemp.getOrderState()], orderTemp.getPriceSum() });
			}

			jTable.updateUI();
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
	}

	private void menu() {
	}
}
