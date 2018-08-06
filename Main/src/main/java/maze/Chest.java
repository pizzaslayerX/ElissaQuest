package maze;
import java.util.ArrayList;

import entities.Interactive;
import entities.Player;
import misc.Probability;
import run.ElissaRunner;

public class Chest implements Interactive {
	public int gold;
	public int xp;
	public ArrayList<Item> loot = new ArrayList<Item>();
	
	@SafeVarargs
	public Chest(int gMin, int gMax, int xpMin, int xpMax, Probability<Item>... i) {
		gold = gMin + (int) (Math.random()*(gMax - gMin + 1));
		xp = xpMin + (int) (Math.random()*(xpMax - xpMin + 1));
		for(Probability<Item> ip : i) if(ip.execute()) loot.add(ip.item);
	}
	
	public Chest(int gMin, int gMax, int xpMin, int xpMax, Player p) {
		gold = gMin + (int) (Math.random()*(gMax - gMin + 1));
		xp = xpMin + (int) (Math.random()*(xpMax - xpMin + 1));
		for(Probability<Item> ip : lootTable(p)) if(ip.execute()) loot.add(ip.item);
	}
	
	public static ArrayList<Probability<Item>> lootTable() { //todo
		ArrayList<Probability<Item>> itemProbs = new ArrayList<Probability<Item>>();
		
		return itemProbs;
	}
	@SuppressWarnings("static-access")
	@Override
	public void interact(ElissaRunner r) { //make this immediately display chest contents
		//say you've found a chest
		//r.game.userWait();
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		arr[a][b] = null;
	}
}
