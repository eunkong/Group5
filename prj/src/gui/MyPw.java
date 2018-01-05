/**
 * 
 */
package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.swing.*;

import client.Member;

/**
 * 
 * prj
 * 
 * @param
 */
public class MyPw extends JFrame {

	private JPanel bg = new JPanel(new BorderLayout());

	private JLabel title = new JLabel("��й�ȣ ã��");
	private JLabel idText = new JLabel("Id");
	private JLabel pnumText = new JLabel("Phonenumber");

	private JTextField idArea = new JTextField();
	private JTextField pnumArea = new JTextField();

	private JButton searchButton = new JButton("search");

	public MyPw() {
		design();
		event();
		menu();
		setTitle("���̵�/��й�ȣ ã��");
		setSize(300, 300);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// ��� ��ġ
		bg.setLayout(null);

		title.setFont(new Font("����", Font.PLAIN, 20));

		title.setBounds(72, 10, 133, 47);
		bg.add(title);

		idText.setBounds(70, 106, 87, 29);
		bg.add(idText);

		pnumText.setBounds(12, 135, 87, 29);
		bg.add(pnumText);

		idArea.setBounds(110, 110, 130, 21);
		bg.add(idArea);
		idArea.setColumns(10);

		pnumArea.setBounds(110, 140, 130, 21);
		bg.add(pnumArea);
		pnumArea.setColumns(10);

		searchButton.setBounds(170, 170, 80, 23);
		bg.add(searchButton);

	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// ActionListener act = e -> {
		// searchMethod();
		// };
		//
		// searchButton.addActionListener(act);
		//
		// KeyAdapter actKey = new KeyAdapter() {
		// public void keyPressed(KeyEvent e) {
		// if (e.getKeyCode() == KeyEvent.VK_ENTER)
		// searchMethod();
		// if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		// dispose();
		// }
		// };
		// idArea.addKeyListener(actKey);
		// pnumArea.addKeyListener(actKey);
	}

	private void menu() {
	}
	/*���̵� ��й�ȣ ã��
	 * private void searchMethod() { String pw = ""; try
	 * (ObjectInputStream in = new ObjectInputStream( new BufferedInputStream(new
	 * FileInputStream(new File("files", "memberlist.db"))));) { Map<String, Member>
	 * map = (Map<String, Member>) in.readObject(); Member mem; try { String id =
	 * idArea.getText(); mem = map.get(id); if
	 * (!(mem.getPhoneNumber()).equals(pnumArea.getText())) {
	 * JOptionPane.showMessageDialog(null, "�߸��� �ڵ��� ��ȣ �Դϴ�.", "",
	 * JOptionPane.WARNING_MESSAGE); return; } pw = mem.getPwd(); } catch (Exception
	 * e2) { JOptionPane.showMessageDialog(null, "�߸��� ���̵� �Դϴ�", "",
	 * JOptionPane.WARNING_MESSAGE); return; } } catch (Exception err) { }
	 * StringBuffer pwd = new StringBuffer(pw.substring(0, (1 + pw.length()) / 2));
	 * while (pw.length() - pwd.length() > 0) { pwd.append("*");
	 * 
	 * } JOptionPane.showMessageDialog(null, "ȸ������ ���̵�� ��ȭ��ȣ�� �˻��� ��й�ȣ�� " +
	 * pwd.toString() + "�Դϴ�."); dispose(); }
	 */
}
