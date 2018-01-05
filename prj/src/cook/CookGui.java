package cook;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Window01 extends JFrame {
	private JPanel bg = new JPanel();
	private JPanel menuPanel = new JPanel();

	private JLabel lbCook = new JLabel("<요리사>");
	private JLabel lb[] = new JLabel[5];
	private String lbString[] = new String[] { "주문 상태", "주문 번호", "주문 시간", "고객 아이디", "주문 메뉴" };
	private JLabel lbOrderState = new JLabel();
	private JLabel lbOrderNum = new JLabel();
	private JLabel lbOrderTime = new JLabel();
	private JLabel lbId = new JLabel();

	private JButton btCookStart = new JButton("요리 시작");
	private JButton btCookFinish = new JButton("요리 완료");
	private JButton btBack = new JButton("가즈아ㅏㅏ");
	
	private String columnNames[] = { "분류", "메뉴명", "수량","가격(원)" };
	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
		design();
		event();
		menu();

		setTitle("");
		setSize(660, 800);
		setLocation(450, 50);
		//setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		menuPanel.setBounds(150, 350, 450, 280);
		bg.add(menuPanel);

		lbCook.setFont(new Font("굴림", Font.PLAIN, 40));
		lbCook.setBounds(209, 10, 205, 84);
		bg.add(lbCook);

		for (int i = 0; i < lbString.length; i++) {
			lb[i] = new JLabel(lbString[i]);
			lb[i].setText(lbString[i]);
			lb[i].setBounds(50, 100 + i * 60, 80, 30);
			bg.add(lb[i]);
		}

		lbOrderState.setBounds(209, 119, 57, 15);
		bg.add(lbOrderState);

		lbOrderNum.setBounds(209, 162, 57, 15);
		bg.add(lbOrderNum);

		lbOrderTime.setBounds(209, 208, 57, 15);
		bg.add(lbOrderTime);

		lbId.setBounds(209, 255, 57, 15);
		bg.add(lbId);

		btCookStart.setBounds(70, 670, 110, 55);
		bg.add(btCookStart);

		btCookFinish.setBounds(270, 670, 110, 55);
		bg.add(btCookFinish);

		btBack.setBounds(470, 670, 110, 55);
		bg.add(btBack);
		

		menuPanel.add(jScollPane);
		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //색
		jTable.setRowHeight(20); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(370, 250)); // 사이즈?
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
		
		

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)
	}

	private void menu() {

	}
}

public class CookGui {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}
