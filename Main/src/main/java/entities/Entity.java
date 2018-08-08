package entities;
import java.util.ArrayList;

import items.StatusEffect;
import items.Weapon;
import misc.Probability;
import run.ElissaRunner;
import run.GamePlay;

public abstract class Entity { //add armor slots
	public String name;
	public int maxHealth;
	public int baseMaxHealth;
	public int health;
	public int maxMana;
	public int baseMaxMana;
	public int mana;
	public double baseSparkMitigation;
	public double sparkMitigation; //0 - 1
	public double maxStamina;
	public double baseMaxStamina;
	public double stamina;
	public int baseEndurance; // base 0
	public int endurance;
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
	public void attack(Entity e, GamePlay g) { //add slow multi hit display (maybe in enhancements?)
		double sparkMultiplier = 1;
		for(int i = 0; i < currWeapon.hits; i++) {
			double dmg = currWeapon.baseDmg - currWeapon.range + (.7 + .3*Math.random())*currWeapon.range*(1 + stamina/maxStamina - e.stamina/e.maxStamina);
			if(i > 0 && Math.random() < currWeapon.sparkChance) {
				sparkMultiplier *= 1 + (1-e.sparkMitigation)*currWeapon.sparkBonus; 
				//r.text.append("\nSpark!\tx " + (int)(100*(1 + e.sparkMitigation*currWeapon.sparkBonus)) + "%");
			}
			dmg *= sparkMultiplier;
			int critCount = 0;
			for(double[] c : currWeapon.criticals) if(Math.random() < c[0]) {
				dmg *= c[1];
				critCount++;
				//r.pause(500);
				//r.text.append("\n" + (tuple(critCount) + "critical!").toUpperCase() + "\tx" + (int)(c[1]*100) + "%");
			}
			int dmg1 = (int) Math.max(1, Math.round(atkMultiplier*e.dmgMultiplier*(1 - e.defense)*dmg - e.flatDefense));
			int dmg2 = (int) Math.max(1, Math.round(atkMultiplier*dmgMultiplier*e.dmgReflect*(1 - defense)*dmg - flatDefense));
			e.health -= dmg1;
			health = (int)Math.min(currWeapon.lifeSteal*dmg1+health, maxHealth);
			//r.pause(1500);
			//r.text.append("\n" + dmg1 + " damage");
			e.stamina = Math.max(0, e.stamina - 50/(50d+e.endurance)*(dmg1*currWeapon.staminaDepletion + currWeapon.flatStaminaDepletion));
			if(e.dmgReflect != 0) {
				health -= dmg2;
				//r.pause(1500);
				//r.text.append("\n" + dmg2 + " reflect damage");
				stamina = Math.max(0, stamina - 50/(50d+endurance)*(dmg2*currWeapon.staminaDepletion + currWeapon.flatStaminaDepletion));
			}
		}
		for(Probability<StatusEffect> prob : currWeapon.atkEnemyEffects) if(prob.execute()) prob.item.addTo(e);
		for(Probability<StatusEffect> prob : currWeapon.atkSelfEffects) if(prob.execute()) prob.item.addTo(this);
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
		sparkMitigation = baseSparkMitigation;
		endurance = baseEndurance;
		dmgReflect = 0;
		atkMultiplier = dmgMultiplier = 1;
		currWeapon = baseCurrWeapon.clone();
		for(StatusEffect se : statusEffects) if(se.active) se.affect(this);
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
