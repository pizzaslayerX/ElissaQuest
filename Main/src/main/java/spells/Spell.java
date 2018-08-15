package spells;

import java.util.function.Consumer;

import entities.Entity;
import entities.Player;

public class Spell {
	public int manaCost, healthCost, cooldown,cooldownTimer;
	public String name,desc;
	public Player player;
	public boolean useTurn;
	public Consumer<Entity> use;
	
	public Spell(String n,String d,int c, int mc,int hc,boolean ut,Consumer<Entity> u) {
		name = n;
		desc = d;
		cooldown = c;
		cooldownTimer = 0;
		manaCost = mc;
		healthCost = hc;
		useTurn = ut;
		use = u;
	}
	
	public void use(Entity e) {
		if(canUse(e)) {
			use.accept(e);
			cooldownTimer = cooldown;
			e.mana -= manaCost;
			e.health -= healthCost;
		}
	}
	
	public boolean canUse(Entity e) {
		if(cooldownTimer != 0 || e.mana < manaCost || e.health < healthCost) return false;
			return true;
	}
	
	
	public boolean equals(Spell s) {
		return name.equals(s.name) && desc.equals(s.desc);
	}
	
	public static class Spells{
		public static Spell mist() {
			return null;
			
			}
		}
		
	}

