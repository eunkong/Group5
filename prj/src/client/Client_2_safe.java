package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class Client_2_safe {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ClientTool c = new ClientTool();
			c.clientHome();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�ý��� ����");
	}
}
