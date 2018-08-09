package items;

public class VoidFuser extends Item{
//make void deconstructor, which deconstructs a weapon into a bunch of atomic units, which will need to be used up when atomic binding
	int level = 1;
	
	public VoidFuser() {
		super("Void Fuser");
		// TODO Auto-generated constructor stub
	}
	
	public void levelUp() {
		level++;
	}
	
	public Weapon use(Weapon a, Weapon b, int stat) {
		Weapon c = a.clone();
		switch(stat) {
			case 0: //dmg
				c.baseDmg = newStat(a.baseDmg, b.baseDmg);
				break;
			case 1: //range
				break;
			case 2: //flat stamina dep
				c.flatStaminaDepletion = newStat(a.flatStaminaDepletion, b.flatStaminaDepletion);
			case 3: //stamina dep
		}
		return c;
	}
	
	private double newStat(double a, double b) {
		return (a*a+b*b)*(1 + Math.random()*Math.random()*Math.random()*(Math.sqrt(2)-1)/(1+Math.abs(a-b)/20));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Item i) {
		// TODO Auto-generated method stub
		return (i instanceof VoidFuser) && ((VoidFuser)i).level == level;
	}

}
