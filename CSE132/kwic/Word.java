package kwic;

/** Represents the original and matching forms of a word.  
 * You must implement 
 * {@link Object#hashCode()} correctly as well as
 * {@link Object#equals(Object)} 
 * for this to work.
 */
/**
 * @author seanmcmanus
 * Lab Section G
 * sean.mcmanus@wustl.edu
 * CSE132 Lab 2a
 * Word.java
 * Represents a words original and matching forms.
 */
public class Word {
	private String word;

	/** Represent a word of a {@link Phrase}
	 * @param w The original word
	 */
	public Word(String w){
		word = w;
	}

	/**
	 * The word used for matching is the original word with
	 *	punctuation and spaces removed, and to lowercase.
	 * @return the form of the word used for matching.
	 * 
	 */
	public String getMatchWord() {
		return word.replaceAll("[\\d[^\\w\\s]]+", "").toLowerCase(); 
	}

	/**
	 * 
	 * @return the original word
	 */
	public String getOriginalWord() {
		return word;
	}

	/** 
	 * You must implement this right, see lab writeup notes.
	 * 
	 * Generated using eclipse generator function. Uses the 
	 * matchWord form to produce hashcode.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getMatchWord() == null) ? 0 : this.getMatchWord().hashCode());
		return result;
	}


	/**
	 * Determines if the Match Word forms of two words are equal.
	 * 
	 */

	public boolean equals(Object obj) {
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null){
				return false;
			}
		} else if (! this.getMatchWord().equals(other.getMatchWord())){
			return false;
		}
		return true;
	}

	/**
	 * @return the word and its matching form, if different
	 */
	public String toString(){
		if (getOriginalWord().equals(getMatchWord())) {
			return getOriginalWord();
		} else {
			return getOriginalWord() + " --> " + getMatchWord();
		}
	}

}
