import java.util.ArrayList;

public class DepGraph {	
	// n: number of nodes
	private int n;
	// number of edges
	private int m;

	// graph has adjLists mapping sources to destination lists 
	private ArrayList<AdjList> graph;

	public DepGraph(boolean debug){
		graph = new ArrayList<AdjList>();
		n = 0;
		m = 0;
	}

	public String toString(){
		String res = "Dependence Graph: " + n + " nodes, " + m + " edges\n";
		for (int i = 0; i<graph.size(); i++)
			res += i + ": " + graph.get(i) + "\n";
		return res;
	}


        // return whether graph contains the source
	private boolean graphContains(String source){
		for(AdjList a:graph)
			if (a.getSource().equals(source))
				return true;
		return false;
	}
	
	// add **new** source entry with empty destList to end of graph
	public void addAdjList(String source) throws GraphException{
		if (graphContains(source))
			throw new GraphException(source +" already defined!"); 		
		else {
			graph.add(new AdjList(source));
			n++;
		}
	}
	
	// get the adjList of source
	private AdjList getAdjList(String source) throws GraphException{
		if (!graphContains(source))
			throw new GraphException(source +" undefined!");
		else
			for(AdjList a:graph)
				if (a.getSource().equals(source))
					return a;
		return null;
	}

	// if dest not in the source AdjList add it to the source adjList
	// increment inDegree in dest adjList
	public void addDest(String source, String dest) throws GraphException{
		if (!graphContains(source))
			throw new GraphException(source +" undefined!");
		else {
			if (getAdjList(source).contains(dest))
				throw new GraphException(source + " already contains " + dest);
			getAdjList(source).addDest(dest);
			m++;
			getAdjList(dest).incrInDegree();
		}
	}

    // copy all inDegrees to temporary inDegrees, as these will be decremented to zero
    // repeat
    //   1. find sources with temporary inDegree 0 (making sure only new ones are found, see step 3)
    //   2. print these on one line in definition order in format [SOURCE1, SOURCE2, ... , SOURCEn]
    //   3. decrement the temporary inDegree of all successors of the sources found above
	public void topoPrint() throws GraphException{
		ArrayList<String> prints;
		int[] tempid = new int[graph.size()];
		int[] temptempid = new int[graph.size()];
		for (int i=0;i<tempid.length;i++)
			tempid[i] = graph.get(i).getInDegree();
		System.arraycopy( tempid, 0, temptempid, 0, tempid.length );
		int maxid = 0;
		while (maxid > -1){
			maxid = -1;
			prints = new ArrayList<>();
			for (int i=0;i<tempid.length;i++) {
				if (tempid[i]==0) {
					prints.add(graph.get(i).getSource());
					temptempid[i]--;
					for (int j=0;j<graph.get(i).numDests();j++) {
						for (AdjList k: graph) {
							if (k.getSource().equals(graph.get(i).getDest(j))) {
								temptempid[graph.indexOf(k)]--;
							}
						}
					}
					// decrement indegree of all dests
				}
				if (maxid < tempid[i])
					maxid = tempid[i];
				// tempid[i]--; // remove once above is working
			}
			System.arraycopy( temptempid, 0, tempid, 0, tempid.length );
			if (!prints.isEmpty())
				System.out.println(prints);
		}
		
	}
	

//	public static void main(String[] args) throws GraphException{
//		DepGraph dG = new DepGraph(false);
//		dG.addAdjList("a");
//		dG.addAdjList("b");
//		dG.addAdjList("c");
//		dG.addAdjList("d");
//		dG.addAdjList("e");
//		//dG.addAdjList("e");  // checking graph exception
//
//		dG.addDest("a","c");
//		dG.addDest("a","d");
//
//		dG.addDest("b","c");
//		dG.addDest("b","d");
//
//		dG.addDest("c","d");
//
//		dG.addDest("c","e");
//		dG.addDest("d","e");
//
//		System.out.println(dG);
//
//	}


}