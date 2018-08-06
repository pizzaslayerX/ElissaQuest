package items;

import entities.Player;

public class GreaterPotion extends Item implements Consumable{

	public GreaterPotion(String str) {
		super(str);
		desc = "A very helpful item that heals for 20 HP.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.health += 20;
		if(p.health > p.maxHealth)
			p.health = p.maxHealth;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nHeal Amt: 20";
	}

	@Override
	public Item clone() {
		Item greaterPotion = new GreaterPotion(name);
		return greaterPotion;
	}

}
