package items;

import entities.Player;
import misc.Pair;

public class Voider extends Item {

	public Voider() {
		super("Voider", "Decomposes items into their constitutive void shards");
		// TODO Auto-generated constructor stub
	}

	public Pair<VoidShard, Integer> decompose(Item i, Player p) {
		p.inventory.remove(i);
		return new Pair<VoidShard, Integer>(VoidShard.vs, VoidShard.value(i));
	}
	
	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Item i) {
		// TODO Auto-generated method stub
		return false;
	}

}
