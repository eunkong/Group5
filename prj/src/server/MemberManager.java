package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import client.Member;


//memberlist.db를 관리하는 클래스
public class MemberManager {
	public static final File db = new File("files", "memberlist.db");
	
	//파일을 관리할 각종 기능 메소드
	
	//로그인 기능
	// - 준비물 : 아이디, 비밀번호
	// - 처리내용 : 파일에 해당 아이디, 비밀번호가 있는지 검사
	// - 결과물 : 존재하는지 / 존재하지 않는지에 대한 결과값
	public static boolean login(String id, String password){
		
		List<Member> list = loadDatabase();
		
		for(Member m : list) {
			if(m.getId().equals(id) && m.getPwd().equals(password)) {
				return true;
			}
		}
		
		return false;
	}

	private static List<Member> loadDatabase() {
		try(
			ObjectInputStream in = new ObjectInputStream(
														new BufferedInputStream(
															new FileInputStream(db)));
		){
			@SuppressWarnings("unchecked")
			List<Member> list = (List<Member>) in.readObject();
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static boolean register(String id, String password) {
		List<Member> list = loadDatabase();
		
		//list를 전체 조사하여 id가 동일한 멤버가 있으면 가입 불가
		for(Member m : list) {
			if(m.getId().equals(id) ) {
				return false;
			}
		}
		
		list.add(new Member(id, password));
		saveDatabase(list);
		return true;
	}

	private static void saveDatabase(List<Member> list) {
		try(
				ObjectOutputStream out = new ObjectOutputStream(
															new BufferedOutputStream(
																new FileOutputStream(db)));
		){
			out.writeObject(list);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}





