package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.Member;
import master.Order;

public class OrderInfo extends JDialog{
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("�� �ֹ�");
	private JButton bt1 = new JButton("�Ⱓ�� ��ȸ");
	private JButton bt2 = new JButton("��ü�ֹ�");
	private JButton bt3 = new JButton("���ư���");
	
	
	private String columnNames[] = { "�ֹ���ȣ", "�ֹ��ð�", "�� �ּ�", "�� ����ó","�ֹ� ����", "�ݾ�" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderInfo(Frame owner, boolean modal){
		super(owner, modal);
		design();
		event();
		menu();

		setTitle("");
		setSize(1200, 800);
//		setLocation(100, 40);
		//setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true); // �׻���
		setLocationRelativeTo(owner);
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
		
		bt1.setBounds(696, 60, 133, 70);
		bg.add(bt1);
		
		bt2.setBounds(841, 60, 133, 70);
		bg.add(bt2);
		
		bt3.setBounds(986, 60, 133, 70);
		bg.add(bt3);
		
		jpanel.add(jScollPane);
		//jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //��
		jTable.setRowHeight(25); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		
		/*try {
			Map<Long,Order> orderIdx=MainOrderView.ct.myorderlist(); //����
			
			if(orderIdx==null) {
				JOptionPane.showMessageDialog(null, "�ֹ� ������ �����ϴ�!","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			DefaultTableModel tp=(DefaultTableModel)jTable.getModel();
			
			for (long onumber: orderIdx.keySet()) {
				Order orderTemp=orderIdx.get(onumber);
				Member memtp=orderTemp.getMember();
				tp.addRow(new Object[] {onumber,orderTemp.getOrdertime(),memtp.getAddress(),
				memtp.getPhoneNumber(),orderTemp.getOrderState(),orderTemp.getPriceSum()		
				});
			}
			
			 jTable.updateUI();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}*/
		
		
	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)
		
		bt3.addActionListener(e->{
			dispose();
		});
	}

	private void menu() {
	}
}
