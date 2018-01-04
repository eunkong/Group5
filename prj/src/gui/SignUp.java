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

	private static JLabel idLb = new JLabel("���̵�");
	private static JLabel pwLb1 = new JLabel("�н�����");
	private static JLabel pwLb2 = new JLabel("�н�����Ȯ��");
	private static JLabel pnumLb = new JLabel("��ȭ��ȣ");
	private static JLabel addressLb = new JLabel("�ּ�");
	
	private static JButton signInNow = new JButton("ȸ������");
	private static JButton cancelBt = new JButton("���");
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

		setTitle("ȸ������");
		setSize(400, 500);
		setLocationRelativeTo(log);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
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
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
		
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
				JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ�� 4~20���ڻ��� ����Ȥ�� ���� ȥ�ո� �����մϴ�.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try (ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(new File("files", "memberlist.db"))));) {

				@SuppressWarnings("unchecked")
				Map<String, Member> map = (Map<String, Member>) in.readObject();

				if(map.containsKey(id)) {
				JOptionPane.showMessageDialog(null, "�̹� ������� ���̵� �Դϴ�.", "", JOptionPane.WARNING_MESSAGE);
				return;
				}
			} catch (Exception err) {
				// TODO: handle exception
			}
			
			if(!check.matcher(pw).find()) {
				JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ�� 4~20���ڻ��� ����Ȥ�� ���� ȥ�ո� �����մϴ�.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(!pw.equals(pwCheck)) {			
				JOptionPane.showMessageDialog(null, "��й�ȣ Ȯ���� �ٽ� �Է��� �ּ���", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(pw.equals(id)) {
				JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ���� �� �����ϴ�.", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(!checkpnum.matcher(pnum).find()) {
				JOptionPane.showMessageDialog(null, "�ڵ�����ȣ�� �ٽ� �Է��� �ּ���(010���� ����, 11�ڸ� ����)", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(address.equals("")||address==null) {
				JOptionPane.showMessageDialog(null, "�ּҸ� �Է��� �ּ���", "", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			/*
			 * ���ο� ȸ�� ������ ������ ������ member.db�� �߰��ϴ� �ڵ�
			 */
			try {
				if(ct.register(id, pw, pnum, address))
					JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ� �Ǿ����ϴ�.", "", JOptionPane.INFORMATION_MESSAGE);
					dispose();
			} catch (Exception e1) {
				System.err.println("����");
				e1.printStackTrace();
				ct=null;
			}
			
			
			
		});
		
	}

	private void menu() {
	}
}