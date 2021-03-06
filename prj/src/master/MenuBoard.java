package master;
 
import java.awt.*;
import java.util.*;
import javax.swing.*;
/** 
 * 메뉴목록을 GUI로 출력
 * JLabel로 그냥 보여주는 용도
 * @author Team5
 * 
 * @version 1.0
 * @see ForguiShow  
 * @see MenuSFM  
 */
@SuppressWarnings("serial")
public class MenuBoard extends JDialog{
	/** 
	 * @see #design
	 * 
	 */
	private JPanel bg = new JPanel();
	
	private JPanel bg1 = new JPanel();
	
	/** 
	 * 출력되는 글자의 크기를 컨트롤하기 위한 JButton
	 * @see #design
	 * @see #event
	 */
	
	
	/**
	 * 그룹별 메뉴가 출력
	 * 각 라벨을 제어하기 위해서 Arraylist
	 * @see #design
	 */
	private ArrayList<JLabel> jb=new ArrayList<>();
	
	private int fontsize=13;//폰트 사이즈. bt1,bt2 버튼으로 글씨를 키우거나 줄일때 사용
	
	
	public MenuBoard(Frame owner, boolean modal) {
		super(owner, modal);
		design();
		
		setTitle("메뉴목록");
		setSize(700, 300);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
		setVisible(true);
	}
	/** 
	 * 출력화면 구성
	 * @param JPanel bg					back ground에 설치할 JPanel
	 * @param JToolBar tb1				메뉴 목록을 프린트할 JToolBar	
	 * @param JToolBar tb2				bt1,bt2를 둘 JToolBar	
	 * @param JButton bt1,bt2			글씨크기를 조종할 JButton
	 * @param ArrayList<JLabel> jb		분류별로 bg에 메뉴를 프린트할 JLabel
	 * @param int fontsize				현재 글자크기를 저장하는 정수
	 * 
	 */
	private void design() {
//		MenuSFM.menuLoad();
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
		bg.add(bg1);
		
		Set<String> temp=new TreeSet<>(MenuSFM.getGroupString());//분류 이름을 Set으로 가져옴
		//콘솔과 같은 순서대로 나오게 하기위해 TreeSet
		
		int i=0;//arraylist의 get을 위한 key값
		
		for (Iterator<String> ite = temp.iterator(); ite.hasNext();i++) {//이를 이용해 for문을 돌려서 결과 출력 
			//tb.add(new JLabel(MenuSFM.menuPrintJLabel(ite.next())));
			
			String string = ite.next();//분류이름
			jb.add(new JLabel(MenuSFM.menuPrintJLabel(string)));//분류에 따른 메뉴를 출력시킬  String(html을 String 에 넣음)
			bg1.add(jb.get(i));//툴바1에 분류에 따른 메뉴목록 추가
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));//처음엔 15사이즈의 폰트로 출력하도록 설정
		}
	}
	
}
