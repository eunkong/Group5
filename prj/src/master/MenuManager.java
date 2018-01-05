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
		MenuSFM.menuLoad();//files에서 menu목록 가져와서 MenuSFM에 재저장(내용을 읽어옴)
		new ForguiShow();//GUI로 메뉴 관리시작

		}
	
}