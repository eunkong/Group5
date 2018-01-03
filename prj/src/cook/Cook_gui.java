/**
 * 
 */
package cook;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

/**
 * @author 204-26
 * prj
 * @param
 */
public class Cook_gui extends JFrame{

	private JPanel bg = new JPanel(new BorderLayout());
	
	private JToolBar tb1=new JToolBar();
	private JToolBar tb2=new JToolBar();
	
	private JLabel jb=new JLabel("dd");
	private JButton okBt=new JButton("����");
	private JButton cnBt=new JButton("���");
	
	public Cook_gui() {
		design();
		event();
		menu();
		
		setTitle("�丮��");
		setSize(500, 400);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//��ġ�� �ü���� ���ϵ��� ����
//		setAlwaysOnTop(true);//�׻���
//		setResizable(false);
		setVisible(true);
	}
	
	private void design() {
		setContentPane(bg);//bg�� ��濡 ��ġ�϶�
		//this�� �ƴ϶� bg�� �۾��� ������ �� �ִ�
		bg.setLayout(new BorderLayout());
		bg.add(tb1,BorderLayout.CENTER);
		bg.add(tb2,BorderLayout.SOUTH);
		
		tb1.add(jb,BorderLayout.CENTER);
		tb2.setLayout(new GridLayout(1,2));
		tb2.add(okBt);
		tb2.add(cnBt);
		
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.246"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				){
			
			
		}catch (Exception e) {
					// TODO: handle exception
				}
	}

	private void event() {
//		JFrame���� �⺻������ �����ϴ� ���� �ɼ�
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x Ű ������ ����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x Ű ������ â �ݱ�
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//xŰ ������ ����
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//xŰ ����(+�̺�Ʈ)

	
	}

	private void menu() {
		
	}
	
}
