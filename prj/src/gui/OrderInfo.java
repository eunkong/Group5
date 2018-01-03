package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.Member;
import master.Order;

public class OrderInfo extends JDialog{
	private JPanel bg = new JPanel();
	
	private JPanel jpanel = new JPanel();
	private JLabel jlabel = new JLabel("내 주문");
	private JButton bt1 = new JButton("기간별 조회");
	private JButton bt2 = new JButton("전체주문");
	private JButton bt3 = new JButton("돌아가자");
	
	
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
		
		/*try {
			Map<Long,Order> orderIdx=MainOrderView.ct.myorderlist(); //오류
			
			if(orderIdx==null) {
				JOptionPane.showMessageDialog(null, "주문 내역이 없습니다!","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			DefaultTableModel tp=(DefaultTableModel)jTable.getModel();
			
			for (long onumber: orderIdx.keySet()) {
				Order orderTemp=orderIdx.get(onumber);
				Member memtp=orderTemp.getMember();
				tp.addRow(new Object[] {onumber,orderTemp.getOrdertime(),memtp.getAddress(),
				memtp.getPhoneNumber(),orderTemp.getOrderState(),orderTemp.getPriceSum()		
				});
			}
			
			 jTable.updateUI();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}*/
		
		
	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // x키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)
		
		bt3.addActionListener(e->{
			dispose();
		});
	}

	private void menu() {
	}
}
