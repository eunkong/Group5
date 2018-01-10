package serverGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import master.ForguiShow;
import master.MenuSFM;
  
//서버관리창 메인창
public class ServerMainFrame extends JFrame{
	private JPanel bg = new JPanel(new BorderLayout());	//패널 만들기.
	private JLabel lb1 = new JLabel("사장님꺼~♥");
	private JLabel lb2 = new JLabel("이건");
	private JButton bt1 = new JButton("주문관리");
	private JButton bt2 = new JButton("고객관리");
	private JButton bt3 = new JButton("메뉴관리");
	private JButton bt4 = new JButton("나가자");

	private OrderManageWindow orderWindow = new OrderManageWindow(this,true);		//OrderManageWindow 창 종속 매개변수
	private GuestManageWindow guestWindow = new GuestManageWindow(this,true);	//GuestManageWindow 창 종속 매개 변수
	
	public ServerMainFrame() {
		design();
		event();
		menu();
		setTitle("사장님꺼~♥♥");
		setSize(482, 343);
		setResizable(false);		
		setLocationByPlatform(true);	
		setVisible(true);
	}

	private void menu() {
		
	}

	private void event() {
		bt1.addActionListener(e->{
			//주문관리창 불러오기
			orderWindow.setVisible(true);
		});
		
		bt2.addActionListener(e->{
			//고객관리창 불러오기
			guestWindow.setVisible(true);
		});
		
		bt3.addActionListener(e->{
			MenuSFM.menuLoad();//files에서 menu목록 가져와서 MenuSFM에 재저장(내용을 읽어옴)
			new ForguiShow();//GUI로 메뉴 관리시작
		});
		
		bt4.addActionListener(e->{
			System.exit(0); //종료
		});
	}

	private void design() {
		setContentPane(bg);  
		bg.setLayout(null);
		bg.setBackground(Color.WHITE);
		
		bt1.setBounds(304, 73, 133, 40);
		bt1.setForeground(Color.darkGray);
		bt1.setBackground(Color.orange);
		bg.add(bt1);
		
		bt2.setBounds(304, 123, 133, 40);
		bt2.setForeground(Color.darkGray);
		bt2.setBackground(Color.orange);
		bg.add(bt2);
		
		bt3.setBounds(304, 172, 133, 40);
		bt3.setForeground(Color.darkGray);
		bt3.setBackground(Color.orange);
		bg.add(bt3);
		
		bt4.setBounds(304, 222, 133, 40);
		bt4.setForeground(Color.darkGray);
		bt4.setBackground(Color.orange);
		bg.add(bt4);
		
		lb1.setForeground(new Color(135, 206, 250));
		lb1.setFont(new Font("궁서체", Font.ITALIC, 40));
		lb1.setBounds(12, 86, 285, 71);
		bg.add(lb1);
		
		lb2.setForeground(new Color(30, 144, 255));
		lb2.setFont(new Font("궁서체", Font.ITALIC, 40));
		lb2.setBounds(12, 36, 285, 71);
		bg.add(lb2);
		
	}
}
