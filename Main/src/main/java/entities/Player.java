package entities;
import java.util.ArrayList;

import items.Consumable;
import items.Item;
import run.GamePlay;

public class Player extends Entity{
	public GamePlay runner;
	private String direction;
	public int level;
	public int x;
	public int y;
	public ArrayList<Item> items;
	public ArrayList<Consumable> pots;
	
	
	public Player(GamePlay d) {
		runner = d;
		level = 1;
		health = 12;
		maxHealth = 20;
		maxMana = 10;
		mana = 8;
		maxStamina = 30;
		stamina = 15;
	}//h
	
	
}
