package items;

import java.util.Iterator;

import entities.Player;

public class Antidote extends Item implements Consumable{

	public Antidote(String str) {
		super(str);
		desc = "Removes the 'Poison' status ailment";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		for(int i = 0;i<p.statusEffects.size();i++) {
			if(p.statusEffects.get(i).effect.name().equals("poison"))
				p.statusEffects.remove(i);
		}
			
		
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc;
	}

	@Override
	public Item clone() {
		Item antidote = new Antidote(name);
		return antidote;
	}

}
