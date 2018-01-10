package master;
 
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class MenuSample implements Serializable{//¼³°èµµ
protected String name;
protected String group;
protected int price;


	public String getName() {return name;}
	
	abstract void setName(String name);
	public int getPrice() {
	return price;
}
	abstract void setPrice(int price);
	public String getGroup() {
	return group;
 }
}
