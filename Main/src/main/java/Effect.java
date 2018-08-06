
import java.util.function.BiConsumer;

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
}
