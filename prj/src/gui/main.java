package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.lang.invoke.StringConcatFactory;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import master.MenuSFM;

class Window01 extends JFrame {
	private JPanel bg = new JPanel();

	private JPanel jpanel1 = new JPanel();
	private JPanel jpanel2 = new JPanel();
	private JPanel jpanel3 = new JPanel();
	
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
	
	private static final int ROW = MenuSFM.getMenus().size();
	private static final int COL = 4;
	
	private static final int INSERT_MODE=1;
	private static final int CLICK_MODE=2;
	
	private int orderMode=INSERT_MODE;
	private String[] modeName= {"입력","클릭"};
	
	private JButton[] groups = new JButton[ROW];
	private JButton[][] menus = new JButton[ROW][COL];

	private JButton myinfo = new JButton("주문 정보");
	private JButton orderinfo = new JButton("내 정보");
	private JButton order = new JButton("주문");
	private JButton moreOrLess= new JButton("+");
	private JButton reset = new JButton("삭제");
	private JButton setMode = new JButton(modeName[orderMode-1]);
	
	private OrderInfo window = new OrderInfo(this, true);
	private MyInfo window1 = new MyInfo(this, true);

	private String columnNames[] = { "분류", "메뉴명", "수량","가격(원)" };

	
//	private Object rowData[][] = { { 1, "짜장면", 1 }, { 2, "볶음밥", 200 }, { 3, "탕수육", 300 }, { 4, "탕수육", 300 },
//			{ 5, "탕수육", 300 }, { 6, "탕수육", 300 }, { 6, "탕수육", 300 }, { 6, "탕수육", 300 }, { 6, "탕수육", 300 } };

	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {{"총 수량","총 액",0,0}}, columnNames);

	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
		
		
		
		design();
		event();
		menu();

		setTitle("KG반점");
		setBounds(100, 100, 1000, 600);
		setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
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
		// jTable.setBackground(Color.DARK_GRAY); //색
		jTable.setRowHeight(25); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(230, 270)); // 사이즈?
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가


		myinfo.setBounds(665, 10, 114, 31);
		bg.add(myinfo);

		orderinfo.setBounds(800, 10, 114, 31);
		bg.add(orderinfo);

		order.setBounds(660, 435, 250, 55);
		bg.add(order);
		
		moreOrLess.setBounds(660, 402, 70, 30);
		bg.add(moreOrLess);
		setMode.setBounds(750, 402, 70, 30);
		bg.add(setMode);
		reset.setBounds(840, 402, 70, 30);
		bg.add(reset);
		
	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
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
			 * 줄 맞춤 for (; idx < ROW; idx++) { if(groups[idx].getText().equals(gp)) break;
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
		};
		
		ActionListener act2 = e -> {
			
			
			String order=e.getActionCommand();
			int temp=orders.get(order);
			ordering(order, orderMode);
			
			int num=orders.get(order);
			
			String ordergname="";
			
			for (Iterator<String> ite = MenuSFM.getGroupString().iterator(); ite.hasNext();) {
				String gp=ite.next();
				if(MenuSFM.getMenuName(gp).contains(order)) {
					ordergname=gp;
				}
				
			}
			if(ordergname=="")return;
			
			DefaultTableModel m =
	                (DefaultTableModel)jTable.getModel();
			
			
	        //모델에 데이터 추가 , 마지막 출에 새로운 데이터를 추가합니다
			if(temp!=0||(!pm&&orderMode==CLICK_MODE)) {
				for (int row = 0; row < m.getRowCount(); row++) {
					if(order.equals(m.getValueAt(row, 1))) {
					m.setValueAt(num, row, 2);
					m.setValueAt(num*MenuSFM.getMenu(order).getPrice(), row, 3);
					}
					
				}
			}else{
	        m.insertRow(m.getRowCount()-1, new Object[]{ordergname,order,num,num*MenuSFM.getMenu(order).getPrice()});
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
			window.setVisible(true);
		});

		orderinfo.addActionListener(e -> {
			window1.setVisible(true);
		});
		
		reset.addActionListener(e->{
		});
		
		setMode.addActionListener(e->{orderMode=1+(orderMode)%modeName.length; setMode.setText(modeName[orderMode-1]);});
		
	}
	
	

	private void menu() {
	}
	
	private void ordering(String order,int mode) {
		int num=0;
		switch (mode) {
		case INSERT_MODE:
			String s=JOptionPane.showInputDialog("주문/수정하실"+order+"의 개수를 입력하세요");
			boolean flag=Pattern.compile("(^([0-9]*)$)").matcher(s).find();
			if(!flag) {
				JOptionPane.showConfirmDialog(null, "잘못된 입력입니다", "", JOptionPane.WARNING_MESSAGE);
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
}

public class main {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}

// https://m.blog.naver.com/PostView.nhn?blogId=tkddlf4209&logNo=220599772823&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
