package lab7;
/**
 * 
 * @author seanmcmanus
 *A series of methods written around a point object
 */
public class Point {
	
	private final double x, y;
	/**
	 * Setter
	 * @param x
	 * @param y
	 */
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	/**
	 * Getter
	 * @return x
	 */
	public double getX() {
		return x;
	}
	/**
	 * Getter
	 * @return y
	 */
	public double getY() {
		return y;
	}
	/**
	 * Moves a point in the direction and magnitude of a given vector
	 * @param v1 a vector
	 * @return a new point
	 */
	public Point plus(Vector v1){
		return new Point(this.x + v1.getDeltaX(), this.y + v1.getDeltaY());	//Mutates the original coordinates by the vectors x and y components
	}
	/**
	 * Subtracts two points to generate a vector between the two points
	 * @param p1
	 * @return a vector
	 */
	public Vector minus(Point p1){
		return new Vector(this.x -p1.x, this.y - p1.y);
	}
	/**
	 * Creates a vector between two given points and finds its magnitude to calculate the distance between
	 * @param p1
	 * @return distance between two points
	 */
	public double distance(Point p1){
		return minus(p1).magnitude();	//Generates a vector between the points using the minus method, and then
										//uses the magnitude method to find its length
	}
	/**
	 * To String Method
	 */
	public String toString() {
		return "(" + this.x + " , " + this.y + ")";
	}
	public static void main(String[] args){
	}
}
