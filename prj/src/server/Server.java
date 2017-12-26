package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import client.Member;
import master.Menu;
import master.MenuSFM;

public class Server {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static File target = new File("files", "menus.db");
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		MenuSFM.menuLoad();
		MenuSFM.menuPrintConsole();
		
		//소켓연결
		Socket socket = new ServerSocket(20000).accept();
		
		//객체형으로 int, boolean 모두 넘김
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
		
		//회원가입, 로그인
		while(true) {
			int select = in.readInt();
			System.out.println("선택 = "+ select);
			//회원가입
			if(select == REGISTER) {
//				Member m = (Member) in.readObject();
				String id = in.readUTF();
				String pw = in.readUTF();
				String phone = in.readUTF();
				String address = in.readUTF();
				System.out.println(id);
				System.out.println(pw);
				System.out.println(phone);
				System.out.println(address);
				boolean result = MemberManager.register(id, pw, phone, address);
				out.writeBoolean(result); out.flush();
			//로그인
			}else if(select == LOGIN) {
				String id = in.readUTF();
				String pw = in.readUTF();
				boolean result = MemberManager.login(id, pw);
				System.out.println(result);	//로그인 성공여부 체크
				out.writeBoolean(result); out.flush();
				//로그인 성공하면
				if(result) {
					System.out.println("로그인 성공");
				}
			}
			
		}
		
		
		
		
	}
}
