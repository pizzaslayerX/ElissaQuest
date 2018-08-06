package items;

import entities.Player;

public class Quincy extends Item implements Consumable{

	public Quincy(String str) {
		super(str);
		desc = "Life is like a Quincy potion. Ya never know what ya gonna get.";
	}

	@Override
	public void use(Player p) {
		//Play drink sound
		//remove from inventory
		for(int i = 0;i<2;i++) {
			int choice = (int)(Math.random() * 21);
			switch(choice) {
				case 0:
					new StatusEffect("vitality",(int)(Math.random() * 21)+5,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 1:
					new StatusEffect("charm",(int)(Math.random() * 21)+5,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 2:
					new StatusEffect("endurance",(int)(Math.random() * 6)+5,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 3:
					new StatusEffect("critInverse",0,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 4:
					new StatusEffect("reflection",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 5:
					new StatusEffect("critNegate",0,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 6:
					new StatusEffect("precision",(int)(Math.random() * 2)+1,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 7:
					new StatusEffect("accuracy",(int)(Math.random() * 2)+1,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 8:
					new StatusEffect("absorbtion",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 9:
					new StatusEffect("invulnerability",0,(int)(Math.random() * 1)+1).addTo(p);
					break;
				case 10:
					new StatusEffect("weakness",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 11:
					new StatusEffect("strength",(int)(Math.random() * 1)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 12:
					new StatusEffect("poison",(int)(Math.random() * 3)+2,(int)(Math.random() * 3)+1).addTo(p);
					break;
				case 13:
					new StatusEffect("fatigue",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 14:
					new StatusEffect("disenchantment",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 15:
					new StatusEffect("sparkResistance",(int)(Math.random() * 2)+1,(int)(Math.random() * 5)+1).addTo(p);
					break;
				case 16:
					p.health = p.maxHealth;
					break;
				case 17:
					p.health = 1;
					break;
			}
		}
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nDescription: " + desc + "\n trolllllllllllol";
	}

	@Override
	public Item clone() {
		Item q = new Quincy(name);
		return q;
	}

}
