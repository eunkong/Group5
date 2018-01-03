package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import client.Client;
import client.ClientTool;
import client.Member;

public class Login extends JFrame {
	private JPanel bg = new JPanel(new BorderLayout());

	private JToolBar search = new JToolBar();

	private JLabel title = new JLabel("Login System");
	private JLabel idText = new JLabel("UserID");
	private JLabel pwText = new JLabel("UserPassword");

	private JTextField idArea = new JTextField();
	private JPasswordField pwArea = new JPasswordField();

	private JButton login = new JButton("Login");
	private JButton signup = new JButton("Sign Up");
	
	private SignUp signview = new SignUp(this, true);

	public Login() {

		design();
		event();
		menu();

		setTitle("�α���");
		setSize(300, 350);
		// setLocation(100, 100);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		// setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);
		search.setLayout(null);

		title.setFont(new Font("�ü�ü", Font.PLAIN, 20));
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
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)

		ActionListener act = e -> {
			loginNow();
		};

		login.addActionListener(act);
		KeyAdapter enterKey = new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					loginNow();
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
			}
		};
		idArea.addKeyListener(enterKey);
		pwArea.addKeyListener(enterKey);
		
		signup.addActionListener(e->{
			signview.setVisible(true);
		});
	}

	private void menu() {
		JMenuBar mb = new JMenuBar();

		JMenu fg = new JMenu("forget Id/Pw");

		JMenuItem searchId = new JMenuItem("search Id");
		JMenuItem searchPw = new JMenuItem("search Pw");

		searchId.addActionListener(e -> {
			new MyId();
		});
		searchPw.addActionListener(e -> {
			new MyPw();
		});

		fg.add(searchId);
		fg.add(searchPw);

		mb.add(fg);
		setJMenuBar(mb);

	}

	private void loginNow() {
		String id=idArea.getText();
		String pwd=pwArea.getText();
		try {
			
			Member mem=MainOrderView.ct.login(id, pwd);
			if(mem==null) {
				JOptionPane.showMessageDialog(null, "�α��� ����", "", JOptionPane.WARNING_MESSAGE);
			    return;	
			}
			JOptionPane.showMessageDialog(null, "�α��ο� �����Ͽ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			new MainOrderView(mem);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		/*try (ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(new File("files", "memberlist.db"))));) {

			@SuppressWarnings("unchecked")
			Map<String, Member> map = (Map<String, Member>) in.readObject();

			for (String string : map.keySet()) {
				Member mem = map.get(string);

				if (idArea.getText().equals(mem.getId()) && pwArea.getText().equals(mem.getPwd())) {
					JOptionPane.showMessageDialog(null, "�α��ο� �����Ͽ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					new MainOrderView(mem);
					return;
				}
				;
			}
			JOptionPane.showMessageDialog(null, "�α��� ����", "", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e2) {
			// TODO: handle exception
		}*/
	}

}
