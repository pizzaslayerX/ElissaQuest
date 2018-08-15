package entities;
import java.util.ArrayList;

import items.Consumable;
import items.Effect;
import items.Equipment;
import items.Inventory;
import items.Item;
import items.StatusEffect;
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
		health = 20;
		maxHealth = baseMaxHealth = 20;
		maxMana = baseMaxMana = 10;
		mana = 10;
		maxStamina = baseMaxStamina = 30;
		stamina = 30;
		healthRegen = baseHealthRegen = 0;
		manaRegen = baseManaRegen = 1;
		staminaRegen = baseStaminaRegen = 3;
		sparkMitigation = baseSparkMitigation = 0;
		endurance = baseEndurance = 0;
		defense = baseDefense = 0;
		flatDefense = baseFlatDefense = 0;
		dmgReflect = 0;
		
		inventory = new Inventory(this);
		spells = new ArrayList<Spell>();
		equippedPots = new Pair<ArrayList<Item>,Integer>(new ArrayList<Item>(),5);  
		
		
		inventory.add(Consumable.Consumables.aether()); // testing
		inventory.add(Consumable.Consumables.superAether());
		inventory.add(Consumable.Consumables.uberPotion());
		inventory.add(Consumable.Consumables.potion());
		inventory.add(Consumable.Consumables.potion());
		inventory.add(Weapon.Weapons.hadesScythe(90));
		new StatusEffect("accuracy", 12, 90).addTo(this);
		new StatusEffect("precision", 12, 90).addTo(this);
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

