package lab2;
import java.lang.Math;
import java.util.Arrays;

/**
 * A hash table mapping Strings to their positions in the pattern sequence.
 *
 * Fill in the methods for this class.
 */
public class StringTable {
	Record[] recordTable;
	int size;
	int trueSize;
	int load;
    /**
     * Create an empty table of size n
     *
     * @param n size of the table
     */
    public StringTable(int n) {
        size = n;
        trueSize = nextPower(n);
        recordTable = new Record[trueSize];
        Arrays.fill(recordTable, new Record("empty"));
    }

    /**
     * Create an empty table.  You should use this construction when you are
     * implementing the doubling procedure.
     */
    public StringTable() {
        recordTable = new Record[4];
        size = 4;
        trueSize = 4;
        Arrays.fill(recordTable, new Record("empty"));
    }
	public void doubleSize(){
		size = size * 2;
		int newSize = nextPower(size);
		holdTable(newSize);
	}
	public void holdTable(int newTableSize) {
		Record[] cleanTable = cleanTable(recordTable);
		recordTable = new Record[newTableSize];
		trueSize = newTableSize;
		Arrays.fill(recordTable, new Record("empty"));
		load = 0;
		for(Record theRecord : cleanTable){
			insert(theRecord);
		}
	}
	public Record[] cleanTable(Record[] tableToClean) {
		Record[] recordList = new Record[load];
		int i = 0;
		int empty = toHashKey("empty");
		for(Record theRecord : tableToClean){
			if(theRecord.getHash() != empty){
				recordList[i] = theRecord;
				i++;
			}
		} 
		return recordList;
	}
    /**
     * Insert a Record r into the table.
     *
     * If you get two insertions with the same key value, return false.
     *
     * @param r Record to insert into the table.
     * @return boolean true if the insertion succeeded, false otherwise
     */
    public boolean insert(Record r) {
    	String key = r.getKey();
    	int intKey = toHashKey(key);
    	int empty = toHashKey("empty");
    	int hash = baseHash(intKey);
    	int step = stepHash(intKey);
       	while(recordTable[hash].getHash() != empty){
       		if(recordTable[hash].getHash() == r.getHash()){
       			if(recordTable[hash].getKey().equals(r.getKey())){
       				return false;
       			}
       		}
       		hash += step;
       		hash %= trueSize;
        }
        recordTable[hash] = r;
        load++;
        if((load / (double)trueSize) >= 0.25){
        	doubleSize();	
        }
        return true;
    }
    /**
     * Delete a record from the table.
     *
     * Note: You'll have to find the record first unless you keep some
     * extra information in the Record structure.
     *
     * @param r Record to remove from the table.
     */
    public void remove(Record r) {
    	int intKey = r.getHash();
    	int hash = baseHash(intKey);
    	int step = stepHash(intKey);
    	boolean unmatched = true;
    	while(unmatched){
    		while(recordTable[hash].getHash() != r.getHash()){
    			hash += step;
    			hash %= trueSize;
    		}
    		if(recordTable[hash].getKey().equals(r.getKey())){
    			unmatched = false;
    		}
    		else {
    			hash += step;
    			hash %= trueSize;
    		}
    	}
    	recordTable[hash] = new Record("empty");
    	load--;
    }

    /**
     * Find a record with a key matching the input.
     *
     * @param key to find
     * @return the matched Record or null if no match exists.
     */
    public Record find(String key) {
        int intKey = toHashKey(key);
        int hash = baseHash(intKey);
    	int step = stepHash(intKey);
    	int count = 0;
    	boolean unmatched = true;
    	while(unmatched){
			while(!recordTable[hash].getKey().equals(key)){
				count++;
				hash += step;
				hash %= trueSize;
				if(count >= ((trueSize / 4)+1)){
					return null;
				}
			}
			if(recordTable[hash].getKey().equals(key)){
    			unmatched = false;
    		}
    		else {
    			hash += step;
    			hash %= trueSize;
    		}
		}
        return recordTable[hash];
    }

    /**
     * Return the size of the hash table (i.e. the number of elements
     * this table can hold)
     *
     * @return the size of the table
     */
    public int size() {
       return size;
    }

    /**
     * Return the hash position of this key.  If it is in the table, return
     * the postion.  If it isn't in the table, return the position it would
     * be put in.  This value should always be smaller than the size of the
     * hash table.
     *
     * @param key to hash
     * @return the int hash
     */
    public int hash(String key) {
       	int intKey = toHashKey(key);
       	int hash = baseHash(intKey);
    	int step = stepHash(intKey);
    	int count = 0;
    	boolean unmatched = true;
    	while(unmatched){
			while(recordTable[hash].getHash() != intKey){
				count++;
				hash += step;
				hash %= trueSize;
				if(count >= ((trueSize / 4) + 1)){
					break;
				}	
			}
			if(count >= ((trueSize / 4) + 1)){
				break;
			}	
			if(recordTable[hash].getKey().equals(key)){
    			unmatched = false;
    		}
    		else {
    			hash += step;
    			hash %= trueSize;
    		}
		}	
    	if(count != trueSize){
        	return hash;
        }
        hash = baseHash(intKey);
        int empty = toHashKey("empty");
        while(recordTable[hash].getHash() != empty){
			hash += step;
    		hash %= trueSize;
    	}
    	return hash;
    }

    /**
     * Convert a String key into an integer that serves as input to hash functions.
     * This mapping is based on the idea of a linear-congruential pseuodorandom
     * number generator, in which successive values r_i are generated by computing
     *    r_i = (A * r_(i-1) + B) mod M
     * A is a large prime number, while B is a small increment thrown in so that
     * we don't just compute successive powers of A mod M.
     *
     * We modify the above generator by perturbing each r_i, adding in the ith
     * character of the string and its offset, to alter the pseudorandom
     * sequence.
     *
     * @param s String to hash
     * @return int hash
     */
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
     * Computes the base hash of a hash key
     *
     * @param hashKey
     * @return int base hash
     */
    int baseHash(int hashKey) {
    	double A = ((Math.sqrt(5) - 1) / 2.0);
    	A *= hashKey;
    	A = A % 1;
    	A = Math.floor(trueSize * A); 
		return (int)(A);
    }
    /**
     * Computes the step hash of a hash key
     *
     * @param hashKey
     * @return int step hash
     */
    int stepHash(int hashKey) {
        double A = ((Math.sqrt(3) / 2.0));
        A *= hashKey;
    	A = A % 1;
    	A = Math.floor(trueSize * A); 
		int B = (int)(A);
		if((B % 2) == 0){
			B += 1;
		}
		return B;
	}
	int nextPower(int n) {
    	double A = Math.ceil(Math.log(n) / Math.log(2));
    	A = Math.pow(2, A);
    	return (int)(A);
    }
}