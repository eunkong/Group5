package master;

import java.awt.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MenuBoard extends JFrame{

	private JPanel bg = new JPanel(new BorderLayout());
	
	private JToolBar tb1=new JToolBar();
	private JToolBar tb2=new JToolBar();
	
	private JButton bt1=new JButton("+");//글씨크기 키우기
	private JButton bt2=new JButton("-");//글씨크기 줄이기
	
	private ArrayList<JLabel> jb=new ArrayList<>();//각 라벨을 제어하기 위해서 Arraylist
	
	private int fontsize=13;//폰트 사이즈. bt1,bt2 버튼으로 글씨를 키우거나 줄일때 사용
	
	public MenuBoard() {
		design();
		event();
		menu();
		
		setTitle("메뉴목록");
		setSize(600, 500);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
		setResizable(true);
		setVisible(true);
	}

	private void design() {
//		MenuSFM.menuLoad();
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		bg.add(tb1,BorderLayout.CENTER);
		bg.add(tb2,BorderLayout.NORTH);
		
		tb1.setLayout(new GridLayout(3, (MenuSFM.getGroupString().size()+2)/3));
		
		Set<String> temp=new TreeSet<>(MenuSFM.getGroupString());//분류 이름을 Set으로 가져옴
		//콘솔과 같은 순서대로 나오게 하기위해 TreeSet
		
		int i=0;//arraylist의 get을 위한 key값
		
		for (Iterator<String> ite = temp.iterator(); ite.hasNext();i++) {//이를 이용해 for문을 돌려서 결과 출력 
			//tb.add(new JLabel(MenuSFM.menuPrintJLabel(ite.next())));
			
			String string = ite.next();//분류이름
			jb.add(new JLabel(MenuSFM.menuPrintJLabel(string)));//분류에 따른 메뉴를 출력시킬  String(html을 String 에 넣음)
			tb1.add(jb.get(i));//툴바1에 분류에 따른 메뉴목록 추가
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));//처음엔 15사이즈의 폰트로 출력하도록 설정
		}
			tb2.add(bt1);
			tb2.add(bt2);
			tb2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	private void event() {
//		JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
		bt1.addActionListener(e->{
			fontSizeConntrol(true);
		});
		
		bt2.addActionListener(e->{
			fontSizeConntrol(false);
		});
		//ActionListener로 이벤트 추가
	}

	private void menu() {
		
	}
	private void fontSizeConntrol(boolean flag) {//true를 넣고 실행하면 글씨크기가 2증가 false를 넣고 실행하면 글씨크기가 2 감소
		fontsize=fontsize+(flag?2:-2);
		for (int i = 0; i < jb.size(); i++) {
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));
		}
	}
	
}
