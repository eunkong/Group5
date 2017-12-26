package master;

import java.util.*;

import client.Member;

public class Order {
	static Scanner sc = new Scanner(System.in);
	private static int priceSum;
	private static Map<Menu, Integer> orderIdx = new HashMap<>();

	public static void orderMain(Member member) {

		printInfo(member);
		MenuSFM.menuLoad();

		priceSum = 0;
		orderIdx.clear();

		while (true) {
			System.out.println("1.�ֹ��ϱ�");
			System.out.println("2.�ֹ�����");
			System.out.println("3.��������");
			System.out.println("4.�ֹ�����");
			System.out.println("5.��������");
			System.out.println("0.����");
			switch (sc.nextInt()) {
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
				break;
			}
		}

	}

	public static void order(Member member) {
		System.out.println("=====�޴��з�=====");

		Set<String> groups = MenuSFM.getGroupString();
		Menu menu;
		int num = 0;

		for (String string : groups) {
			System.out.println(string);
		}
		System.out.print("�з�����:");

		String group = sc.next();
		
		try {
			MenuSFM.getMenuGroup(group);
		} catch (Exception e) {
			System.err.println("�߸��� ���� �Դϴ�");
			return;
		}

		for (Menu mn : MenuSFM.getMenuGroup(group)) {
			System.out.println(mn.getName() + "-" + mn.getPrice() + "��");
		}

		System.out.print("�޴�����:");
		String name = sc.next();
		try {
			menu = MenuSFM.getMenu(group, name);
		} catch (Exception e) {
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

	public static void editOrder(Member member) {
		System.out.println("1. ��� �ֹ� ����   2.�ֹ��ϳ��� ���� 3.�ֹ����� ����");
		int choice=0;
		try {
			choice=sc.nextInt();
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
		if(choice==2) {
		orderIdx.remove(menu);
		System.out.println("���� �Ϸ�");}
		
		else if(choice==3) {
			System.out.println("��� �����Ͻð� ���ϱ�?");
		int num=0;
		try {
			num=sc.nextInt();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("error");
		}
		orderIdx.put(menu, num);
		priceSum+=menu.getPrice()*num;
		}
	}

	public static void infoEdit(Member member) {

		System.out.println("1.���ּ�");
		System.out.println("2.��ȭ��ȣ");
		System.out.print("�ٲ� ����:");
		try {

			switch (sc.nextInt()) {
			case 1:
				System.out.print("�ٲ� �ּ�:");
				member.setAddress(sc.next());
				break;
			case 2:
				System.out.print("�ٲ� ��ȣ:");
				member.setPhoneNumber(sc.next());
				break;

			}
		} catch (Exception e) {
			System.err.println("error");
		}
	}

	public static void printOrder() {

		for (Iterator<Menu> iterator = orderIdx.keySet().iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();
			System.out.println(menu.getName() + "-" + menu.getPrice() + "-" + orderIdx.get(menu) + "��");
		}
		System.out.println("��" + priceSum + "��");
	}

	public static void printInfo(Member member) {
		System.out.println("id:" + member.getId());
		System.out.println("pwd:" + member.getPwd());
		System.out.println("address:" + member.getAddress());
		System.out.println("grade:" + member.getGrade());
	}

}
