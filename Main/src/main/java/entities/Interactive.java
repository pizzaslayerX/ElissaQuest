package entities;
import run.ElissaRunner;

public interface Interactive {
	public void interact(ElissaRunner r);
	public void disappear(Interactive[][] arr, int a, int b);
}
