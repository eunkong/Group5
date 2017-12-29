package deliveryman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import master.Order;

public class Deliveryman {
	public static void main(String[] args){
		boolean state = true; //배달 가능 상태
		
		try(Socket socket = new Socket(InetAddress.getByName("192.168.0.243"), 20000);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
			System.out.println("배달가능 상태 : " + state);
			out.writeBoolean(state); out.flush(); //상태 전송
			
//			in.readObject(); //주문 받기
			state = false; //배달 불가 상태
			out.writeBoolean(state); out.flush();
			
			state = true; //배달 가능 상태
			out.writeBoolean(state); out.flush(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
