package items;

import entities.Player;

public class SuperAether extends Item implements Consumable{

	public SuperAether(String str) {
		super(str);
		desc = "A tesseract containing a large reservoir of magical energy. Restores 50% of Mana.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.mana += p.maxMana / 2;
		if(p.mana > p.maxMana)
			p.mana = p.maxMana;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nMana += 50%";
	}

	@Override
	public Item clone() {
		Item superaether = new SuperAether(name);
		return superaether;
	}

}
