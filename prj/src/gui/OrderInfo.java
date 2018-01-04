package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.ClientTool;
import client.Member;
import master.Order;
import server.ReceiptManager;

public class OrderInfo extends JDialog{
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("내 주문");
	private JButton bt1 = new JButton("기간별 조회");
	private JButton bt2 = new JButton("전체주문");
	private JButton bt3 = new JButton("돌아가자");
	
	private Map<Long,Order> orderIdx;
	
	public static String[] state = {"","주문완료","요리중","요리완료","배달중","배달완료"};

	private String columnNames[] = { "주문번호", "주문시간", "고객 주소", "고객 연락처","주문 상태", "금액" };
	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {},
			columnNames);
	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	private JScrollPane jScollPane = new JScrollPane(jTable);
	
	
	public OrderInfo(Frame owner, boolean modal){
		super(owner, modal);
		design();
		event();
		menu();

		setTitle("");
		setSize(1200, 800);
//		setLocation(100, 40);
		//setLocationByPlatform(true); // 위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true); // 항상위
		setLocationRelativeTo(owner);
//		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	private void design() {
		setContentPane(bg); // bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);
		
		jpanel.setBounds(47, 166, 1092, 569);
		bg.add(jpanel);
		
		jlabel.setFont(new Font("굴림", Font.PLAIN, 70));
		jlabel.setBounds(72, 60, 385, 94);
		bg.add(jlabel);
		
		bt1.setBounds(696, 60, 133, 70);
		bg.add(bt1);
		
		bt2.setBounds(841, 60, 133, 70);
		bg.add(bt2);
		
		bt3.setBounds(986, 60, 133, 70);
		bg.add(bt3);
		
		jpanel.add(jScollPane);
		//jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		// jTable.setBackground(Color.DARK_GRAY); //색
		jTable.setRowHeight(25); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // 사이즈?
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
		allPrint();
 		
	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // x키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)
		bt1.addActionListener(e->{
			
			//^[01][0-9][01][0-9][123][0-9]$ 정규식 넣을 예정
			String start = JOptionPane.showInputDialog("조회 시작할 기간을 입력   ex) 180101");
			if(start.equals("")||start==null)return;
			
			String finish = JOptionPane.showInputDialog("조회종료할 기간도 입력해!  ex) 180101");
			if(finish.equals("")||finish==null)return;
			
			//틀만들고 
			DefaultTableModel tp=(DefaultTableModel)jTable.getModel();
			while(tp.getRowCount()!=0) {tp.removeRow(0);}

//			ReceiptManager receiptManager = new ReceiptManager();
			Map<Long, Order> map = ReceiptManager.getPeriodReceipt(orderIdx, start, finish);
			Iterator<Long> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				Long num = iterator.next();	//1개로만 한다.
				Order order = map.get(num);
				tp.insertRow(tp.getRowCount(), new Object[]{num, order.getOrdertime(),order.getMember().getAddress(),order.getMember().getPhoneNumber(),state[order.getOrderState()],order.getPriceSum()});
			}
			jTable.updateUI();
		});
		
		bt2.addActionListener(e->{
			allPrint();
		});
		
		bt3.addActionListener(e->{
			dispose();
		});
	}

	private void allPrint() {
		DefaultTableModel tp=(DefaultTableModel)jTable.getModel();
		while(tp.getRowCount()!=0) {tp.removeRow(0);}
		
		try {
			ClientTool ct=new ClientTool();
			orderIdx=MainOrderView.ct.myorderlist(); //오류
			
			if(orderIdx==null) {
				JOptionPane.showMessageDialog(null, "주문 내역이 없습니다!","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			for (long onumber: orderIdx.keySet()) {
				Order orderTemp=orderIdx.get(onumber);
				Member memtp=orderTemp.getMember();
				tp.addRow(new Object[] {onumber,orderTemp.getOrdertime(),memtp.getAddress(),
				memtp.getPhoneNumber(),state[orderTemp.getOrderState()],orderTemp.getPriceSum()		
				});
			}
			
			 jTable.updateUI();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	}
	private void menu() {
	}
}
