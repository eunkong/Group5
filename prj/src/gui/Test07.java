package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Window07 extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());
	
	//Dialog �غ�
	//private JDialog window = new JDialog(this, "ȯ�漳��", false);
	private MyInfo window = new MyInfo(this, true);
	
	public Window07() {
		design();
		event();
		menu();
		
		setTitle("���� ����07");
		setSize(500, 400);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//��ġ�� �ü���� ���ϵ��� ����
//		setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		Dialog�� ���� �ڵ� - Frame�� ���
//		window.setSize(300, 400);
//		window.setLocationRelativeTo(bg);
		window.setVisible(true);
		
	}

	private void design() {
		setContentPane(bg);//bg�� ��濡 ��ġ�϶�
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		
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

public class Test07 {
	public static void main(String[] args) {
		JFrame f = new Window07();
	}
}
