package spells;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import entities.Entity;
import entities.Player;

public class Spell {
	public int manaCost, healthCost, cooldown,cooldownTimer;
	public String name,desc;
	public Player player;
	public boolean useTurn;
	public BiConsumer<Entity, Entity> ability;
	
	public Spell(String n,String d,int co, int mc,int hc,boolean ut,BiConsumer<Entity, Entity> u, boolean override) {
		name = n;
		desc = d;
		cooldown = co;
		cooldownTimer = 0;
		manaCost = mc;
		healthCost = hc;
		useTurn = ut;
		if(override) ability = u;
		else ability = (c, a) -> {
			if(canUse(c)) {
				u.accept(c, a);
				cooldownTimer = cooldown;
				c.mana -= manaCost;
				c.health -= healthCost;
			}
		};
	}
	
	public void use(Entity caster, Entity attacked) {
		ability.accept(caster, attacked);
	}
	
	public boolean canUse(Entity e) {
		return cooldownTimer == 0 && e.mana >= manaCost && e.health >= healthCost;
	}
	
	
	public boolean equals(Spell s) {
		return name.equals(s.name) && desc.equals(s.desc);
	}
	
	public static class Spells{
		public static Spell mist() {
			return new Spell("Mist", "Fucking misty", 1, 1, 0, true, (c,a) -> {
				a.health = Math.max(0, a.health - 3);
			}, false);
		}
	}
		
}

