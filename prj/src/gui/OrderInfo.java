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
	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�����̵�", "�� �ּ�","�� ����ó", "�ֹ�����" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderInfo(Frame owner, boolean modal,Member member){
		super(owner, modal);
		this.member=member;
		
		setTitle("�ֹ�����");
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
