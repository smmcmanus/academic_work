package kwic;
import java.util.HashMap; 
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.beans.PropertyChangeSupport;
import java.io.*;

/**
* @author seanmcmanus
* Lab Section G
* sean.mcmanus@wustl.edu
* CSE132 Lab 2a
* KWIC.java
* Key Word in Context, processes and stores Words mapped to the set of Phrases
* containing those individual words.
*/

public class KWIC {

	protected PropertyChangeSupport pcs;
	protected  Map<Word,Set<Phrase>> map;
	public KWIC() { 
		pcs = (new PropertyChangeSupport(this)); 
		map = new HashMap<Word, Set<Phrase>>();
	}

	/** 
	 * Required for part (b) of this lab.
	 * Accessor for the {@link PropertyChangeSuppport} 
	 */

	public PropertyChangeSupport getPCS() { return pcs; }

	/** 
	 * Convenient interface, accepts a standrd Java {@link String}
	 * @param s String to be added
	 */
	public void addPhrase(String s) {
		addPhrase(new Phrase(s));
	}
	
	/**
	 * Add each line in the file as a phrase.
	 * For each line in the file, call {@link addPhrase(String)} to
	 *   add the line as a phrase.
	 * @param file the file whose lines should be loaded as phrases
	 * @throws IOException 
	 */
	public void addPhrases(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedReader d = new BufferedReader(new InputStreamReader(fis));
		while(d.ready()){
			
			String s = d.readLine();
			addPhrase(s);
		}
		d.close();
	}

	/** 
	 * For each {@link Word} in the {@link Phrase}, 
	 * add the {@link Word}
	 * to the association.
	 * Use reduction to {@link #forceAssoc(Word, Phrase)}.
	 * @param p Phrase to be added
	 */
	public void addPhrase(Phrase p) {
		for (Word w : p.getWords()) {
			forceAssoc(w, p);			
		}
		pcs.firePropertyChange("Phrase Added",false, true);
	}
	

	/** For each word in the {@link Phrase}, delete the association between
      the word and the phrase.
      Use reduction to {@link #dropAssoc(Word, Phrase)}.
	 */
	public void deletePhrase(Phrase p) {
		for (Word w : p.getWords()) {
			dropAssoc(w,p);
		}
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}

	/** Force a mapping between the specified {@link Word} and {@link Phrase} */
	public void forceAssoc(Word w, Phrase p) {
		if(map.containsKey(w)){
			map.get(w).add(p);
		} else {
			Set<Phrase> f = new HashSet<Phrase>(); 
			map.put(w, f);
			map.get(w).add(p);
		}
		pcs.firePropertyChange("Phrase Added",false,true);
	}


	/** 
	 * Drop the association between the 
	 * specified {@link Word} and {@link Phrase}, if any
	 */
	public void dropAssoc(Word w, Phrase p) {
		map.get(w).remove(p);
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}


	/** Return a Set that provides each {@link Phrase} associated with
    the specified {@link Word}.
     * @param Word to return phrases of
	 */
	public Set<Phrase> getPhrases(Word w) {
		if(map.containsKey(w)){
			return map.get(w);
		} else {
			Set<Phrase> f = new HashSet<Phrase>();
			return f;
		}
	}
	
	/** 
	 * Drop a word completely from the KWIC 
	 * 
	 * @param w Word to be dropped
	 */
	public void deleteWord(Word w) {
		map.remove(w);
		pcs.firePropertyChange("Word Deleted",false,true);
	}

	/** Rerturn a Set of all words */
	public Set<Word> getWords() {
		return map.keySet();
	}
}
