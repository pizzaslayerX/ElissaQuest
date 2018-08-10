package entities;
import java.util.ArrayList;

import items.Consumable;
import items.Equipment;
import items.Inventory;
import items.Item;
import items.Weapon;
import misc.Pair;
import run.GamePlay;
import spells.Spell;

public class Player extends Entity{
	public GamePlay runner;
	public int level;
	public int x;
	public int y;
	
	public Pair<ArrayList<Item>,Integer> equippedPots;
	public Inventory inventory;
	public ArrayList<Spell> spells;
	
	public Player(GamePlay d) {
		runner = d;
		level = 1;
		health = 12;
		maxHealth = 120;
		maxMana = 110;
		mana = 8;
		maxStamina = 30;
		stamina = 15;
		
		inventory = new Inventory(this);
		spells = new ArrayList<Spell>();
		equippedPots = new Pair<ArrayList<Item>,Integer>(new ArrayList<Item>(),5);  
		
		
		inventory.add(Consumable.Consumables.aether()); // testing
		inventory.add(Consumable.Consumables.superAether());
		inventory.add(Consumable.Consumables.uberPotion());
		inventory.add(Consumable.Consumables.potion());
		inventory.add(Consumable.Consumables.potion());
		inventory.add(Weapon.Weapons.fist());
	}
	
	public boolean openSlots(Pair<ArrayList<Item>,Integer> in) {
		if(in.first.size()<in.second) return true;
			return false;
	}
	
	public void equipItem(Item item) {
		if(item instanceof Consumable && openSlots(equippedPots)) equippedPots.first.add(item);
		if(item instanceof Weapon && (baseCurrWeapon == null || baseCurrWeapon.name.equals("Punch"))) {
			baseCurrWeapon = (Weapon)item;
			currWeapon = (Weapon)item;
		}
	}
	

}

