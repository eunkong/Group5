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

		MenuSFM.menuLoad();//files에서 menu목록 가져와서 MenuSFM에 재저장(내용을 읽어옴)
		new ForguiShow();//GUI로 메뉴 관리시작
		
		//멀티 TCP 서버
		try(ServerSocket server = new ServerSocket(20000);){
			
			while(true) {
				Socket socket = server.accept();
				System.out.println(socket+" 접속");
				Connection c = new Connection(socket);
				Connection.add(c);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		
	}
}
