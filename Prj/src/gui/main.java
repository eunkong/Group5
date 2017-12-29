package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	
	private static final int ROW = MenuSFM.getMenus().size();
	private static final int COL = 6;

	private JButton[] groups = new JButton[ROW];
	private JButton[][] menus = new JButton[ROW][COL - 2];

	private JButton myinfo = new JButton("�ֹ� ����");
	private JButton orderinfo = new JButton("�� ����");
	private JButton order = new JButton("�ֹ�");
	private JButton more = new JButton("+");
	private JButton less = new JButton("-");
	private JButton reset = new JButton("����");
	
	private OrderInfo window = new OrderInfo(this, true);
	private MyInfo window1 = new MyInfo(this, true);

	private String columnNames[] = { "�з�", "�޴���", "����" };

	private Object rowData[][] = { { 1, "¥���", 1 }, { 2, "������", 200 }, { 3, "������", 300 }, { 4, "������", 300 },
			{ 5, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 }, { 6, "������", 300 } };

	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(rowData, columnNames);

	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
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
		jpanel2.setLayout(new GridLayout(ROW, COL - 1));
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

		myinfo.setBounds(665, 10, 114, 31);
		bg.add(myinfo);

		orderinfo.setBounds(800, 10, 114, 31);
		bg.add(orderinfo);

		order.setBounds(660, 435, 250, 55);
		bg.add(order);
		
		more.setBounds(660, 402, 70, 30);
		bg.add(more);
		less.setBounds(750, 402, 70, 30);
		bg.add(less);
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
				if (idx == menus.length * (menus[0].length))
					break;
				menus[idx / menus[0].length][idx % menus[0].length].setText(string);
				idx++;
			}
		};
		
		ActionListener act3 = e -> {
			if (e.getActionCommand().equals("-"))
				pm = false;
			else
				pm = true;
		};
		more.addActionListener(act3);
		less.addActionListener(act3);
		
		for (int i = 0; i < groups.length; i++) {
			groups[i].addActionListener(act1);
		}

		myinfo.addActionListener(e -> {
			window.setVisible(true);
		});

		orderinfo.addActionListener(e -> {
			window1.setVisible(true);
		});
		ActionListener act4 = e -> {
			for (int i = 0; i < rowData[0].length; i++) {
				if(orders.get((String)rowData[i][1])>0);
				{
					JTable table=new JTable(defaultTableModel);
					
				}
			}
		};
		
		
	}
	
	

	private void menu() {
	}
}

public class main {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}

// https://m.blog.naver.com/PostView.nhn?blogId=tkddlf4209&logNo=220599772823&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
