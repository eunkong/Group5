/**
 * 
 */
package gui;

import java.awt.*;
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
    public class MyId extends JFrame{

	private JPanel bg = new JPanel(new BorderLayout());
	
	private JLabel title = new JLabel("아이디 찾기");
	private JLabel pnumText = new JLabel("Phonenumber");

	private JTextField pnumArea = new JTextField();
	
	private JButton searchButton = new JButton("search");
	
	public MyId() {
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
		
		pnumText.setBounds(12, 106, 87, 29);
		bg.add(pnumText);
		
		
		pnumArea.setBounds(128, 110, 116, 21);
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
		searchButton.addActionListener(e->{
			String id="";
			try (ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(new File("files", "memberlist.db"))));){
				Map<String, Member> map = (Map<String, Member>) in.readObject();
				for (String string : map.keySet()) {
					Member mem=map.get(string);
					if(mem.getPhoneNumber().equals(pnumArea.getText())) {
						id=mem.getId();
						break;
					}
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			if(id.equals(""))JOptionPane.showMessageDialog(null, "검색된 아이디가 없습니다.");
			else { JOptionPane.showMessageDialog(null, "회원님의 번호로 검색된 아이디는 "+id+"입니다.");
			dispose();
			}
			
		});
	
	}

	private void menu() {
		
	}
}




