package items;

import entities.Player;

public class UberPotion extends Item implements Consumable{

	public UberPotion(String str) {
		super(str);
		desc = "A super rare healing item that will save your life. Heals for 50 HP.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.health += 50;
		if(p.health > p.maxHealth)
			p.health = p.maxHealth;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nHeal Amt: 50";
	}

	@Override
	public Item clone() {
		Item UberPotion = new UberPotion(name);
		return UberPotion;
	}

}
