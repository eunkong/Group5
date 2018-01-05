package serverGui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
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

import master.Order;
import server.ReceiptManager;

//�ֹ�����â ���� Ŭ����
class OrderManageWindow extends JDialog {
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�ֹ�����");
	private JButton bt1 = new JButton("�Ⱓ�� ��ȸ");
	private JButton bt2 = new JButton("���� ��ȸ");
	private JButton bt3 = new JButton("��ü�ֹ�");
	private JButton bt4 = new JButton("���ư���");

	public static String[] state = {"","�ֹ��Ϸ�","�丮��","�丮�Ϸ�","�����","��޿Ϸ�"};

	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�����̵�", "�� �ּ�","�� ����ó", "�ֹ�����" };
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	private DefaultTableModel m;
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderManageWindow(Frame mw, boolean modal) {
		super(mw,modal);							//â ����
		design();
		event();
		menu();

		setTitle("�ֹ�����");
		setSize(1200, 800);
		setLocationRelativeTo(mw);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg); 
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
		
		jTable.setRowHeight(25); 
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); 
		jTable.getTableHeader().setReorderingAllowed(false); 
		jTable.getTableHeader().setResizingAllowed(false); 
		
		
		
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		
		//������
		ActionListener ac = e->{
			dispose();
		};
		bt4.addActionListener(ac);
		
		
		//��ü������ ���
		ActionListener ac1 = e->{
			m = (DefaultTableModel)jTable.getModel();		
			while(m.getRowCount()!=0) {m.removeRow(0);} 	

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //�������� ����
			while(iterator.hasNext()) {
				Long num = iterator.next();	
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
		};
		bt3.addActionListener(ac1);
		
		
		//���� ��ȸ
		ActionListener ac2 = e->{
			m = (DefaultTableModel)jTable.getModel();		
			while(m.getRowCount()!=0) {m.removeRow(0);} 	
			String inputId = JOptionPane.showInputDialog("���̵� �Է����ּ���~");

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPerReceipt(inputId);
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //��������
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
			
		};
		bt2.addActionListener(ac2);
		
		
		
		//�Ⱓ�� ��ȸ
		ActionListener ac3 = e->{
			m = (DefaultTableModel)jTable.getModel();		//Ʋ����� 
			while(m.getRowCount()!=0) {m.removeRow(0);} 	//���̺� �ʱ�ȭ
			String start = JOptionPane.showInputDialog("��ȸ ������ �Ⱓ�� �Է�   ex) 180101");
			String finish = JOptionPane.showInputDialog("��ȸ������ �Ⱓ�� �Է���!  ex) 180101");
			m = (DefaultTableModel)jTable.getModel();		//Ʋ����� 

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPeriodReceipt(start, finish);
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	//����
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //��������
			while(iterator.hasNext()) {
				Long num = iterator.next();	
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
			
		};
		bt1.addActionListener(ac3);
		
	}

	private void menu() {
	}
}
