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
			System.out.println("1.주문하기");
			System.out.println("2.주문수정");
			System.out.println("3.정보편집");
			System.out.println("4.주문보기");
			System.out.println("5.정보보기");
			System.out.println("0.종료");
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
		System.out.println("=====메뉴분류=====");

		Set<String> groups = MenuSFM.getGroupString();
		Menu menu;
		int num = 0;

		for (String string : groups) {
			System.out.println(string);
		}
		System.out.print("분류선택:");

		String group = sc.next();
		
		try {
			MenuSFM.getMenuGroup(group);
		} catch (Exception e) {
			System.err.println("잘못된 접근 입니다");
			return;
		}

		for (Menu mn : MenuSFM.getMenuGroup(group)) {
			System.out.println(mn.getName() + "-" + mn.getPrice() + "원");
		}

		System.out.print("메뉴선택:");
		String name = sc.next();
		try {
			menu = MenuSFM.getMenu(group, name);
		} catch (Exception e) {
			System.err.println("잘못된 접근 입니다");
			return;
		}

		System.out.print("수량선택:");
		try {
			num = sc.nextInt();
		} catch (Exception e) {
			System.err.println("잘못된 접근 입니다");
			return;
		}
		System.out.println("정말 주문 하시겠습니까?");
		System.out.println("1.예  2.아니오");
		if (sc.nextInt() != 1)
			return;

		member.setOrderCount(member.getOrderCount() + 1);
		priceSum += menu.getPrice() * num;
		orderIdx.put(menu, num);
		System.out.println("주문 완료");
	}

	public static void editOrder(Member member) {
		System.out.println("1. 모든 주문 삭제   2.주문하나만 삭제 3.주문수량 변경");
		int choice=0;
		try {
			choice=sc.nextInt();
			if (choice == 1) {
				orderIdx.clear();
				System.out.println("삭제완료");
				return;
			}
		} catch (Exception e) {
			System.err.println("insert error");
			return;
		}

		printOrder();
		System.out.println("어느주문을 수정/삭제하시겠 습니까?");
		String name = sc.next();
		Menu menu = MenuSFM.getMenu(name);
		if (!orderIdx.keySet().contains(MenuSFM.getMenu(name))) {
			System.out.println("주문하지 않은 메뉴 입니다");
			return;
		}
		priceSum -= orderIdx.get(menu) * menu.getPrice();
		if(choice==2) {
		orderIdx.remove(menu);
		System.out.println("삭제 완료");}
		
		else if(choice==3) {
			System.out.println("몇개로 수정하시겠 습니까?");
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

		System.out.println("1.집주소");
		System.out.println("2.전화번호");
		System.out.print("바꿀 정보:");
		try {

			switch (sc.nextInt()) {
			case 1:
				System.out.print("바뀐 주소:");
				member.setAddress(sc.next());
				break;
			case 2:
				System.out.print("바뀐 번호:");
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
			System.out.println(menu.getName() + "-" + menu.getPrice() + "-" + orderIdx.get(menu) + "개");
		}
		System.out.println("총" + priceSum + "원");
	}

	public static void printInfo(Member member) {
		System.out.println("id:" + member.getId());
		System.out.println("pwd:" + member.getPwd());
		System.out.println("address:" + member.getAddress());
		System.out.println("grade:" + member.getGrade());
	}

}
