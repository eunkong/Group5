/**
 * 
 */
package gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
		setSize(300, 200);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);//배경 설치
		bg.setLayout(null);
		
		title.setFont(new Font("굴림", Font.PLAIN, 20));
		title.setBounds(72, 10, 133, 47);
		bg.add(title);
		
		pnumText.setBounds(5, 76, 87, 29);
		bg.add(pnumText);
		
		
		pnumArea.setBounds(90, 80, 116, 21);
		bg.add(pnumArea);
		pnumArea.setColumns(10);
		
		searchButton.setBounds(205, 80, 80, 23);
		bg.add(searchButton);
		
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
		searchButton.addActionListener(e->{
				searchId();
		});
		
		pnumArea.addKeyListener(
			new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				searchId();
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				dispose();
			}	
		});
	}

	private void menu() {
	}
	
	private void searchId() {
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
		} catch (Exception e) {
		}
		if(id.equals(""))JOptionPane.showMessageDialog(null, "검색된 아이디가 없습니다.");
		else { JOptionPane.showMessageDialog(null, "회원님의 번호로 검색된 아이디는 "+id+"입니다.");
		dispose();
		}
	}
}




