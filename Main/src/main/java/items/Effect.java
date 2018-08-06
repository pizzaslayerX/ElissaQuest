package items;

import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.Arrays;

import entities.Entity;
import misc.Probability;

public enum Effect {
	vitality("Vitality", (e, p) -> {}, (e, p) -> { 
		e.healthRegen += p;
	}),
	charm("Charm", (e, p) -> {}, (e, p) -> { 
		e.manaRegen += p;
	}),
	endurance("Endurance", (e, p) -> {}, (e, p) -> { 
		e.staminaRegen += p;
	}),
	superVitality("Supervitality", (e, p) -> {}, (e, p) -> { 
		e.healthRegen += (int)(e.maxHealth*p/50d);
	}),
	superCharm("Supercharm", (e, p) -> {}, (e, p) -> { 
		e.manaRegen += (int)(e.maxMana*p/50d);
	}),
	superEndurance("Superendurance", (e, p) -> {}, (e, p) -> {
		e.staminaRegen += e.maxStamina*p/50;
	}),
	critInverse("Critical Inversion", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[1] = 1/c[1];
	}),
	reflection("Reflection", (e, p) -> {
		e.dmgReflect += p/20;
	}),
	critNegate("Critical Negation", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[0] = 0;
	}),
	precision("Precision", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[0] = 1 - Math.pow(1 - c[0], 1 + p/6d);
	}),
	accuracy("Accuracy", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[1] = Math.pow(c[1], 1 + p/6d);
	}),
	absorbtion("Absorbtion", (e, p) -> {
		e.dmgMultiplier *= -p/5d;
	}),
	invulnerability("Invulnerability", (e, p) -> {
		e.dmgMultiplier = 0;
	}),
	nullify("Nullify", (e, p) -> { //must be first
		for(StatusEffect se : e.statusEffects) if(!se.effect.name().equals("nullify")) se.active = false;
	}, (e, p) -> {
		for(StatusEffect se : e.statusEffects) if(!se.effect.name().equals("nullify")) se.active = false;
	}, (e, p) -> {
		for(StatusEffect se : e.statusEffects) se.active = true;
	}),
	weakness("Weakness", (e,p) -> {
		e.atkMultiplier = Math.pow(.9, p);
	}),
	strength("Strength", (e,p) -> {
		e.atkMultiplier = Math.pow(1.25, p);
	}),
	poison("Poison", (e,p) -> {
		e.health -= 2*p;
	}, (e,p) -> {
		e.health -= 2*p;
	}, (e,p) -> {}),
	fatigue("Fatigue", (e, p) -> {
		e.staminaRegen *= 2/(2d + p);
	}),
	disenchantment("Disenchantment", (e,p) -> {
		e.manaRegen *= 2/(2d + p);
	}),
	sparkResistance("Spark Resistance", (e, p) -> {
		e.sparkMitigation = 2/(2d + p);
	}), 
	curse("Curse", (e,p) -> {
		e.currWeapon.atkSelfEffects.add(new Probability<StatusEffect>(new StatusEffect(debuff().get((int)(Math.random()*Effect.debuff().size())), (int)(p*(.5 + Math.random()*.5)), (int)(p*3*(.5 + Math.random()*.5))), 1-20/(20d+p)));
	},(e,p) -> {
		e.currWeapon.atkSelfEffects.add(new Probability<StatusEffect>(new StatusEffect(debuff().get((int)(Math.random()*Effect.debuff().size())), (int)(p*(.5 + Math.random()*.5)), (int)(p*3*(.5 + Math.random()*.5))), 1-20/(20d+p)));
	},null),
	lifeSteal("Life Steal", (e,p) -> {
		e.currWeapon.lifeSteal += p/20d;
	});
	
	public String name;
	public BiConsumer<Entity, Integer> function; //constant for duration of effect
	public BiConsumer<Entity, Integer> turn;
	public BiConsumer<Entity, Integer> end;
	
	private Effect(String s, BiConsumer<Entity, Integer> f) {
		name = s;
		function = f;
		turn = (e, p) -> {};
		end = (e, p) -> {};
	}
	
	private Effect(String s, BiConsumer<Entity, Integer> f, BiConsumer<Entity, Integer> t) {
		name = s;
		function = f;
		turn = t;
		end = (e, p) -> {};
	}
	
	private Effect(String s, BiConsumer<Entity, Integer> f, BiConsumer<Entity, Integer> t, BiConsumer<Entity, Integer> e) {
		name = s;
		function = f;
		turn = t;
		end = e;
	}
	
	public static StatusEffect addEffects(StatusEffect a, StatusEffect b) {
		if(a.potency >= b.potency) return a;
		else return b;
	}

	public static ArrayList<Effect> debuff() {
		return new ArrayList<Effect>(Arrays.asList(critInverse, critNegate, nullify, poison, weakness, disenchantment, fatigue));
	}
}
