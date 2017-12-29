package gui;

import java.awt.Frame;

import javax.swing.JDialog;

public class MyInfo extends JDialog{

	public MyInfo(Frame owner, boolean modal) {
		super(owner, modal);
		
		setTitle("³»Á¤º¸");
		setSize(300,400);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
}
