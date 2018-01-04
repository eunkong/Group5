package serverGui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Member;
import master.MenuSFM;
import server.MemberManager;

class GuestManageWindow extends JDialog {
	private JPanel bg = new JPanel();

	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("고객 관리");
	private JButton bt1 = new JButton("추가");
	private JButton bt2 = new JButton("수정");
	private JButton bt3 = new JButton("삭제");
	private JButton bt4 = new JButton("나가자");
	
	private JLabel jlb1 = new JLabel("아이디");
	private JLabel jlb2 = new JLabel("비밀번호");
	private JLabel jlb3 = new JLabel("전화번호");
	private JLabel jlb4 = new JLabel("고객 주소");
	
	private JTextField jtf1 = new JTextField();
	private JTextField jtf2 = new JTextField();
	private JTextField jtf3 = new JTextField();
	private JTextField jtf4 = new JTextField();
	
	private JButton bt5 = new JButton("추가");
	

	private String columnNames[] = { "아이디", "비밀번호", "전화번호", "고객 주소", "등급", "주문 수", "포인트" };
	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private DefaultTableModel m;
	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public GuestManageWindow(Frame mw, boolean modal) {
		super(mw, modal);
		design();
		event();
		menu();

		setTitle("고객관리");
		setSize(1200, 800);
		setLocationRelativeTo(mw);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg); // bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);

		jpanel.setBounds(47, 166, 1092, 500);
		bg.add(jpanel);

		jlabel.setFont(new Font("굴림", Font.PLAIN, 70));
		jlabel.setBounds(72, 60, 385, 94);
		bg.add(jlabel);

		bt1.setBounds(551, 60, 133, 70);
		bg.add(bt1);

		bt2.setBounds(696, 60, 133, 70);
		bg.add(bt2);

		bt3.setBounds(841, 60, 133, 70);
		bg.add(bt3);

		bt4.setBounds(986, 60, 133, 70);
		bg.add(bt4);

		jpanel.add(jScollPane);

		// jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //색
		jTable.setRowHeight(25); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 470)); // 사이즈?
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
		jlb1.setBounds(50, 696, 60, 36);
		bg.add(jlb1);
		jlb2.setBounds(289, 696, 60, 36);
		bg.add(jlb2);
		jlb3.setBounds(524, 696, 60, 36);
		bg.add(jlb3);
		jlb4.setBounds(802, 696, 60, 36);
		bg.add(jlb4);
		
		jtf1.setBounds(110, 696, 125, 36);
		bg.add(jtf1);
		jtf2.setBounds(360, 696, 125, 36);
		bg.add(jtf2);
		jtf3.setBounds(590, 696, 164, 36);
		bg.add(jtf3);
		jtf4.setBounds(860, 696, 164, 36);
		bg.add(jtf4);
		
		bt5.setBounds(1050, 696, 81, 36);
		bg.add(bt5);
		
		

		// 창에 뿌려주는 거
		m = (DefaultTableModel) jTable.getModel(); // 틀만들고

		System.out.println("m.getRowCount() : " + m.getRowCount());

		MemberManager member = new MemberManager();
		Map<String, Member> map = member.loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String id = iterator.next(); // 1개로만 한다.
			Member man = map.get(id);
			m.insertRow(m.getRowCount(), new Object[] { man.getId(), man.getPwd(), man.getPhoneNumber(),
					man.getAddress(), man.getGrade(), man.getOrderCount(), man.getPoint() });
		}
		m.insertRow(m.getRowCount(), new Object[] { "", "", "", "", "", "", "" });
		jTable.updateUI();

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // x키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)

		ActionListener ac = e -> {
			// 뒤로간다? (=현재창을 닫고 이전창을 열어준다)
			dispose();
		};
		bt4.addActionListener(ac);

		// 회원삭제
		ActionListener act2 = e -> {
			int row = jTable.getSelectedRow();
			System.out.println("row : " + row);
			int col = 0;
			String value = (String) jTable.getValueAt(row, col);
			System.out.println("value : " + value);

			m.removeRow(row); // 선택열 삭제

			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("일치하는 아이디 발견");
					map.remove(id);
					break;
				}
			}

			member.saveDatabase(map);
		};
		bt3.addActionListener(act2);

		
		// 회원추가
		ActionListener act3 = e -> {
			int lastRow = m.getRowCount() - 1; // 마지막 행
			int lastCol = m.getColumnCount(); // 마지막 열

			// 마지막행의 처음부터 끝까지 값을 가져온다. 그리고 그 값으로 회원가입한다.
			String[] array = new String[7];
			for (int i = 0; i < 4; i++) {
				String a = (String) m.getValueAt(lastRow, i);
				array[i] = a;
				System.out.println(a);
			}
			MemberManager.register(array[0], array[1], array[2], array[3]);

			m.insertRow(m.getRowCount(), new Object[] { "", "", "", "", "", "", "" });
			jTable.updateUI();
		};
		bt1.addActionListener(act3);
		
		

		// 회원수정
		ActionListener act4 = e -> {
			int row = jTable.getSelectedRow();
			System.out.println("row : " + row);
			int col = 0;
			String value = (String) jTable.getValueAt(row, col);
			System.out.println("value : " + value);

			//선택 행의 값을 불러와 배열에 저장한다.
			String[] array = new String[7];
			for (int i = 0; i < 4; i++) {
				String a = (String) m.getValueAt(row, i);
				array[i] = a;
				System.out.println(a);
			}
			
			//선택행 값 변경 적용
			MemberManager member = new MemberManager();
			Map<String, Member> map = member.loadDatabase();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String id = iterator.next();
				if (id.equals(value)) {
					System.out.println("일치하는 아이디 발견");
					//해당 맵값을 수정한다.
					map.put(id, new Member(id, array[1], array[2], array[3]));
					break;
				}
			}
			member.saveDatabase(map);
		};
		bt2.addActionListener(act4);
		
		

	}

	private void menu() {
	}
}
