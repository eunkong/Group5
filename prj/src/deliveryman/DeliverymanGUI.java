package deliveryman;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import master.Order;

class DeliverymanGUI extends JDialog {
	
	private static Socket socket ;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	{
		try {
			socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	final static int DELIVERYMAN = 3;
	static boolean deliveryState;
	
	private JPanel bg = new JPanel();

	public static String[] state = { "", "주문완료", "요리중", "요리완료", "배달중", "배달완료" };

	private String columnNames[] = { "주문번호", "주문시간", "고객아이디", "고객 주소", "고객 연락처", "배달 상태" };
	// DefaultTableModel을 선언하고 데이터 담기
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	// JTable에 DefaultTableModel을 담기
	private JTable jTable = new JTable(defaultTableModel);
	// JScrollPane에 JTable을 담기
	/*
	 * private JLabel jb1 = new JLabel(columnNames[0]); private JLabel jb2 = new
	 * JLabel(columnNames[1]); private JLabel jb3 = new JLabel(columnNames[2]);
	 * private JLabel jb4 = new JLabel(columnNames[3]); private JLabel jb5 = new
	 * JLabel(columnNames[4]); private JLabel jb6 = new JLabel(columnNames[5]);
	 */
	private JLabel[] jb = new JLabel[columnNames.length];
	private JLabel[] show = new JLabel[columnNames.length];
	private JButton btStart = new JButton("배달시작");
	private JButton btFinish = new JButton("배달완료");
	private JButton btBack = new JButton("종료");

	public DeliverymanGUI(Frame mw, boolean modal) {
		super(mw, modal); // 창 종속 위해 필요
		design();
		event();
		menu();
		
		setTitle("주문관리");
		setSize(600, 800);
		setLocationRelativeTo(mw);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void design() {
		try {
			out.writeInt(DELIVERYMAN); out.flush();  //배달맨이라는 정보를 서버에 넘김
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(bg); // 배경 설치
		bg.setLayout(null);
		for (int i = 0; i < columnNames.length; i++) {
			jb[i] = new JLabel(columnNames[i]);
			jb[i].setBounds(50, 100 + i * 80, 150, 20);
			jb[i].setFont(new Font("", Font.BOLD, 15));
			bg.add(jb[i]);

			show[i] = new JLabel("");
			show[i].setBounds(200, 100 + i * 80, 300, 20);
			show[i].setFont(new Font("", Font.PLAIN, 14));
			bg.add(show[i]);
		}
		btStart.setBounds(100, 600, 90, 50);
		btFinish.setBounds(255, 600, 90, 50);
		btBack.setBounds(410, 600, 90, 50);

		bg.add(btStart);
		bg.add(btFinish);
		btFinish.setEnabled(false);
		bg.add(btBack);

		jTable.setRowHeight(25); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(1050, 500)); // 사이즈?
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		basicPrint();

	}

	private void event() {
		btStart.addActionListener(e->{
			btStart.setEnabled(false);
			btFinish.setEnabled(true);
			btBack.setEnabled(false);
			try {
				out.writeInt(0); out.flush(); //서버에 배달가느여부(0) 전송
				JOptionPane.showMessageDialog(null,"배달준비완료!", "", JOptionPane.INFORMATION_MESSAGE);
				Map<Long, Order> orderlist = (Map<Long, Order>)in.readObject();
				if(orderlist==null) {
					JOptionPane.showMessageDialog(null, "배달할게 없음ㅋ", "", JOptionPane.INFORMATION_MESSAGE); 
					System.exit(0);
				}
				Iterator<Long> iterator = orderlist.keySet().iterator();
				Long num = iterator.next();
				Order order = orderlist.get(num);
				show[0].setText(num.toString()); //주문번호
				show[1].setText(order.getOrdertime()); //주문시간
				show[2].setText(order.getMember().getId()); //고객아이디
				show[3].setText(order.getMember().getAddress());//고객주소
				show[4].setText(order.getMember().getPhoneNumber());//고객연락처
				show[5].setText(state[order.getOrderState()]);//주문상태
				out.writeBoolean(true); out.flush(); //배달중(true) 전송
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			show[5].setText("배달중~★");
		});
		btFinish.addActionListener(e->{
			btFinish.setEnabled(false);
			btStart.setEnabled(true);
			btBack.setEnabled(true);
			try {
				deliveryState = true;
				out.writeBoolean(deliveryState); out.flush(); //배달완료 상태를 서버에 넘김
				JOptionPane.showMessageDialog(null,"배달완료!", "", JOptionPane.INFORMATION_MESSAGE);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			show[5].setText("배달 완료~★");
		});
		
		btBack.addActionListener(e->{
			System.exit(0);
		});
		
	}

	private void menu() {
	}

	private void basicPrint() {

	}
	
	public static void main(String[] args) {
		JDialog jg = new DeliverymanGUI(new Frame(), true);
		jg.setVisible(true);
	}
}
