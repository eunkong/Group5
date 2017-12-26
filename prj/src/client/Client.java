package client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static final int REGISTER = 1;
	private static final int LOGIN = 2;
	public static void main(String[] args) throws IOException {
		int port = 20000;
		InetAddress inet = InetAddress.getByName("192.168.0.243");
		Socket socket = new Socket(inet, port);
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		System.out.println("전송준비");
		
		Member master = new Member("master", "1234");
		
		out.writeInt(LOGIN);
		out.flush();
		System.out.println("int 전송");
		out.writeObject(master);
		out.flush();
		System.out.println("master 전송");
		
		out.close();
		System.out.println("종료");
	}
}
