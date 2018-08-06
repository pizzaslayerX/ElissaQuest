package items;


public abstract class Item{

	protected String name,desc;
	
	public Item(String str) {
		name = str;
	}
	
	abstract public String toString();
	
	abstract public Item clone();
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
}
