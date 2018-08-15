package spells;

import java.util.function.Consumer;

import entities.Entity;
import entities.Player;

public class Spell {
	public int manaCost, healthCost, cooldown,cooldownTimer;
	public String name,desc;
	public Player player;
	public boolean useTurn;
	public Consumer<Entity> ability;
	
	public Spell(String n,String d,int c, int mc,int hc,boolean ut,Consumer<Entity> u, boolean override) {
		name = n;
		desc = d;
		cooldown = c;
		cooldownTimer = 0;
		manaCost = mc;
		healthCost = hc;
		useTurn = ut;
		if(override) ability = u;
		else u = e -> {
			if(canUse(e)) {
				ability.accept(e);
				cooldownTimer = cooldown;
				e.mana -= manaCost;
				e.health -= healthCost;
			}
		};
	}
	
	public void use(Entity e) {
		ability.accept(e);
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

