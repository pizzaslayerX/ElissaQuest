package items;

import entities.Player;

public class Aether extends Item implements Consumable{

	public Aether(String str) {
		super(str);
		desc = "An edible sphere imbued with magical energy. Restores 25% of Mana.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.mana += p.maxMana / 4;
		if(p.mana > p.maxMana)
			p.mana = p.maxMana;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nMana += 25%";
	}

	@Override
	public Item clone() {
		Item aether = new Aether(name);
		return aether;
	}

}
