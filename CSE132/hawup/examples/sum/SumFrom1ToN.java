package hawup.examples.sum;

import hawup.core.Task;
import hawup.core.TasksProvider;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class SumFrom1ToN implements TasksProvider<Integer> {
	
	private int N, n;

	public SumFrom1ToN(int N) {
		this(0, N);
	}
	
	public SumFrom1ToN(int n, int N){
		this.N = N;
		this.n = n;
	}

	@Override
	public Set<Task<Integer>> getTasks(int numNodes) {
		int numTasks = numNodes*4;
		int numsPerTask = (this.N - this.n) / numTasks;
		Set<Task<Integer>> ans = new HashSet<Task<Integer>>();
		int lo = this.n;
		if(numsPerTask == 0){
			numsPerTask = 1;
		}
		for (int i=0; i < numTasks; ++i) {
			int hi = Math.min(lo+numsPerTask,this.N);
			Task<Integer> t = new SumLoToHi(lo, hi);
			ans.add(t);
			lo = hi + 1;
		}
		
		return ans;
	}
	
	public String toString() {
		return "Sum from 1 to " + this.N;
	}

}
