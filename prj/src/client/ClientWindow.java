package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Window extends JFrame{
	private JPanel bg = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 300));
	private JButton register = new JButton("회원가입");
	private JButton login = new JButton("로그인");
	private JButton end = new JButton("종료");
	
	public Window() {
		design();
		event();
		menu();
		
		setTitle("스윙 예제01");
		setSize(900, 700);
//		setLocation(100, 100);
		setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true); // 항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg); //bg를 배경에 설치하라
		bg.add(register);
		bg.add(login);
		bg.add(end);
		//this가 아니라 bg에 작업을 수행할 수 있다
		
	}

	private void event() {
		//JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); //x키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)
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
