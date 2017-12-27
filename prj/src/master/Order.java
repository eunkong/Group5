package master;

import java.io.Serializable;
import java.util.*;

import client.Member;
import server.MemberManager;

@SuppressWarnings("serial")
public class Order implements Serializable {
	static Scanner sc = new Scanner(System.in);
	public Calendar cal = Calendar.getInstance();;
	private int priceSum;
	private Map<Menu, Integer> orderIdx = new HashMap<>();
	
	private String ordernum=(cal.get(Calendar.YEAR)-2000)+""+(1+cal.get(Calendar.MONTH))+""+cal.get(Calendar.DAY_OF_MONTH);
	int idxnum;

	Member member;

	public Order(Member member) {
		this.member = member;
	
		
	}

	

	public void orderMain() {

		MenuSFM.menuLoad();

		priceSum = 0;

		while (true) {
			System.out.println();
			System.out.println("1.�ֹ��ϱ�");
			System.out.println("2.�ֹ�����");
			System.out.println("3.��������");
			System.out.println("4.�ֹ�����");
			System.out.println("5.��������");
			System.out.println("0.����");
			int choice = 0;
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				System.err.println("Insert Error");
				return;
			}
			switch (choice) {
			case 1:
				order();
				break;
			case 2:
				editOrder();
				break;
			case 3:
				infoEdit();
				break;
			case 4:
				printOrder();
				break;
			case 5:
				member.printInfo();
				break;
			case 0:

				return;

			default:
				System.out.println("����");
				break;
			}

		}

	}

	private void order() {
		System.out.println("=====�޴��з�=====");

		Set<String> groups = MenuSFM.getGroupString();

		int num = 0;

		for (String string : groups) {
			System.out.println(string);
		}
		System.out.print("�з�����:");

		String group = sc.next();

		if (MenuSFM.getMenuGroup(group) == null) {
			System.err.println("�߸��� ���� �Դϴ�");
			return;
		}

		for (Menu mn : MenuSFM.getMenuGroup(group)) {
			System.out.println(mn.getName() + "-" + mn.getPrice() + "��");
		}

		System.out.print("�޴�����:");
		String name = sc.next();
		Menu menu = MenuSFM.getMenu(group, name);
		if (menu == null) {
			System.err.println("�߸��� ���� �Դϴ�");
			return;
		}

		System.out.print("��������:");
		try {
			num = sc.nextInt();
		} catch (Exception e) {
			System.err.println("�߸��� ���� �Դϴ�");
			return;
		}
		System.out.println("���� �ֹ� �Ͻðڽ��ϱ�?");
		System.out.println("1.��  2.�ƴϿ�");
		if (sc.nextInt() != 1)
			return;

		member.setOrderCount(member.getOrderCount() + 1);
		priceSum += menu.getPrice() * num;
		orderIdx.put(menu, num);
		System.out.println("�ֹ� �Ϸ�");
	}

	private void editOrder() {
		if (orderIdx.size() == 0) {
			System.out.println("�ֹ������� �����ϴ�");
			return;
		}
		System.out.println("1. ��� �ֹ� ����   2.�ֹ��ϳ��� ���� 3.�ֹ����� ����");
		int choice = 0;
		try {
			choice = sc.nextInt();
			if (choice == 1) {
				orderIdx.clear();
				System.out.println("�����Ϸ�");
				return;
			}
		} catch (Exception e) {
			System.err.println("insert error");
			return;
		}

		printOrder();
		System.out.println("����ֹ��� ����/�����Ͻð� ���ϱ�?");
		String name = sc.next();
		Menu menu = MenuSFM.getMenu(name);
		if (!orderIdx.keySet().contains(MenuSFM.getMenu(name))) {
			System.out.println("�ֹ����� ���� �޴� �Դϴ�");
			return;
		}
		priceSum -= orderIdx.get(menu) * menu.getPrice();
		if (choice == 2) {
			orderIdx.remove(menu);
			System.out.println("���� �Ϸ�");
		}

		else if (choice == 3) {

			System.out.println("��� �����Ͻð� ���ϱ�?");
			int num = 0;
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("error");
				return;
			}
			orderIdx.put(menu, num);
			priceSum += menu.getPrice() * num;
			System.out.println("�����Ϸ�");
		}
		System.out.println("��� ���� �Ͻðڽ��ϱ�?\n 1. ��       2. �ƴϿ�");
		try {
			if (sc.nextInt() == 2)
				return;
		} catch (Exception e) {
			System.err.println("Insert Error");
		}
		editOrder();
	}

	private void infoEdit() {

		System.out.println("1.���̵�");
		System.out.println("2.��й�ȣ");
		System.out.println("3.���ּ�");
		System.out.println("4.��ȭ��ȣ");
		System.out.print("�ٲ� ����:");
		try {

			switch (sc.nextInt()) {
			case 1:
				editIdPw(true);
				break;
			case 2:
				editIdPw(false);
				member.setPhoneNumber(sc.next());
				break;
			case 3:
				System.out.print("�ٲ� �ּ�:");
				member.setAddress(sc.next());
				break;
			case 4:
				System.out.print("�ٲ� ��ȣ:");
				member.setPhoneNumber(sc.next());
				break;

			}
		} catch (Exception e) {
			System.err.println("error");
			return;
		}
	}

	private void printOrder() {

		for (Iterator<Menu> iterator = orderIdx.keySet().iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();
			System.out.println(menu.getName() + "-" + menu.getPrice() + "-" + orderIdx.get(menu) + "��");
		}
		System.out.println("��" + priceSum + "��");
	}

	private void editIdPw(boolean flag) {

		System.out.println("���̵�� �н����带 �ٽ��ѹ� �Է����ּ���");
		System.out.print("���̵�:");
		String id = sc.next();
		System.out.print("�н�����:");
		String pw = sc.next();
		if (!member.getId().equals(id) || !member.getPwd().equals(pw)) {
			System.out.println("��й�ȣ Ȥ�� ���̵� �߸��Ǿ����ϴ�");
			return;
		}
		System.out.println("���ο� ���̵�/�н����带 �Է��ϼ���");
		String str = sc.next();
		if (flag)
			member.setId(str);
		else
			member.setPwd(str);
	}// infoedit�� ���� �޼ҵ�

	public int getPriceSum() {
		return priceSum;
	}

	public Map<Menu, Integer> getOrderIdx() {
		return orderIdx;
	}

	public static Scanner getSc() {
		return sc;
	}

	public Member getMember() {
		return member;
	}


	public void addOrdernum(int num) {
		ordernum+=num+"";
	}
	
	public void addOrdernum(String str) {
		ordernum+=str;
	}
	
}
