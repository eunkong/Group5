//Map<id, Member>���� ��ȯ

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

//membermap.db�� �����ϴ� Ŭ����
public class MemberManager {
	public static final File db = new File("files", "memberlist.db");
	/**
	 * �α��� �޼ҵ�
	 * @param id
	 * @param password
	 * @return �ش� Member��ü����
	 */
	public static Member login(String id, String password){
		Map<String, Member> map = loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			if(id.equals(iterator.next())) {
				//���Ͽ��� �ش� member����Ʈ ��������
				return loadDatabase().get(id);
			}
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	private static Map<String, Member> loadDatabase() {
		try(
			ObjectInputStream in = new ObjectInputStream(
														new BufferedInputStream(
															new FileInputStream(db)));
		){
			@SuppressWarnings("unchecked")
			Map<String, Member> map = (Map<String, Member>) in.readObject();
			System.out.println(map.get("master"));
			return map;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

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

	private static void saveDatabase(Map<String, Member> map) {
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





