package testgameost;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Iterator;

/**
 * An undirected graph with a fixed number of vertices implemented using
 * adjacency lists. Space complexity is O(V + E) where V is the number
 * of vertices and E the number of edges.
 * 
 * @author Simon
 * @version 23-03-2012
 */
public class ListGraph implements UndirectedGraph {
    /** Number of vertices in the graph. */
    private final int numVertices;

    /** Number of edges in the graph. */
    private int numEdges;

    /**
     * All vertices adjacent to v are stored in adjacentVertices[v].
     * No set is allocated if there are no adjacent vertices.
     */
    private final Set<Integer>[] adjacentVertices;

    /**
     * Edge costs are stored in a hash map. The key is an
     * Edge(v, w)-object and the cost is an Integer object.
     */
    private final Map<Edge, Integer> edgeCosts;

    /**
     * Constructs a ListGraph with v vertices and no edges.
     * Time complexity: O(v)
     * 
     * @throws IllegalArgumentException if v < 0
     */
    public ListGraph(int v) {
    	if (v < 0){
            throw new IllegalArgumentException("v = " + v);
    	}
    	
        numVertices = v;
        numEdges = 0;
        
        // The array will contain only Set<Integer> instances created
        // in addEdge(). This is sufficient to ensure type safety.
        @SuppressWarnings("unchecked")
        Set<Integer>[] a = new HashSet[numVertices];
        adjacentVertices = a;
        
        edgeCosts = new HashMap<Edge, Integer>();
    }

    /** An undirected edge between v and w. */
    private static class Edge {
        // Invariant: v <= w
        final int v;
        final int w;

        Edge(int v, int w) {
            if (v <= w) {
                this.v = v;
                this.w = w;
            } else {
                this.v = w;
                this.w = v;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Edge))
                return false;
            Edge e = (Edge) o;
            return v == e.v && w == e.w;
        }

        @Override
        public int hashCode() {
            return 31*v + w;
        }

        @Override
        public String toString() {
            return "" + v + "," + w;
        }
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public int numVertices() {
        return numVertices;
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public int numEdges() {
        return numEdges;
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public int degree(int v) throws IllegalArgumentException {
    	checkVertexParameter(v);
        return adjacentVertices[v].size();
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public VertexIterator adjacentVertices(int v) {
    	checkVertexParameter(v);
    	return new AdjacentVerticesIterator(v);
    }
    
    private class AdjacentVerticesIterator implements VertexIterator {
        final int vertex;
        Iterator<Integer> iterator;

        AdjacentVerticesIterator(int v) {
            vertex = v;
            if(adjacentVertices[vertex]!=null){
            	 iterator = adjacentVertices[vertex].iterator();
            }
            else{
            	iterator = null;
            }
           
        }


        public boolean hasNext() {
        	if(iterator != null){
        		 return iterator.hasNext();
        	}
        	return false;
           
        }

        public int next() {
            if (hasNext()) {
                return iterator.next();
            }
            throw new NoSuchElementException(
            "This iterator has no more elements.");
        }
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public boolean areAdjacent(int v, int w) {
    	checkVertexParameters(v, w);
    	if(adjacentVertices[v].contains(w)){
    		return true;
    	}
    	return false;
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public int edgeCost(int v, int w) throws IllegalArgumentException {
    	checkVertexParameters(v, w);
    	Edge key = new Edge(v,w);
    	if(edgeCosts.containsKey(key)){
    		return edgeCosts.get(key);
    	}
    	return -1;
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public void addEdge(int v, int w) {
    	checkVertexParameters(v, w);
    	addEdge(v,w,-1);
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public void addEdge(int v, int w, int c) {
    	checkVertexParameters(v, w);
    	checkNonNegativeCost(c);
    	if(adjacentVertices[v]==null){
    		adjacentVertices[v] = new HashSet<Integer>();
    	}
    	if(adjacentVertices[w]==null){
    		adjacentVertices[w] = new HashSet<Integer>();
    	}
    	if(!areAdjacent(v,w)){
    		adjacentVertices[v].add(w);
        	adjacentVertices[w].add(v);
        	numEdges++;
        	edgeCosts.put(new Edge(v,w),c);
    	}
    }

    /**
     * {@inheritDoc UndirectedGraph}
     */
    @Override
    public void removeEdge(int v, int w) {
    	checkVertexParameters(v, w);
    	if(areAdjacent(v,w)){
    		adjacentVertices[v].remove(w);
        	adjacentVertices[w].remove(v);
        	numEdges--;
        	edgeCosts.remove(new Edge(v,w));
    	}
    }

    /**
     * Returns a string representation of this graph.
     *  sb.append("(" + i + "," + j + "), ");
     *  sb.append("(" + i + "," + j + "," + cost[i][j] + "), ");
     * @return a String representation of this graph
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("Vertices: " + numVertices + ", Edges: {");
        Set<Map.Entry<Edge, Integer>> edges = edgeCosts.entrySet();
        for(Map.Entry<Edge, Integer> edge : edges){
        	if(edge.getValue()==-1){
        		sb.append("(" + edge.getKey() + "), ");
        	}
        	else{
        		sb.append("(" + edge.getKey() + "," + edge.getValue() + "), ");
        	}
        }
        if (numEdges > 0){
        	 sb.setLength(sb.length() - 2); // Remove trailing ", "
        }
        sb.append("}");
        return sb.toString();
    }
    

    /**
     * Checks a single vertex parameter v.
     * @throws IllegalArgumentException if v is out of range
     */
    private void checkVertexParameter(int v) {
        if (v < 0 || v >= numVertices)
            throw new IllegalArgumentException(
                    "Out of range: v = " + v + ".");
    }

    /**
     * Checks two vertex parameters v and w.
     * @throws IllegalArgumentException if v or w is out of range
     */
    private void checkVertexParameters(int v, int w) {
        if (v < 0 || v >= numVertices || w < 0 || w >= numVertices)
            throw new IllegalArgumentException(
                    "Out of range: v = " + v + ", w = " + w + ".");
    }

    /**
     * Checks that the cost c is non-negative.
     * @throws IllegalArgumentException if c < 0
     */
    private void checkNonNegativeCost(int c) {
        if (c < -1)
            throw new IllegalArgumentException(
                    "Illegal cost: c = " + c + ".");
    }
}
