import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Tester {
	private static ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> beeperHandle;

	public static void main(String[] args) {
		     beeperHandle =
		       scheduler.scheduleAtFixedRate(new Runnable() {
			       public void run() { System.out.println("beep"); beeperHandle.cancel(true); System.out.println("beep2");}
			     }, 1, 1, TimeUnit.SECONDS);
		     scheduler.schedule(new Runnable() {
		       public void run() { beeperHandle.cancel(true); }
		     }, 60 * 60, TimeUnit.SECONDS);

	}

}
