package lab8;

//Style Grade 9/15

//TA you don't need to have comments for every line of code

import java.util.Iterator;

import java.util.LinkedList;
/**
 * 
 * @author seanmcmanus
 *Creating a polynomial abstract data type using a pre-built abstract data type, the linked list.
 */
public class Polynomial {

	final private LinkedList<Double> list;

	/**
	 * Constructs a Polynomial with no terms yet.
	 */
	public Polynomial() {
		//
		// Set the instance variable (list) to be a new linked list of Double type
		//
		this.list = new LinkedList<Double>();  //Setting instance variable
	}
	/**
	 * To String Method
	 */
	public String toString() {
		String out = "";
		for(int i = list.size()-1; i >= 0; i--){	//moves through the list and creates a string representation
			if(i == list.size()-1){					//of the polynomial from highest power to lowest.
				out += "" + list.get(i) + "x^" + i + ""; //first term
			}
			else{
				out += "+" + list.get(i) + "x^" + i + ""; //every term after, with a + sign in front
			}
		}

		return out; 
	}
	/**
	 * Adds a term to polynomial
	 * @param coeff
	 * @return a polynomial with the coefficient added to a term on the end
	 */
	public Polynomial addTerm(double coeff) {
		this.list.add(coeff);
		return this;  // required by lab spec
	}
	/**
	 * 
	 * @param x
	 * @return the polynomial evaluated at the value x
	 */
	public double evaluate(double x) {
		double sum = 0;					//keeps a total of the polynomial's value
		for(int i = this.list.size()-1; i >= 0; i--){
			sum = this.list.get(i) + (x * sum);	//Recursively calculates the polynomial according to Horner's method
		} //TA this is not recursive
		return sum;  // FIXME
	}
	/**
	 * Find the derivative of a standard polynomial according to the power rule.
	 * @return the derivative of an inputted polynomial
	 */
	public Polynomial derivative() {
		Polynomial a = new Polynomial();
		for(int i = 1; i < this.list.size(); i++){ //appends the first term and returns a new polynomial 
			a.addTerm(this.list.get(i) * i);	//with coefficients multiplied by the former power of the term
		}
		return a; 
	}
	/**
	 * 
	 * @param another polynomial
	 * @return the sum of the two polynomials
	 */
	//TA you should do something with the really long lines of code so they are readable
	public Polynomial sum(Polynomial another) {
		Polynomial e = new Polynomial();
		for(int i = 0; i < Math.min(another.list.size(), this.list.size()); i++){	//for the length of the shorter polynomial, that many terms are summed
			e.addTerm(this.list.get(i) + another.list.get(i));
		}
		if(another.list.size() > this.list.size()){	//when one is longer than the other, this starts on the longer polynomial where the shorter one ended and 
			for(int j = (another.list.size() - (another.list.size() - this.list.size())); j < another.list.size(); j++){	//finishes the new sum with the values of the longer polynomial
				e.addTerm(another.list.get(j));
			}
		}
		else if(this.list.size() > another.list.size()){
			for(int k = (this.list.size() - (this.list.size() - another.list.size())); k < this.list.size(); k++){
				e.addTerm(this.list.get(k));
			}
		}
		return e;   
	}

	/**
	 * This is the "equals" method that is called by
	 *    assertEquals(...) from your JUnit test code.
	 *    It must be prepared to compare this Polynomial
	 *    with any other kind of Object (obj).  Eclipse
	 *    automatically generated the code for this method,
	 *    after I told it to use the contained list as the basis
	 *    of equality testing.  I have annotated the code to show
	 *    what is going on.
	 */

	public boolean equals(Object obj) {
		// If the two objects are exactly the same object,
		//    we are required to return true.  The == operator
		//    tests whether they are exactly the same object.
		if (this == obj)
			return true;
		// "this" object cannot be null (or we would have
		//    experienced a null-pointer exception by now), but
		//    obj can be null.  We are required to say the two
		//    objects are not the same if obj is null.
		if (obj == null)
			return false;

		//  The two objects must be Polynomials (or better) to
		//     allow meaningful comparison.
		if (!(obj instanceof Polynomial))
			return false;

		// View the obj reference now as the Polynomial we know
		//   it to be.  This works even if obj is a BetterPolynomial.
		Polynomial other = (Polynomial) obj;

		//
		// If we get here, we have two Polynomial objects,
		//   this and other,
		//   and neither is null.  Eclipse generated code
		//   to make sure that the Polynomial's list references
		//   are non-null, but we can prove they are not null
		//   because the constructor sets them to some object.
		//   I cleaned up that code to make this read better.


		// A LinkedList object is programmed to compare itself
		//   against any other LinkedList object by checking
		//   that the elements in each list agree.

		return this.list.equals(other.list);
	}

}
