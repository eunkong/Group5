/**
 * 
 */
package gui;

import java.util.*;
import java.io.*;
import client.*;
/**
 * @author 204-26
 * prj
 * @param
 */
public class MemIdx {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(new File("files", "memberlist.db"))));
				){
			Map<String, Member> map = new TreeMap<>((Map<String, Member>) in.readObject());
			int total=map.size();
			for (String string : map.keySet()) {
				System.out.println("id:"+string);
				System.out.println("pw:"+map.get(string).getPwd());
				System.out.println("address:"+map.get(string).getAddress());
				System.out.println("pnum:"+map.get(string).getPhoneNumber());
				System.out.println();
			}	
			System.out.println("รั "+total+"ธํ");
		} catch (Exception e) {
		}
	}

}
