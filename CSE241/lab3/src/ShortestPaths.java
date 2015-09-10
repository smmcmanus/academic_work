package lab3;
import java.util.HashMap; 
import java.util.ArrayList;

/**
 * Compute shortest paths in a graph.
 *
 * Your constructor should compute the actual shortest paths and
 * maintain all the information needed to reconstruct them.  The
 * returnPath() function should use this information to return the
 * appropriate path of edge ID's from the start to the given end.
 *
 * Note that the start and end ID's should be mapped to vertices using
 * the graph's get() function.
 */
class ShortestPaths {
	Multigraph G;
	int startId;
    PriorityQueue<Vertex> nodes;
    HashMap<Vertex, Integer> distances;
    HashMap<Vertex,Handle> handles;
    HashMap<Vertex, Edge> previous;
    
    /**
     * Constructor
     */
    public ShortestPaths(Multigraph G, int startId) {
    	this.G = G;
    	this.startId = startId;
    	previous = new HashMap<Vertex, Edge>();
    	handles = new HashMap<Vertex,Handle>();
    	distances = new HashMap<Vertex, Integer>();
    	nodes = new PriorityQueue<Vertex>();
       	G.get(startId);
       	for(int i = 0; i <G.nVertices(); i++){
       		if(i!=startId){
       			distances.put(G.get(i), Integer.MAX_VALUE);
       		}
       		else{
       			distances.put(G.get(i), 0);
       			Handle h = nodes.insert(0, G.get(i));
       			handles.put(G.get(i),h);
       		}
       	}
       	while(!nodes.isEmpty()){
       		Vertex present = nodes.extractMin();
       		Vertex.EdgeIterator edges = present.adj();
       		while(edges.hasNext()){
       			Edge e = edges.next();
       			Vertex next = e.to();
       			int weight = e.weight();
       			if(distances.get(next) == Integer.MAX_VALUE){
       				Handle h = nodes.insert(Integer.MAX_VALUE, next);
       				handles.put(next, h);
       			}
       			int dist = distances.get(present) + weight;
       			if(dist < distances.get(next)){
       				distances.put(next, dist);
       				nodes.decreaseKey(handles.get(next), dist);
       				previous.put(next, e);
       			}
       		}
       	}
    }
    
    /**
     * Calculates the list of edge ID's forming a shortest path from the start
     * vertex to the specified end vertex.
     *
     * @return the array
     */
    public int[] returnPath(int endId) {
		if(startId == endId){
       		return new int[0];
       	}
		ArrayList<Integer> shortpath = new ArrayList<Integer>();
       	Vertex here = G.get(endId);
       	Edge there = previous.get(here);
       	while(here.id() != startId){
       		if(there.from()==null){
       			return new int[0];
       		}
       		shortpath.add(there.id());
       		here = there.from();
			there = previous.get(here); 
       	}
       	int[] returnpath = new int[shortpath.size()];
       	int count = 0;
       	for(int i = shortpath.size() - 1; i >= 0; i--){
       		returnpath[count] = shortpath.get(i);
       		count++;
       	} 
		return returnpath;
    }
}
