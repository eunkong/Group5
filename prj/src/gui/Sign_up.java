package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Window11 extends JFrame {
	private JPanel bg = new JPanel(new BorderLayout());

	private static JTextField tf0 = new JTextField();
	private static JTextField tf1 = new JTextField();
	private static JPasswordField tf2 = new JPasswordField();
	private static JTextField tf3 = new JTextField();
	private static JTextField tf4 = new JTextField();

	private static JLabel jl0 = new JLabel("이름");
	private static JLabel jl1 = new JLabel("아이디");
	private static JLabel jl2 = new JLabel("비밀번호");
	private static JLabel jl3 = new JLabel("전화번호");
	private static JLabel jl4 = new JLabel("주소");
	
	private static JButton bt0 = new JButton("회원가입");
	private static JButton bt1 = new JButton("취소");

	public Window11() {
		design();
		event();
		menu();

		setTitle("회원가입");
		setSize(400, 500);
		// setLocation(100, 100);
		setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);

		tf0.setBounds(128, 35, 170, 35);
		bg.add(tf0);
		tf1.setBounds(128, 100, 170, 35);
		bg.add(tf1);
		tf2.setBounds(128, 165, 170, 35);
		bg.add(tf2);
		tf3.setBounds(128, 230, 170, 35);
		bg.add(tf3);
		tf4.setBounds(128, 295, 220, 35);
		bg.add(tf4);

		jl0.setBounds(27, 45, 57, 15);
		bg.add(jl0);
		jl1.setBounds(27, 110, 57, 15);
		bg.add(jl1);
		jl2.setBounds(27, 175, 57, 15);
		bg.add(jl2);
		jl3.setBounds(27, 240, 57, 15);
		bg.add(jl3);
		jl4.setBounds(27, 305, 57, 15);
		bg.add(jl4);
		
		bt0.setBounds(70, 370, 113, 35);
		bg.add(bt0);
		
		bt1.setBounds(220, 370, 113, 35);
		bg.add(bt1);

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
		
		bt1.addActionListener(e->{
			System.exit(0);
		});
	}

	private void menu() {
	}
}

public class Sign_up {
	public static void main(String[] args) {
		JFrame f = new Window11();
	}
}
