package cook;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import master.Menu;
import master.Order;

class Window01 extends JFrame {
	private static Socket socket;
	private File ipFile = new File("files", "ip.txt"); //ip파일
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	{
		try {
			BufferedReader inFile = new BufferedReader(new FileReader(ipFile)); //ip파일 불러오기 통로 생성
			Socket socket = new Socket(InetAddress.getByName(inFile.readLine()), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	final static int COOK = 2;
	public static String[] state = { "", "주문완료", "요리중", "요리완료", "배달중", "배달완료" };
	private static Map<Long, Order> orderlist;
	private static boolean cookFinish, cookStart;

	private JPanel bg = new JPanel();
	private JPanel menuPanel = new JPanel();

	private JLabel lbCook = new JLabel("<요리사>");
	private JLabel lb[] = new JLabel[5];
	private String lbString[] = new String[] { "주문 상태", "주문 번호", "주문 시간", "고객 아이디", "주문 메뉴" };
	private JLabel lbOrderState = new JLabel();
	private JLabel lbOrderNum = new JLabel();
	private JLabel lbOrderTime = new JLabel();
	private JLabel lbId = new JLabel();

	private JButton btCookStart = new JButton("요리 준비");
	private JButton btCookFinish = new JButton("요리 완료");
	private JButton btBack = new JButton("돌아가기");

	private String columnNames[] = { "메뉴명", "수량", "가격(원)" };

	private DefaultTableModel m;
	// DefaultTableModel -> JTable -> JScrollPane
	private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
	private JTable jTable = new JTable(defaultTableModel);
	private JScrollPane jScollPane = new JScrollPane(jTable);

	public Window01() {
		design();
		event();
		menu();

		setTitle("");
		setSize(660, 800);
		setLocation(450, 50);
		setResizable(false);
		setVisible(true);
	}

	private void design() {
		try {
			out.writeInt(COOK);
			out.flush(); // 서버에 요리사(2) 입장
			cookFinish = false;
			cookStart = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(bg);// 배경 설치
		bg.setLayout(null);

		menuPanel.setBounds(150, 350, 450, 280);
		bg.add(menuPanel);

		lbCook.setFont(new Font("굴림", Font.PLAIN, 40));
		lbCook.setBounds(250, 10, 205, 84);
		bg.add(lbCook);

		for (int i = 0; i < lbString.length; i++) {
			lb[i] = new JLabel(lbString[i]);
			lb[i].setText(lbString[i]);
			lb[i].setBounds(50, 100 + i * 60, 80, 30);
			bg.add(lb[i]);
		}

		lbOrderState.setBounds(210, 100, 150, 30);
		bg.add(lbOrderState);

		lbOrderNum.setBounds(210, 160, 150, 30);
		bg.add(lbOrderNum);

		lbOrderTime.setBounds(210, 220, 150, 30);
		bg.add(lbOrderTime);

		lbId.setBounds(210, 280, 150, 30);
		bg.add(lbId);

		btCookStart.setBounds(70, 670, 110, 55);
		bg.add(btCookStart);

		btCookFinish.setBounds(270, 670, 110, 55);
		btCookFinish.setEnabled(false);
		bg.add(btCookFinish);

		btBack.setBounds(470, 670, 110, 55);
		bg.add(btBack);

		menuPanel.add(jScollPane);
		jTable.setRowHeight(20); // 높이 조절
		jTable.setPreferredScrollableViewportSize(new Dimension(370, 250)); // 사이즈
		jTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		jTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
	}

	private void event() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// x 키 누르면 창 닫기
		m = (DefaultTableModel) jTable.getModel(); // 틀 만들기

		btCookStart.addActionListener(e -> {
			btCookStart.setEnabled(false);
			btCookFinish.setEnabled(true);
			btBack.setEnabled(false);

			try {
				orderlist = (Map<Long, Order>) in.readObject(); // 주문서 받기
				if (orderlist != null) {
					Iterator<Long> iterator = orderlist.keySet().iterator();
					Long num = iterator.next();
					Order order = orderlist.get(num);

					lbOrderState.setText(state[order.getOrderState()]); // 주문상태
					lbOrderNum.setText(num.toString());// 주문번호
					lbOrderTime.setText(order.getOrdertime());// 주문시간
					lbId.setText(order.getMember().getId());// 고객아이디
					for (Iterator<Menu> iterator2 = order.getOrderIdx().keySet().iterator(); iterator2.hasNext();) {
						Menu menu = iterator2.next();
						int numb = order.getOrderIdx().get(menu);
						m.insertRow(m.getRowCount(), new Object[] { menu.getName(), numb, menu.getPrice() * numb });// 메뉴
					}
				} else {
					JOptionPane.showMessageDialog(null, "요리할게 없음ㅋ", "", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			cookStart = true;
			cookFinish = false;
			try {
				out.writeBoolean(cookStart);
				out.flush(); // 요리시작 상태 정보를 서버에 넘김
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("요리시작!");
		});

		btCookFinish.addActionListener(e -> {
			btCookFinish.setEnabled(false);
			btCookStart.setEnabled(true);
			btBack.setEnabled(true);
			cookStart = false;
			cookFinish = true;
			try {
				out.writeBoolean(cookFinish);
				out.flush(); // 요리완료 상태 정보를 서버에 넘김
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (jTable.getRowCount() != 0) {
				m.removeRow(0);
			}
			jTable.updateUI();
			lbOrderState.setText(""); // 주문상태
			lbOrderNum.setText("");// 주문번호
			lbOrderTime.setText("");// 주문시간
			lbId.setText("");// 고객아이디
			JOptionPane.showMessageDialog(null, "요리가 완료되었습니다.", "", JOptionPane.INFORMATION_MESSAGE);
		});
		btBack.addActionListener(e -> {
			System.exit(0);
		});
	}

	private void menu() {
	}
}

public class CookGui {
	public static void main(String[] args) {
		JFrame f = new Window01();
	}
}
