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
	
	private JLabel title = new JLabel("���̵� ã��");
	private JLabel pnumText = new JLabel("Phonenumber");

	private JTextField pnumArea = new JTextField();
	
	private JButton searchButton = new JButton("search");
	
	public MyId() {
		design();
		event();
		menu();
		
		setTitle("���̵�/��й�ȣ ã��");
		setSize(300, 200);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//��ġ�� �ü���� ���ϵ��� ����
//		setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);//bg�� ��濡 ��ġ�϶�
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);
		
		title.setFont(new Font("�ü�ü", Font.PLAIN, 20));
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
//		JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
		searchButton.addActionListener(e->{
				searchId();
		});
		
		pnumArea.addKeyListener(
			new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
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
			// TODO: handle exception
		}
		if(id.equals(""))JOptionPane.showMessageDialog(null, "�˻��� ���̵� �����ϴ�.");
		else { JOptionPane.showMessageDialog(null, "ȸ������ ��ȣ�� �˻��� ���̵�� "+id+"�Դϴ�.");
		dispose();
		}
	
	}
	
}



