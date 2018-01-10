package serverGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import master.ForguiShow;
import master.MenuSFM;
  
//��������â ����â
public class ServerMainFrame extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());	//�г� �����.
	private JLabel lb1 = new JLabel("����Բ�~��");
	private JLabel lb2 = new JLabel("�̰�");
	private JButton bt1 = new JButton("�ֹ�����");
	private JButton bt2 = new JButton("������");
	private JButton bt3 = new JButton("�޴�����");
	private JButton bt4 = new JButton("������");

	private OrderManageWindow orderWindow = new OrderManageWindow(this,true);		//OrderManageWindow â ���� �Ű�����
	private GuestManageWindow guestWindow = new GuestManageWindow(this,true);	//GuestManageWindow â ���� �Ű� ����
	
	public ServerMainFrame() {
		design();
		event();
		menu();
		setTitle("����Բ�~����");
		setSize(482, 343);
		setResizable(false);		
		setLocationByPlatform(true);	
		setVisible(true);
	}

	private void menu() {
		
	}

	private void event() {
		bt1.addActionListener(e->{
			//�ֹ�����â �ҷ�����
			orderWindow.setVisible(true);
		});
		
		bt2.addActionListener(e->{
			//������â �ҷ�����
			guestWindow.setVisible(true);
		});
		
		bt3.addActionListener(e->{
			MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
			new ForguiShow();//GUI�� �޴� ��������
		});
		
		bt4.addActionListener(e->{
			System.exit(0); //����
		});
	}

	private void design() {
		setContentPane(bg);  
		bg.setLayout(null);
		bg.setBackground(Color.WHITE);
		
		bt1.setBounds(304, 73, 133, 40);
		bt1.setForeground(Color.darkGray);
		bt1.setBackground(Color.orange);
		bg.add(bt1);
		
		bt2.setBounds(304, 123, 133, 40);
		bt2.setForeground(Color.darkGray);
		bt2.setBackground(Color.orange);
		bg.add(bt2);
		
		bt3.setBounds(304, 172, 133, 40);
		bt3.setForeground(Color.darkGray);
		bt3.setBackground(Color.orange);
		bg.add(bt3);
		
		bt4.setBounds(304, 222, 133, 40);
		bt4.setForeground(Color.darkGray);
		bt4.setBackground(Color.orange);
		bg.add(bt4);
		
		lb1.setForeground(new Color(135, 206, 250));
		lb1.setFont(new Font("�ü�ü", Font.ITALIC, 40));
		lb1.setBounds(12, 86, 285, 71);
		bg.add(lb1);
		
		lb2.setForeground(new Color(30, 144, 255));
		lb2.setFont(new Font("�ü�ü", Font.ITALIC, 40));
		lb2.setBounds(12, 36, 285, 71);
		bg.add(lb2);
		
	}
}
