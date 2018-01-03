package gui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import client.Member;

public class OrderInfo extends JDialog{

	Member member;
	JLabel lb=new JLabel("");
	private JToolBar bg = new JToolBar();
	private String columnNames[] = { "주문번호", "주문시간", "고객아이디", "고객 주소","고객 연락처", "주문상태" };
	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderInfo(Frame owner, boolean modal,Member member){
		super(owner, modal);
		this.member=member;
		
		setTitle("주문정보");
		setSize(300,400);
		setLocationRelativeTo(owner);
		design();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	private void design() {
		
		setContentPane(bg);
		bg.add(jTable);
	}
	
}
