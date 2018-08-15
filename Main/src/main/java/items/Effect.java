package items;

import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.Arrays;

import entities.Entity;
import misc.Probability;

public enum Effect {
	vitality("Vitality", (e, p) -> { 
		e.healthRegen += p;
	}, false),
	charm("Charm", (e, p) -> { 
		e.manaRegen += p;
	}, false),
	endurance("Endurance", (e, p) -> { 
		e.staminaRegen += p;
	}, false),
	superVitality("Supervitality", (e, p) -> { 
		e.healthRegen += (int)(e.maxHealth*p/50d);
	}, false),
	superCharm("Supercharm", (e, p) -> { 
		e.manaRegen += (int)(e.maxMana*p/50d);
	}, false),
	superEndurance("Superendurance", (e, p) -> {
		e.staminaRegen += e.maxStamina*p/50;
	}, false),
	critInverse("Critical Inversion", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[1] = 1/c[1];
	}, true),
	reflection("Reflection", (e, p) -> {
		e.dmgReflect += p/20;
	}, true),
	critNegate("Critical Negation", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[0] = 0;
	}, true),
	precision("Precision", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[0] = 1 - Math.pow(1 - c[0], 1 + p/6d);
	}, true),
	accuracy("Accuracy", (e, p) -> {
		for(double[] c : e.currWeapon.criticals) c[1] = Math.pow(c[1], 1 + p/6d);
	}, true),
	absorption("Absorption", (e, p) -> {
		e.dmgMultiplier *= -p/5d;
	}, true),
	invulnerability("Invulnerability", (e, p) -> {
		e.dmgMultiplier = 0;
	}, true),
	nullify("Nullify", (e, p) -> { //must be first
		for(StatusEffect se : e.statusEffects) if(!se.effect.name().equals("nullify")) se.active = false;
	}, (e, p) -> {
		for(StatusEffect se : e.statusEffects) if(!se.effect.name().equals("nullify")) se.active = false;
	}, (e, p) -> {
		for(StatusEffect se : e.statusEffects) se.active = true;
	}),
	weakness("Weakness", (e,p) -> {
		e.atkMultiplier = Math.pow(.9, p);
	}, true),
	strength("Strength", (e,p) -> {
		e.atkMultiplier = Math.pow(.9, -p); //makes sure it can do negative
	}, true),
	poison("Poison", (e,p) -> {
		e.health -= 2*p;
	}, false),
	fatigue("Fatigue", (e, p) -> {
		e.staminaRegen *= 2/(2d + p);
	}, true),
	disenchantment("Disenchantment", (e,p) -> {
		e.manaRegen *= 2/(2d + p);
	}, true),
	sparkResistance("Spark Resistance", (e, p) -> {
		e.sparkMitigation = 2/(2d + p);
	}, true), 
	curse("Curse", (e,p) -> {
		if(Math.random() < 1 - 4/(4d + p)) e.currWeapon.atkSelfEffects.add(new Probability<StatusEffect>(new StatusEffect(debuff().get((int)(Math.random()*Effect.debuff().size())), (int)(p*(.5 + Math.random()*.5)), (int)(p*(.5 + Math.random()*.5))), 1-20/(20d+p)));
	}, false),
	lifeSteal("Life Steal", (e,p) -> {
		e.currWeapon.lifeSteal += p/20d;
	}, true),
	rejuvenation("Rejuvenation", (e,p) -> {
	 	e.health = (int)Math.min(e.maxHealth, Math.ceil(p/10d*(e.maxHealth-e.health)));
	}, false),
	galvanization("Galvanization", (e,p) -> {
		e.mana = (int)Math.min(e.maxMana, Math.ceil(p/10d*(e.maxMana-e.mana)));
	}, false),
	invigoration("Invigoration", (e,p) -> {
		e.stamina = Math.min(e.maxStamina, p/10d*(e.maxStamina-e.stamina));
	}, false);//add health/mana/stamina boost
	
	
	public String name;
	public BiConsumer<Entity, Integer> function; //upon infliction
	public BiConsumer<Entity, Integer> turn; //every turn
	public BiConsumer<Entity, Integer> end; //at the end
	
	private Effect(String s, BiConsumer<Entity, Integer> f, boolean bool) {
		name = s;
		if(bool) {
			function = f;
			turn = (e, p) -> {};
		} else {
			function = (e, p) -> {};
			turn = f;
		}
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

	public static ArrayList<Effect> buff() {
		return new ArrayList<Effect>(Arrays.asList(vitality, charm, endurance, superVitality, superCharm, superEndurance, reflection, strength, precision, accuracy, absorption, sparkResistance, lifeSteal, rejuvenation, galvanization, invigoration));
	}
}
