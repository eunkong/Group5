package master;

import client.Member;

public class Main {

	public static void main(String[] args) throws Exception {
		
		MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
		new ForguiShow();//GUI�� �޴� ��������

//		Order.orderMain(new Member("id", "pwd", "phoneNumber", "address"));
		}
	
}