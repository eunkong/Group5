package gui;

import java.awt.Dimension;
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
	
	String cusId="";
	
	private JPanel bg = new JPanel();
	private JPanel jpanel1 = new JPanel();
	private JPanel jpanel2 = new JPanel();
	private JPanel jpanel3 = new JPanel();
	
//	public static ClientTool ct;
//	static {	
//		try {
//		ct = new ClientTool();
//		ct.setClientTool();
//		} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	 }
//	}
	static {
		MenuSFM.menuLoad();
	}
	
	private boolean pm = true;
	
	private Map<String, Integer> orders = new HashMap<>();
	{
		for (String s: MenuSFM.getGroupString()) {
			for (String string : MenuSFM.getMenuName(s)) {
				orders.put(string, 0);
			}
		}
	}
	Member member;
	private boolean gopp=false;
	private int djs=1;
	
	private static final int ROW = MenuSFM.getMenus().size();
	private static final int COL = 4;
	
	private static final int INSERT_MODE=1;
	private static final int CLICK_MODE=2;
	
	
	private int orderMode=INSERT_MODE;
	private String[] modeName= {"insert","click"};
	
	private JButton[] groups = new JButton[ROW];
	private JButton[][] menus = new JButton[ROW][COL];

	private JButton orderinfo = new JButton("�ֹ� ����");
	private JButton myinfo = new JButton("�� ����");
	private JButton order = new JButton("�ֹ�");
	private JButton moreOrLess= new JButton("+");
	private JButton reset = new JButton("����");
	private JButton setFoodSize = new JButton("");
	
	
	private JMenuBar mb = new JMenuBar(); 
	private JMenu basicmenu = new JMenu("Menu");
	private JMenu setting = new JMenu("Setting");
	private JMenuItem setMode=new JMenuItem(modeName[orderMode-1]+" mode");
	
	private MyInfo myInfoView = new MyInfo(this, true);
	

	private String columnNames[] = { "�з�", "�޴���", "����","����(��)" };
	String[] lookNfeel = { "com.jtattoo.plaf.mcwin.McWinLookAndFeel", "com.jtattoo.plaf.smart.SmartLookAndFeel",//2
			"com.jtattoo.plaf.acryl.AcrylLookAndFeel","com.jtattoo.plaf.aero.AeroLookAndFeel","com.jtattoo.plaf.aero.AeroLookAndFeel",//3
			"com.jtattoo.plaf.aluminium.AluminiumLookAndFeel","com.jtattoo.plaf.bernstein.BernsteinLookAndFeel",//2
			"com.jtattoo.plaf.graphite.GraphiteLookAndFeel","com.jtattoo.plaf.hifi.HiFiLookAndFeel","com.jtattoo.plaf.mint.MintLookAndFeel",//3
			"com.jtattoo.plaf.luna.LunaLookAndFeel","com.jtattoo.plaf.mcwin.McWinLookAndFeel"//2
	};//look&feel �׸� �̸� ��
	
	
