package items;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import maze.MultiplierUtils;
import misc.Pair;
import misc.Probability;

public class Weapon extends Item { //add stamina reduction
	public String message; //change into BiFunction<String, String, String>
	public double baseDmg;
	public double range;
	public double[][] criticals; //different types of crits and their probabilies
	public double staminaDepletion;
	public double flatStaminaDepletion;
	public int hits;
	public double sparkChance;
	public double sparkBonus;
	public double lifeSteal;
	

	

	
	public ArrayList<Probability<StatusEffect>> atkEnemyEffects;
	public ArrayList<Probability<StatusEffect>> atkSelfEffects;
	//add std miss chance?
	
	public Weapon(String name, String msg, double a, double b, double[]... c) {
		super(name);
		message = msg;
		baseDmg = a;
		range = b;
		criticals = c;
		staminaDepletion = .5;
		flatStaminaDepletion = baseDmg/3;
		hits = 1;
		lifeSteal = 0;
		sparkChance = 0;
		sparkBonus = 0;
	}
	
	public Weapon(String name, String msg, double a, double b, double sd, double fsd, int h, double sc, double sb, ArrayList<Probability<StatusEffect>> aee, ArrayList<Probability<StatusEffect>> ase, double[]... c) {
		super(name);
		message = msg;
		baseDmg = a;
		range = b;
		criticals = c;
		staminaDepletion = sd;
		flatStaminaDepletion = fsd;
		hits = h;
		lifeSteal = 0;
		sparkChance = sc;
		sparkBonus = sb;
		atkEnemyEffects = aee;
		atkSelfEffects = ase;
	}
	
	public Weapon(String name, String msg, double a, double b, double sd, double fsd, int h, double sc, double sb, double ls, ArrayList<Probability<StatusEffect>> aee, ArrayList<Probability<StatusEffect>> ase, double[]... c) {
		super(name);
		message = msg;
		baseDmg = a;
		range = b;
		criticals = c;
		staminaDepletion = sd;
		flatStaminaDepletion = fsd;
		hits = h;
		sparkChance = sc;
		sparkBonus = sb;
		lifeSteal = ls;
		atkEnemyEffects = aee;
		atkSelfEffects = ase;
	}
	
	public Weapon(String name, String msg, double a, double b, int h, double sc, double sb, ArrayList<Probability<StatusEffect>> aee, ArrayList<Probability<StatusEffect>> ase, double[]... c) {
		super(name);
		message = msg;
		baseDmg = a;
		range = b;
		criticals = c;
		hits = h;
		staminaDepletion = .5;
		flatStaminaDepletion = baseDmg/3;
		sparkChance = sc;
		sparkBonus = sb;
		atkEnemyEffects = aee;
		atkSelfEffects = ase;
		lifeSteal = 0;
	}
	
	public Weapon(String name, String msg, double a, double b, int h, double sc, double sb, double ls, ArrayList<Probability<StatusEffect>> aee, ArrayList<Probability<StatusEffect>> ase, double[]... c) {
		super(name);
		message = msg;
		baseDmg = a;
		range = b;
		criticals = c;
		hits = h;
		staminaDepletion = .5;
		flatStaminaDepletion = baseDmg/3;
		sparkChance = sc;
		sparkBonus = sb;
		lifeSteal = ls;
		atkEnemyEffects = aee;
		atkSelfEffects = ase;
	}
	
