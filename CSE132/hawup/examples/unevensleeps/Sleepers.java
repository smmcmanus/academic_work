package hawup.examples.unevensleeps;

import hawup.core.Task;
import hawup.core.TasksProvider;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Sleepers implements TasksProvider<Integer> {
	
	final private int maxSleep;
	public Sleepers(int maxSleep) {
		this.maxSleep = maxSleep;
	}

	@Override
	public Set<Task<Integer>> getTasks(int numNodes) {
		int numTasks = numNodes*16;
		Random r = new Random();
		Set<Task<Integer>> ans = new HashSet<Task<Integer>>();
		for (int i=0; i < numTasks; ++i) {
			int sleepTime = r.nextInt(maxSleep);
			if (r.nextDouble() < .80) {
				sleepTime = sleepTime / 100;
			}
			ans.add(new Sleeper(sleepTime));
		}
		return ans;
	}
	
	public String toString() {
		return "Sleepers with max sleep of " + maxSleep +"ms";
	}

}
