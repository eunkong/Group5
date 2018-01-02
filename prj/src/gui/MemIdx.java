/**
 * 
 */
package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

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
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(new File("files", "memberlist.db"))));
		Map<String, Member> map = (Map<String, Member>) in.readObject();
		for (String string : map.keySet()) {
			System.out.println("id:"+string);
			System.out.println("pw:"+map.get(string).getPwd());
			System.out.println("address:"+map.get(string).getAddress());
			System.out.println("pnum:"+map.get(string).getPhoneNumber());
			System.out.println();
		}
		in.close();
		
	}

}
