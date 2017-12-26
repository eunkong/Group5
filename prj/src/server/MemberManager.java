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


//memberlist.db�� �����ϴ� Ŭ����
public class MemberManager {
	public static final File db = new File("files", "memberlist.db");
	
	//������ ������ ���� ��� �޼ҵ�
	
	//�α��� ���
	// - �غ� : ���̵�, ��й�ȣ
	// - ó������ : ���Ͽ� �ش� ���̵�, ��й�ȣ�� �ִ��� �˻�
	// - ����� : �����ϴ��� / �������� �ʴ����� ���� �����
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
		
		//list�� ��ü �����Ͽ� id�� ������ ����� ������ ���� �Ұ�
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





