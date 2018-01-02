package gui;

import java.awt.Font;
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
	if(mem==null) {
		lb.setText("로그인 정보가 없습니다");
		return;
		}	
		StringBuffer buffer=new StringBuffer("");
		
		lb.setFont(new Font("", Font.PLAIN, 20));
		
		buffer.append("<html>");
		
		buffer.append("id:");
		buffer.append(mem.getId());
		buffer.append("<br>");
	
		buffer.append("pw:");
		buffer.append(mem.getPwd());
		buffer.append("<br>");
		
		buffer.append("phone number:");
		buffer.append(mem.getPhoneNumber());
		buffer.append("<br>");
		
		buffer.append("address:");
		buffer.append(mem.getAddress());
		buffer.append("<br>");
		
		buffer.append("point:");
		buffer.append(mem.getPoint());
		buffer.append("<br>");
		
		buffer.append("grade:");
		buffer.append(mem.getGrade());
		buffer.append("<br>");
		
		buffer.append("<html>");
		
		lb.setText(buffer.toString());
	 }
	}
