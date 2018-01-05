package cook;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Window01 extends JFrame {
	private JPanel bg = new JPanel();
	private JPanel menuPanel = new JPanel();

	private JLabel lbCook = new JLabel("<�丮��>");
	private JLabel lb[] = new JLabel[5];
	private String lbString[] = new String[] { "�ֹ� ����", "�ֹ� ��ȣ", "�ֹ� �ð�", "�� ���̵�", "�ֹ� �޴�" };
	private JLabel lbOrderState = new JLabel();
	private JLabel lbOrderNum = new JLabel();
	private JLabel lbOrderTime = new JLabel();
	private JLabel lbId = new JLabel();

	private JButton btCookStart = new JButton("�丮 ����");
	private JButton btCookFinish = new JButton("�丮 �Ϸ�");
	private JButton btBack = new JButton("����Ƥ���");
	
	private String columnNames[] = { "�з�", "�޴���", "����","����(��)" };
	// DefaultTableModel�� �����ϰ� ������ ���
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	// JTable�� DefaultTableModel�� ���
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane�� JTable�� ���
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
		design();
		event();
		menu();

		setTitle("");
		setSize(660, 800);
		setLocation(450, 50);
		//setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);
		menuPanel.setBounds(150, 350, 450, 280);
		bg.add(menuPanel);

		lbCook.setFont(new Font("����", Font.PLAIN, 40));
		lbCook.setBounds(209, 10, 205, 84);
		bg.add(lbCook);

		for (int i = 0; i < lbString.length; i++) {
			lb[i] = new JLabel(lbString[i]);
			lb[i].setText(lbString[i]);
			lb[i].setBounds(50, 100 + i * 60, 80, 30);
			bg.add(lb[i]);
		}

		lbOrderState.setBounds(209, 119, 57, 15);
		bg.add(lbOrderState);

		lbOrderNum.setBounds(209, 162, 57, 15);
		bg.add(lbOrderNum);

		lbOrderTime.setBounds(209, 208, 57, 15);
		bg.add(lbOrderTime);

		lbId.setBounds(209, 255, 57, 15);
		bg.add(lbId);

		btCookStart.setBounds(70, 670, 110, 55);
		bg.add(btCookStart);

		btCookFinish.setBounds(270, 670, 110, 55);
		bg.add(btCookFinish);

		btBack.setBounds(470, 670, 110, 55);
		bg.add(btBack);
		

		menuPanel.add(jScollPane);
		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //��
		jTable.setRowHeight(20); // ���� ����
		jTable.setPreferredScrollableViewportSize(new Dimension(370, 250)); // ������?
		jTable.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		jTable.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		
		
		

	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
	}

	private void menu() {

	}
}

public class CookGui {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}
