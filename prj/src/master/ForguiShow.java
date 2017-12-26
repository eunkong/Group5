package master;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class ForguiShow extends JFrame {

	private JPanel bg = new JPanel(new BorderLayout());
	private JToolBar tb = new JToolBar();
	private JLabel lb = new JLabel();

	private static final String[] USING = { "메뉴보기", "메뉴추가", "메뉴삭제", "분류추가", "분류삭제", "메뉴리셋", "종료" };// 버튼 이름을 Array로 정리

	private JButton[] bts = new JButton[USING.length];// button을 모아놓은 Array USING과 당연히 length가 같음

	public ForguiShow() {// 강사님의 기본 틀대로 만든 JFrame 상속 클래스
		
		mains();
		event();
		menu();

		setTitle("메뉴관리");
		setSize(500, 400);
		// setLocation(100, 100);
		setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void mains() {
		
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		setContentPane(bg);// bg를 배경에 설치하라

		bg.add(tb, BorderLayout.CENTER);// JToolBar를 중앙에 설치

		tb.setLayout(new GridLayout(USING.length, 1));
		for (int i = 0; i < USING.length; i++) {
			bts[i] = new JButton(USING[i]);
			tb.add(bts[i]);// for문으로버튼 추가
		}
		// this가 아니라 bg에 작업을 수행할 수 있다

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)

		ActionListener act = e -> {
			action(e.getActionCommand());
			if (e.getActionCommand().equals("종료")) {

				switch (JOptionPane.showConfirmDialog(null, "저장하시겠습니까?")) // JOption으로 예 아니오 취소 여부를 물음
				{
				case JOptionPane.YES_OPTION:// 예를 누를경우
					MenuSFM.menuWrite();// 객체로 저장 Yes나 No나 둘다 종료하므로 break;나 return; 필요없음
				case JOptionPane.NO_OPTION:
					System.exit(0);// JFrame종료
					return;// 콘솔창 종료
				// 취소를 누를경우 아무런 이벤트도 필요없으므로 따로 설정하지 않음
				}

			}

		};

		for (int i = 0; i < USING.length; i++) {
			bts[i].addActionListener(act);// JButton 배열에 action부여
		}

	}

	private void menu() {

	}

	private void action(String key) {// event를 위한 메소드 (이전버전에선 메인 클래스에 있었음)

		String name;
		String group;
		System.out.println("======" + key + "======\n");
		int price = 0;
		switch (key) {
		case "메뉴보기":
			MenuSFM.menuPrintConsole();// 콘솔창에서 메뉴목록 확인가능
			new MenuBoard();//새창에서 메뉴 보여줌
			break;

		case "메뉴추가":
			System.out.print("메뉴분류:");
			group = JOptionPane.showInputDialog("분류 입력");// JOptionPane로 입력 받음

			if (group == null || group.equals("")) {// 취소버튼을 누르거나 아무것도 넣지않고 확인을 누를때 자동 취소
				System.out.println("취소");
				break;
			}

			if (!MenuSFM.getGroupString().contains(group)) {// 메뉴 목록에 있는 분류인지 검사후 없으면 경고창출력
				System.out.println(MenuSFM.WARNING[1]);
				JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
				break;
			}

			System.out.println(group);

			System.out.print("메뉴이름:");

			name = JOptionPane.showInputDialog("메뉴이름");
			System.out.println(name);
			if(MenuSFM.getMenu(name)!=null) {//이미 있는 메뉴인지 검사후 있면 경고창출력
				System.out.println(MenuSFM.WARNING[4]);
				JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
				break;
			}
			
			System.out.print("가격:");
			
			String str=JOptionPane.showInputDialog("가격");
			if (str == null || str.equals("")) {// 취소버튼을 누르거나 아무것도 넣지않고 확인을 누를때 자동 취소
				System.out.println("취소");
				break;
			}

			try {
				price = Integer.parseInt(str);
			} catch (Exception e) {
				System.out.println(MenuSFM.WARNING[0]);
				JOptionPane.showMessageDialog(null, "ERROR", "", JOptionPane.ERROR_MESSAGE);
				break;
				// 정수 이외의 것을 출력할경우 예외처리후 멈춤
			}

			System.out.println(price);
			MenuSFM.addMenu(group, new Menu(name, price));
			
			break;
		// JOptionPane으로 입력 -> 제대로 입력됐는지 검사 ->콘솔창에 검사출력&예외가 발생하면 메세지 날리기의 반복.
		// 아무 문제 없으면 기능 수행. 다른 기능도 마찬가지
		//MenuSFM	
		case "메뉴삭제":
			System.out.print("메뉴분류:");
			group = JOptionPane.showInputDialog("메뉴 분류");
			if (group == null || group.equals("")) {
				System.out.println("취소");
				break;
			}
			if (!MenuSFM.getGroupString().contains(group)) {
				System.out.println(MenuSFM.WARNING[1]);
				JOptionPane.showMessageDialog(null, MenuSFM.WARNING[1], "", JOptionPane.ERROR_MESSAGE);
				break;
			}
			System.out.println(group);
			System.out.print("메뉴이름:");

			name = JOptionPane.showInputDialog("메뉴 이름");

			if (name == null || name.equals("")) {
				System.out.println("취소");
				break;
			}

			System.out.println(name);
			// 메뉴추가와 거의 동일
			MenuSFM.removeMenu(group, name);// 메뉴 삭제 메소드
											// 이름만으로 메뉴를 찾으므로 가격 입력 불필요
			break;

		case "분류추가":
			System.out.print("분류명:");

			group = JOptionPane.showInputDialog("메뉴 분류");// JOptionPane으로 입력

			if (group == null || group.equals("")) {// 마찬가지로 입력을 하지 않고 확인을 누르거나 아니면 그냥 취소버튼을 누르면 종료
				System.out.println("취소");
				break;
			}
			System.out.println(group);
			MenuSFM.addMenu(group);// 분류 추가 메소드

			break;

		case "분류삭제":
			System.out.print("분류명:");
			group = JOptionPane.showInputDialog("메뉴 분류");

			if (group == null || group.equals("")) {// 전전의 주석과 같음
				System.out.println("취소");
				break;
			}
			System.out.println(group);
			MenuSFM.removeMenu(group);// 분류 삭제 메소드
			break;

		case "메뉴리셋":
			int choice = JOptionPane.showConfirmDialog(null, "정말 리셋 하시겠습니까?", "",
					JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, null);
			if (choice == JOptionPane.CANCEL_OPTION)break;
			
			MenuSFM.menuReset();
			System.out.println("리셋완료");
			JOptionPane.showMessageDialog(null, "리셋완료");

			break;

		case "종료":
			// 객체로 저장하는건 event에서 처리
			break;

		default:
			JOptionPane.showMessageDialog(null, MenuSFM.WARNING[0], "", JOptionPane.ERROR_MESSAGE);
			System.out.println(MenuSFM.WARNING[0]);
			break;
		}
	}
}
