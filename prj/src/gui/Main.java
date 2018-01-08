package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.ClientTool;
import client.Member;
import master.ForguiShow;
import master.Menu;
import master.MenuSFM;

class MainOrderView extends JFrame {

	String cusId = "";

	private JPanel bg = new JPanel();
	private JPanel jpanel1 = new JPanel();
	private JPanel jpanel2 = new JPanel();
	private JPanel jpanel3 = new JPanel();

	static {
		MenuSFM.menuLoad();
	}



	private Map<String, Integer> orders = new HashMap<>();
	{
		for (String s : MenuSFM.getGroupString()) {
			for (String string : MenuSFM.getMenuName(s)) {
				orders.put(string, 0);
			}
		}
	}
	Member member;
	private boolean gopp = false;

	private static final int ROW = MenuSFM.getMenus().size();
	private static final int COL = 4;

	
	private JButton[] groups = new JButton[ROW];
	private JButton[][] menus = new JButton[ROW][COL];

	private JButton orderinfo = new JButton("�ֹ� ����");
	private JButton myinfo = new JButton("�� ����");
	private JButton order = new JButton("�ֹ�");
	
	private JButton reset = new JButton("����");
	

	private JMenuBar mb = new JMenuBar();
	private JMenu basicmenu = new JMenu("Menu");
	private JMenu setting = new JMenu("Setting");
	
	

	private MyInfo myInfoView = new MyInfo(this, true);

	private String columnNames[] = { "�з�", "�޴���", "����", "����(��)" };

