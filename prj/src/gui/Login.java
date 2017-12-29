package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Window02 extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());
	
	
	private JLabel jlable1 = new JLabel("Login System");
	private JLabel jlable2 = new JLabel("UserName");
	private JLabel jlable3 = new JLabel("UserPassword");

	private JTextField jtf1 = new JTextField();
	private JTextField jtf2 = new JTextField();
	
	private JButton jbt1 = new JButton("Login");
	private JButton jbt2 = new JButton("Sign Up");

	
	public Window02() {
		design();
		event();
		menu();
		
		setTitle("�α���");
		setSize(300, 350);
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
		
		jlable1.setFont(new Font("�ü�ü", Font.PLAIN, 20));
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
//		JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
	}

	private void menu() {
	}
}

public class Login {
	public static void main(String[] args) {
		JFrame f = new Window02();
	}
}
