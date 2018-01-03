package serverGui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class OrderManageWindow extends JDialog {
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�ֹ�����");
	private JButton bt1 = new JButton("�Ⱓ�� ��ȸ");
	private JButton bt2 = new JButton("���� ��ȸ");
	private JButton bt3 = new JButton("��ü�ֹ�");
	private JButton bt4 = new JButton("���ư���");
	

	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�����̵�", "�� �ּ�","�� ����ó", "�ֹ�����" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderManageWindow(Frame mw, boolean modal) {
		super(mw,modal);							//â ���� ���� �ʿ�
		design();
		event();
		menu();

		setTitle("�ֹ�����");
		setSize(1200, 800);
//		setLocation(100, 40);
		//setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true); // �׻���
		setLocationRelativeTo(mw);
//		setResizable(false);
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
		
		//jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //��
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		
		
		
	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)
		
		ActionListener ac = e->{
			//�ڷΰ���? (=����â�� �ݰ� ����â�� �����ش�)
			dispose();
		};
		bt4.addActionListener(ac);
	}

	private void menu() {
	}
}
