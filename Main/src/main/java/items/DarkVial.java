package items;

import entities.Player;

public class DarkVial extends Item implements Consumable{

	public DarkVial(String str) {
		super(str);
		desc = "???";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		p.health += 18;
		if(p.health > p.maxHealth)
			p.health = p.maxHealth;
		new StatusEffect("lifeSteal",2,6).addTo(p);
		new StatusEffect("endurance",10,6).addTo(p);
		new StatusEffect("disenchantment",4,6).addTo(p);
		new StatusEffect("curse",3,3).addTo(p);
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\nSpooky";
	}

	@Override
	public Item clone() {
		Item darkVial = new DarkVial(name);
		return darkVial;
	}

}
