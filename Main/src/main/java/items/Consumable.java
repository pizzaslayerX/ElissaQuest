package items;

import java.util.function.Consumer;

import entities.Player;

public class Consumable extends Item{
	
	private Consumer<Player> use;
	
	public Consumable(String s, String d, Consumer<Player> u) {
		super(s,d);
		use = u;
	}
	
	public void use(Player p) {
		use.accept(p);
		p.inventory.remove(this);
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return new Consumable(name, desc, use);
	}
	
	public static class Consumables {
		
		public static Consumable aether() {
			return new Consumable("Aether", "An edible sphere imbued with magical energy. Restores 25% of Mana.", p -> {;
				//Play drink sound
				//remove from inventory
				p.mana = Math.min(p.maxMana, p.mana + p.maxMana / 4);
			});
		}
		
		public static Consumable antidote() {
			return new Consumable("Antidote", "Removes the 'Poison' status ailment", p -> {
				StatusEffect.removeEffect(p, "poison");
			});
		}
		
		public static Consumable darkVial() {
			return new Consumable("Dark Vial", "???", p -> {
				//Play drink sound
				//remove from inventory
				p.health = Math.min(p.maxHealth, p.health + 18);
				new StatusEffect("lifeSteal",2,6).addTo(p);
				new StatusEffect("endurance",10,6).addTo(p);
				new StatusEffect("disenchantment",4,6).addTo(p);
				new StatusEffect("curse",3,3).addTo(p);
			});
		}
		
		public static Consumable greaterPotion() {
			return new Consumable("Greater Potion", "A very helpful item that heals for 20 HP.", p -> {
				p.health = Math.min(p.maxHealth, p.health + 20);
			});
		}
		
		public static Consumable potion() {
			return new Consumable("Potion", "A helpful item that heals for 10 HP.", p -> {
				p.health = Math.min(p.maxHealth, p.health + 10);
			});
		}
		
		public static Consumable quincy() {
			return new Consumable("Quincy", "Life is like a Quincy potion. Ya never know what ya gonna get.", p -> {
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
			});
		}
		
		public static Consumable superAether() {
			return new Consumable("Super Aether", "A tesseract containing a large reservoir of magical energy. Restores 50% of Mana.", p -> {;
				//Play drink sound
				//remove from inventory
				p.mana = Math.min(p.maxMana, p.mana + p.maxMana / 2);
			});
		}
		
		public static Consumable uberPotion() {
			return new Consumable("Uber Potion", "A super rare healing item that will save your life. Heals for 50 HP.", p -> {
				p.health = Math.min(p.maxHealth, p.health + 50);
			});
		}
		
		public static Consumable warrior() {
			return new Consumable("Warrior", "It'll make a man out of you. +15% health regen for 3 turns.", p -> {
				new StatusEffect("vitality",(int)(p.maxHealth * 0.15),3).addTo(p);
			});
		}
		
	}
	
}
