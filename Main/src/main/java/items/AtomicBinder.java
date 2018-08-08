package items;

public class AtomicBinder extends Item{

	int level = 1;
	
	public AtomicBinder() {
		super("Atomic Binder");
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

}
