// Style: 15/15
// Grader: Corey Elowsky

package lab6;

import sedgewick.StdDraw;
/**
 * 
 * @author seanmcmanus
 *Draws an equilateral triangle made of smaller triangles using recursion
 */

public class Triangles {
	/**
	 * Draws small triangles within bigger triangles
	 * @param  an x value x0
	 * @param  a y value y0
	 * @param a double, length of the side of the largest triangle 
	 */
	public static void draw(double x0, double y0, double size){
		if(size < .006){	//ends the recursive loop when the size of the smallest triangle is small enough
			return;
		}
		StdDraw.line(x0, y0, x0 + size, y0);	//draws bottom of triangle
		StdDraw.line(x0, y0, x0 + size/2, y0 + size * Math.sqrt(3)/2);	//draws left side of triangle
		StdDraw.line(x0 + size/2, y0 + size * Math.sqrt(3)/2, x0 + size, y0);	//draws right side of triangle
		draw(x0, y0, size/2); //call to draw smaller, bottom left hand triangle
		draw(x0 + size/2, y0, size/2);	//call to draw smaller, lower right hand triangle
		draw(x0 + size/4, (y0 + size * Math.sqrt(3)/4), size/2);	//call to draw top smaller triangle
	}
	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		draw(0, 0, 1);

	}

}
