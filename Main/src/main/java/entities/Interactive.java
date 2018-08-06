package entities;
import run.ElissaRunner;
import run.GamePlay;

public interface Interactive {
	public void interact(GamePlay g);
	public void disappear(Interactive[][] arr, int a, int b);
}
