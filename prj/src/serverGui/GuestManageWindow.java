package serverGui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.Member;
import master.MenuSFM;
import master.Order;
import server.MemberManager;

class GuestManageWindow extends JDialog {
	private JPanel bg = new JPanel();

	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�� ����");
	private JButton bt1 = new JButton("�߰�");
	private JButton bt2 = new JButton("����");
	private JButton bt3 = new JButton("����");
	private JButton bt4 = new JButton("������");

	private String columnNames[] = { "���̵�", "��й�ȣ", "��ȭ��ȣ", "�� �ּ�", "���", "�ֹ� ��", "����Ʈ" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private DefaultTableModel m;
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public GuestManageWindow(Frame mw, boolean modal) {
		super(mw, modal);
		design();
		event();
		menu();

		setTitle("������");
		setSize(1200, 800);
		setLocationRelativeTo(mw);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg); // bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);

		jpanel.setBounds(47, 166, 1092, 569);
		bg.add(jpanel);

		jlabel.setFont(new Font("����", Font.PLAIN, 70));
		jlabel.setBounds(72, 60, 385, 94);
		bg.add(jlabel);

		bt1.setBounds(551, 60, 133, 70);
		bg.add(bt1);

		bt2.setBounds(696, 60, 133, 70);
		bg.add(bt2);

		bt3.setBounds(841, 60, 133, 70);
		bg.add(bt3);

		bt4.setBounds(986, 60, 133, 70);
		bg.add(bt4);

		jpanel.add(jScollPane);

		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //��
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		// â�� �ѷ��ִ� ��
		m = (DefaultTableModel)jTable.getModel();		//Ʋ����� 

		System.out.println("m.getRowCount() : " + m.getRowCount());

		MemberManager member = new MemberManager();
		Map<String, Member> map = member.loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String id = iterator.next(); // 1���θ� �Ѵ�.
			Member man = map.get(id);
			m.insertRow(m.getRowCount(), new Object[] { man.getId(), man.getPwd(), man.getPhoneNumber(),
					man.getAddress(), man.getGrade(), man.getOrderCount(), man.getPoint() });
		}
		m.insertRow(m.getRowCount(), new Object[] { "", "", "", "", "", "", "" });
		jTable.updateUI();

	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)

		ActionListener ac = e -> {
			// �ڷΰ���? (=����â�� �ݰ� ����â�� �����ش�)
			
			//�� ���� �ʱ�ȭ
			// â�� �ѷ��ִ� ��
			m = (DefaultTableModel)jTable.getModel();		//Ʋ����� 
			while(m.getRowCount()!=0) {m.removeRow(0);} 	//���̺� �ʱ�ȭ
			System.out.println("m.getRowCount() : " + m.getRowCount());
			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			TreeMap<String,Member> tmap = new TreeMap<String, Member>(map);	//����
			Iterator<String> iterator = tmap.keySet().iterator();   //��������
			while (iterator.hasNext()) {
				String id = iterator.next(); // 1���θ� �Ѵ�.
				Member man = map.get(id);
				m.insertRow(m.getRowCount(), new Object[] { man.getId(), man.getPwd(), man.getPhoneNumber(),
						man.getAddress(), man.getGrade(), man.getOrderCount(), man.getPoint() });
			}
			m.insertRow(m.getRowCount(), new Object[] { "", "", "", "", "", "", "" });
			jTable.updateUI();
			
			//�������
			dispose();
		};
		bt4.addActionListener(ac);

		// ȸ������
		ActionListener act2 = e -> {
			int row = jTable.getSelectedRow();
			System.out.println("row : " + row);
			int col = 0;
			String value = (String) jTable.getValueAt(row, col);
			System.out.println("value : " + value);

			m.removeRow(row); // ���ÿ� ����

			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("��ġ�ϴ� ���̵� �߰�");
					map.remove(id);
					break;
				}
			}

			member.saveDatabase(map);
		};
		bt3.addActionListener(act2);

		
		// ȸ���߰�
		ActionListener act3 = e -> {
			int lastRow = m.getRowCount() - 1; // ������ ��
			int lastCol = m.getColumnCount(); // ������ ��

			// ���������� ó������ ������ ���� �����´�. �׸��� �� ������ ȸ�������Ѵ�.
			String[] array = new String[7];
			for (int i = 0; i < 7; i++) {
				String a = (String) m.getValueAt(lastRow, i);
				array[i] = a;
				System.out.println(a);
			}
			
			//�ֹ���, ���ϸ��� ���� �̿� ���ý� ó��
			int array5, array6;
			try {
				array5 = Integer.parseInt(array[5]);
			}catch(Exception e1) {
				array5=0;
			}
			
			try {
				array6 = Integer.parseInt(array[6]);
			}catch(Exception e1) {
				array6=0;
			}
			
			MemberManager.register(array[0], array[1], array[2], array[3], array[4], array5, array6);

			m.insertRow(m.getRowCount(), new Object[] { "", "", "", "", "", "", "" });
			jTable.updateUI();
		};
		bt1.addActionListener(act3);
		
		

		// ȸ������
		ActionListener act4 = e -> {
			int row = jTable.getSelectedRow();
			System.out.println("row : " + row);
			int col = 0;
			String value = (String) jTable.getValueAt(row, col);
			System.out.println("value : " + value);

			//���� ���� ���� �ҷ��� �迭�� �����Ѵ�.
			String[] array = new String[7];
			for (int i = 0; i < 4; i++) {
				String a = (String) m.getValueAt(row, i);
				array[i] = a;
				System.out.println(a);
			}
			
			//������ �� ���� ����
			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("��ġ�ϴ� ���̵� �߰�");
					//�ش� �ʰ��� �����Ѵ�.
					map.put(id, new Member(id, array[1], array[2], array[3]));
					break;
				}
			}
			member.saveDatabase(map);
		};
		bt2.addActionListener(act4);

	}

	private void menu() {
	}
}
