package items;

public class VoidShard extends Item {
	public static VoidShard vs = new VoidShard();
	private static final double EXPECTED_ENEMY_MAX_STAMINA_TO_PLAYER_DAMAGE_RATIO = 1; //the expected value of EMS/PD where EMS is enemy max stamina and PD is amount of damage a player does in one attack w/o range factored in
	private static final double EXPECTED_ENEMY_MAX_HEALTH_TO_PLAYER_MAX_HEALTH_RATIO = 10;
	
	private VoidShard() {
		super("Void Shard", "An elemental fragment which makes up many types of objects in the world. Can be obtained by decomposing an item with a voider, and may be used by a void binder to combine items.");
		// TODO Auto-generated constructor stub
	}
	
	public static int value(Item i) {
		double val = 0;
		if(i instanceof Weapon) {
			Weapon w = (Weapon)i;
			double mult = 1;
			for(double[] c : w.criticals) mult *= 1 - c[0] + c[0]*c[1];
			double val1 = mult;
			for(int j = 1; j < w.hits; j++) mult += Math.pow(1+w.sparkChance*w.sparkBonus,j)*val1; //this is (probably)wrong. consider all possibilities of sequences of sparks
			double dmg = mult*w.baseDmg;
			double stm = dmg*w.staminaDepletion + w.flatStaminaDepletion;
			dmg += mult*w.range*stm/(dmg*EXPECTED_ENEMY_MAX_STAMINA_TO_PLAYER_DAMAGE_RATIO);
			val = dmg*(1+w.lifeSteal*EXPECTED_ENEMY_MAX_HEALTH_TO_PLAYER_MAX_HEALTH_RATIO);
			//ad status effect calculation
		}
		return (int)val;
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Item i) {
		// TODO Auto-generated method stub
		return i instanceof VoidShard;
	}

}
