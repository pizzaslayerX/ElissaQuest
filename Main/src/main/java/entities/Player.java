package entities;
import java.util.ArrayList;

import items.Consumable;
import items.Equipment;
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
	private ArrayList<Pair<Item,Integer>> inventory;
	private ArrayList<Pair<Item,Integer>> pots;
	private ArrayList<Pair<Item,Integer>> weapons;
	private ArrayList<Pair<Item,Integer>> equipment;
	private ArrayList<Spell> spells;
	
	public Player(GamePlay d) {
		runner = d;
		level = 1;
		health = 12;
		maxHealth = 20;
		maxMana = 10;
		mana = 8;
		maxStamina = 30;
		stamina = 15;
		
		inventory = new ArrayList<Pair<Item,Integer>>();
		pots = new ArrayList<Pair<Item,Integer>>();
		weapons = new ArrayList<Pair<Item,Integer>>();
		equipment = new ArrayList<Pair<Item,Integer>>();
		spells = new ArrayList<Spell>();
	}
	
	
	public void removeItem(Item i,int amt) {
		if(!containsItem(i)) {System.out.println("No item to remove!");return;}
		inventory.get(findItem(i,inventory)).second -= amt;
		
	}
	
	public boolean containsItem(Item i) {
		for(Pair<Item,Integer> p : inventory)
			if(p.first.getName().equals(i.getName()))
				return true;
		return false;
	}
	
	public int findItem(Item i,ArrayList<Pair<Item,Integer>> list) {
		for(int c = 0;c<list.size();c++)
			if(list.get(c).first.getName().equals(i.getName()))
				return c;
		return -1;
	}
	
	public void addItem(Item i) {
		if(containsItem(i)) {
			for(int c = 0;c<inventory.size();c++) {
				if(inventory.get(c).first.getName().equals(i.getName())) {
					inventory.get(c).second += 1;
					if(i instanceof Consumable) pots.get(findItem(i,pots)).second++;
					else if(i instanceof Weapon)  weapons.get(findItem(i,weapons)).second++;
					else if(i instanceof Equipment)  equipment.get(findItem(i,equipment)).second++;
				}
			}
		}else {
			inventory.add(new Pair<Item,Integer>(i,1));
			if(i instanceof Consumable) pots.add(new Pair<Item,Integer>(i,1));
			else if(i instanceof Weapon)  weapons.add(new Pair<Item,Integer>(i,1));
			else if(i instanceof Equipment)  equipment.add(new Pair<Item,Integer>(i,1));
		}
	}
	public void addItem(Item i,int amt) {
		if(containsItem(i)) {
			for(int c = 0;c<inventory.size();c++) {
				if(inventory.get(c).first.getName().equals(i.getName())) {
					inventory.get(c).second += amt;
					if(i instanceof Consumable) pots.get(findItem(i,pots)).second+=amt;
					else if(i instanceof Weapon)  weapons.get(findItem(i,weapons)).second+=amt;
					else if(i instanceof Equipment)  equipment.get(findItem(i,equipment)).second+=amt;
				}
			}
		}else {
			inventory.add(new Pair<Item,Integer>(i,amt));
			if(i instanceof Consumable) pots.add(new Pair<Item,Integer>(i,amt));
			else if(i instanceof Weapon)  weapons.add(new Pair<Item,Integer>(i,amt));
			else if(i instanceof Equipment)  equipment.add(new Pair<Item,Integer>(i,amt));
		}
	}

}

