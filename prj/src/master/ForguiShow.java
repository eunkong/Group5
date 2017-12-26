package master;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class ForguiShow extends JFrame {

	private JPanel bg = new JPanel(new BorderLayout());
	private JToolBar tb = new JToolBar();
	private JLabel lb = new JLabel();

	private static final String[] USING = { "�޴�����", "�޴��߰�", "�޴�����", "�з��߰�", "�з�����", "�޴�����", "����" };// ��ư �̸��� Array�� ����

	private JButton[] bts = new JButton[USING.length];// button�� ��Ƴ��� Array USING�� �翬�� length�� ����

	public ForguiShow() {// ������� �⺻ Ʋ��� ���� JFrame ��� Ŭ����

		mains();
		event();
		menu();

		setTitle("�޴�����");
		setSize(500, 400);
		// setLocation(100, 100);
		setLocationByPlatform(true); // ��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
	}

	private void mains() {

		lb.setHorizontalAlignment(SwingConstants.CENTER);
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�

		bg.add(tb, BorderLayout.CENTER);// JToolBar�� �߾ӿ� ��ġ

		tb.setLayout(new GridLayout(USING.length, 1));
		for (int i = 0; i < USING.length; i++) {
			bts[i] = new JButton(USING[i]);
			tb.add(bts[i]);// for�����ι�ư �߰�
		}
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�

	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x Ű ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)

		ActionListener act = e -> {
			action(e.getActionCommand());
			if (e.getActionCommand().equals("����")) {

				switch (JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?")) // JOption���� �� �ƴϿ� ��� ���θ� ����
				{
				case JOptionPane.YES_OPTION:// ���� �������
					MenuSFM.menuWrite();// ��ü�� ���� Yes�� No�� �Ѵ� �����ϹǷ� break;�� return; �ʿ����
				case JOptionPane.NO_OPTION:
					System.exit(0);// JFrame����
					return;// �ܼ�â ����
				// ��Ҹ� ������� �ƹ��� �̺�Ʈ�� �ʿ�����Ƿ� ���� �������� ����
				}

			}

		};

		for (int i = 0; i < USING.length; i++) {
			bts[i].addActionListener(act);// JButton �迭�� action�ο�
		}

	}

	private void menu() {

	}

	private void action(String key) {// event�� ���� �޼ҵ� (������������ ���� Ŭ������ �־���)

		String name;
		String group;
		System.out.println("======" + key + "======\n");
		int price = 0;
		switch (key) {
		case "�޴�����":
			MenuSFM.menuPrintConsole();// �ܼ�â���� �޴���� Ȯ�ΰ���
			new MenuBoard();// ��â���� �޴� ������
			break;

		case "�޴��߰�":
			menuEdit(true);

			break;
		case "�޴�����":
			menuEdit(false);
			break;

		case "�з��߰�":
			groupEdit(true);

			break;

		case "�з�����":
			groupEdit(false);

			break;

		case "�޴�����":
			int choice = JOptionPane.showConfirmDialog(null, "���� ���� �Ͻðڽ��ϱ�?", "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null);
			if (choice != JOptionPane.OK_OPTION)
				break;

			MenuSFM.menuReset();
			System.out.println("���¿Ϸ�");
			JOptionPane.showMessageDialog(null, "���¿Ϸ�");

			break;

		case "����":
			// ��ü�� �����ϴ°� event���� ó��
			break;

		default:
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[0], "", JOptionPane.ERROR_MESSAGE);
			System.out.println(MenuSFM.WARNING[0]);
			break;
		}
	}

	private static void menuEdit(boolean flag) {// �޴� �߰�/������  sysout�� ���� �ܼ�Ȯ�ο�
		String group = "";//�޴� �з�
		String name = "";//�޴� �̸�
		
		System.out.print("�޴��з�:");
		
		group = JOptionPane.showInputDialog("�з� �Է�");// JOptionPane�� �Է� ����

		if (group == null || group.equals("")) {// ��ҹ�ư�� �����ų� �ƹ��͵� �����ʰ� Ȯ���� ������ �ڵ� ���
			System.out.println("���");
			return;
		}

		if (!MenuSFM.getGroupString().contains(group)) {// �޴� ��Ͽ� �ִ� �з����� �˻��� ������ ���â���
			System.out.println(MenuSFM.WARNING[1]);
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
		}

		System.out.println(group);

		System.out.print("�޴��̸�:");

		name = JOptionPane.showInputDialog("�޴��̸�");
		System.out.println(name);

		if (name == null || name.equals("")) {// ��ҹ�ư�� �����ų� �ƹ��͵� �����ʰ� Ȯ���� ������ �ڵ� ���
			System.out.println("���");
			return;
		}

		if (flag) {//flag�� true�� �޴��߰�
			//�ٷ� MenuSFM.addMenu�� ���� ��� ������ �׷��� �Է��� ������ ���� ��������. �Է��� ���ϰ� ������ �� �� ����. ���� ���Ǽ��� ���� �Ʒ�ó�� ó��
			int price = 0;//����
			if (MenuSFM.getMenu(name) != null) {// �̹� �ִ� �޴����� �˻��� �ָ� ���â���
				System.out.println(MenuSFM.WARNING[4]);
				JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
				return;//�̹� �ִ� �޴��� ����
			}

			System.out.print("����:");

			String str = JOptionPane.showInputDialog("����");
			if (str == null || str.equals("")) {// ��ҹ�ư�� �����ų� �ƹ��͵� �����ʰ� Ȯ���� ������ �ڵ� ���
				System.out.println("���");
				return;//�Ȱ��� �Է��Ѱ� ������ �ڵ����
			}

			try {
				price = Integer.parseInt(str);//���� �̿��� ���� �����޼��� ����� ���
			} catch (Exception e) {
				System.out.println(MenuSFM.WARNING[0]);
				JOptionPane.showMessageDialog(null, "ERROR", "", JOptionPane.ERROR_MESSAGE);
				return;
			}

			System.out.println(price);
			MenuSFM.addMenu(group, new Menu(name, price));
		} else {
			MenuSFM.removeMenu(group, name);//MenuSFM.removeMenu���� ���� ó��
		}

	}

	private static void groupEdit(boolean flag) {// �׷� �߰�/������
		String group = "";
		System.out.print("�з���:");
		group = JOptionPane.showInputDialog("�޴� �з�");

		if (group == null || group.equals("")) {// ������ �ּ��� ����
			System.out.println("���");
			return;
		}
		System.out.println(group);
		if (flag)
			MenuSFM.addMenu(group);//true�� �߰�
		else
			MenuSFM.removeMenu(group);//false�� ����
	}
}
