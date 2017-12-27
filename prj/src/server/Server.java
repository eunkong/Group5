package server;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import master.ForguiShow;
import master.MenuSFM;

public class Server {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static File target = new File("files", "menus.db");
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
		new ForguiShow();//GUI�� �޴� ��������
		
		//��Ƽ TCP ����
		try(ServerSocket server = new ServerSocket(20000);){
			
			while(true) {
				Socket socket = server.accept();
				System.out.println(socket+" ����");
				Connection c = new Connection(socket);
				Connection.add(c);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		
	}
}
