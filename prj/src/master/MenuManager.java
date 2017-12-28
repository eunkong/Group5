package master;

//import client.Member;
/** 
 * 메뉴관리 메인
 * @author Team5
 * @see MenuBoard
 * @see ForguiShow  
 * @see MenuSFM  
 */
public class MenuManager {
	
	public static void main(String[] args) throws Exception {
		String[] lookNfeel = { "com.jtattoo.plaf.mcwin.McWinLookAndFeel", "com.jtattoo.plaf.smart.SmartLookAndFeel",//2
				"com.jtattoo.plaf.acryl.AcrylLookAndFeel","com.jtattoo.plaf.aero.AeroLookAndFeel","com.jtattoo.plaf.aero.AeroLookAndFeel",//3
				"com.jtattoo.plaf.aluminium.AluminiumLookAndFeel","com.jtattoo.plaf.bernstein.BernsteinLookAndFeel",//2
				"com.jtattoo.plaf.graphite.GraphiteLookAndFeel","com.jtattoo.plaf.hifi.HiFiLookAndFeel","com.jtattoo.plaf.mint.MintLookAndFeel",//3
				"com.jtattoo.plaf.luna.LunaLookAndFeel","com.jtattoo.plaf.mcwin.McWinLookAndFeel"//2
		};//look&feel 테마 이름 
		MenuSFM.menuLoad();//files에서 menu목록 가져와서 MenuSFM에 재저장(내용을 읽어옴)
		new ForguiShow();//GUI로 메뉴 관리시작

//		new Order(new Member("id", "pwd", "phoneNumber", "address")).orderMain();
		}
	
}