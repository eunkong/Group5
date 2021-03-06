package gui;
 
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

import client.ClientTool;
import client.Member;

public class MyInfo extends JDialog {
	private JPanel bg = new JPanel(new BorderLayout());

	private JLabel tfId = new JLabel();
	private JPasswordField tfPw = new JPasswordField();
	private JTextField tfPhone = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JLabel tfPoint = new JLabel();
	private JLabel tfGrade = new JLabel();

	private JLabel jlId = new JLabel("아이디");
	private JLabel jlPw = new JLabel("비밀번호");
	private JLabel jlPhone = new JLabel("전화번호");
	private JLabel jlAddress = new JLabel("주소");
	private JLabel jlPoint = new JLabel("포인트");
	private JLabel jlGrade = new JLabel("등급");

	private JButton btEdit = new JButton("수정");
	private JButton btCancel = new JButton("취소");

	private static Member member;

	public MyInfo(Frame owner, boolean modal) {
		super(owner, modal);
		design();
		event();
		menu();

		setTitle("내정보");
		setSize(400, 500);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // X키 비활성화
	}

	private void design() {
		setContentPane(bg);// 배경 설치
		bg.setLayout(null);

		tfId.setBounds(128, 40, 170, 20);
		bg.add(tfId, BorderLayout.NORTH);

		tfPw.setBounds(128, 90, 170, 20);
		bg.add(tfPw);
		tfPhone.setBounds(128, 140, 170, 20);
		bg.add(tfPhone);
		tfAddress.setBounds(128, 190, 170, 20);
		bg.add(tfAddress);
		tfPoint.setBounds(128, 240, 220, 20);
		bg.add(tfPoint, BorderLayout.CENTER);
		tfGrade.setBounds(128, 290, 220, 20);
		bg.add(tfGrade, BorderLayout.CENTER);

		jlId.setBounds(27, 40, 57, 15);
		bg.add(jlId);
		jlPw.setBounds(27, 90, 57, 15);
		bg.add(jlPw);
		jlPhone.setBounds(27, 140, 57, 15);
		bg.add(jlPhone);
		jlAddress.setBounds(27, 190, 57, 15);
		bg.add(jlAddress);
		jlPoint.setBounds(27, 240, 57, 15);
		bg.add(jlPoint);
		jlGrade.setBounds(27, 290, 57, 15);
		bg.add(jlGrade);

		btEdit.setBounds(70, 370, 113, 30);
		bg.add(btEdit);
		btCancel.setBounds(220, 370, 113, 30);
		bg.add(btCancel);
	}

	private void event() {
		btCancel.addActionListener(e-> {
			//btCalcel이 static으로 선언될경우 Action이 중복으로 쌓이게 된다
			System.out.println("취소 버튼 클릭");
			System.out.println(btCancel.getActionListeners().length);
			try {
				ClientTool.getTool().myinfoClose();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dispose();
		});
		btEdit.addActionListener(e -> {
			try {
				boolean result = ClientTool.getTool().myinfoEdit(tfPw.getText(), tfPhone.getText(),
						tfAddress.getText());
				if (result) {
					JOptionPane.showMessageDialog(null, "회원정보가 수정되었습니다!", "", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "회원정보가 수정되지 않았습니다", "", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		});
	}

	private void menu() {
	}

	public void setMember(Member member) {
		MyInfo.member = member;
		tfId.setText(member.getId());
		tfPw.setText(member.getPwd());
		tfPhone.setText(member.getPhoneNumber());
		tfAddress.setText(member.getAddress());
		tfGrade.setText(member.getGrade());
		tfPoint.setText(member.getPoint() + "");
	}

}
