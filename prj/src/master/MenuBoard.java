package master;
 
import java.awt.*;
import java.util.*;
import javax.swing.*;
/** 
 * �޴������ GUI�� ���
 * JLabel�� �׳� �����ִ� �뵵
 * @author Team5
 * 
 * @version 1.0
 * @see ForguiShow  
 * @see MenuSFM  
 */
@SuppressWarnings("serial")
public class MenuBoard extends JDialog{
	/** 
	 * @see #design
	 * 
	 */
	private JPanel bg = new JPanel();
	
	private JPanel bg1 = new JPanel();
	
	/** 
	 * ��µǴ� ������ ũ�⸦ ��Ʈ���ϱ� ���� JButton
	 * @see #design
	 * @see #event
	 */
	
	
	/**
	 * �׷캰 �޴��� ���
	 * �� ���� �����ϱ� ���ؼ� Arraylist
	 * @see #design
	 */
	private ArrayList<JLabel> jb=new ArrayList<>();
	
	private int fontsize=13;//��Ʈ ������. bt1,bt2 ��ư���� �۾��� Ű��ų� ���϶� ���
	
	
	public MenuBoard(Frame owner, boolean modal) {
		super(owner, modal);
		design();
		
		setTitle("�޴����");
		setSize(700, 300);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
		setVisible(true);
	}
	/** 
	 * ���ȭ�� ����
	 * @param JPanel bg					back ground�� ��ġ�� JPanel
	 * @param JToolBar tb1				�޴� ����� ����Ʈ�� JToolBar	
	 * @param JToolBar tb2				bt1,bt2�� �� JToolBar	
	 * @param JButton bt1,bt2			�۾�ũ�⸦ ������ JButton
	 * @param ArrayList<JLabel> jb		�з����� bg�� �޴��� ����Ʈ�� JLabel
	 * @param int fontsize				���� ����ũ�⸦ �����ϴ� ����
	 * 
	 */
	private void design() {
//		MenuSFM.menuLoad();
		setContentPane(bg);//bg�� ��濡 ��ġ�϶�
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.add(bg1);
		
		Set<String> temp=new TreeSet<>(MenuSFM.getGroupString());//�з� �̸��� Set���� ������
		//�ְܼ� ���� ������� ������ �ϱ����� TreeSet
		
		int i=0;//arraylist�� get�� ���� key��
		
		for (Iterator<String> ite = temp.iterator(); ite.hasNext();i++) {//�̸� �̿��� for���� ������ ��� ��� 
			//tb.add(new JLabel(MenuSFM.menuPrintJLabel(ite.next())));
			
			String string = ite.next();//�з��̸�
			jb.add(new JLabel(MenuSFM.menuPrintJLabel(string)));//�з��� ���� �޴��� ��½�ų  String(html�� String �� ����)
			bg1.add(jb.get(i));//����1�� �з��� ���� �޴���� �߰�
			jb.get(i).setFont(new Font("", Font.PLAIN, fontsize));//ó���� 15�������� ��Ʈ�� ����ϵ��� ����
		}
	}
	
}
