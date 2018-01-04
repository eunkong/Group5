package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ClientTool;
import client.Member;

class SignUp extends JDialog {
	private JPanel bg = new JPanel(new BorderLayout());

	private static JTextField idTf = new JTextField();
	private static JTextField pwTf1 = new JPasswordField();
	private static JTextField pwTf2 = new JPasswordField();
	private static JTextField pnumTf = new JTextField();
	private static JTextField addressTf = new JTextField();

	private static JLabel idLb = new JLabel("아이디");
	private static JLabel pwLb1 = new JLabel("패스워드");
	private static JLabel pwLb2 = new JLabel("패스워드확인");
	private static JLabel pnumLb = new JLabel("전화번호");
	private static JLabel addressLb = new JLabel("주소");
	
	private static JButton signInNow = new JButton("회원가입");
	private static JButton cancelBt = new JButton("취소");
	public static ClientTool ct;
	static {	
		try {
		ct = new ClientTool();
		ct.setClientTool();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
	}

	public SignUp(Frame log, boolean modal) {
		super(log, modal);
		design();
		event();
		menu();

		setTitle("회원가입");
		setSize(400, 500);
		setLocationRelativeTo(log);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);

		idTf.setBounds(128, 35, 170, 35);
		bg.add(idTf);
		pwTf1.setBounds(128, 100, 170, 35);
		bg.add(pwTf1);
		pwTf2.setBounds(128, 165, 170, 35);
		bg.add(pwTf2);
		pnumTf.setBounds(128, 230, 170, 35);
		bg.add(pnumTf);
		addressTf.setBounds(128, 295, 220, 35);
		bg.add(addressTf);

		idLb.setBounds(27, 45, 57, 15);
		bg.add(idLb);
		pwLb1.setBounds(27, 110, 57, 15);
		bg.add(pwLb1);
		pwLb2.setBounds(27, 175, 57, 15);
		bg.add(pwLb2);
		pnumLb.setBounds(27, 240, 57, 15);
		bg.add(pnumLb);
		addressLb.setBounds(27, 305, 57, 15);
		bg.add(addressLb);
		
		signInNow.setBounds(70, 370, 113, 35);
		bg.add(signInNow);
		
		cancelBt.setBounds(220, 370, 113, 35);
		bg.add(cancelBt);

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
		
		cancelBt.addActionListener(e->{
			dispose();
		});
		signInNow.addActionListener(e->{
			String id=idTf.getText();
			String pw=pwTf1.getText();
			String pwCheck=pwTf2.getText();
			String address=addressTf.getText();
			String pnum=pnumTf.getText();
			
			
			Pattern check=Pattern.compile("^[0-9|a-z|A-Z]{4,20}$");
			Pattern checkpnum=Pattern.compile("^010[0-9]{8}$");
			
			if(!check.matcher(id).find()) {
				JOptionPane.showMessageDialog(null, "아이디/비밀번호는 4~20글자사이 영문혹은 숫자 혼합만 가능합니다.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try (ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(new File("files", "memberlist.db"))));) {

				@SuppressWarnings("unchecked")
				Map<String, Member> map = (Map<String, Member>) in.readObject();

				if(map.containsKey(id)) {
				JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.", "", JOptionPane.WARNING_MESSAGE);
				return;
				}
			} catch (Exception err) {
				// TODO: handle exception
			}
			
			if(!check.matcher(pw).find()) {
				JOptionPane.showMessageDialog(null, "아이디/비밀번호는 4~20글자사이 영문혹은 숫자 혼합만 가능합니다.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(!pw.equals(pwCheck)) {			
				JOptionPane.showMessageDialog(null, "비밀번호 확인을 다시 입력해 주세요", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(pw.equals(id)) {
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호는 같을 수 없습니다.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(!checkpnum.matcher(pnum).find()) {
				JOptionPane.showMessageDialog(null, "핸드폰번호를 다시 입력해 주세요(010으로 시작, 11자리 숫자)", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(address.equals("")||address==null) {
				JOptionPane.showMessageDialog(null, "주소를 입력해 주세요", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			/*
			 * 새로운 회원 정보를 서버에 보낸후 member.db에 추가하는 코드
			 */
			try {
				if(ct.register(id, pw, pnum, address))
					JOptionPane.showMessageDialog(null, "회원가입이 완료 되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
					dispose();
			} catch (Exception e1) {
				System.err.println("예외");
				e1.printStackTrace();
				ct=null;
			}
			
			
			
		});
		
	}

	private void menu() {
	}
}