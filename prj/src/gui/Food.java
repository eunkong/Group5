package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class FoodView extends JFrame {
	private JPanel bg = new JPanel(new BorderLayout());

	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	private JPanel jp6 = new JPanel();

	private JButton jbt1 = new JButton("����");
	private JButton jbt2 = new JButton("�Ϸ�");
	private JButton jbt3 = new JButton("����");
	private JButton jbt4 = new JButton("����");
	private JButton jbt5 = new JButton("�Ϸ�");
	private JButton jbt6 = new JButton("����");
	private JButton jbt7 = new JButton("����");
	private JButton jbt8 = new JButton("�Ϸ�");
	private JButton jbt9 = new JButton("����");
	private JButton jbt10 = new JButton("����");
	private JButton jbt11 = new JButton("�Ϸ�");
	private JButton jbt12 = new JButton("����");
	private JButton jbt13 = new JButton("����");
	private JButton jbt14 = new JButton("�Ϸ�");
	private JButton jbt15 = new JButton("����");
	private JButton jbt16 = new JButton("����");
	private JButton jbt17 = new JButton("�Ϸ�");
	private JButton jbt18 = new JButton("����");

	private String columnNames[] = { "�ֹ���ȣ", "�з�", "�޴���", "����" };
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] { { "������", "����", 0, 0 } },
			columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable1 = new JTable(defaultTableModel);
	private JTable jTable2 = new JTable(defaultTableModel);
	private JTable jTable3 = new JTable(defaultTableModel);
	private JTable jTable4 = new JTable(defaultTableModel);
	private JTable jTable5 = new JTable(defaultTableModel);
	private JTable jTable6 = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane1 = new JScrollPane(jTable1);
	private JScrollPane jScollPane2 = new JScrollPane(jTable2);
	private JScrollPane jScollPane3 = new JScrollPane(jTable3);
	private JScrollPane jScollPane4 = new JScrollPane(jTable4);
	private JScrollPane jScollPane5 = new JScrollPane(jTable5);
	private JScrollPane jScollPane6 = new JScrollPane(jTable6);

	private JLabel jlabel = new JLabel("¥��� «�� ¥��� «��");

	public FoodView() {
		design();
		event();
		menu();

		setTitle("????");
		setSize(1200, 700);
		setLocation(200, 100);
		// setLocationByPlatform(true); //��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);

		jTable1.setRowHeight(15); // ���� ����
		jTable1.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable1.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable1.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		jTable2.setRowHeight(15); // ���� ����
		jTable2.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable2.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable2.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		jTable3.setRowHeight(15); // ���� ����
		jTable3.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable3.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable3.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		jTable4.setRowHeight(15); // ���� ����
		jTable4.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable4.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable4.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		jTable5.setRowHeight(15); // ���� ����
		jTable5.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable5.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable5.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		jTable6.setRowHeight(15); // ���� ����
		jTable6.setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
		jTable6.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable6.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�

		// JPane
		jp1.setBounds(40, 100, 300, 225);
		bg.add(jp1);
		jp1.add(jScollPane1);
		jp2.setBounds(440, 100, 300, 225);
		bg.add(jp2);
		jp2.add(jScollPane2);
		jp3.setBounds(840, 100, 300, 225);
		bg.add(jp3);
		jp3.add(jScollPane3);
		jp4.setBounds(40, 390, 300, 225);
		bg.add(jp4);
		jp4.add(jScollPane4);
		jp5.setBounds(440, 390, 300, 225);
		bg.add(jp5);
		jp5.add(jScollPane5);
		jp6.setBounds(840, 390, 300, 225);
		bg.add(jp6);
		jp6.add(jScollPane6);

		// JButton
		jbt1.setBounds(70, 335, 80, 23);
		bg.add(jbt1);
		jbt2.setBounds(150, 335, 80, 23);
		bg.add(jbt2);
		jbt3.setBounds(230, 335, 80, 23);
		bg.add(jbt3);

		jbt4.setBounds(470, 335, 80, 23);
		bg.add(jbt4);
		jbt5.setBounds(550, 335, 80, 23);
		bg.add(jbt5);
		jbt6.setBounds(630, 335, 80, 23);
		bg.add(jbt6);

		jbt7.setBounds(870, 335, 80, 23);
		bg.add(jbt7);
		jbt8.setBounds(950, 335, 80, 23);
		bg.add(jbt8);
		jbt9.setBounds(1030, 335, 80, 23);
		bg.add(jbt9);

		jbt10.setBounds(70, 625, 80, 23);
		bg.add(jbt10);
		jbt11.setBounds(150, 625, 80, 23);
		bg.add(jbt11);
		jbt12.setBounds(230, 625, 80, 23);
		bg.add(jbt12);

		jbt13.setBounds(470, 625, 80, 23);
		bg.add(jbt13);
		jbt14.setBounds(550, 625, 80, 23);
		bg.add(jbt14);
		jbt15.setBounds(630, 625, 80, 23);
		bg.add(jbt15);

		jbt16.setBounds(870, 625, 80, 23);
		bg.add(jbt16);
		jbt17.setBounds(950, 625, 80, 23);
		bg.add(jbt17);
		jbt18.setBounds(1030, 625, 80, 23);
		bg.add(jbt18);

		jlabel.setForeground(Color.RED);
		jlabel.setFont(new Font("����", Font.PLAIN, 40));
		jlabel.setBounds(350, 0, 705, 116);
		bg.add(jlabel);

	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)
		// ����
		jbt1.addActionListener(e -> {
			jbt1.setEnabled(false);
		});
		jbt4.addActionListener(e -> {
			jbt4.setEnabled(false);
		});
		jbt7.addActionListener(e -> {
			jbt7.setEnabled(false);
		});
		jbt10.addActionListener(e -> {
			jbt10.setEnabled(false);
		});
		jbt13.addActionListener(e -> {
			jbt13.setEnabled(false);
		});
		jbt16.addActionListener(e -> {
			jbt16.setEnabled(false);
		});

		// �Ϸ�
		jbt2.addActionListener(e -> {
			jbt1.setEnabled(true);
		});
		jbt5.addActionListener(e -> {
			jbt4.setEnabled(true);
		});
		jbt8.addActionListener(e -> {
			jbt7.setEnabled(true);
		});
		jbt11.addActionListener(e -> {
			jbt10.setEnabled(true);
		});
		jbt14.addActionListener(e -> {
			jbt13.setEnabled(true);
		});
		jbt17.addActionListener(e -> {
			jbt16.setEnabled(true);
		});

		// ����
		jbt3.addActionListener(e -> {
		});
		jbt6.addActionListener(e -> {
		});
		jbt9.addActionListener(e -> {
		});
		jbt12.addActionListener(e -> {
		});
		jbt15.addActionListener(e -> {
		});
		jbt18.addActionListener(e -> {
		});

	}

	private void menu() {
	}
}

public class Food {
	public static void main(String[] args) {
		Frame f = new FoodView();
	}
}