	@SuppressWarnings("unchecked")
	public Weapon clone() { //make this clone rest of fields
		return new Weapon(name, message, baseDmg, range, staminaDepletion, flatStaminaDepletion, hits, sparkChance, sparkBonus, lifeSteal, atkEnemyEffects == null ? null : (ArrayList<Probability<StatusEffect>>)atkEnemyEffects.clone(), atkSelfEffects == null ? null : (ArrayList<Probability<StatusEffect>>)atkSelfEffects.clone(), criticals == null ? null : Arrays.stream(criticals).map(double[]::clone).toArray(double[][]::new));
	}
	
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		String crits = "";
		for(double[] c : criticals) {
			String chance = df.format(c[0]*100);
			String damage = df.format(c[1]*100);
			crits += "\n\tChance:" + String.join("", Collections.nCopies(6 - chance.length(), " ")) + chance
			+ "%\tDamage:" + String.join("", Collections.nCopies(6 - damage.length(), " ")) + damage + "%";
		}
		return "Message: " + String.format(message, "You", "[enemy]")
		+ "\nBase damage: " + baseDmg
		+ "\nVariance: " + range
		+ "\nCriticals:" + crits;
	}
	
	public static final class Weapons { //make methods

		public static String[] weaponA = {"Angry","Trusting","Hot","Metal","Chubby","Retarded","Happy","Swift","Szchenic","Demonic","Cold","Copper","Wooden","Silky","Sharp"};
		public static String[] weaponB = {"Ball","Knife","Sword","Balloon","Rapier","Gauntlets","Hatchet","Shovel","Spoon","Sickle","Sling","Bow","Slicer","Stabber","Smasher","Bat"};
		public static String[] weaponC = {"You assault %2$s.","You flail your weapon wildly.","Ouch.","You hit the enemy on the nose!","Your weapon slices through tender meat.","Oof.","You throw your weapon at %2$s."};
		
		public static Weapon fist() {
			return new Weapon("Punch", "%1$s punch %2$s", 1, 0, new double[]{.5, 2});
		}
		
		public static Weapon rustyDagger() {
			return new Weapon("Rusty Dagger", "%1$s drive a rusty dagger into %2$s", (3 + (int)(Math.random()*3)), 2, (4 + (int)(Math.random()*3))/7, 2, .3, .2, new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect("weakness", 2, 3), .3))), null);
		}
		
		public static Weapon ulfbehrt() {
			return new Weapon("Ulfbehrt", "%1$s plunge a jagged viking sword into %2$s", 4 + (int)(Math.random()*5), 3, 1.2, 2, 1, 0, 1, new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect("fatigue", 2, 3), .3))), null, new double[] {.2, 1.4});
		}
		
		public static Weapon bludgeon() {
			return new Weapon("Bludgeon", "%1$s bludgeon %2$s", 10, 4, new double[] {.3, 1.2});
		}
		
		public static Weapon morningStar() {
			return new Weapon("Morning Star", "%1$s bludgeon %2$s with a morning star", 12, 6, new double[] {.3, 1.2}, new double[] {.2, 1.6});
		}
		
		public static Weapon scythe() {
			return new Weapon("Scythe", "%1$s slice %2$s", 8, 7, new double[] {.5, 1.5});
		}
		
		public static Weapon critStaff() {
			return new Weapon("Crit Staff", "A great ball of lightning emerges from the staff and explodes on %2$s", 37, 5, new double[]{.9, 1.1}, new double[]{.8, 1.2}, new double[]{.7, 1.3}, new double[]{.6, 1.4}, new double[]{.5, 1.5}, new double[]{.4, 1.6}, new double[]{.3, 1.7}, new double[]{.2, 1.8}, new double[]{.1, 1.9});
		}
		
		public static Weapon poisonSword(int level) {
			return new Weapon("Poison Sword " + MultiplierUtils.romanNumeral(level), "A toxic slash cuts deep into %2$s", level*(6+Math.random()*2), 4, .5, level*(6+Math.random()*2)/3, 1, 0, 0,
					new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect("poison", level, level*3), .3))), null, new double[]{(.1 + Math.random()*.2)*level+.1, 1.2+ level*.05});
		}
		
		public static Weapon hadesScythe(int level) {
			return new Weapon("Hades's Scythe", "%1$s slice %2$s. A dark purple aura emanates from the wound", (24+Math.random()*12)*level/4, 3*(3+2*Math.random())*level, 4, .4, .3, new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect("curse", level, 5), .3))), null, new double[]{.3, 1.2}, new double[] {.25, 1.4}, new double[] {.1, 2.2});
		}
		
		public static Weapon ryuksSythe(int level) {
			return new Weapon("Ryuk's Scythe", "%1$s slice through %2$s. A red essence travels from the wound to %1$s", (20+Math.random()*16)*level/3, 3, 3, .2, .2, level*.1, null, new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect("lifeSteal", level, level*3), .3))));
		}
		
		public static Weapon randWeapon1() {
			double dmg = Math.random()*Math.random()*16+4;
			return new Weapon(weaponA[(int)(Math.random()*weaponA.length)] + " " + weaponB[(int)(Math.random()*weaponB.length)], weaponC[(int)(Math.random()*weaponC.length)] ,dmg, Math.random()*dmg, (int)(Math.random()*3)+1, Math.random()*.5, Math.random(), new ArrayList<Probability<StatusEffect>>(Arrays.asList(new Probability<StatusEffect>(new StatusEffect(Effect.debuff().get((int)(Math.random()*Effect.debuff().size())), 1, 3), .4*Math.random()))), null);
		}
		
		/*
		
		public static Weapon ratTail() {
			return new Weapon("Rat whip", "%1$s rat tail %2$s", 4, 1, new double[]{.5, 2});
		}*/
	}

	@Override
	public boolean equals(Item i) {          //DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER DO LATER
		// TODO Auto-generated method stub
		

		/*
		Weapon w;
		if(i instanceof Weapon) w = (Weapon)i;
		else return false;
		return name.equals(w.name) && message.equals(w.message) && baseDmg == w.baseDmg;*/
		return false;
	}
}