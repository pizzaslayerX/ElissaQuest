package items;

import entities.Player;

public class Potion extends Item implements Consumable{

	public Potion(String str) {
		super(str);
		desc = "A helpful item that heals for 10 HP.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.health += 10;
		if(p.health > p.maxHealth)
			p.health = p.maxHealth;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nHeal Amt: 10";
	}

	@Override
	public Item clone() {
		Item potion = new Potion(name);
		return potion;
	}

}
