package entities;
import run.GamePlay;

public interface Interactive {
	public void interact(GamePlay g);
	default public void disappear(Interactive[][] arr, int a, int b) {
		arr[a][b]=null;
	}
}
