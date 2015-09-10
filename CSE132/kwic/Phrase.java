package kwic;
import java.util.HashSet;

import java.util.Set;
import java.util.StringTokenizer;

/**
* @author seanmcmanus
* Lab Section G
* sean.mcmanus@wustl.edu
* CSE132 Lab 2a
* Phrase.java
* Represents a phrase, constructed of a set of Words
*/
public class Phrase {

	final protected String phrase;

	public Phrase(String s){
		phrase = s;
	}

	/** 
	 * Provide the words of a phrase.  
	 * Each word may have to be cleaned up:  
	 * punctuation removed, put into lower case
	 */

	public Set<Word> getWords() {
		Set<Word> set = new HashSet<Word>();
		StringTokenizer st = new StringTokenizer(phrase, " ");
		while(st.hasMoreTokens()){
			Word w = new Word(cleanUp(st.nextToken()));	//Adds cleaned version of each word to the Set
			set.add(w);
		}
		return set;
	}

	/** The behavior of this lab depends on how you view this method.
      Are two phrases the same because they have the same words?
      Or are they the same because they are string-equlivalent.
      This method asserts two phrases are equal if they have the same words.
      Compares cleanUp of two phrases.
	 */
	public boolean equals(Object o ) {
		Phrase p = (Phrase) o;
		if (phrase == null) {
			if (o != null)
				return false;
		} else if (!(cleanUp(phrase).equals(cleanUp(p.toString())))){ 
			return false;
		}
		return true;
	}

	/** This method must also be properly defined, or else your {@link HashSet}
      structure won't operate properly.
      Implemented based on eclipse generated code.
	 */
	public int hashCode() {
		final int prime = 23;
		int result = 2;
		result = prime * result + ((cleanUp(phrase) == null) ? 0 : cleanUp(phrase).hashCode());
		return result;
	}

	/** 
	 * @param Phrase to be cleaned
	 * Returns phrase without punctuation, spaces, and in lowercase.
	 */
	protected static String cleanUp(String s){
		String clean = s.replaceAll("[\\d[^\\w\\s]]+", "").toLowerCase();
		return clean;
	}

	public String toString(){
		return phrase;
	}

}
