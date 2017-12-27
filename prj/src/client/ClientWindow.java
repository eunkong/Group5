package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Window extends JFrame{
	private JPanel bg = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 300));
	private JButton register = new JButton("ȸ������");
	private JButton login = new JButton("�α���");
	private JButton end = new JButton("����");
	
	public Window() {
		design();
		event();
		menu();
		
		setTitle("���� ����01");
		setSize(900, 700);
//		setLocation(100, 100);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
//		setAlwaysOnTop(true); // �׻���
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg); //bg�� ��濡 ��ġ�϶�
		bg.add(register);
		bg.add(login);
		bg.add(end);
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		
	}

	private void event() {
		//JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); //xŰ ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)
		ActionListener act = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		};
	}

	private void menu() {
	}
}

public class ClientWindow {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		Frame f = new Window();
	}
}
