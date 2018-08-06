package items;

import entities.Player;

public class Warrior extends Item implements Consumable{

	public Warrior(String str) {
		super(str);
		desc = "It'll make a man out of you. +15% health regen for 3 turns.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		new StatusEffect("vitality",(int)(p.maxHealth * 0.15),3).addTo(p);
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nRegen = 15%. 5 Turns";
	}

	@Override
	public Item clone() {
		Item warrior = new Warrior(name);
		return warrior;
	}

}
