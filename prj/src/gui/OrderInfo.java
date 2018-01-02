package gui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class OrderInfo extends JDialog{

	JLabel lb=new JLabel("");
	public OrderInfo(Frame owner, boolean modal) {
		super(owner, modal);
		
		setTitle("주문정보");
		setSize(300,400);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	
	
}
