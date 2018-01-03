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
	private JButton okBt=new JButton("시작");
	private JButton cnBt=new JButton("취소");
	
	public Cook_gui() {
		design();
		event();
		menu();
		
		setTitle("요리사");
		setSize(500, 400);
//		setLocation(100, 100);
		setLocationByPlatform(true);	//위치를 운영체제가 정하도록 설정
//		setAlwaysOnTop(true);//항상위
//		setResizable(false);
		setVisible(true);
	}
	
	private void design() {
		setContentPane(bg);//bg를 배경에 설치하라
		//this가 아니라 bg에 작업을 수행할 수 있다
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
//		JFrame에서 기본적으로 제공하는 종료 옵션
//		setDefaultCloseOperation(EXIT_ON_CLOSE);//x 키 누르면 종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x 키 누르면 창 닫기
//		setDefaultCloseOperation(HIDE_ON_CLOSE);//x키 누르면 숨김
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x키 방지(+이벤트)

	
	}

	private void menu() {
		
	}
	
}
