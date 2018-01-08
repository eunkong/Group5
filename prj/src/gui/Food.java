package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import cook.Cook;
import master.MenuSFM;
import master.Order;

public class Food extends JFrame {

	private	Socket socket ;
	ObjectOutputStream out ;
	ObjectInputStream in ;
	
	private static final int TABLE_NUM = 6;

	private JPanel bg = new JPanel(new BorderLayout());

	private JPanel[] jp = new JPanel[TABLE_NUM];
	{
		for (int i = 0; i < TABLE_NUM; i++)
			jp[i] = new JPanel();
	}

	private JButton[] jbtOk = new JButton[TABLE_NUM];
	private JButton[] jbtCpt = new JButton[TABLE_NUM];
	private JButton[] jbtCncl = new JButton[TABLE_NUM];

	{
		for (int i = 0; i < TABLE_NUM; i++) {
			jbtOk[i] = new JButton("수락");
			jbtCpt[i] = new JButton("완료");
			jbtCncl[i] = new JButton("거절");
		}
	}

	private String columnNames[] = { "주문번호", "메뉴명", "수량" };
	private DefaultTableModel[] defaultTableModel = new DefaultTableModel[TABLE_NUM];
	{
		for (int i = 0; i < TABLE_NUM; i++) {
			defaultTableModel[i] = new DefaultTableModel(null, columnNames);
		}
	}

	// JScrollPane에 JTable을 담기
	private JTable[] jTable = new JTable[TABLE_NUM];
	{
		for (int i = 0; i < TABLE_NUM; i++)
			jTable[i] = new JTable(defaultTableModel[i]);
	}

	private JScrollPane[] jScollPane = new JScrollPane[TABLE_NUM];
	{
		for (int i = 0; i < TABLE_NUM; i++)
			jScollPane[i] = new JScrollPane(jTable[i]);
	}

	private JLabel jlabel = new JLabel("짜장면 짬뽕 짜장면 짬뽕");

	private Thread[] thd=new Thread[TABLE_NUM];
	
	public Food() {
		
		design();
		try {
			socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeInt(2);
			out.flush();
			
		} catch (Exception e) {
			socket=null;
			out=null;
			in=null;
		}
		event();
		menu();
		setTitle("????");
		setSize(1200, 700);
		setLocation(200, 100);
		// setLocationByPlatform(true); //위치를 운영체제가 정하도록 설정
		// setAlwaysOnTop(true);//항상위
		setResizable(false);
		setVisible(true);
		orderShow();
	}

	private void design() {
		setContentPane(bg);// bg를 배경에 설치하라
		// this가 아니라 bg에 작업을 수행할 수 있다
		bg.setLayout(null);

		for (int i = 0; i < TABLE_NUM; i++) {

			jTable[i].setRowHeight(15); // 높이 조절
			jTable[i].setPreferredScrollableViewportSize(new Dimension(230, 190)); // 사이즈?
			jTable[i].getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가

			int row = 400 * (i % 3);
			int col = 280 * (i / 3);

			jp[i].setBounds(40 + row, 100 + col, 300, 225);
			bg.add(jp[i]);
			jp[i].add(jScollPane[i]);

			jbtOk[i].setBounds(70 + row, 335 + col, 80, 23);
			jbtCpt[i].setBounds(150 + row, 335 + col, 80, 23);
			jbtCncl[i].setBounds(230 + row, 335 + col, 80, 23);

			bg.add(jbtOk[i]);
			bg.add(jbtCpt[i]);
			bg.add(jbtCncl[i]);
		}

		jlabel.setForeground(Color.RED);
		jlabel.setFont(new Font("굴림", Font.PLAIN, 40));
		jlabel.setBounds(350, 0, 705, 116);
		bg.add(jlabel);

	}

