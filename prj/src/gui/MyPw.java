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
	
	private JLabel title = new JLabel("��й�ȣ ã��");
	private JLabel idText = new JLabel("Id");
	private JLabel pnumText = new JLabel("Phonenumber");

	private JTextField idArea = new JTextField();
	private JTextField pnumArea = new JTextField();
	
	private JButton searchButton = new JButton("search");
	
	public MyPw() {
		design();
		event();
		menu();
		
		setTitle("���̵�/��й�ȣ ã��");
		setSize(300, 400);
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
//		JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
		
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
						JOptionPane.showMessageDialog(null, "�߸��� �ڵ��� ��ȣ �Դϴ�.");
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
			JOptionPane.showMessageDialog(null, "ȸ������ ���̵�� ��ȭ��ȣ�� �˻��� ��й�ȣ�� "+pwd.toString()+"�Դϴ�.");
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


