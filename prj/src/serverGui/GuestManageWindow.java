package serverGui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Member;
import server.MemberManager;

// ������â ���� Ŭ����
class GuestManageWindow extends JDialog {
	private JPanel bg = new JPanel();

	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�� ����");
	private JButton bt1 = new JButton("�߰�");
	private JButton bt2 = new JButton("����");
	private JButton bt3 = new JButton("����");
	private JButton bt4 = new JButton("������");
	
	private JLabel jlb1 = new JLabel("���̵�");
	private JLabel jlb2 = new JLabel("��й�ȣ");
	private JLabel jlb3 = new JLabel("��ȭ��ȣ");
	private JLabel jlb4 = new JLabel("�� �ּ�");
	
	private JTextField jtf1 = new JTextField();
	private JTextField jtf2 = new JTextField();
	private JTextField jtf3 = new JTextField();
	private JTextField jtf4 = new JTextField();
	

	

	private String columnNames[] = { "���̵�", "��й�ȣ", "��ȭ��ȣ", "�� �ּ�", "���", "�ֹ� ��", "����Ʈ" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private DefaultTableModel m;
	private JTable jTable = new JTable(defaultTableModel);
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
		setContentPane(bg); 
		bg.setLayout(null);

		jpanel.setBounds(47, 166, 1092, 500);
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

		jTable.setRowHeight(25); 
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 470)); 
		jTable.getTableHeader().setReorderingAllowed(false); 
		jTable.getTableHeader().setResizingAllowed(false); 
		
		jlb1.setBounds(50, 696, 60, 36);
		bg.add(jlb1);
		jlb2.setBounds(289, 696, 60, 36);
		bg.add(jlb2);
		jlb3.setBounds(524, 696, 60, 36);
		bg.add(jlb3);
		jlb4.setBounds(802, 696, 60, 36);
		bg.add(jlb4);
		
		jtf1.setBounds(110, 696, 125, 36);
		bg.add(jtf1);
		jtf2.setBounds(360, 696, 125, 36);
		bg.add(jtf2);
		jtf3.setBounds(590, 696, 164, 36);
		bg.add(jtf3);
		jtf4.setBounds(860, 696, 164, 36);
		bg.add(jtf4);
		
		

		//������â Ŭ���� �ٷ� ���
		m = (DefaultTableModel) jTable.getModel();			//Jtable ���� Ʋ ����
		System.out.println("[Test] m.getRowCount() : " + m.getRowCount());
		MemberManager member = new MemberManager();
		Map<String, Member> map = member.loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String id = iterator.next();
			Member man = map.get(id);
			m.insertRow(m.getRowCount(), new Object[] { man.getId(), man.getPwd(), man.getPhoneNumber(),
					man.getAddress(), man.getGrade(), man.getOrderCount(), man.getPoint() });
		}
		jTable.updateUI();

	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

		//������ ��ư(���̺� ���� �ֽ�ȭ)
		ActionListener ac = e -> {
			m = (DefaultTableModel) jTable.getModel(); 
			System.out.println("[Test] m.getRowCount() : " + m.getRowCount());
			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next(); // 1���θ� �Ѵ�.
				Member man = map.get(id);
				m.insertRow(m.getRowCount(), new Object[] { man.getId(), man.getPwd(), man.getPhoneNumber(),
						man.getAddress(), man.getGrade(), man.getOrderCount(), man.getPoint() });
			}
			jTable.updateUI();
			dispose();
		};
		bt4.addActionListener(ac);

		
		// ȸ������
		ActionListener act2 = e -> {
			int row = jTable.getSelectedRow();
			System.out.println("[test] row : " + row);
			int col = 0;
			String value = (String) jTable.getValueAt(row, col);
			System.out.println("[test] value : " + value);

			m.removeRow(row); // ���ÿ� ����

			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("[test] ��ġ�ϴ� ���̵� �߰�");
					map.remove(id);
					break;
				}
			}
			member.saveDatabase(map);
		};
		bt3.addActionListener(act2);

		
		// ȸ���߰�
		ActionListener act3 = e -> {
			String addName = jtf1.getText();	//�̸�
			String addPassword = jtf2.getText();	//��й�ȣ
			String addPhone = jtf3.getText();	//��ȭ��ȣ
			String addAddress = jtf4.getText(); //�ּ�
			
			boolean check = MemberManager.register(addName, addPassword, addPhone, addAddress);
			if(check) {	//ȸ����� �� ���� �� �߰�
				m.insertRow(m.getRowCount(), new Object[] {addName,addPassword, addPhone, addAddress, "�Ϲ�", "0", "0" });
				jTable.updateUI();
			}
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
			for (int i = 0; i < 7; i++) {
				String a = (String) m.getValueAt(row, i);
				array[i] = a;
				System.out.println(a);
			}
			
			//�ֹ���, ���ϸ��� ���� �ƴ� ������ ������ ó��
			int array5, array6;
			try {
				array5 = Integer.parseInt(array[5]);
			}catch(Exception e1) {
				e1.printStackTrace();
				array5 = 0;
			}
			
			try {
				array6 = Integer.parseInt(array[6]);
			}catch(Exception e1) {
				e1.printStackTrace();
				array6 = 0;
			}
				
			
			//������ �� ���� ����
			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("[test] ��ġ�ϴ� ���̵� �߰�");
					//�ش� �ʰ��� �����Ѵ�.
					map.put(id, new Member(id, array[1], array[2], array[3], array[4], array5, array6));
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
