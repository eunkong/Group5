package client;

import java.io.IOException;
import java.net.UnknownHostException;
//
public class Client_1 {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ClientTool.getTool().setClientTool();
			ClientTool.getTool().clientHome();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�ý��� ����");
	}
}
