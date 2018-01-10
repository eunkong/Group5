package master;
 
import java.io.Serializable;
import java.util.*;

import client.Member;


@SuppressWarnings("serial")
public class Order implements Serializable {
	static Scanner sc = new Scanner(System.in);
	public Calendar cal = Calendar.getInstance();;
	private int priceSum;
	private String ordertime;
	private Map<Menu, Integer> orderIdx = new HashMap<>();
	private int orderState=1;
	
	int idxnum;

	Member member;

	public Order(Member member) {
		this.member = member;
	
		
	}

	

	//test
	public void order(Map<Menu,Integer> ordertemp) {
		
		priceSum=0;
		for (Menu menu : ordertemp.keySet()) {
			priceSum+=menu.getPrice()*ordertemp.get(menu);
		}
		orderIdx=ordertemp;
		member.setOrderCount(member.getOrderCount() + 1);
		
	}

	//setter getter
	public int getPriceSum() {
		return priceSum;
	}

	public Map<Menu, Integer> getOrderIdx() {
		return orderIdx;
	}

	public static Scanner getSc() {
		return sc;
	}

	public Member getMember() {
		return member;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	
	
}
