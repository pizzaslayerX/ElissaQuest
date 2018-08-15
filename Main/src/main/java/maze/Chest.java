package maze;
import java.util.ArrayList;

import entities.Interactive;
import entities.Player;
import items.Consumable;
import items.Item;
import items.Weapon;
import misc.Pair;
import misc.Probability;
import run.GamePlay;

public class Chest implements Interactive {
	public ArrayList<Item> loot = new ArrayList<Item>();
	boolean doLootTable;
	@SafeVarargs
	public Chest(boolean lt,Probability<Item>... i) {
		doLootTable = lt;
		for(Probability<Item> ip : i) if(ip.execute()) loot.add(ip.item);
	}
	
/*
	public void lootTableChest(Player p) {
		boolean success = false;
		for(Pair<Probability<Item>,Integer> ip : lootTable(p)) 
			if(ip.first.execute()) {
				success = true;
			for(int i=0;i<ip.second;i++) loot.add(ip.first.item);
			}
		if(!success)
			loot.add(lootTable(p).get(0).first.item);
			

	}
	*/
	public static ArrayList<Pair<Probability<Item>,Integer>> lootTable(Player p) { //todo
		ArrayList<Pair<Probability<Item>,Integer>> itemProbs = new ArrayList<Pair<Probability<Item>,Integer>>();
		switch(p.level) {
			case 1:
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.potion(),1/2d),(int)(Math.random()*2)+1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.greaterPotion(),1/3d),1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.aether(),1/2d),(int)(Math.random()*2)+1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.rustyDagger(),1/3d),1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.randWeapon1(),1/4d),1));
				return itemProbs;
		}
		
		
		return itemProbs;
	}
	@SuppressWarnings("static-access")
	@Override
	public void interact(GamePlay r) { //make this immediately display chest contents
		if(doLootTable) for(Pair<Probability<Item>,Integer> ipp : lootTable(r.player)) for(int i = 0; i < ipp.second; i++) if(ipp.first.execute()) loot.add(ipp.first.item);
/*
	public void interact(GamePlay r) { 
		if(lootTable) lootTableChest(r.player);
		
		for(Item i:loot) {
			System.out.println(i.name);
			r.player.inventory.add(i);
		}
		*/
		//make this immediately display chest contents

		//say you've found a chest
		//r.game.userWait();
		for(Item i:loot) {
			System.out.println(i.name);
			r.player.inventory.add(i);
		}
		r.r.enable();
		
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		arr[a][b] = null;
	}
}
