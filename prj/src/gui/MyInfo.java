package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.*;

import client.Member;

public class MyInfo extends JDialog {
	private JPanel bg = new JPanel(new BorderLayout());

	private static JLabel tf0 = new JLabel();
	private static JTextField tf1 = new JTextField();
	private static JTextField tf2 = new JTextField();
	private static JTextField tf3 = new JTextField();
	private static JLabel tf4 = new JLabel();
	private static JLabel tf5 = new JLabel();

	private static JLabel jl0 = new JLabel("아이디");
	private static JLabel jl1 = new JLabel("비밀번호");
	private static JLabel jl2 = new JLabel("전화번호");
	private static JLabel jl3 = new JLabel("주소");
	private static JLabel jl4 = new JLabel("포인트");
	private static JLabel jl5 = new JLabel("등급");

	private static JButton bt0 = new JButton("수정");
	private static JButton bt1 = new JButton("취소");

	public MyInfo(Frame owner, boolean modal) {
		super(owner, modal);
		design();
		event();
		menu();

		setTitle("내정보");
		setSize(400, 500);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		

		tf0.setBounds(128, 40, 170, 20);
		bg.add(tf0, BorderLayout.NORTH);
		tf1.setBounds(128, 90, 170, 20);
		bg.add(tf1);
		tf2.setBounds(128, 140, 170,20);
		bg.add(tf2);
		tf3.setBounds(128, 190, 170, 20);
		bg.add(tf3);
		tf4.setBounds(128, 240, 220, 20);
		bg.add(tf4, BorderLayout.CENTER);
		tf5.setBounds(128, 290, 220, 20);
		bg.add(tf5, BorderLayout.CENTER);

		jl0.setBounds(27, 40, 57, 15);
		bg.add(jl0);
		jl1.setBounds(27, 90, 57, 15);
		bg.add(jl1);
		jl2.setBounds(27, 140, 57, 15);
		bg.add(jl2);
		jl3.setBounds(27, 190, 57, 15);
		bg.add(jl3);
		jl4.setBounds(27, 240, 57, 15);
		bg.add(jl4);
		jl5.setBounds(27, 290, 57, 15);
		bg.add(jl5);
		
		
		bt0.setBounds(70, 370, 113, 30);
		bg.add(bt0);
		bt1.setBounds(220, 370, 113, 30);
		bg.add(bt1);
	}

	private void event() {
		bt1.addActionListener(e->{
			dispose();
		});

	}

	private void menu() {

	}

//	void generateInfo(Member mem) {
//		if (mem == null) {
//			lb.setText("로그인 정보가 없습니다");
//			return;
//		}
//		StringBuffer buffer = new StringBuffer("");
//
//		//lb.setFont(new Font("", Font.PLAIN, 20));
//
//		buffer.append("<html>");
//
//		buffer.append("id:");
//		buffer.append(mem.getId());
//		buffer.append("<br>");
//
//		buffer.append("pw:");
//		buffer.append(mem.getPwd());
//		buffer.append("<br>");
//
//		buffer.append("phone number:");
//		buffer.append(mem.getPhoneNumber());
//		buffer.append("<br>");
//
//		buffer.append("address:");
//		buffer.append(mem.getAddress());
//		buffer.append("<br>");
//
//		buffer.append("point:");
//		buffer.append(mem.getPoint());
//		buffer.append("<br>");
//
//		buffer.append("grade:");
//		buffer.append(mem.getGrade());
//		buffer.append("<br>");
//
//		buffer.append("<html>");
//
//		lb.setText(buffer.toString());
//	}
}
