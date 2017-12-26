package master;

import java.util.*;

import client.Member;
import server.MemberManager;

public class Order {
	static Scanner sc = new Scanner(System.in);
	private static int priceSum;
	private static Map<Menu, Integer> orderIdx = new HashMap<>();
	private static Order instance=new Order();
	
	private Order() {
		// TODO Auto-generated constructor stub
	}
	
	public static void orderMain(Member member) {
		orderIdx.clear();
		printInfo(member);
		MenuSFM.menuLoad();

		priceSum = 0;
		orderIdx.clear();

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
				order(member);
				break;
			case 2:
				editOrder(member);
				break;
			case 3:
				infoEdit(member);
				break;
			case 4:
				printOrder();
				break;
			case 5:
				printInfo(member);
				break;
			case 0:

				return;

			default:
				System.out.println("����");
				break;
			}

		}

	}

	private static void order(Member member) {
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

	private static void editOrder(Member member) {
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
			if(sc.nextInt()==2) return;
		} catch (Exception e) {
			System.err.println("Insert Error");
		}
		editOrder(member);
	}

	private static void infoEdit(Member member) {

		System.out.println("1.���̵�");
		System.out.println("2.��й�ȣ");
		System.out.println("3.���ּ�");
		System.out.println("4.��ȭ��ȣ");
		System.out.print("�ٲ� ����:");
		try {

			switch (sc.nextInt()) {
			case 1:
				editIdPw(member, true);
				break;
			case 2:
				editIdPw(member, false);
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

	private static void printOrder() {

		for (Iterator<Menu> iterator = orderIdx.keySet().iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();
			System.out.println(menu.getName() + "-" + menu.getPrice() + "-" + orderIdx.get(menu) + "��");
		}
		System.out.println("��" + priceSum + "��");
	}

	private static void printInfo(Member member) {
		System.out.println("id:" + member.getId());
		System.out.println("pwd:" + member.getPwd());
		System.out.println("address:" + member.getAddress());
		System.out.println("grade:" + member.getGrade());
	}

	private static void editIdPw(Member member,boolean flag) {
		
		System.out.println("���̵�� �н����带 �ٽ��ѹ� �Է����ּ���");
		System.out.print("���̵�:");
		String id = sc.next();
		System.out.print("�н�����:");
		String pw = sc.next();
		if (!member.getId().equals(id)|| !member.getPwd() .equals(pw)) {
			System.out.println("��й�ȣ Ȥ�� ���̵� �߸��Ǿ����ϴ�");
			return;
		}
		System.out.println("���ο� ���̵�/�н����带 �Է��ϼ���");
		String str=sc.next();
		if(flag) member.setId(str);
		else member.setPwd(str);
	}// infoedit�� ���� �޼ҵ�
	public static Order getInstance() {
		return instance;
	}
}
