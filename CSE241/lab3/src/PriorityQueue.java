package lab3;
import java.util.ArrayList;
/**
 * A priority queue class supporting operations needed for
 * Dijkstra's algorithm.
 */
class PriorityQueue<T> {
	private ArrayList<PQNode<T>> heap;
    /**
     * Constructor
     */
    public PriorityQueue() {
    	heap = new ArrayList<PQNode<T>>();
    }

    /**
     * @return true iff the queue is empty.
     */
    public boolean isEmpty() {
       return heap.size() == 0;
    }

    /**
     * Insert a (key, value) pair into the queue.
     *
     * @return a Handle to this pair so that we can find it later.
     */
    Handle insert(int key, T value) {
    	int position = heap.size();
    	PQNode<T> add = new PQNode<T>(key, value);
		heap.add(add);
		while(position > 0 && heap.get(position).getKey() < heap.get(parent(position)).getKey()){
			swap(heap, position, parent(position));
			position = parent(position);
		}
		add.getHandle().setIndex(position);
    	return add.getHandle();
    }
    /**
     * @return the min key in the queue.
     */
    public int min() {
    	if(heap.isEmpty()){
    		return -1;
    	}
    	else{
    		return heap.get(0).getKey();
    	}
    }

    /**
     * Find and remove the smallest (key, value) pair in the queue.
     *
     * @return the value associated with the smallest key
     */
    public T extractMin() {
    	if(heap.size()<=0){
    		return null;
    	}
    	else{
    		PQNode<T> t = heap.get(0);
    		heap.set(0, heap.get(heap.size()-1));
    		heap.remove(heap.size()-1);
    		heapify(heap, 0);
    		t.getHandle().setIndex(-1);
    		return t.getValue(); 
    	} 	
    }


    /**
     * Decrease the key to the newKey associated with the Handle.
     *
     * If the pair is no longer in the queue, or if its key <= newKey,
     * do nothing and return false.  Otherwise, replace the key with newkey,
     * fix the ordering of the queue, and return true.
     *
     * @return true if we decreased the key, false otherwise.
     */
    public boolean decreaseKey(Handle h, int newKey) {
 		if(h.getIndex()<heap.size()){   	
			int index = h.getIndex();
			if(heap.get(index).getKey() <= newKey || index == -1){
				return false;
			}
			else{
				heap.get(index).setKey(newKey);
				while(index > 0 && heap.get(index).getKey() < heap.get(parent(index)).getKey()){
					swap(heap, index, parent(index));
					index = parent(index);
				}
				return true;
			}

		}
		return false;
    }

    /**
     * @return the key associated with the Handle.
     */
    public int handleGetKey(Handle h) {
    	int i = h.getIndex();
    	if(i == -1){
    		return -1;
    	}
    	return heap.get(h.getIndex()).getKey();
    }

    /**
     * @return the value associated with the Handle.
     */
    public T handleGetValue(Handle h) {
		return heap.get(h.getIndex()).getValue();
		
    }
	
	public void heapify(ArrayList<PQNode<T>> a, int i){
		int left = 2*i + 1;
		int right = 2*i + 2;
		int smallest;
		if(left <= a.size()-1 && heap.get(left).getKey() < heap.get(i).getKey()){
			smallest = left;
		}
		else{
			smallest = i;
		}
		if(right <= a.size()-1 && heap.get(right).getKey() < heap.get(smallest).getKey()){
			smallest = right;
		}
		if(smallest != i) {
			swap(a, i, smallest);
			heapify(a, smallest);
		}
	}
	private void swap(ArrayList<PQNode<T>> a, int i, int j) {
		PQNode<T> t = a.get(i);
		a.set(i, a.get(j));
		a.get(i).getHandle().setIndex(i);
		a.set(j, t);
		a.get(j).getHandle().setIndex(j);
	}
	private static int parent(int i){
		return (i-1)/2;
	}
    /**
     * Print every element of the queue in the order in which it appears
     * (i.e. the array representing the heap)
     */
    public String toString(){
       String out = "";
       for(int k = 0; k <= heap.size()-1; k++){
       		out += "(" + String.valueOf(heap.get(k).getKey()) + "," + String.valueOf(heap.get(k).getKey()) + ") ";
      }
      return out;
    }
}
