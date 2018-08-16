package maze;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

import entities.Interactive;
import entities.Player;
import gui.LootScreen;
import gui.MainFightPanel;
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

	public static ArrayList<Pair<Probability<Item>,Integer>> lootTable(Player p) { //todo
		ArrayList<Pair<Probability<Item>,Integer>> itemProbs = new ArrayList<Pair<Probability<Item>,Integer>>();
		switch(p.level) {
			case 1:
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.potion(),0.5),(int)(Math.random()*2)+1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.greaterPotion(),0.33),1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Consumable.Consumables.aether(),0.5),1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.rustyDagger(),0.12),1));
				itemProbs.add(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.randWeapon1(),0.25),1));
				break;
		}
		
		
		return itemProbs;
	}
	@SuppressWarnings("static-access")
	@Override
	public void interact(GamePlay r) { //make this immediately display chest contents
		boolean success = false;
		if(doLootTable) 
		  for(Pair<Probability<Item>,Integer> ipp : lootTable(r.player)) 
			for(int i = 0; i < ipp.second; i++)
				if(ipp.first.execute()) {
					loot.add(ipp.first.item);
					success = true;
				}
		if(!success)
			loot.add(lootTable(r.player).get(0).first.item);
		
		//make this immediately display chest contents

		//say you've found a chest
		//r.game.userWait();
		for(Item i:loot) {
			System.out.println(i.name);
			r.player.inventory.add(i);
		}
		
		r.r.setVisible(false);
		r.openPanel = true;

		r.r.window.add(new LootScreen(r,r.r.chest,loot,r.r.window.getWidth(),r.r.window.getHeight()));
		
	}


	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		arr[a][b] = null;
	}
}
