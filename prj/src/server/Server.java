package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import client.Member;

public class Server {
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	public static File target = new File("files", "menus.db");
	public static void main(String[] args) throws IOException, ClassNotFoundException {

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
				Member m = (Member) in.readObject();
				boolean result = MemberManager.register(m.getId(), m.getPwd());
				out.writeInt(REGISTER); out.flush();
				out.writeBoolean(result); out.flush();
			//로그인
			}else if(select == LOGIN) {
				Member m = (Member) in.readObject();
				boolean result = MemberManager.login(m.getId(), m.getPwd());
				System.out.println(result);	//로그인 성공여부 체크
				out.writeBoolean(result); out.flush();
				
				//로그인 성공하면
				if(result) {
				
				}
			}	
			
			
			
			
			
			
			
			
			
			
			
			
			
		
		}
		
	}
}
