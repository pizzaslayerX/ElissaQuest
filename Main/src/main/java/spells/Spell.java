package spells;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import entities.Entity;
import entities.Player;
import items.StatusEffect;

public class Spell {
	public int manaCost, healthCost, cooldown,cooldownTimer;
	public String name,desc;
	public Player player;
	public boolean useTurn;
	public BiConsumer<Entity, Entity> ability;
	public BiPredicate<Entity, Entity> able;
	
	public Spell(String n,String d,int co, int mc,int hc,boolean ut,BiConsumer<Entity, Entity> u, BiPredicate<Entity, Entity> ab, boolean override) {
		name = n;
		desc = d;
		cooldown = co;
		cooldownTimer = 0;
		manaCost = mc;
		healthCost = hc;
		useTurn = ut;
		if(override) ability = u;
		else ability = (c, t) -> {
				u.accept(c, t);
				cooldownTimer = cooldown;
				c.mana -= manaCost;
				c.health -= healthCost;
		};
		if(ab == null) able = (c, t) -> cooldownTimer == 0 && c.mana >= manaCost && c.health >= healthCost;
			else able = ab;
		
	}
	
	
	public void use(Entity caster, Entity target) {
		if(canUse(caster,target))
			ability.accept(caster, target);
	}
	
	
	public boolean canUse(Entity caster, Entity target) {
		return able.test(caster, target);
	}
	
	
	public boolean equals(Spell s) {
		return name.equals(s.name) && desc.equals(s.desc);
	}
	
	public static class Spells{
		public static Spell guard() {
			return new Spell("Guard", "+20% def. for 2 turns", 3, 8, 0, false, (c,t) -> {
				new StatusEffect("susceptible",20,2).addTo(t);
			}, null, false);
			
		}
		
		public static Spell infection() {
			return new Spell("Infectious Wave", "Inflicts Minor Curse and Minor Poison for 3 turns", 2, 6, 0, true, (c,t) -> {
				new StatusEffect("poison",1,3).addTo(t);
				new StatusEffect("curse",1,3).addTo(t);
			}, null, false);
			
		}
		
		public static Spell deshell() {
			return new Spell("Deshell", "Target suffers from -15% def. for 2 turns", 1, 5, 0, false, (c,t) -> {
				new StatusEffect("fragility",15,2).addTo(t);
			}, null, false);
			
		}
		
	}
		
}

