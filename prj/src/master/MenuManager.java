package master;

//import client.Member;
/** 
 * �޴����� ����
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
		};//look&feel �׸� �̸� 
		MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
		new ForguiShow();//GUI�� �޴� ��������

//		new Order(new Member("id", "pwd", "phoneNumber", "address")).orderMain();
		}
	
}