package items;

public class Equipment extends Item{

	public int type; // 0 - helm, 1 - chest, 2 - leg, 3 - boots, 4 - gloves, 5 - necklace, 6 - ring
	
	public Equipment(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	//Armor, accessories, etc. NYI

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
