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
	protected String pic;
	public Weapon[][] attacks;
	public ArrayList<Pair<Probability<Item>,Integer>> drops;
	public int attackMode=0;
	public BiFunction<Enemy, Player, Integer> attackCalc;
	
	public Enemy(ArrayList<Pair<Probability<Item>,Integer>> d, BiFunction<Enemy, Player, Integer> ac, Weapon[]... w) {
		drops = d;
		attackCalc = ac;
		attacks = w;
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
		public static Enemy skeleton() {
			return new Enemy(new ArrayList<Pair<Probability<Item>,Integer>>(Arrays.asList(new Pair<Probability<Item>,Integer>(new Probability<Item>(Weapon.Weapons.rustyDagger(),.35),1))), (u,v) -> {
				return 0;
			}, new Weapon[] {new Weapon("Bone club", "%1$s bone clubs %2$s", 3, 2)});
		}
	}
}
