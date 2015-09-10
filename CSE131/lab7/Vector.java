/**
 * Style: 15/15
 * Grader: Corey Elowsky
 */

package lab7;
/**
 * 
 * @author seanmcmanus
 *A series of methods written around a vector object.
 */
public class Vector {
	
	private final double deltaX, deltaY;
	
	/**
	 * Setter
	 * @param deltaX
	 * @param deltaY
	 */
	public Vector(double deltaX, double deltaY){
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	/**
	 * Getter
	 * @return
	 */
	public double getDeltaX() {
		return deltaX;
	}
	/**
	 * Getter
	 * @return
	 */
	public double getDeltaY() {
		return deltaY;
	}
	/**
	 * Calculates magnitude of a vector
	 * @return the length of a given vector
	 */
	public double magnitude(){
		return Math.sqrt(this.deltaX*this.deltaX + this.deltaY*this.deltaY); //Calculates magnitude using Pythagorean theorm
	}
	/**
	 * Reverses the sign of the x-magnitude of a vector
	 * @return another vector
	 */
	public Vector deflectX(){
		double x = this.deltaX;
		if(this.deltaX < 0){
			x = Math.abs(this.deltaX);
		}
		else {
			x = -this.deltaX;
		}
		return new Vector(x, this.deltaY);
	}
	/**
	 * Reverses the sign of the y-magnitude of a vector
	 * @return another vector
	 */
	public Vector deflectY(){
		double y = this.deltaY;
		if(this.deltaY < 0){
			y = Math.abs(this.deltaY);
		}
		else if(this.deltaY > 0){
			y = -this.deltaY;
		}
		else {
			y = this.deltaY;
		}
		return new Vector(this.deltaX, y);
	}
	/**
	 * Adds one vector to the original vector
	 * @param v1 a second vector
	 * @return a new vector
	 */
	public Vector plus(Vector v1){
		return new Vector((this.deltaX + v1.deltaX),(this.deltaY + v1.deltaY));
	}
	/**
	 * Subtracts one vector from the original vector
	 * @param v1 a second vector
	 * @return a new vector
	 */
	public Vector minus(Vector v1){
		return new Vector((this.deltaX - v1.deltaX),(this.deltaY - v1.deltaY));
	}
	/**
	 * Resizes the original vector by a factor without changing its direction
	 * @param factor to resize the magnitude of the vector by
	 * @return a resized vector
	 */
	public Vector scale(double factor){
		return new Vector((this.deltaX * factor), (this.deltaY * factor));
	}
	/**
	 * This method takes a vector and resizes it to a given magnitude.
	 * @param magnitude that the return vector should be
	 * @return a vector resized to the given magnitude
	 */
	public Vector rescale(double magnitude){
		double mag = magnitude();	//Uses magnitude method to calculate the starting magnitude
		double scaleFactor = magnitude / mag; //Calculates the scale factor
		if(mag != 0){
		return scale(scaleFactor);	//Resizes the vector using the calculated scale factor and scale method
		}
		else{
			return new Vector(magnitude, 0.0);	//Provides for cases where the input-ed magnitude is 0
		}
	}
	/**
	 * To String Method
	 */
	public String toString() {
		return "[ " + this.deltaX + "," + this.deltaY + " ]";
	}
	public static void main(String[] args){
	}
}

