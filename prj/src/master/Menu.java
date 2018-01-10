package master;
 
@SuppressWarnings("serial")
public class Menu extends MenuSample{

	
	public Menu(String name, int price) {//주인용 메뉴
		
		this.setName(name);
		this.setPrice(price);
	}

	@Override
	void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
	}

	@Override
	 void setPrice(int price) {
		// TODO Auto-generated method stub
		this.price=price;
	}


}
