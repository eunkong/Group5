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
			if(id.equals(iterator.next())&&password.equals(map.get(id).getPwd())) {
				//���Ͽ��� �ش� member����Ʈ ��������
				return loadDatabase().get(id);
			}
		}
		return null;
	}

	/**
	 * memberlist.db���� ������� �ҷ����� �޼ҵ�
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
	 * ȸ������ �޼ҵ�
	 * @param id
	 * @param password
	 * @param phone
	 * @param address
	 * @return ȸ������ ��������
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
	 * ȸ������ �޼ҵ�(�����ε�)
	 * @param id
	 * @param password
	 * @param phone
	 * @param address
	 * @param grade
	 * @param orderCount
	 * @param point
	 * @return
	 */
	public static boolean register(String id, String password, String phone, String address, String grade, int orderCount, int point) {
		Map<String, Member> map = loadDatabase();
		
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			if(id.equals(iterator.next()))
				return false;
		}
		
		map.put(id, new Member(id, password,phone,address,grade,orderCount,point));
		saveDatabase(map);
		return true;
		
	}
	
	
	/**
	 * ȸ������ ���� �޼ҵ�
	 * @param Member
	 */
	public static Member editInfo(Member member){
		String editId = member.getId();
		String editPwd = member.getPwd();
		String editPhone = member.getPhoneNumber();
		String editAddress = member.getAddress();
		
		Map<String, Member> map = loadDatabase();
		Iterator<String> iterator = map.keySet().iterator();
		
		while(iterator.hasNext()) {
			if(editId.equals(iterator.next())) {
				Member mem = map.get(editId);
				mem.setPwd(editPwd);
				mem.setPhoneNumber(editPhone);
				mem.setAddress(editAddress);
				//db�� �ִ´�.
				map.put(editId, mem);	//��� �ֽ�ȭ
				saveDatabase(map);
				return member;
			}
		}
		return null;
	}
	
	
	/**
	 * memberlist.db�� ���� ���� �޼ҵ�
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