//	private Object rowData[][] = { { 1, "¥���", 1 }, { 2, "������", 200 }, { 3, "������", 300 }, { 4, "������", 300 },
//			{ 5, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 } };

	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {{"�� ����","�� ��",0,0}}, columnNames);

	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public MainOrderView(Member member) {
		this.member=member;
//		myInfoView.setMember(member); //������
//		window = new OrderInfo(this, true,member);
//		window1 = new MyInfo(this, true);
//		window1.generateInfo(member);
		design();
		event();
		menu();

		setTitle("KG����");
		setBounds(100, 100, 1000, 600);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
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
				// menus[i][j].setBackground(Color.LIGHT_GRAY);
			}
		}

		jpanel3.setBounds(660, 64, 250, 300);
		bg.add(jpanel3);
		jpanel3.add(jScollPane);
		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //��
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(230, 270)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�


		myinfo.setBounds(800, 10, 114, 31);
		bg.add(myinfo);
		
		orderinfo.setBounds(665, 10, 114, 31);
		bg.add(orderinfo);

		order.setBounds(660, 435, 250, 55);
		bg.add(order);
		
		moreOrLess.setBounds(660, 402, 70, 30);
		bg.add(moreOrLess);
		setFoodSize.setBounds(750, 402, 70, 30);
		bg.add(setFoodSize);
		reset.setBounds(840, 402, 70, 30);
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

			/*
			 * �� ���� for (; idx < ROW; idx++) { if(groups[idx].getText().equals(gp)) break;
			 * 
			 * } idx*=COL-1;
			 * 
			 * if(ROW*(COL-1)-idx<menu.size()) {
			 * 
			 * idx+=-menu.size()+COL-1; idx/=COL-1; idx*=COL-1; } if(idx<0)idx=0;
			 */

			for (String string : menu) {
				if (idx == ROW* COL)
					break;
				menus[idx / COL][idx % COL].setText(string);
				idx++;
			}
			if(gp.equals("���")) setFoodSize.setText("����");
		};
		
		ActionListener act2 = e -> {
			
			
			String order=e.getActionCommand();//���� Ŭ���ؼ� �ֹ��� �޴� �̸�
			
			if(order.equals("")||order==null)return;
			int temp=orders.get(order);//�ֹ� �߰�/���������� ���� ex) ¥��� 4�� �����ֹ� =>0 ,  «�� 4������ 3���� ����=>4
			
			ordering(order, orderMode);//��忡 ���� �ֹ�
			
			int num=orders.get(order);//�ֹ� �߰�/���� ���� ���� ex) ¥��� 4�� �����ֹ� =>4 ,  «�� 4������ 3���� ����=>3
			
			
			String ordergname="";
			
			for (Iterator<String> ite = MenuSFM.getGroupString().iterator(); ite.hasNext();) {
				String gp=ite.next();
				if(MenuSFM.getMenuName(gp).contains(order)) {
					ordergname=gp;
				}
				
			}
			if(ordergname.equals(""))return;
			
			DefaultTableModel m =
	                (DefaultTableModel)jTable.getModel();
			
			
	        //�𵨿� ������ �߰� , ������ �⿡ ���ο� �����͸� �߰��մϴ�
			if(temp!=0||(!pm&&orderMode==CLICK_MODE)) {//�ű��ֹ��� �ƴϰų� Ŭ������ �ֹ����� ���̰� �ִ� ���
				for (int row = 0; row < m.getRowCount(); row++) {
					if(order.equals(m.getValueAt(row, 1))) {
					m.setValueAt(num, row, 2);//�޴� ���� ����
					m.setValueAt(num*MenuSFM.getMenu(order).getPrice(), row, 3);//�޴� ���� �� �޴� ����
					}
					
				}
			}else{
	        m.insertRow(m.getRowCount()-1, new Object[]{ordergname,order,num,
	        		num*(MenuSFM.getMenu(order).getPrice()+(gopp?1000:0))});//�ű��ֹ��� ���
			}
			
			
			int menuSum=0;
			int priceSum=0;
			
			for (int i = 0; i < m.getRowCount()-1; i++) {
			priceSum+=(int)m.getValueAt(i, 3);
			menuSum+=(int)m.getValueAt(i, 2);
			if(m.getValueAt(i, 2).equals(0))
				m.removeRow(i);	
			}
			m.setValueAt(menuSum,m.getRowCount()-1 , 2);
			m.setValueAt(new DecimalFormat("#,###,###").format(priceSum),m.getRowCount()-1 , 3);
			
	        jTable.updateUI();
		};
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				menus[i][j].addActionListener(act2);
			}
		}
		
		
		
		
		ActionListener act3 = e -> {
			if (e.getActionCommand().equals("+"))
				{pm = false; moreOrLess.setText("-");}
			else
				{pm = true;moreOrLess.setText("+");}
		};
		moreOrLess.addActionListener(act3);
		
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
	
		reset.addActionListener(e->{resetOrder();});
		order.addActionListener(e->{
			int priceSum=0;
			for (String name : orders.keySet()) {
				priceSum+=MenuSFM.getMenu(name).getPrice()*orders.get(name);
			}
			if(((DefaultTableModel)jTable.getModel()).getRowCount()==1) {
				
				JOptionPane.showMessageDialog(null, "���� �޴��� ����ּ���", "", JOptionPane.WARNING_MESSAGE);	
				return;
			}else if(priceSum<10000) {
				JOptionPane.showMessageDialog(null, "�����̻� ��� �����մϴ�", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Map<Menu,Integer> ordertemp=new HashMap<>();
			
			for (String string : orders.keySet()) {
				if(orders.get(string)==0)continue;
				Menu menu= MenuSFM.getMenu(string);
				ordertemp.put(menu, orders.get(string));
			}
			
			try {
				
				ClientTool.getTool().order(ordertemp);
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			
			/**@code{
			 * ���⿡ ���ο��� �ֹ� ������ ������ �ڵ尡 ��
			 * }
			  */
			
			JOptionPane.showMessageDialog(null, "���������� �ֹ��Ǿ����ϴ�", "", JOptionPane.INFORMATION_MESSAGE);
			resetOrder();
		});
		
		setFoodSize.addActionListener(e->{
			gopp=!gopp;
			setFoodSize.setText(setFoodSize.getText().equals("����")?"������":"����");
		});
		setMode.addActionListener(e->{orderMode=1+(orderMode)%modeName.length; setMode.setText(modeName[orderMode-1]+" mode");});
		
	}
	
	

	private void menu() {
		
		JMenuItem logoutMenu=new JMenuItem("logout");
		JMenuItem exit=new JMenuItem("exit");
		
		JMenuItem settingItem=new JMenuItem("setting");
		
		
		logoutMenu.addActionListener(e->{
			try {
				ClientTool.getTool().logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "�α� �ƿ��� �����Ͽ����ϴ�", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.dispose();
			JOptionPane.showMessageDialog(null, "���������� �α׾ƿ� �Ǿ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
			new Login();
		});
		
		
		settingItem.addActionListener(e->{});
		
		exit.addActionListener(e->{System.exit(0);});
		
		basicmenu.add(logoutMenu);
		
		if(member.getId().equals("master")) {
			JMenuItem menuChange=new JMenuItem("menu manage");
			menuChange.addActionListener(e->{
				
				MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
				new ForguiShow();//GUI�� �޴� ��������

			});
			basicmenu.add(menuChange);	
		}
		
		basicmenu.add(setMode);
		basicmenu.add(exit);
		
		setting.add(settingItem);
		
		mb.add(basicmenu);
		mb.add(setting);
		
		setJMenuBar(mb);
		
	}
	
	private void ordering(String order,int mode) {
		
		int num=0;
		switch (mode) {
		case INSERT_MODE:
			String s=JOptionPane.showInputDialog("�ֹ�/�����Ͻ�"+order+"�� ������ �Է��ϼ���");
			if(s.equals(null))return;
			try {
				int n=Integer.parseInt(s);
				if(n<0) throw new Exception();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "�߸��� �Է��Դϴ�", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			num=Integer.parseInt(s);
			orders.put(order, num);
			
			break;
		case CLICK_MODE:
			
			num=orders.get(order)+(pm?1:-1);
			
			if(num<0) {num=0;return;}
			
			
			
			orders.put(order, num);
			break;
		}
	}
	private void resetOrder() {
		DefaultTableModel m =(DefaultTableModel)jTable.getModel();
		while(m.getRowCount()>1) {
			m.removeRow(0);
		}
		m.setValueAt(0, 0, 2);
		m.setValueAt(0, 0, 3);
		for (String s: MenuSFM.getGroupString()) {
			for (String string : MenuSFM.getMenuName(s)) {
				orders.put(string, 0);
			}
		}
	}
	
	private void foodSize(String str) {
		if(!str.equals("����")&&!str.equals("������"))return;
		gopp=!gopp;
		menus[ROW-1][COL-1].setText(str.equals("������")?"����":"������");
	}


	
	public void setCusId(String id) {
		cusId=id;
	}

}
	
	
public class Main{
	public static void main(String[] args) {
//		new MainOrderView(new Member());
		new Login();
	}
}


// https://m.blog.naver.com/PostView.nhn?blogId=tkddlf4209&logNo=220599772823&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