	// DefaultTableModel -> JTable -> JScrollPane
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] { { "�� ����", "�� ��", 0, 0 } },
			columnNames);
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public MainOrderView(Member member) {
		this.member = member;
		design();
		event();
		menu();

		setTitle("KG����");
		setBounds(100, 100, 1000, 600);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		bg.setLayout(null);

		jpanel1.setBounds(20, 70, 140, 420);
		bg.add(jpanel1);
		jpanel1.setLayout(new GridLayout(ROW, 1));
		int idx = 0;
		for (String str : MenuSFM.getGroupString()) {
			groups[idx] = new JButton(str);
			jpanel1.add(groups[idx]);
			idx++;
		}

		jpanel2.setBounds(180, 70, 450, 420);
		bg.add(jpanel2);
		jpanel2.setLayout(new GridLayout(ROW, COL));
		for (int i = 0; i < menus.length; i++) {
			for (int j = 0; j < menus[i].length; j++) {
				menus[i][j] = new JButton("");
				jpanel2.add(menus[i][j]);
			}
		}

		jpanel3.setBounds(660, 64, 250, 300);
		bg.add(jpanel3);
		jpanel3.add(jScollPane);
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(230, 270)); // ������
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		myinfo.setBounds(800, 10, 114, 31);
		bg.add(myinfo);

		orderinfo.setBounds(665, 10, 114, 31);
		bg.add(orderinfo);

		order.setBounds(665, 390, 110, 65);
		bg.add(order);

		
		reset.setBounds(795, 390, 110, 65);
		bg.add(reset);
	
	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
		ActionListener act1 = e -> {
			String gp = e.getActionCommand();
			Set<String> menu = MenuSFM.getMenuName(gp);

			for (int i = 0; i < menus.length; i++) {
				for (int j = 0; j < menus[i].length; j++) {
					menus[i][j].setText("");
				}
			}
			int idx = 0;

			for (String string : menu) {
				if (idx == ROW * COL)
					break;
				menus[idx / COL][idx % COL].setText(string);
				idx++;
			}
			
		};

		ActionListener act2 = e -> {

			String order = e.getActionCommand();// ���� Ŭ���ؼ� �ֹ��� �޴� �̸�

			if (order.equals("") || order == null)
				return;
			int temp = orders.get(order);// �ֹ� �߰�/���������� ���� ex) ¥��� 4�� �����ֹ� =>0 , «�� 4������ 3���� ����=>4

			ordering(order);

			int num = orders.get(order);// �ֹ� �߰�/���� ���� ���� ex) ¥��� 4�� �����ֹ� =>4 , «�� 4������ 3���� ����=>3

			String ordergname = "";

			for (Iterator<String> ite = MenuSFM.getGroupString().iterator(); ite.hasNext();) {
				String gp = ite.next();
				if (MenuSFM.getMenuName(gp).contains(order)) {
					ordergname = gp;
				}

			}
			if (ordergname.equals(""))
				return;

			DefaultTableModel m = (DefaultTableModel) jTable.getModel();

			// �𵨿� ������ �߰� , ������ �⿡ ���ο� �����͸� �߰��մϴ�
			if (temp != 0) {// �ű��ֹ��� �ƴϰų� Ŭ������ �ֹ����� ���̰� �ִ� ���
				for (int row = 0; row < m.getRowCount(); row++) {
					if (order.equals(m.getValueAt(row, 1))) {
						m.setValueAt(num, row, 2);// �޴� ���� ����
						m.setValueAt(num * MenuSFM.getMenu(order).getPrice(), row, 3);// �޴� ���� �� �޴� ����
					}

				}
			} else {
				m.insertRow(m.getRowCount() - 1, new Object[] { ordergname, order, num,
						num * (MenuSFM.getMenu(order).getPrice() + (gopp ? 1000 : 0)) });// �ű��ֹ��� ���
			}

			int menuSum = 0;
			int priceSum = 0;

			for (int i = 0; i < m.getRowCount() - 1; i++) {
				priceSum += (int) m.getValueAt(i, 3);
				menuSum += (int) m.getValueAt(i, 2);
				if (m.getValueAt(i, 2).equals(0))
					m.removeRow(i);
			}
			m.setValueAt(menuSum, m.getRowCount() - 1, 2);
			m.setValueAt(new DecimalFormat("#,###,###").format(priceSum), m.getRowCount() - 1, 3);

			jTable.updateUI();
		};
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				menus[i][j].addActionListener(act2);
			}
		}

		
		
		for (int i = 0; i < groups.length; i++) {
			groups[i].addActionListener(act1);
		}

		myinfo.addActionListener(e -> {
			try {
				member = ClientTool.getTool().myinfoOpen();
				myInfoView.setMember(member);
				myInfoView.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		orderinfo.addActionListener(e -> {
			new OrderInfo(this, true);
		});

		reset.addActionListener(e -> {
			resetOrder();
		});
		order.addActionListener(e -> {
			int priceSum = 0;
			for (String name : orders.keySet()) {
				priceSum += MenuSFM.getMenu(name).getPrice() * orders.get(name);
			}
			if (((DefaultTableModel) jTable.getModel()).getRowCount() == 1) {

				JOptionPane.showMessageDialog(null, "���� �޴��� ����ּ���", "", JOptionPane.WARNING_MESSAGE);
				return;
			} else if (priceSum < 10000) {
				JOptionPane.showMessageDialog(null, "�����̻� ��� �����մϴ�", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Map<Menu, Integer> ordertemp = new HashMap<>();

			for (String string : orders.keySet()) {
				if (orders.get(string) == 0)
					continue;
				Menu menu = MenuSFM.getMenu(string);
				ordertemp.put(menu, orders.get(string));
			}

			try {

				ClientTool.getTool().order(ordertemp);
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}

			/**
			 * @code{ ���⿡ ���ο��� �ֹ� ������ ������ �ڵ尡 �� }
			 */

			JOptionPane.showMessageDialog(null, "���������� �ֹ��Ǿ����ϴ�", "", JOptionPane.INFORMATION_MESSAGE);
			resetOrder();
		});

	
		

	}

	private void menu() {

		JMenuItem logoutMenu = new JMenuItem("logout");
		JMenuItem exit = new JMenuItem("exit");

	

		logoutMenu.addActionListener(e -> {
			try {
				ClientTool.getTool().logout();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "�α� �ƿ��� �����Ͽ����ϴ�", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.dispose();
			JOptionPane.showMessageDialog(null, "���������� �α׾ƿ� �Ǿ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
			new Login();
		});

		
		exit.addActionListener(e -> {
			System.exit(0);
		});

		basicmenu.add(logoutMenu);

		if (member.getId().equals("master")) {
			JMenuItem menuChange = new JMenuItem("menu manage");
			menuChange.addActionListener(e -> {
				MenuSFM.menuLoad();// files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
				new ForguiShow();// GUI�� �޴� ��������
			});
			basicmenu.add(menuChange);
		}
		
		basicmenu.add(exit);

		mb.add(basicmenu);
		mb.add(setting);

		setJMenuBar(mb);

	}

	private void ordering(String order) {
		
		int num = 0;
			String s = JOptionPane.showInputDialog("�ֹ�/�����Ͻ�" + order + "�� ������ �Է��ϼ���");
			if (s.equals(null)||s.equals(""))
				return;
			try {
				int n = Integer.parseInt(s);
				if (n < 0)
					throw new Exception();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "�߸��� �Է��Դϴ�", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			num = Integer.parseInt(s);
			orders.put(order, num);

	}

	private void resetOrder() {
		DefaultTableModel m = (DefaultTableModel) jTable.getModel();
		while (m.getRowCount() > 1) {
			m.removeRow(0);
		}
		m.setValueAt(0, 0, 2);
		m.setValueAt(0, 0, 3);
		for (String s : MenuSFM.getGroupString()) {
			for (String string : MenuSFM.getMenuName(s)) {
				orders.put(string, 0);
			}
		}
	}



	public void setCusId(String id) {
		cusId = id;
	}

}

public class Main {
	public static void main(String[] args) {
		new Login();
	}
}
