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
		MenuSFM.menuLoad();//files���� menu��� �����ͼ� MenuSFM�� ������(������ �о��)
		new ForguiShow();//GUI�� �޴� ��������

		}
	
}