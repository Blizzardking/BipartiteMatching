import java.util.*;

public class BipartiteMatching {
  // Priority adjacency lists
  private int numberOfX;
  private List<Integer> vertices;
  private List<List<Integer>> ajacencyList;
  /** Construct a WeightedGraph from edges and vertices in List */
  public BipartiteMatching(int[][] edges, int numberOfX, int numberOfVertices) {
	  	this.numberOfX = numberOfX;
	  	vertices = new ArrayList<Integer>(); // Create vertices
	    for (int i = 0; i < numberOfVertices; i++) {
	    	vertices.add(new Integer(i)); // vertices is {0, 1, ...}
	    }
	    createAdjacencyLists(edges, numberOfVertices);
  }

  /** Create priority adjacency lists from edge arrays */
  private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
	 ajacencyList = new ArrayList<List<Integer>>(); 
    for (int i = 0; i < numberOfVertices; i++) {
    	ajacencyList.add(new ArrayList<Integer>()); // Create a queue
    }
    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      
      // Insert an edge into the queue
      ajacencyList.get(u).add(v);
      ajacencyList.get(v).add(u);
    }
  }

  /** Display edges with weights */
  public void printWeightedEdges() {
    for (int i = 0; i < ajacencyList.size(); i++) {
      System.out.print("Vertex in X " + i + ": ");
      for (Integer j : ajacencyList.get(i)) {
        System.out.print("(" + i +
          ", " + j + ") ");
      }
      System.out.println();
    }
  }

  public List<List<Integer>> getWeightedEdges() {
    return ajacencyList;
  }
  
  public int findMaximumMatching() {
	  
	  int[] matchXY = new int[vertices.size()];
	  boolean[] isVisited = new boolean[vertices.size()];
	  for(int i = 0; i < matchXY.length; i++) {
		  matchXY[i] = -1;
	  }
	  HashMap<Integer, List<Integer>> A = new HashMap<Integer, List<Integer>>();
	  for(int u = 0; u<numberOfX; u++) {
			for (Integer v: ajacencyList.get(u)) {
				if (matchXY[u] ==-1 && matchXY[v] ==-1) {
					matchXY[u] = v;
					matchXY[v] = u;
					break;
				}
			}
			A.put(u,new LinkedList<Integer>());	
	  }
	  int[] free = new int[numberOfX];
	  int[] grandparent = new int[numberOfX];
	  while(true) {
		  initialize(free,matchXY,A);
		  Queue<Integer> h_tree = new LinkedList<Integer>();
		  for(int i = 0; i<numberOfX; i++) {
			  grandparent[i] = -1;
			  if(matchXY[i]==-1) {
				  h_tree.offer(i);
			  }
		  }
		  while(!h_tree.isEmpty()) {
			  int x = h_tree.poll();
		//	  System.out.println(matchXY[x]);
		//	  System.out.println(free[x]);
			  if(free[x]!=-1) {
				  
				  Augment(x, matchXY, free, grandparent);
				  break;
			  }
			  else {
				  List<Integer> grandchildren =  A.get(x);
				  for(int y: grandchildren) 
						  if(grandparent[y] == -1){
							  grandparent[y] = x;
							  h_tree.offer(y);
					  }
			  }
		  }
		  if(h_tree.isEmpty()) 
			  break;
	  }
	  int maxMatching = 0;
	  for(int i = 0; i<numberOfX; i++) {
		  if(matchXY[i] != -1) maxMatching++;
	  }
	  return maxMatching;
  }
  public void initialize(int[] free, int[] matchXY, HashMap<Integer, List<Integer>> A) {
	  for(int i = 0; i<numberOfX; i++) {
		  free[i] = -1;			  
	  }
	  for(int u = 0; u<numberOfX; u++) {
		  A.get(u).clear();
		  for(Integer v: ajacencyList.get(u)) {
			  if(matchXY[v] == -1) {
				  free[u] = v;
		  }
			  else if(matchXY[v] != u){
				  A.get(u).add(matchXY[v]);
			  }
		  }
	  }
  }
  public void Augment(int x, int[] matchXY, int[] free, int[] grandparent) {
	  if (grandparent[x] == -1) {
		  matchXY[x] = free[x];
		  matchXY[free[x]] = x;
	  }
	  else {
		  free[grandparent[x]] = matchXY[x];
		  matchXY[x] = free[x];
		  matchXY[free[x]] = x;
		  Augment (grandparent[x], matchXY, free, grandparent);
	  }
  }
	
  public void addEdge(int u, int v) {
	  ajacencyList.get(u).add(v);
	  ajacencyList.get(v).add(u);
  }
 
}
