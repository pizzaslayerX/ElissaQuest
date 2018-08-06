package entities;
import java.util.ArrayList;

import items.StatusEffect;
import items.Weapon;
import run.ElissaRunner;

public abstract class Entity { //add armor slots
	public String name;
	public int maxHealth;
	public int baseMaxHealth;
	public int health;
	public int maxMana;
	public int baseMaxMana;
	public int mana;
	public double maxStamina;
	public double baseMaxStamina;
	public double stamina;
	public int healthRegen; //base 0?
	public int baseHealthRegen;
	public int manaRegen;
	public int baseManaRegen;
	public double staminaRegen;
	public double baseStaminaRegen;
	public double defense; //0 - 1 
	public double baseDefense;
	public double flatDefense;
	public double baseFlatDefense;
	public double dmgReflect; //base 0
	public double dmgMultiplier = 1; //base 1
	public double atkMultiplier = 1; //base 1
	public Weapon currWeapon;
	public Weapon baseCurrWeapon;
	public ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();
	
	@SuppressWarnings("static-access")
	public void attack(Entity e, ElissaRunner r) { //add slow multi hit display (maybe in enhancements?)
		double dmg = currWeapon.baseDmg - currWeapon.range + Math.random()*currWeapon.range*(2 + stamina/maxStamina - e.stamina/e.maxStamina);
		int critCount = 0;
		for(double[] c : currWeapon.criticals) if(Math.random() < c[0]) {
			dmg *= c[1];
			critCount++;
			r.pause(500);
			r.text.append("\n" + (tuple(critCount) + "critical!").toUpperCase() + "\tx" + (int)(c[1]*100) + "%");
		}
		int dmg1 = (int) Math.max(1, Math.round(atkMultiplier*e.dmgMultiplier*(1 - e.defense)*dmg - e.flatDefense));
		int dmg2 = (int) Math.max(1, Math.round(atkMultiplier*dmgMultiplier*e.dmgReflect*(1 - defense)*dmg - flatDefense));
		e.health -= dmg1;
		r.pause(1500);
		r.text.append("\n" + dmg1 + " damage");
		e.stamina = Math.max(0, e.stamina - dmg1*currWeapon.staminaDepletion - currWeapon.flatStaminaDepletion);
		if(e.dmgReflect != 0) {
			health -= dmg2;
			r.pause(1500);
			r.text.append("\n" + dmg2 + " reflect damage");
			stamina = Math.max(0, stamina - dmg2*currWeapon.staminaDepletion - currWeapon.flatStaminaDepletion);
		}
	}
	/* avg damage:
		double dmg = baseDmg;
		for(double[] c : criticals) dmg *= 1 - c[0] + c[0]*c[1];
	*/
	
	public void recalculateStats() {
		maxHealth = baseMaxHealth;
		maxMana = baseMaxMana;
		maxStamina = baseMaxStamina;
		healthRegen = baseHealthRegen;
		manaRegen = baseManaRegen;
		staminaRegen = baseStaminaRegen;
		defense = baseDefense;
		flatDefense = baseFlatDefense;
		dmgReflect = 0;
		atkMultiplier = dmgMultiplier = 1;
		currWeapon = baseCurrWeapon.clone();
		for(StatusEffect se : statusEffects) if(se.active) se.affect(this);
	}
	
	private String tuple(int n) {
		if(n < 2) return "";
		String[] firsts = new String[]{"double", "triple", "quadruple", "quintuple", "sextuple", "septuple", "octuple", "nonuple"};
		if(n < 10) return firsts[n - 2] + " ";
		String[] ones = new String[]{"", "un", "duo", "tre", "quattuor", "quin", "sex", "septen", "octo", "novem"};
		String[] tens = new String[]{"decuple", "vigintuple", "trigintuple", "quadragintuple", "quinquagintuple", "sexagintuple", "septuagintuple", "octogintuple", "nongentuple"};
		return ones[n % 10] + tens[n/10 - 1] + " ";
	}
	
	public void regen(int i) {
		health = Math.min(maxHealth, health + i*healthRegen);
		mana = Math.min(maxMana, mana + i*manaRegen);
		stamina = Math.min(maxStamina, stamina + i*staminaRegen);
	}
	
	public void regen() {
		regen(1);
	}
}
