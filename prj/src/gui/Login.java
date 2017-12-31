package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.swing.*;

import client.Member;

public class Login extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());
	
	private JLabel jlable1 = new JLabel("Login System");
	private JLabel jlable2 = new JLabel("UserName");
	private JLabel jlable3 = new JLabel("UserPassword");

	private JTextField jtf1 = new JTextField();
	private JTextField jtf2 = new JTextField();
	
	private JButton jbt1 = new JButton("Login");
	private JButton jbt2 = new JButton("Sign Up");
	
	
	MainOrderView wd=null;
	
	public Login(MainOrderView wd) {
		
		design();
		event();
		menu();
		
		setTitle("로그인");
		setSize(300, 350);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
		this.wd=wd;
	}

	private void design() {
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		
		jlable1.setFont(new Font("궁서체", Font.PLAIN, 20));
		jlable1.setBounds(72, 10, 133, 47);
		bg.add(jlable1);
		
		jlable2.setBounds(12, 106, 87, 29);
		bg.add(jlable2);
		
		jlable3.setBounds(12, 170, 87, 29);
		bg.add(jlable3);
		
		jtf1.setBounds(128, 110, 116, 21);
		bg.add(jtf1);
		jtf1.setColumns(10);
		
		jtf2.setBounds(128, 174, 116, 21);
		bg.add(jtf2);
		jtf2.setColumns(10);
		
		jbt1.setBounds(30, 251, 97, 23);
		bg.add(jbt1);
		
		jbt2.setBounds(147, 251, 97, 23);
		bg.add(jbt2);
	}

	private void event() {
//		JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
		
		jbt1.addActionListener(e->{
			try (ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(new File("files", "memberlist.db"))));){
				
				@SuppressWarnings("unchecked")
				Map<String, Member> map = (Map<String, Member>) in.readObject();
				
				for (String string : map.keySet()) {
					Member mem=map.get(string);
					
					if(jtf1.getText().equals(mem.getId())&&jtf2.getText().equals(mem.getPwd()))
					{
						wd.setLoginout(true);
						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다."
							,"",JOptionPane.INFORMATION_MESSAGE);
						this.setVisible(false);
						return;
					};
				}
				JOptionPane.showMessageDialog(null,"로그인 실패", "", JOptionPane.WARNING_MESSAGE);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
		
	}


	private void menu() {
	}
	
	
}
