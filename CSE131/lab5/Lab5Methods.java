/* TA: Aarthi
 * Grade: 15/15
 * 
 * Good job!
 */

package lab5;
/**
 * 
 * @author sean mcmanus
 *Return solutions for various methods to be tested for functionality.
 */

public class Lab5Methods {
	/**
	 * 
	 * @param an integer n
	 * @return the sum of the positive integers n + (n-2) + (n-4) + ...
	 */
	public static int sumDownBy2(int n){
		int sum = 0;
		for(int i = n; i > 0; i = i-2){
			sum += i;
		}
		return sum;
	}
	/**
	 * 
	 * @param a positive integer n
	 * @return the sum 1 + 1/2 + 1/3 + ... + 1/(n-1) + 1/n
	 */
	public static double harmonicSum(int n){
		double sum = 0;
		for(double i = 1; i <= n; i++){
			sum += 1/i;
		}
		return sum;	
	}
	/**
	 * 
	 * @param a positive integer n
	 * @return the sum 1 + 1/2 + 1/4 + 1/8 + ... + 1/Math.pow(2,n)
	 */ 
	public static double geometricSum(int n){
		double sum = 0;
		for(double i = 0; i <= n; i++){
			sum += 1/Math.pow(2, i);
		}
		return sum;	
	}
	/**
	 * 
	 * @param a positive integer a
	 * @param a positive integer b
	 * @return the product a*b
	 */
	public static int multPos(int a, int b){
		int ans = 0;
		for(double i = 1; i <= b; i++){		//Multiplication is one number added together a second number of times. This loop completes that action.
			ans += a;
		}
		return ans;	
	}
	/**
	 * 
	 * @param integer j
	 * @param integer k
	 * @return the product j*k
	 */
	public static int mult(int j, int k){
		int posJ = Math.abs(j);
		int posK = Math.abs(k);
		int ans = multPos(posJ, posK);
		if(j == 0 || k ==0){
			ans = 0;
		}
		if(j < 0){			//Checking if the answer should be negative
			ans = -ans;
		}
		if(k < 0){			//Checking if the answer should be negative
			ans = -ans;
		}
		return ans;
	}
	/**
	 * 
	 * @param an integer n
	 * @param an integer k
	 * REQUIRES: k >= 0
	 * @return the value of n to the power k
	 */
	public static int expt(int n, int k){
		int posN = Math.abs(n);
		int ans = 1;
		for(double i = 1; i <= k ; i++){
			ans *= posN;
		}
		if(n < 0 && k%2 !=0){	//if n was a negative number, and k was odd, the answer must be odd.
			ans = -ans;
		}
		return ans;
	}
}
