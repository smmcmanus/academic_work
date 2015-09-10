package lab9;

/**
 * 
 * @author seanmcmanus
 *Creating a ListItem class to explore the linked structure of raw lists.
 */
public class ListItem {
	//
	// Important:  Do NOT make these instance variables private
	// Our testing needs to be able to access them, so leave their
	//   declarations as you see them below.
	//
	final int number;
	ListItem next;

	/**
	 * Creates a single list item.
	 * @param number the value to be held in the item
	 * @param next a reference to the next item in the list
	 */
	ListItem(int number, ListItem next) {
		this.number = number;
		this.next   = next;
	}

	/**
	 * Return a copy of this list using recursion.  No
	 * credit if you use any iteration!  All existing lists should remain
	 * intact -- this method must not mutate anything.
	 * @return a copy of the list.
	 */
	public ListItem duplicate() {
		ListItem copy = new ListItem(this.number, duplicator(this.next));
		return copy;
	}
	/**
	 * Recursive method used to produce the copy of the original list
	 * @param ListItem next, the ListItem following the current ListItem
	 * @return the copied list
	 */
	public ListItem duplicator(ListItem next){
		if(next != null){
			ListItem copy = new ListItem(next.number, duplicator(next.next));	//produces a copy by continually analyzing the next component of the list 
			return copy;
		}
		else return null;	//ends when the next ListItem is null, signaling the end of the list
	}

	/**
	 * Recursively compute the number of elements in the list. No
	 * credit if you use any iteration!  All existing lists should remain
	 * intact.
	 * @return the integer length of the list
	 */
	public int length() {
		int length = 1;
		return lengthCounter(this.next, length); //Calls the length measuring method
	}
	/**
	 * Recursive method to count the items in a list
	 * @param ListItem next, next item in the list
	 * @param i, a counter for the list's length
	 * @return the length of the list
	 */
	public int lengthCounter(ListItem next, int length){
		if(next == null){
			return length; //Ends when the next item is null, meaning the list ends
		}
		else{
			length++;
			return next.lengthCounter(next.next, length); //Adds one for every cycle through the method where the list does not end
		}
	}

	/**
	 * Return a new list, like duplicate(), but every element
	 * appears n times instead of once.  See the web page for details.
	 * You must do this method iteratively.  No credit
	 * if you use any recursion!
	 * @param n a positive (never 0) number specifying how many times to copy each element
	 * @return
	 */

	public ListItem stretch(int n) {
		ListItem prev = null;	//Holds the value of the previous ListItem, used to move the list forward
		ListItem current = null; //Holds the current value of the list being stretched
		ListItem initial = null; //Will hold the template list.
		ListItem stretched = new ListItem(this.number, null); //The ListItem to be returned, started with the first number
		if (n > 1){
			initial = this;
			}
		else {
			initial = this.next;
			}
		current = new ListItem(initial.number,null);
		stretched.next=current;
		prev = stretched;
		for (int i = 2; i<n; i++){	//Finishes the stretching of the first number
			prev=current;	//Moves the list forward one
			current = new ListItem(initial.number,null); //Creates the next ListItem
			prev.next=current; //Moves forward once more
		}
		while (initial.next != null){	//Iterates through the list until the end is reached
			initial = initial.next;

			for (int i = 0; i<n; i++){ //Adds each number in the initial list n times
				prev = current;
				current = new ListItem(initial.number,null);
				prev.next=current;
			}
		}
		return stretched;
	}
	

	/**
	 * Return the first ListItem, looking from "this" forward,
	 * that contains the specified number.  No lists should be
	 * modified as a result of this call.  You may do this recursively
	 * or iteratively, as you like.
	 * @param n, the number to be found
	 * @return the first ListItem that contains n
	 */

	public ListItem find(int n) {
		return finder(this, n);  // FIXME
	}
	/**
	 * Recursive method to move through the list and find n, if possible
	 * @param current, the ListItem being examined
	 * @param n, the number to be found
	 * @return the first ListItem found to contain n
	 */
	public ListItem finder(ListItem current, int n){
		if(current.number == n){ //Returns the ListItem when it finds n
			return current;
		}
		else if(current.next == null){	//Ends the method when the end of the list is reached
			return null;
		}
		else return finder(current.next, n); //Otherwise moves forward one item
	}

	/**
	 * Return the maximum number contained in the list
	 * from this point forward.  No lists should be modified
	 * as a result of this call.  You may do this method recursively
	 * or iteratively,as you like.
	 * @return the maximum integer found in the list
	 */

	public int max() {
		return maxFinder(this,this.number); //Returns the max, starting the max at the value of the first item
	}
	/**
	 * 
	 * @param current, the ListItem being examined
	 * @param max, the current maximum value
	 * @return
	 */
	public int maxFinder(ListItem current, int max){
		if(current.number > max){	//Reassigns the max when a bigger number is found
			max = current.number;
		}
		if(current.next == null){	//Ends when the list end is reached
			return max;
		}
		else return maxFinder(current.next, max);	//Otherwise moves forward one item 	
	}

	/**
	 * Returns a copy of the list beginning at ls, but containing
	 * only elements that are even.
	 * @param ls
	 * @return a copy of the list with above parameters
	 */
	public static ListItem evenElements(ListItem ls) {
		if(ls == null){	//Ends when the list end is reached
			return ls;
		}
		else if(ls.number%2 == 0){	//Adds the current number to the new list if it is even
			return new ListItem(ls.number, evenElements(ls.next));
		}
		else return evenElements(ls.next);	//Otherwise moves forward one item
	}



	/**
	 * Returns a string representation of the values reachable from
	 * this list item.  Values appear in the same order as the occur in
	 * the linked structure.  Leave this method alone so our testing will work
	 * properly.
	 */
	public String toString() {
		if (next == null)
			return ("" + number);
		else
			return (number + " " + next); // calls next.toString() implicitly
	}

}
