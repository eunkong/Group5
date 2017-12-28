package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class Client_1 {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ClientTool c = new ClientTool();
			c.clientHome();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("시스템 종료");
	}
}
