package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import cook.Cook;
import master.MenuSFM;
import master.Order;

class FoodView extends JFrame {

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
			jbtOk[i] = new JButton("����");
			jbtCpt[i] = new JButton("�Ϸ�");
			jbtCncl[i] = new JButton("����");
		}
	}

	private String columnNames[] = { "�ֹ���ȣ", "�޴���", "����" };
	private DefaultTableModel[] defaultTableModel = new DefaultTableModel[TABLE_NUM];
	{
		for (int i = 0; i < TABLE_NUM; i++) {
			defaultTableModel[i] = new DefaultTableModel(null, columnNames);
		}
	}

	// JScrollPane�� JTable�� ���
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

	private JLabel jlabel = new JLabel("¥��� «�� ¥��� «��");

	public FoodView() {
		design();
		event();
		menu();
		setTitle("????");
		setSize(1200, 700);
		setLocation(200, 100);
		// setLocationByPlatform(true); //��ġ�� �ü���� ���ϵ��� ����
		// setAlwaysOnTop(true);//�׻���
		setResizable(false);
		setVisible(true);
		orderShow();
	}

	private void design() {
		setContentPane(bg);// bg�� ��濡 ��ġ�϶�
		// this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(null);

		for (int i = 0; i < TABLE_NUM; i++) {

			jTable[i].setRowHeight(15); // ���� ����
			jTable[i].setPreferredScrollableViewportSize(new Dimension(230, 190)); // ������?
			jTable[i].getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�

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
		jlabel.setFont(new Font("����", Font.PLAIN, 40));
		jlabel.setBounds(350, 0, 705, 116);
		bg.add(jlabel);

	}

	private void event() {
		// JFrame���� �⺻������ �����ϴ� ���� �ɼ�
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //xŰ ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // xŰ ������ â �ݱ�
		// setDefaultCloseOperation(HIDE_ON_CLOSE); //xŰ ������ ����
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //xŰ ����(+�̺�Ʈ����)
		// ����
		jbtOk[0].addActionListener(e -> {
			okAction(0);
		});
		jbtCpt[0].addActionListener(e -> {
			cptAction(0);
		});
		jbtCncl[0].addActionListener(e -> {
			cnclAction(0);
		});

		jbtOk[1].addActionListener(e -> {
			okAction(1);
		});
		jbtCpt[1].addActionListener(e -> {
			cptAction(1);
		});
		jbtCncl[1].addActionListener(e -> {
			cnclAction(1);
		});

		jbtOk[2].addActionListener(e -> {
			okAction(2);
		});
		jbtCpt[2].addActionListener(e -> {
			cptAction(2);
		});
		jbtCncl[2].addActionListener(e -> {
			cnclAction(2);
		});

		jbtOk[3].addActionListener(e -> {
			okAction(3);
		});
		jbtCpt[3].addActionListener(e -> {
			cptAction(3);
		});
		jbtCncl[3].addActionListener(e -> {
			cnclAction(3);
		});

		jbtOk[4].addActionListener(e -> {
			okAction(4);
		});
		jbtCpt[4].addActionListener(e -> {
			cptAction(4);
		});
		jbtCncl[4].addActionListener(e -> {
			cnclAction(4);
		});

		jbtOk[5].addActionListener(e -> {
			okAction(5);
		});
		jbtCpt[5].addActionListener(e -> {
			cptAction(5);
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
		
		
		try (Socket socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
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
			out.writeBoolean(cookStart); out.flush(); //�丮���� ���� ������ ������ �ѱ�
			System.out.println("�丮����!");
//			Thread.sleep(3000);
			cookFinish = true;
			out.writeBoolean(cookFinish); out.flush();
			System.out.println("�丮Rmt!");
			
	
			
		} catch (Exception e) {
			System.out.println("����");
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

		try (Socket socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
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

	
			
		} catch (Exception e) {
			System.out.println("����");
			return ;
		}
		
		
		
	}

	private void menu() {
	}
}

public class Food {
	public static void main(String[] args) {
		new FoodView();
	}
}
