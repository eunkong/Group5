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
public class MenuSFM implements Serializable {// 메뉴관리
	
	private static final String NOODLE = "면류";
	private static final String RICE = "밥류";
	private static final String DRINK = "음료";// 메뉴 분류를 위한 상수 ex) 짜장면, 짬뽕-면류 볶음밥, 잡탕밥-밥류
	
	public static final String[] WARNING= {"잘못된 선택입니다","없는 분류 입니다","이미 있는 분류 입니다"
			,"없는 메뉴 입니다","이미 있는 메뉴 입니다."};//경고메세지
	
	private static final File MENUFILE = new File("files", "menus.db");
	
	private static Map<String, Set<Menu>> menus = new HashMap<>();// 분류별로 메뉴 저장

	public MenuSFM() {
		basicMenu();// 기본적인 메뉴를 저장한 메소드(추후 증가될 수 있음)
		menuWrite();
	} // 모든 메소드를 static화 시키면서 사실상 필요 X

	private static void basicMenu() {// 기본적으로 존재하는 메뉴

		menus.put(NOODLE, new HashSet<>());
		menus.get(NOODLE).add(new Menu("짜장면", 4000));
		menus.get(NOODLE).add(new Menu("짬뽕", 5000));

		menus.put(RICE, new HashSet<>());
		menus.get(RICE).add(new Menu("볶음밥", 5500));
		menus.get(RICE).add(new Menu("김치덮밥", 1000));

		menus.put(DRINK, new HashSet<>());
		menus.get(DRINK).add(new Menu("콜라", 1000));
		menus.get(DRINK).add(new Menu("사이다", 1000));

	}

	
	/**
	 * 메뉴추가 , 이름이 같은 메뉴가 있거나 없은 분류면 추가 불가능
	 * @param group
	 * @param menu
	 * {@code}
	 */
	public static void addMenu(String group, Menu menu) {

		if (!getGroupString().contains(group)) { // 존재하는 분류인지 검사
			System.out.println(WARNING[4]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
			}
		if (getMenu(menu.getName()) != null) { // getMenu 메소드로 같은 이름의 메뉴가 있는지 검사
			System.out.println(MenuSFM.WARNING[1]);
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
		}
		menus.get(group).add(menu);// 같은 이름의 메뉴가 없을경우 추가

		System.out.println("추가완료");
		JOptionPane.showMessageDialog(null, "추가완료");// 완료 메세지
	}

	/**
	 * 분류 추가,이미 있는 분류면 추가 불가능  ex)주류혹은 요리류 추가
	 * @param String , Menu
	 */
	
	public static void addMenu(String group) {

		if (getGroupString().contains(group)) {
			System.out.println(MenuSFM.WARNING[1]);
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;// 이미 있는 분류는 추가 불가
		}
		menus.put(group, new HashSet<Menu>());
		System.out.println("추가완료");
		JOptionPane.showMessageDialog(null, "추가완료");// 완료 메세지
	}
	
	/**
	 * 메뉴 삭제,이미 있는 분류면 추가 불가능  ex)면류에 짜장면 삭제
	 * @param group
	 * @param name
	 */
	
	public static void removeMenu(String group, String name) { // 메뉴삭제 ex) 면류에 짜장면 삭제

		if (!getGroupString().contains(group)) {
			System.out.println(WARNING[1]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Set<Menu> temp = getMenuGroup(group);

		for (Iterator<Menu> ite = temp.iterator(); ite.hasNext();) {// 해당 메뉴의 이름이 존재하는지 검사 & 존재하면 삭제후 종료
			Menu menu = ite.next();
			if (name.equals(menu.getName())) {

				temp.remove(menu);
				menus.put(group, temp);// menus.get(group).remove(menu);는 에러남
				System.out.println("삭제완료");
				JOptionPane.showMessageDialog(null, "삭제완료");// 완료 메세지
				return;
			}
		}

		System.out.println(WARNING[3]);
		JOptionPane.showMessageDialog(null, WARNING[3], "", JOptionPane.ERROR_MESSAGE);
	}

	public static void removeMenu(String group) {// 분류 삭제 ex) 면류 분류는 삭제

		if (!getGroupString().contains(group)) {
			System.out.println(WARNING[1]);
			JOptionPane.showMessageDialog(null, WARNING[1], "", JOptionPane.ERROR_MESSAGE);
		} else {
			menus.remove(group);
			System.out.println("삭제완료");
			JOptionPane.showMessageDialog(null, "삭제완료");
		}
	}

	public static void menuReset() {// 기본적인 메뉴 외엔 리셋
		menus.clear();
		basicMenu();
	}

	public static void menuPrintConsole() {// 콘솔창에 메뉴 출력

		for (Iterator<String> ite = (new TreeMap<>(menus)).keySet().iterator(); ite.hasNext();) {

			String group = ite.next();// key값(=분류명) 저장
			System.out.println("-----" + group + "-----");

			for (Iterator<String> iter = getMenuName(group).iterator(); iter.hasNext();) {
				String temp = iter.next();
				System.out.println(temp + "-" + getMenu(group, temp).getPrice() + "원");
			}

			/*
			   for (Iterator<Menu> iter = getMenuGroup(group).iterator(); iter.hasNext();) {
			   Menu temp = iter.next(); System.out.println(temp.getName() + "-" +
			   temp.getPrice() + "원"); }
			 */
			System.out.println();
		}

	}// 중간에 TreeMap, TreeSet 은 가나다순 출력을 위한 것일 뿐입니다.(없는 버전으로 쉽게 수정 가능)

	// GUI용
	public static String menuPrintJLabel(String group) {// JLabel에 메뉴 출력용(아마 쓰이지 않을 듯 하니 무시해도 무관.)
		
		StringBuffer buffer = new StringBuffer("<html>");
		buffer.append("<br>-------" + group + "-------");
			for (Iterator<String> iter = getMenuName(group).iterator(); iter.hasNext();) {
				String temp = iter.next();
				buffer.append("<br>" + temp + "-" + getMenu(temp).getPrice() + "원");
			}

		buffer.append("</html>");
		
		return buffer.toString();

	}

	// 아래는 getter
	public static Menu getMenu(String name) {// 특정 메뉴를 가져옴
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
		return remenu;// 없으면 null 반환
	
	}

	
	public static Menu getMenu(String group, String name) {// 특정 메뉴를 가져옴
		if (!getGroupString().contains(group))
			return null;

		for (Menu menu : getMenuGroup(group)) {
			if (menu.getName().equals(name))
				return menu;
		}

		return null;// 없으면 null 반환
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
			temp.add(new JButton(menu.getName() + "-" + menu.getPrice() + "원"));
		}
		return temp.toArray(new JButton[temp.size()]);
	}// 추후 손님용 레이아웃을 위한 것

	
	
	
	@SuppressWarnings("unchecked")
	public static void menuLoad() {// files에 있는 menus객체를 읽어오는 메소드
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(MENUFILE)));// 객체 통로 염
			menus = (HashMap<String, Set<Menu>>) in.readObject();// return 타입이 Object 이므로 casting 한것.(안그러면 느려짐)
			in.close();// 통로 닫음
		} catch (Exception e) {
			System.out.println("파일이 없습니다");
			menuWrite();// 파일이 없을경우 현재 객제를 저장하는 파일 생성
		}

	}

	public static void menuWrite() {// files에 menus를 객체화 하여 저장하는 메소드

		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(MENUFILE)));// 객체통로 염
			out.writeObject(menus);// 객체추가(저장)

			out.flush();// 플러시
			out.close();// 통로닫음
		} catch (Exception e) {
			System.out.println("저장실패");
		}

	}

	
	public static Set<String> getMenuName(String group) {// 분류들에 해당하는 메뉴들의 이름만 가져옴
		Set<String> names = new HashSet<>();
		for (Menu menu : getMenuGroup(group))
			names.add(menu.getName());
		return new TreeSet<String>(names);
	}
	
	public static Set<String> getGroupString() {// 분류들을 set으로 가져옴

		return new TreeSet<String>(menus.keySet());
	}

	public static Set<Menu> getMenuGroup(String group) {// 한 분류의 모든 메뉴를 set으로 가져옴

		if (!getGroupString().contains(group))
			return null;// 없으면 null 반환

		return menus.get(group);
	}

	public static Map<String, Set<Menu>> getMenus() {// menus를 통째로 가져옴
		return menus;
	}

}
