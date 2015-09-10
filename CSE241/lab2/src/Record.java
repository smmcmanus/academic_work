package lab2;

import java.util.*;

/**
 * Record type for string hash table
 *
 * A record associates a certain string (the key value) with
 * a list of sequence positions at which that string occurs
 *
 * Change anything you want about this except the methods we have implemented already.
 */
public class Record {
    private final String key;
    private final ArrayList<Integer> positions;
    private final int hash;

    /**
     * Constructs a Record with the given string
     *
     * @param s key of the Record
     */
    public Record(String s) {
        key = s;
        positions = new ArrayList<Integer>(1);
        hash = toHashKey(s); 
    }
    int toHashKey(String s) {
        int A = 1952786893;
        int B = 367253;
        int v = B;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            v = A * (v + (int) c + j) + B;
        }
        if (v < 0) {
            v = -v;
        }
        return v;
    }

    /**
     * Returns the key associated with this Record.
     */
    
    public String getKey() {
        return key;
    }
	public int getHash() {
		return hash;
	}
    /**
     * Adds a new position to the positions list.
     *
     * @param position of the key
     */
    public void addPosition(Integer position) {
        positions.add(position);
    }

    /**
     * @return The number of positions we have for this key.
     */
    public int getPositionsCount() {
        return positions.size();
    }

    /**
     * @return the Integer position at index
     */
    public Integer getPosition(int index) {
        return positions.get(index);
    }
}