package gui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import client.Member;

public class MyInfo extends JDialog{
	
	private JToolBar tb=new JToolBar();
	private JLabel lb=new JLabel("");
	public MyInfo(Frame owner, boolean modal) {
		super(owner, modal);
		setContentPane(tb);
		tb.add(lb);
		setTitle("내정보");
		setSize(300,400);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	void generateInfo(Member mem) {
		System.out.println(mem==null);
	if(mem==null) {
		lb.setText("로그인 정보가 없습니다");
		return;
		}	
		StringBuffer buffer=new StringBuffer("");
		
		buffer.append("id:");
		buffer.append(mem.getId());
		buffer.append("\n");
	
		buffer.append("pw:");
		buffer.append(mem.getPwd());
		buffer.append("\n");
	/*	
		buffer.append("phone number:");
		buffer.append(mem.getPhoneNumber());
		buffer.append("\n");
		
		buffer.append("address:");
		buffer.append(mem.getAddress());
		buffer.append("\n");
		
		buffer.append("point:");
		buffer.append(mem.getPoint());
		buffer.append("\n");
		
		buffer.append("grade:");
		buffer.append(mem.getGrade());
		buffer.append("\n");
		*/
		lb.setText(buffer.toString());
	 }
	}
