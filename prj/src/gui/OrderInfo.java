package gui;

import java.awt.Frame;

import javax.swing.JDialog;

public class OrderInfo extends JDialog{

	public OrderInfo(Frame owner, boolean modal) {
		super(owner, modal);
		
		setTitle("�ֹ�����");
		setSize(300,400);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
}
