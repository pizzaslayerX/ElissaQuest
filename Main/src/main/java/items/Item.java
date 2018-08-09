package items;


public abstract class Item{

	public String name,desc;
	
	public Item(String str) {
		name = str;
		desc = "";
	}
	
	public Item(String str, String d) {
		name = str;
		desc = d;
	}
	
	
	abstract public Item clone();
	
	abstract public boolean equals(Item i);
	
}
