package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.Member;


public class Connection extends Thread{
	public static final int REGISTER = 1;
	public static final int LOGIN = 2;
	
	
	//외부데이터 : Connection 전체를 관리하는 기능을 static 형태로 보관
	private static List<Connection> list = new ArrayList<>();
	public static void add(Connection c) {
		list.add(c);
	}
	public static void remove(Connection c) {
		list.remove(c);
	}
	
	//내부데이터
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean flag = true;
	public void kill() {
		try {
			flag = false;
			in.close();
			out.close();
			socket.close();
		}catch(Exception e) {	}
		remove(this);//나를 지워라
	}
	
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.setDaemon(true);
		this.start();
	}
	
	public void run() {
		try {
			while(flag) {
				int select = in.readInt();
				System.out.println("선택 = "+ select);
				
				if(select == REGISTER) {
					String id = in.readUTF();
					String pw = in.readUTF();
					String phone = in.readUTF();
					String address = in.readUTF();
					System.out.println(id);	//Test
					System.out.println(pw);
					System.out.println(phone);
					System.out.println(address);
					boolean result = MemberManager.register(id, pw, phone, address);
					System.out.println(result);
					out.writeBoolean(result); out.flush();
			
				//로그인
				}else if(select == LOGIN) {
					String id = in.readUTF();
					String pw = in.readUTF();
					Member member = MemberManager.login(id, pw);
					System.out.println(member.getAddress());
					out.writeObject(member); out.flush(); 	//로그인 성공시 고객정보 객체 전송
					System.out.println("객체전송완료");
				}
			}
		}catch(Exception e) {
			kill();
		}
	}
}