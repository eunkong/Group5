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
			System.out.println("1.주문하기");
			System.out.println("2.주문수정");
			System.out.println("3.정보편집");
			System.out.println("4.주문보기");
			System.out.println("5.정보보기");
			System.out.println("0.종료");
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
				System.out.println("종료");
				break;
			}

		}

	}

	private void order() {
		System.out.println("=====메뉴분류=====");

		Set<String> groups = MenuSFM.getGroupString();

		int num = 0;

		for (String string : groups) {
			System.out.println(string);
		}
		System.out.print("분류선택:");

		String group = sc.next();

		if (MenuSFM.getMenuGroup(group) == null) {
			System.err.println("잘못된 접근 입니다");
			return;
		}

		for (Menu mn : MenuSFM.getMenuGroup(group)) {
			System.out.println(mn.getName() + "-" + mn.getPrice() + "원");
		}

		System.out.print("메뉴선택:");
		String name = sc.next();
		Menu menu = MenuSFM.getMenu(group, name);
		if (menu == null) {
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

	private void editOrder() {
		if (orderIdx.size() == 0) {
			System.out.println("주문내역이 없습니다");
			return;
		}
		System.out.println("1. 모든 주문 삭제   2.주문하나만 삭제 3.주문수량 변경");
		int choice = 0;
		try {
			choice = sc.nextInt();
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
		if (choice == 2) {
			orderIdx.remove(menu);
			System.out.println("삭제 완료");
		}

		else if (choice == 3) {

			System.out.println("몇개로 수정하시겠 습니까?");
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
			System.out.println("수정완료");
		}
		System.out.println("계속 수정 하시겠습니까?\n 1. 예       2. 아니오");
		try {
			if (sc.nextInt() == 2)
				return;
		} catch (Exception e) {
			System.err.println("Insert Error");
		}
		editOrder();
	}

	private void infoEdit() {

		System.out.println("1.아이디");
		System.out.println("2.비밀번호");
		System.out.println("3.집주소");
		System.out.println("4.전화번호");
		System.out.print("바꿀 정보:");
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
				System.out.print("바뀐 주소:");
				member.setAddress(sc.next());
				break;
			case 4:
				System.out.print("바뀐 번호:");
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
			System.out.println(menu.getName() + "-" + menu.getPrice() + "-" + orderIdx.get(menu) + "개");
		}
		System.out.println("총" + priceSum + "원");
	}

	private void editIdPw(boolean flag) {

		System.out.println("아이디와 패스워드를 다시한번 입력해주세요");
		System.out.print("아이디:");
		String id = sc.next();
		System.out.print("패스워드:");
		String pw = sc.next();
		if (!member.getId().equals(id) || !member.getPwd().equals(pw)) {
			System.out.println("비밀번호 혹은 아이디가 잘못되었습니다");
			return;
		}
		System.out.println("새로운 아이디/패스워드를 입력하세요");
		String str = sc.next();
		if (flag)
			member.setId(str);
		else
			member.setPwd(str);
	}// infoedit을 위한 메소드

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
