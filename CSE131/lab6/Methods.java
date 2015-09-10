package lab6;
/**
 * 
 * @author seanmcmanus
 *Demonstrates two different examples of simple recursive functions
 */
public class Methods {
	
	//
	// In this class, implement the f and g functions described on the lab page
	//

	/**
	 *Always returns 91. A recursive function 
	 * @param an integer x
	 * @return an integer calculated through recursion
	 */
	public static int f(int x){
		if(x > 100){
			return x - 10;
		}
		else{
			return f(f(x+11));
		}
	}
	/**
	 * A recursive function. 
	 * @param an integer x
	 * @param another integer y
	 * @return an integer based on the recursive function
	 */
	public static int g(int x, int y){
		if(x == 0){
			return (y + 1);
		}
		else if(x > 0 && y ==0){
			return g(x - 1, 1);
		}
		else
			return g((x - 1), g(x, y-1));
	
	}
	/**
	 * Main method
	 * @param args
	 * prints answers returned by the recursive loops
	 */
	public static void main(String[] args) {
		System.out.println(f(99));
		System.out.println(f(87));
		System.out.println(g(1, 0));
		System.out.println(g(1, 2));
		System.out.println(g(2, 2));
		//
		// from here, call f or g with the appropriate parameters
		//

	}

}
