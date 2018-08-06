package items;

import java.util.Collections;

import entities.Entity;
import maze.MultiplierUtils;

public class StatusEffect implements Comparable<StatusEffect> {
	public Effect effect;
	public int potency;
	public int duration;
	public boolean active;
	
	public StatusEffect(String s, int p, int d) {
		effect = Effect.valueOf(s);
		potency = p;
		duration = d;
		active = true;
	}
	
	public StatusEffect(Effect e, int p, int d) {
		effect = e;
		potency = p;
		duration = d;
		active = true;
	}
	
	public static void checkEffects(Entity e) {
		for(int i = 0; i < e.statusEffects.size(); i++) if(--e.statusEffects.get(i).duration <= 0) {
			StatusEffect se = e.statusEffects.remove(i--);
			se.effect.end.accept(e, se.potency);
			e.recalculateStats();
		}
	}
 	
	public void addTo(Entity e) {
		boolean repEff = false;
		for(StatusEffect se : e.statusEffects) if(se.effect.equals(effect)) {
			se = Effect.addEffects(this, se);
			repEff = true;
		}
		if(!repEff) e.statusEffects.add(this);
		Collections.sort(e.statusEffects);
		e.recalculateStats();
	}
	
	public void affect(Entity e) {
		effect.function.accept(e,  potency);
	}
	
	public static boolean hasEffect(Entity e, String s) {
		for(StatusEffect se : e.statusEffects) if(se.effect.equals(Effect.valueOf(s))) return true;
		return false;
	}
	
	
	public String toString() {
		return effect.name + MultiplierUtils.romanNumeral(potency);
	}

	@Override
	public int compareTo(StatusEffect se) {
		// TODO Auto-generated method stub
		return effect.compareTo(se.effect);
	}
	
	
}
