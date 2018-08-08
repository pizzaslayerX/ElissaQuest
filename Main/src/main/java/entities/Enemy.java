package entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

import items.Weapon;
import items.Item;
import misc.Pair;
import misc.Probability;

import run.GamePlay;

public class Enemy extends Entity implements Interactive{
	protected String pic = "test.jpg";

	public Weapon[][] attacks;
	public ArrayList<Pair<Probability<Item>,Integer>> drops;
	public int attackMode=0;
	public BiFunction<Enemy, Player, Integer> attackCalc;

	public Enemy(String nm, String p, ArrayList<Pair<Probability<Item>,Integer>> dr, BiFunction<Enemy, Player, Integer> ac, int h, int m, double s, double sm, int e, int hr, int mr, double sr, double d, double fd, Weapon[]... w) {
		drops = dr;
		attackCalc = ac;
		attacks = w;
		pic  = "test.png";
		name = nm;
		
		
		baseMaxHealth = h;
		health = h;
		baseMaxMana = m;
		mana = m;
		baseMaxStamina = s;
		stamina = s;
		baseSparkMitigation = sm;
		baseEndurance = e; // base 0
		baseHealthRegen = hr;
		baseManaRegen = mr;
		baseStaminaRegen = sr;;
		baseDefense = d;
		baseFlatDefense = fd;
		dmgMultiplier = 1; //base 1
		atkMultiplier = 1; //base 1
		choseRandWeapon();
	}
	
	public void choseRandWeapon() {
		baseCurrWeapon = attacks[attackMode][(int)(Math.random()*attacks[attackMode].length)];
		recalculateStats();
	}
	
	@Override
	public void interact(GamePlay r) {
		r.newFight(this);
	}

	@Override
	public void disappear(Interactive[][] arr, int a, int b) {
		// TODO Auto-generated method stub
		
	}
	
	public String getPic() {
		return pic;
	}
	
	public void changePic(String p) {
		//NYI
	}
	

	public static class Enemies {
		/*
		 * 5 normal enemies
		 * 3 hard enemies
		 * 2 dungeon bosses
		 * 1 floor boss
		 */
		
		//forbidden forest
		
		//normal
		
		//hard
		
		//dungeon - wendigo
		
		//boss - basilisk
		
		//secluded swamp
		
		//normal
		
		public static Enemy skeleton() {
			return new Enemy("Skeleton","Skeleton.png",new ArrayList<Pair<Probability<Item>,Integer>>(Arrays.asList(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.rustyDagger(),.35),1))), (u,v) -> {
				return 0;
			}, 10, 10, 10, 0, 0, 0, 1, 2, 0, 0, new Weapon[] {new Weapon("Bone club", "%1$s bone clubs %2$s", 3, 2)});
		}
		
		//hard
				
		//dungeon
				
		//boss - plant thing
				
		
		//rotted ruins
		
		//normal - ghoul
		
		//hard
				
		//dungeon
				
		//boss
				
		
		//arctic alps
		
		//normal
		
		//hard
				
		//dungeon
				
		//boss
				
		
		//dark dungeon
		
		//normal
		
		//hard
				
		//dungeon
				
		//boss
				
		
		//hagridden hell
		
		//normal
		
		//hard
				
		//dungeon
				
		//boss - hades
				
	}

}
