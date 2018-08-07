package spells;

import entities.Player;

public abstract class Spell {
	protected int manaCost;
	protected String name;
	
	public Spell(Player p) {
		
	}
	
	public abstract void use();
}