	private void event() {
		// JFrame에서 기본적으로 제공하는 종료 옵션
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //x키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // x키 누르면 창 닫기
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //x키 누르면 숨김
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //x키 방지(+이벤트설정)
		// 수락
		jbtOk[0].addActionListener(e -> {
					okAction(0);
		});
		jbtCpt[0].addActionListener(e -> {
			
			thd[0]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					System.out.println("쓰레드0");
					cptAction(0);
				}
			};
			thd[0].run();
		});
		jbtCncl[0].addActionListener(e -> {
			cnclAction(0);
		});

		jbtOk[1].addActionListener(e -> {
			okAction(1);
		});
		jbtCpt[1].addActionListener(e -> {
			thd[1]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					System.out.println("쓰레드1");
					cptAction(1);
				}
			};
			thd[1].run();
		});
		jbtCncl[1].addActionListener(e -> {
			cnclAction(1);
		});

		jbtOk[2].addActionListener(e -> {
			okAction(2);
		});
		jbtCpt[2].addActionListener(e -> {
			thd[2]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					System.out.println("쓰레드2");
					cptAction(2);
				}
			};
			thd[2].run();
			
		});
		jbtCncl[2].addActionListener(e -> {
			cnclAction(2);
		});

		jbtOk[3].addActionListener(e -> {
			okAction(3);
		});
		jbtCpt[3].addActionListener(e -> {
			thd[3]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					System.out.println("쓰레드3");
					cptAction(3);
				}
			};
			thd[3].run();
		});
		jbtCncl[3].addActionListener(e -> {
			cnclAction(3);
		});

		jbtOk[4].addActionListener(e -> {
			okAction(4);
		});
		jbtCpt[4].addActionListener(e -> {
			thd[4]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					cptAction(4);
				}
			};
			thd[4].run();
		});
		jbtCncl[4].addActionListener(e -> {
			cnclAction(4);
		});

		jbtOk[5].addActionListener(e -> {
			okAction(5);
		});
		jbtCpt[5].addActionListener(e -> {
			thd[5]=new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					cptAction(5);
				}
			};
			thd[5].run();
		});
		jbtCncl[5].addActionListener(e -> {
			cnclAction(5);
		});

	}

	private void okAction(int i) {
		jbtOk[i].setEnabled(false);
	}

	private void cptAction(int num) {
		
		boolean cookFinish, cookStart;
		System.out.println("aa");
		try  {
			//System.out.println("a");
			
			Map<Long, Order> orderlist = (Map<Long, Order>) in.readObject();
			
			int i = 0;
			while (((DefaultTableModel) jTable[i].getModel()).getRowCount() != 0 && i < TABLE_NUM) {
				i++;
			}
			if (i == TABLE_NUM) {
				return ;
			}

			
			System.out.println(orderlist.size());
			
			for (java.util.Iterator<Long> ite = orderlist.keySet().iterator();ite.hasNext() &&  i < TABLE_NUM; ) {
				
				DefaultTableModel dtemp = (DefaultTableModel) jTable[i].getModel();
				
				long numb = ite.next();
				
				
				
				Map<master.Menu, Integer> menuIdx = (orderlist.get(numb)).getOrderIdx();
				for (java.util.Iterator<master.Menu> itr = menuIdx.keySet().iterator(); itr.hasNext();) {
					master.Menu menu = itr.next();
					dtemp.insertRow(dtemp.getRowCount(), new Object[] { numb, menu.getName(), menuIdx.get(menu) });
					
				}
				jTable[i].updateUI();
				i++;
			}
			cookStart = true;
			out.writeBoolean(cookStart); out.flush(); //요리시작 상태 정보를 서버에 넘김
			System.out.println("요리시작!");
//			Thread.sleep(3000);
			cookFinish = true;
			out.writeBoolean(cookFinish); out.flush();
			System.out.println("요리Rmt!");
			
	
			
		} catch (Exception e) {
			System.out.println("ㅅㅂ");
			return ;
		}
		
	
		jbtOk[num].setEnabled(true);
		DefaultTableModel dtemp = (DefaultTableModel) jTable[num].getModel();
		while (dtemp.getRowCount() != 0)
			dtemp.removeRow(0);
		orderShow();
		
		
		
	}

	private void cnclAction(int i) {
		DefaultTableModel dtemp = (DefaultTableModel) jTable[i].getModel();
		
		while (dtemp.getRowCount() != 0)
			dtemp.removeRow(0);
		orderShow();
	}

	private void orderShow() {
		try {
			//System.out.println("a");
			out.writeInt(2);
			out.flush();
			Map<Long, Order> orderlist = (Map<Long, Order>) in.readObject();
			
			int i = 0;
			
			while (((DefaultTableModel) jTable[i].getModel()).getRowCount() != 0 && i < TABLE_NUM) {
				i++;
			}
			if (i == TABLE_NUM) {
				return ;
			}

			
			//System.out.println(orderlist.size());
			
			System.out.println("ddd");
			for (java.util.Iterator<Long> ite = orderlist.keySet().iterator();ite.hasNext() &&  i < TABLE_NUM; ) {
				
				DefaultTableModel dtemp = (DefaultTableModel) jTable[i].getModel();
				
				long numb = ite.next();
				
				
				
				Map<master.Menu, Integer> menuIdx = (orderlist.get(numb)).getOrderIdx();
				for (java.util.Iterator<master.Menu> itr = menuIdx.keySet().iterator(); itr.hasNext();) {
					master.Menu menu = itr.next();
					dtemp.insertRow(dtemp.getRowCount(), new Object[] { numb, menu.getName(), menuIdx.get(menu) });
					
				}
				jTable[i].updateUI();
				i++;
			}

	
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"ERROR", "",JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		
		
	}

	private void menu() {
	}
}
