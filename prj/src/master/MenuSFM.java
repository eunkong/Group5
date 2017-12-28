package master;

import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * @author Team5
 * @see ForguiShow, MenuBoard
 * @version 2.2
 */

@SuppressWarnings("serial")
public class MenuSFM implements Serializable {// �޴�����
	
	private static final String NOODLE = "���";
	private static final String RICE = "���";
	private static final String DRINK = "����";// �޴� �з��� ���� ��� ex) ¥���, «��-��� ������, ������-���
	
	public static final String[] WARNING= {"�߸��� �����Դϴ�","���� �з� �Դϴ�","�̹� �ִ� �з� �Դϴ�"
			,"���� �޴� �Դϴ�","�̹� �ִ� �޴� �Դϴ�."};//���޼���
	
	private static final File MENUFILE = new File("files", "menus.db");
	
	private static Map<String, Set<Menu>> menus = new HashMap<>();// �з����� �޴� ����

	public MenuSFM() {
		basicMenu();// �⺻���� �޴��� ������ �޼ҵ�(���� ������ �� ����)
		menuWrite();
	} // ��� �޼ҵ带 staticȭ ��Ű�鼭 ��ǻ� �ʿ� X

	private static void basicMenu() {// �⺻������ �����ϴ� �޴�

		menus.put(NOODLE, new HashSet<>());
		menus.get(NOODLE).add(new Menu("¥���", 4000));
		menus.get(NOODLE).add(new Menu("«��", 5000));

		menus.put(RICE, new HashSet<>());
		menus.get(RICE).add(new Menu("������", 5500));
		menus.get(RICE).add(new Menu("��ġ����", 1000));

		menus.put(DRINK, new HashSet<>());
		menus.get(DRINK).add(new Menu("�ݶ�", 1000));
		menus.get(DRINK).add(new Menu("���̴�", 1000));

	}

	
	/**
	 * �޴��߰� , �̸��� ���� �޴��� �ְų� ���� �з��� �߰� �Ұ���
	 * @param group
	 * @param menu
	 * {@code}
	 */
	public static void addMenu(String group, Menu menu) {

		if (!getGroupString().contains(group)) { // �����ϴ� �з����� �˻�
			System.out.println(WARNING[4]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
			}
		if (getMenu(menu.getName()) != null) { // getMenu �޼ҵ�� ���� �̸��� �޴��� �ִ��� �˻�
			System.out.println(MenuSFM.WARNING[1]);
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
		}
		menus.get(group).add(menu);// ���� �̸��� �޴��� ������� �߰�

		System.out.println("�߰��Ϸ�");
		JOptionPane.showMessageDialog(null, "�߰��Ϸ�");// �Ϸ� �޼���
	}

	/**
	 * �з� �߰�,�̹� �ִ� �з��� �߰� �Ұ���  ex)�ַ�Ȥ�� �丮�� �߰�
	 * @param String , Menu
	 */
	
	public static void addMenu(String group) {

		if (getGroupString().contains(group)) {
			System.out.println(MenuSFM.WARNING[1]);
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;// �̹� �ִ� �з��� �߰� �Ұ�
		}
		menus.put(group, new HashSet<Menu>());
		System.out.println("�߰��Ϸ�");
		JOptionPane.showMessageDialog(null, "�߰��Ϸ�");// �Ϸ� �޼���
	}
	
	/**
	 * �޴� ����,�̹� �ִ� �з��� �߰� �Ұ���  ex)����� ¥��� ����
	 * @param group
	 * @param name
	 */
	
	public static void removeMenu(String group, String name) { // �޴����� ex) ����� ¥��� ����

		if (!getGroupString().contains(group)) {
			System.out.println(WARNING[1]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Set<Menu> temp = getMenuGroup(group);

		for (Iterator<Menu> ite = temp.iterator(); ite.hasNext();) {// �ش� �޴��� �̸��� �����ϴ��� �˻� & �����ϸ� ������ ����
			Menu menu = ite.next();
			if (name.equals(menu.getName())) {

				temp.remove(menu);
				menus.put(group, temp);// menus.get(group).remove(menu);�� ������
				System.out.println("�����Ϸ�");
				JOptionPane.showMessageDialog(null, "�����Ϸ�");// �Ϸ� �޼���
				return;
			}
		}

		System.out.println(WARNING[3]);
		JOptionPane.showMessageDialog(null, WARNING[3], "", JOptionPane.ERROR_MESSAGE);
	}

	public static void removeMenu(String group) {// �з� ���� ex) ��� �з��� ����

		if (!getGroupString().contains(group)) {
			System.out.println(WARNING[1]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
		} else {
			menus.remove(group);
			System.out.println("�����Ϸ�");
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
		}
	}

	public static void menuReset() {// �⺻���� �޴� �ܿ� ����
		menus.clear();
		basicMenu();
	}

	public static void menuPrintConsole() {// �ܼ�â�� �޴� ���

		for (Iterator<String> ite = (new TreeMap<>(menus)).keySet().iterator(); ite.hasNext();) {

			String group = ite.next();// key��(=�з���) ����
			System.out.println("-----" + group + "-----");

			for (Iterator<String> iter = getMenuName(group).iterator(); iter.hasNext();) {
				String temp = iter.next();
				System.out.println(temp + "-" + getMenu(group, temp).getPrice() + "��");
			}

			/*
			   for (Iterator<Menu> iter = getMenuGroup(group).iterator(); iter.hasNext();) {
			   Menu temp = iter.next(); System.out.println(temp.getName() + "-" +
			   temp.getPrice() + "��"); }
			 */
			System.out.println();
		}

	}// �߰��� TreeMap, TreeSet �� �����ټ� ����� ���� ���� ���Դϴ�.(���� �������� ���� ���� ����)

	// GUI��
	public static String menuPrintJLabel(String group) {// JLabel�� �޴� ��¿�(�Ƹ� ������ ���� �� �ϴ� �����ص� ����.)
		
		StringBuffer buffer = new StringBuffer("<html>");
		buffer.append("<br>-------" + group + "-------");
			for (Iterator<String> iter = getMenuName(group).iterator(); iter.hasNext();) {
				String temp = iter.next();
				buffer.append("<br>" + temp + "-" + getMenu(temp).getPrice() + "��");
			}

		buffer.append("</html>");
		
		return buffer.toString();

	}

	// �Ʒ��� getter
	public static Menu getMenu(String name) {// Ư�� �޴��� ������
		Menu remenu = null;
		for (Iterator<String> ite = getGroupString().iterator(); ite.hasNext();) {

			HashSet<Menu> menu = (HashSet<Menu>) getMenuGroup(ite.next());

			for (Iterator<Menu> iter = menu.iterator(); iter.hasNext();) {
				Menu temp = iter.next();
				if (temp.getName().equals(name)) {
					remenu = temp;
					break;
				}
			}
		}
		return remenu;// ������ null ��ȯ
	
	}

	
	public static Menu getMenu(String group, String name) {// Ư�� �޴��� ������
		if (!getGroupString().contains(group))
			return null;

		for (Menu menu : getMenuGroup(group)) {
			if (menu.getName().equals(name))
				return menu;
		}

		return null;// ������ null ��ȯ
	}

	public static JButton[] getButton() {
		ArrayList<JButton> temp = new ArrayList<>();
		Set<String> stemp = menus.keySet();
		for (String string : stemp) {
			temp.add(new JButton(string));
		}
		return temp.toArray(new JButton[temp.size()]);
	}

	
	public static JButton[] getButton(String group) {
		ArrayList<JButton> temp = new ArrayList<>();
		Set<Menu> stemp = menus.get(group);
		for (Menu menu : stemp) {
			temp.add(new JButton(menu.getName() + "-" + menu.getPrice() + "��"));
		}
		return temp.toArray(new JButton[temp.size()]);
	}// ���� �մԿ� ���̾ƿ��� ���� ��

	
	
	
	@SuppressWarnings("unchecked")
	public static void menuLoad() {// files�� �ִ� menus��ü�� �о���� �޼ҵ�
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(MENUFILE)));// ��ü ��� ��
			menus = (HashMap<String, Set<Menu>>) in.readObject();// return Ÿ���� Object �̹Ƿ� casting �Ѱ�.(�ȱ׷��� ������)
			in.close();// ��� ����
		} catch (Exception e) {
			System.out.println("������ �����ϴ�");
			menuWrite();// ������ ������� ���� ������ �����ϴ� ���� ����
		}

	}

	public static void menuWrite() {// files�� menus�� ��üȭ �Ͽ� �����ϴ� �޼ҵ�

		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(MENUFILE)));// ��ü��� ��
			out.writeObject(menus);// ��ü�߰�(����)

			out.flush();// �÷���
			out.close();// ��δ���
		} catch (Exception e) {
			System.out.println("�������");
		}

	}

	
	public static Set<String> getMenuName(String group) {// �з��鿡 �ش��ϴ� �޴����� �̸��� ������
		Set<String> names = new HashSet<>();
		for (Menu menu : getMenuGroup(group))
			names.add(menu.getName());
		return new TreeSet<String>(names);
	}
	
	public static Set<String> getGroupString() {// �з����� set���� ������

		return new TreeSet<String>(menus.keySet());
	}

	public static Set<Menu> getMenuGroup(String group) {// �� �з��� ��� �޴��� set���� ������

		if (!getGroupString().contains(group))
			return null;// ������ null ��ȯ

		return menus.get(group);
	}

	public static Map<String, Set<Menu>> getMenus() {// menus�� ��°�� ������
		return menus;
	}

}
