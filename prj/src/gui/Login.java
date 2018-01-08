package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ClientTool;
import client.Member;
import master.Menu;

public class Login extends JFrame {
	private JPanel bg = new JPanel(new BorderLayout());

	private JLabel title = new JLabel("Login System");
	private JLabel idText = new JLabel("UserID");
	private JLabel pwText = new JLabel("UserPassword");

	private JTextField idArea = new JTextField();
	private JPasswordField pwArea = new JPasswordField();

	private JButton login = new JButton("Login");
	private JButton signup = new JButton("Sign Up");

	private SignUp signview = new SignUp(this, true); // SingUp(회원가입)화면

	public Login() {
		design();
		event();
		menu();

		setTitle("로그인");
		setSize(300, 350);
		setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// 배경 설치
		bg.setLayout(null);

		title.setFont(new Font("굴림", Font.PLAIN, 20));
		title.setBounds(72, 10, 133, 47);
		bg.add(title);

		idText.setBounds(12, 106, 87, 29);
		bg.add(idText);

		pwText.setBounds(12, 170, 87, 29);
		bg.add(pwText);

		idArea.setBounds(128, 110, 116, 21);
		bg.add(idArea);
		idArea.setColumns(10);

		pwArea.setBounds(128, 174, 116, 21);
		bg.add(pwArea);
		pwArea.setColumns(10);

		login.setBounds(30, 251, 97, 23);
		bg.add(login);

		signup.setBounds(147, 251, 97, 23);
		bg.add(signup);
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기

		ActionListener act = e -> {
			loginNow();//
		};
		login.addActionListener(act);

		KeyAdapter enterKey = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					loginNow();
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
			}
		};
		idArea.addKeyListener(enterKey);
		pwArea.addKeyListener(enterKey);

		signup.addActionListener(e -> {
			signview.setVisible(true); //SingUp(회원가입)화면 보여지기
		});
	}

	private void menu() {
	}

	//로그인
	private void loginNow() {
		String id = idArea.getText();
		String pwd = pwArea.getText();
		try {
			Member mem = ClientTool.getTool().login(id, pwd);
			Map<String, Set<Menu>> map = (Map<String, Set<Menu>>) ClientTool.getTool().in.readObject();
			if (mem == null) {
				JOptionPane.showMessageDialog(null, "로그인 실패", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.", "", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			new MainOrderView(mem, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
