package items;

public class VoidShard extends Item {
	public static VoidShard vs = new VoidShard();
	
	private VoidShard() {
		super("Void Shard", "An elemental fragment which makes up many types of objects in the world. Can be obtained by decomposing an item with a voider, and may be used by a void binder to combine items.");
		// TODO Auto-generated constructor stub
	}
	
	public static int value(Item i) {
		return 0;
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Item i) {
		// TODO Auto-generated method stub
		return i instanceof VoidShard;
	}

}
