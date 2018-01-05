package serverGui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import master.Order;
import server.ReceiptManager;

//주문관리창 구현 클래스
class OrderManageWindow extends JDialog {
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("주문관리");
	private JButton bt1 = new JButton("기간별 조회");
	private JButton bt2 = new JButton("고객별 조회");
	private JButton bt3 = new JButton("전체주문");
	private JButton bt4 = new JButton("돌아가자");

	public static String[] state = {"","주문완료","요리중","요리완료","배달중","배달완료"};

	private String columnNames[] = { "주문번호", "주문시간", "고객아이디", "고객 주소","고객 연락처", "주문상태" };
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	private DefaultTableModel m;
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderManageWindow(Frame mw, boolean modal) {
		super(mw,modal);							//창 종속
		design();
		event();
		menu();

		setTitle("주문관리");
		setSize(1200, 800);
		setLocationRelativeTo(mw);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		setContentPane(bg); 
		bg.setLayout(null);
		
		jpanel.setBounds(47, 166, 1092, 569);
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
		
		jTable.setRowHeight(25); 
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); 
		jTable.getTableHeader().setReorderingAllowed(false); 
		jTable.getTableHeader().setResizingAllowed(false); 
		
		
		
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // x키 누르면 창 닫기
		
		//나가기
		ActionListener ac = e->{
			dispose();
		};
		bt4.addActionListener(ac);
		
		
		//전체영수증 출력
		ActionListener ac1 = e->{
			m = (DefaultTableModel)jTable.getModel();		
			while(m.getRowCount()!=0) {m.removeRow(0);} 	

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.loadDatabase();
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //내림차순 정렬
			while(iterator.hasNext()) {
				Long num = iterator.next();	
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
		};
		bt3.addActionListener(ac1);
		
		
		//고객별 조회
		ActionListener ac2 = e->{
			m = (DefaultTableModel)jTable.getModel();		
			while(m.getRowCount()!=0) {m.removeRow(0);} 	
			String inputId = JOptionPane.showInputDialog("아이디를 입력해주세요~");

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPerReceipt(inputId);
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //내림차순
			while(iterator.hasNext()) {
				Long num = iterator.next();
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
			
		};
		bt2.addActionListener(ac2);
		
		
		
		//기간별 조회
		ActionListener ac3 = e->{
			m = (DefaultTableModel)jTable.getModel();		//틀만들고 
			while(m.getRowCount()!=0) {m.removeRow(0);} 	//테이블 초기화
			String start = JOptionPane.showInputDialog("조회 시작할 기간을 입력   ex) 180101");
			String finish = JOptionPane.showInputDialog("조회종료할 기간도 입력해!  ex) 180101");
			m = (DefaultTableModel)jTable.getModel();		//틀만들고 

			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPeriodReceipt(start, finish);
			TreeMap<Long, Order> tmap = new TreeMap<Long, Order>(map);	//정렬
			Iterator<Long> iterator = tmap.descendingKeySet().iterator();   //내림차순
			while(iterator.hasNext()) {
				Long num = iterator.next();	
				Order order = map.get(num);
				m.insertRow(m.getRowCount(), new Object[]{num,order.getOrdertime(),order.getMember().getId(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()]});
			}
			jTable.updateUI();
			
		};
		bt1.addActionListener(ac3);
		
	}

	private void menu() {
	}
}
