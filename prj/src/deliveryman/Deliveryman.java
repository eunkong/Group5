package deliveryman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import master.Order;

public class Deliveryman {
	public static void main(String[] args){
		boolean state = true; //��� ���� ����
		
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
			System.out.println("��ް��� ���� : " + state);
			out.writeBoolean(state); out.flush(); //���� ����
			
//			in.readObject(); //�ֹ� �ޱ�
			state = false; //��� �Ұ� ����
			out.writeBoolean(state); out.flush();
			
			state = true; //��� ���� ����
			out.writeBoolean(state); out.flush(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
