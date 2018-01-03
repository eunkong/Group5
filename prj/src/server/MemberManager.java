package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import client.Member;

//membermap.db를 관리하는 클래스
public class MemberManager {
	public static final File db = new File("files", "memberlist.db");
	/**
	 * 로그인 메소드
	 * @param id
	 * @param password
	 * @return 해당 Member객체정보
	 */
	public static Member login(String id, String password){
		Map<String, Member> map = loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			if(id.equals(iterator.next())&&password.equals(map.get(id).getPwd())) {
				//파일에서 해당 member리스트 가져오기
				return loadDatabase().get(id);
			}
		}
		return null;
	}

	/**
	 * memberlist.db에서 멤버정보 불러오는 메소드
	 * @return Map<id, Member>
	 */
	public static Map<String, Member> loadDatabase() {
		try(
			ObjectInputStream in = new ObjectInputStream(
														new BufferedInputStream(
															new FileInputStream(db)));
		){
			@SuppressWarnings("unchecked")
			Map<String, Member> map = (Map<String, Member>) in.readObject();
			return map;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
	/**
	 * 회원가입 메소드
	 * @param id
	 * @param password
	 * @param phone
	 * @param address
	 * @return 회원가입 성공여부
	 */
	public static boolean register(String id, String password, String phone, String address) {
		Map<String, Member> map = loadDatabase();
		
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			if(id.equals(iterator.next()))
				return false;
		}
		
		map.put(id, new Member(id, password,phone,address));
		saveDatabase(map);
		return true;
	}

	/**
	 * memberlist.db에 파일 쓰는 메소드
	 * @param map<id,Member>
	 */
	public static void saveDatabase(Map<String, Member> map) {
		try(
				ObjectOutputStream out = new ObjectOutputStream(
															new BufferedOutputStream(
																new FileOutputStream(db)));
		){
			out.writeObject(map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}



