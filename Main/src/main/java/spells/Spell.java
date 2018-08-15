package spells;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import entities.Entity;
import entities.Player;

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
		if(canUse(caster, target)) ability.accept(caster, target);
	}
	
	public boolean canUse(Entity caster, Entity target) {
		return able.test(caster, target);
	}
	
	
	public boolean equals(Spell s) {
		return name.equals(s.name) && desc.equals(s.desc);
	}
	
	public static class Spells{
		public static Spell mist() {
			return new Spell("Mist", "Fucking misty", 1, 1, 0, true, (c,t) -> {
				t.health = Math.max(0, t.health - 3);
			}, null, false);
		}
	}
		
}

