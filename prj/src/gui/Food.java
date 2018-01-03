package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class FoodView extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());
	
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	private JPanel jp6 = new JPanel();
	
	private JButton jbt1 = new JButton("1 button");
	private JButton jbt2 = new JButton("2 button");
	private JButton jbt3 = new JButton("1 button");
	private JButton jbt4 = new JButton("2 button");
	private JButton jbt5 = new JButton("1 button");
	private JButton jbt6 = new JButton("2 button");
	private JButton jbt7 = new JButton("1 button");
	private JButton jbt8 = new JButton("2 button");
	private JButton jbt9 = new JButton("1 button");
	private JButton jbt10 = new JButton("2 button");
	private JButton jbt11 = new JButton("1 button");
	private JButton jbt12 = new JButton("2 button");
	private JButton jbt13 = new JButton("1 button");
	private JButton jbt14 = new JButton("2 button");

	
	private String columnNames[] = { "주문번호", "분류", "메뉴명", "수량"};
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {{"ㄴㅇㄹ", "ㅇㅀ", 0, 0}}, columnNames);
	// JTable에 DefaultTableModel을 담기
	private JTable jTable1 = new JTable(defaultTableModel);
	private JTable jTable2 = new JTable(defaultTableModel);
	private JTable jTable3 = new JTable(defaultTableModel);
	private JTable jTable4 = new JTable(defaultTableModel);
	private JTable jTable5 = new JTable(defaultTableModel);
	private JTable jTable6 = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane1 = new JScrollPane(jTable1);
	private JScrollPane jScollPane2 = new JScrollPane(jTable2);
	private JScrollPane jScollPane3 = new JScrollPane(jTable3);
	private JScrollPane jScollPane4 = new JScrollPane(jTable4);
	private JScrollPane jScollPane5 = new JScrollPane(jTable5);
	private JScrollPane jScollPane6 = new JScrollPane(jTable6);
	
	
	public FoodView() {
		design();
		event();
		menu();
		
		setTitle("????");
		setSize(1200, 700);
		setLocation(200, 100);
		//setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		
		jTable1.setRowHeight(15); // 높이 조절
		jTable1.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable1.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable1.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		jTable2.setRowHeight(15); // 높이 조절
		jTable2.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable2.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable2.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		jTable3.setRowHeight(15); // 높이 조절
		jTable3.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable3.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable3.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		jTable4.setRowHeight(15); // 높이 조절
		jTable4.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable4.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable4.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		jTable5.setRowHeight(15); // 높이 조절
		jTable5.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable5.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable5.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		jTable6.setRowHeight(15); // 높이 조절
		jTable6.setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
		jTable6.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable6.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		

		
		jp1.setBounds(40, 100, 300, 225);
		bg.add(jp1);
		jp1.add(jScollPane1);
		
		jp2.setBounds(440, 100, 300, 225);
		bg.add(jp2);
		jp2.add(jScollPane2);
		
		jp3.setBounds(840, 100, 300, 225);
		bg.add(jp3);
		jp3.add(jScollPane3);
		
		jp4.setBounds(40, 390, 300, 225);
		bg.add(jp4);
		jp4.add(jScollPane4);
		
		jp5.setBounds(440, 390, 300, 225);
		bg.add(jp5);
		jp5.add(jScollPane5);
		
		jp6.setBounds(840, 390, 300, 225);
		bg.add(jp6);
		jp6.add(jScollPane6);
		
		jbt1.setBounds(70, 335, 120, 23);
		bg.add(jbt1);
		jbt2.setBounds(190, 335, 120, 23);
		bg.add(jbt2);
		
		jbt3.setBounds(470, 335, 120, 23);
		bg.add(jbt3);
		jbt4.setBounds(590, 335, 120, 23);
		bg.add(jbt4);
		
		jbt5.setBounds(870, 335, 120, 23);
		bg.add(jbt5);
		jbt6.setBounds(990, 335, 120, 23);
		bg.add(jbt6);
		
		jbt7.setBounds(70, 625, 120, 23);
		bg.add(jbt7);
		jbt8.setBounds(190, 625, 120, 23);
		bg.add(jbt8);
		
		jbt9.setBounds(470, 625, 120, 23);
		bg.add(jbt9);
		jbt10.setBounds(590, 625, 120, 23);
		bg.add(jbt10);
		
		jbt11.setBounds(870, 625, 120, 23);
		bg.add(jbt11);
		jbt12.setBounds(990, 625, 120, 23);
		bg.add(jbt12);
		
		
		
	}

	private void event() {
		//JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); //x키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)
	}

	private void menu() {
	}
}

public class Food {
	public static void main(String[] args) {
		Frame f = new FoodView();
	}
}
