package master;

import client.Member;

public class Main {

	public static void main(String[] args) throws Exception {
		
		MenuSFM.menuLoad();//files에서 menu목록 가져와서 MenuSFM에 재저장(내용을 읽어옴)
		new ForguiShow();//GUI로 메뉴 관리시작

//		Order.orderMain(new Member("id", "pwd", "phoneNumber", "address"));
		}
	
}