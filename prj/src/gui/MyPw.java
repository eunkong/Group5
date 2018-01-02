/**
 * 
 */
package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.swing.*;

import client.Member;

/**
 
 * prj
 * @param
 */
public class MyPw extends JFrame{

	private JPanel bg = new JPanel(new BorderLayout());
	
	private JLabel title = new JLabel("비밀번호 찾기");
	private JLabel idText = new JLabel("Id");
	private JLabel pnumText = new JLabel("Phonenumber");

	private JTextField idArea = new JTextField();
	private JTextField pnumArea = new JTextField();
	
	private JButton searchButton = new JButton("search");
	
	public MyPw() {
		design();
		event();
		menu();
		
		setTitle("아이디/비밀번호 찾기");
		setSize(300, 400);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		
		title.setFont(new Font("궁서체", Font.PLAIN, 20));
		
		title.setBounds(72, 10, 133, 47);
		bg.add(title);
		
		idText.setBounds(12, 106, 87, 29);
		bg.add(idText);
		
		pnumText.setBounds(12, 170, 87, 29);
		bg.add(pnumText);
		
		idArea.setBounds(128, 110, 116, 21);
		bg.add(idArea);
		idArea.setColumns(10);
		
		pnumArea.setBounds(128, 174, 116, 21);
		bg.add(pnumArea);
		pnumArea.setColumns(10);
		
		
		searchButton.setBounds(30, 251, 97, 23);
		bg.add(searchButton);
		
	}

	private void event() {
//		JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
		
		ActionListener act=e->{
			String pw="";
			try (ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(new File("files", "memberlist.db"))));){
				Map<String, Member> map = (Map<String, Member>) in.readObject();
				Member mem;
				try {
					String id=idArea.getText();
					mem=map.get(id);
					if(!(mem.getPhoneNumber()).equals(pnumArea.getText())) {
						JOptionPane.showMessageDialog(null, "잘못된 핸드폰 번호 입니다.");
						return;
					}
					pw=mem.getPwd();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR","",JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception err) {
				// TODO: handle exception
			}
			StringBuffer pwd=new StringBuffer(pw.substring(0,(1+pw.length())/2));
			while(pw.length()-pwd.length()>0) {
				pwd.append("*");
			
			}
			JOptionPane.showMessageDialog(null, "회원님의 아이디와 전화번호로 검색된 비밀번호는 "+pwd.toString()+"입니다.");
			dispose();
		};
		
		searchButton.addActionListener(act);
		idArea.addActionListener(act);
		pnumArea.addActionListener(act);
	}

	private void menu() {
		
	}
	private void searchMethod() {
		
	}
}


