package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Window07 extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());
	
	//Dialog 준비
	//private JDialog window = new JDialog(this, "환경설정", false);
	private MyInfo window = new MyInfo(this, true);
	
	public Window07() {
		design();
		event();
		menu();
		
		setTitle("스윙 예제07");
		setSize(500, 400);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		Dialog를 띄우는 코드 - Frame과 비슷
//		window.setSize(300, 400);
//		window.setLocationRelativeTo(bg);
		window.setVisible(true);
		
	}

	private void design() {
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		
	}

	private void event() {
//		JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)

	
	}

	private void menu() {
		
	}
}

public class Test07 {
	public static void main(String[] args) {
		JFrame f = new Window07();
	}
}
