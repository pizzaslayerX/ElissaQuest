package maze;
import java.util.ArrayList;

import entities.Interactive;
import entities.Player;
import items.Item;
import misc.Probability;
import run.ElissaRunner;
import run.GamePlay;

public class Chest implements Interactive {
	public ArrayList<Item> loot = new ArrayList<Item>();
	boolean doLootTable;
	
	@SafeVarargs
	public Chest(Probability<Item>... i) {
		for(Probability<Item> ip : i) if(ip.execute()) loot.add(ip.item);
		doLootTable = false;
	}
	
	public Chest() {
		doLootTable = true;
	}
	
	public static ArrayList<Probability<Item>> lootTable(Player p) { //todo
		ArrayList<Probability<Item>> itemProbs = new ArrayList<Probability<Item>>();
		
		return itemProbs;
	}
	@SuppressWarnings("static-access")
	@Override
	public void interact(GamePlay r) { //make this immediately display chest contents
		for(Probability<Item> ip : lootTable(r.player)) if(ip.execute()) loot.add(ip.item);
		//say you've found a chest
		//r.game.userWait();
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		arr[a][b] = null;
	}
}
