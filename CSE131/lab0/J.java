package lab0;

import cse131.ArgsProcessor;

public class J {


	public static void main(String[] args) { 
		ArgsProcessor ap = new ArgsProcessor(args);
		int N = ap.nextInt("whats yo numba");
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i % j == 0 || j % i == 0) {
					System.out.print("* "); 
				}
				else {
					System.out.print("  "); 
				}
			}
			System.out.println(i);
		}
	}

}

