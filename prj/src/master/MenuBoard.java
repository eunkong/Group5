package master;

import java.awt.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MenuBoard extends JFrame{

	private JPanel bg = new JPanel(new BorderLayout());
	
	private JToolBar tb1=new JToolBar();
	private JToolBar tb2=new JToolBar();
	
	private JButton bt1=new JButton("+");//�۾�ũ�� Ű���
	private JButton bt2=new JButton("-");//�۾�ũ�� ���̱�
	
	private ArrayList<JLabel> jb=new ArrayList<>();//�� ���� �����ϱ� ���ؼ� Arraylist
	
	private int fontsize=13;//��Ʈ ������. bt1,bt2 ��ư���� �۾��� Ű��ų� ���϶� ���
	
	public MenuBoard() {
		design();
		event();
		menu();
		
		setTitle("�޴����");
		setSize(600, 500);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//��ġ�� �ü���� ���ϵ��� ����
//		setAlwaysOnTop(true);//�׻���
		setResizable(true);
		setVisible(true);
	}

	private void design() {
//		MenuSFM.menuLoad();
		setContentPane(bg);//bg�� ��濡 ��ġ�϶�
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.add(tb1,BorderLayout.CENTER);
		bg.add(tb2,BorderLayout.NORTH);
		
		tb1.setLayout(new GridLayout(3, (MenuSFM.getGroupString().size()+2)/3));
		
		Set<String> temp=new TreeSet<>(MenuSFM.getGroupString());//�з� �̸��� Set���� ������
		//�ְܼ� ���� ������� ������ �ϱ����� TreeSet
		
		int i=0;//arraylist�� get�� ���� key��
		
		for (Iterator<String> ite = temp.iterator(); ite.hasNext();i++) {//�̸� �̿��� for���� ������ ��� ��� 
			//tb.add(new JLabel(MenuSFM.menuPrintJLabel(ite.next())));
			
			String string = ite.next();//�з��̸�
			jb.add(new JLabel(MenuSFM.menuPrintJLabel(string)));//�з��� ���� �޴��� ��½�ų  String(html�� String �� ����)
			tb1.add(jb.get(i));//����1�� �з��� ���� �޴���� �߰�
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));//ó���� 15�������� ��Ʈ�� ����ϵ��� ����
		}
			tb2.add(bt1);
			tb2.add(bt2);
			tb2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	private void event() {
//		JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)
		bt1.addActionListener(e->{
			fontSizeConntrol(true);
		});
		
		bt2.addActionListener(e->{
			fontSizeConntrol(false);
		});
		//ActionListener�� �̺�Ʈ �߰�
	}

	private void menu() {
		
	}
	private void fontSizeConntrol(boolean flag) {//true�� �ְ� �����ϸ� �۾�ũ�Ⱑ 2���� false�� �ְ� �����ϸ� �۾�ũ�Ⱑ 2 ����
		fontsize=fontsize+(flag?2:-2);
		for (int i = 0; i < jb.size(); i++) {
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));
		}
	}
	
}
