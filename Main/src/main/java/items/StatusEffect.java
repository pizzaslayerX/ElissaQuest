package items;

import java.util.Collections;

import entities.Entity;

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
	
	private String romanNumeral(int a) {
		if(a == 0) return "";
		if(a >= 4000) return " " + a;
		String numerals = "IVXLCDM";
		String str = "";
		int b = a;
		for(int i = 0; b > 0; i += 2) {
			int c = b % 10;
			switch(c) {
				case 1: case 2: case 3:
					str = String.join("", Collections.nCopies(c, numerals.substring(i, i + 1))) + str;
				case 4:
					str = numerals.substring(i, i + 2) + str;
					break;
				case 5: case 6: case 7: case 8:
					str = numerals.substring(i + 1, i + 2) + String.join("", Collections.nCopies(c - 5, numerals.substring(i, i + 1))) + str;
					break;
				case 9:
					str = numerals.substring(i, i + 1) + numerals.substring(i + 2, i + 3) + str;
			}
			b /= 10;
		}
		return " " + str;
	}
	
	public String toString() {
		return effect.name + romanNumeral(potency);
	}

	@Override
	public int compareTo(StatusEffect se) {
		// TODO Auto-generated method stub
		return effect.compareTo(se.effect);
	}
	
	
}
