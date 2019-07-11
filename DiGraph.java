package DiGraph_A5;

import java.util.HashMap;
import java.util.Iterator;

public class DiGraph implements DiGraphInterface {

	double INFINITY = Double.POSITIVE_INFINITY;
	// in here go all your data and methods for the graph

	// I created a HashMap that mapped String names to the Node, and a data structure of long Nodeids 
	//which would allow me to quickly and efficiently check for duplicates.
	//I did the same by creating a data structure of edgeids.
	HashMap<String, Vertex> Vertices = new HashMap<String, Vertex>();
	HashMap<Long, Vertex> VertexIDs = new HashMap<Long, Vertex>();
	HashMap<Long, Vertex> EdgeIDs = new HashMap<Long, Vertex>();

	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
	}

	@Override
	public boolean addNode(long idNum, String label) {
		if(VertexIDs.containsKey(idNum) || idNum < 0 || Vertices.containsKey(label) || label == null) return false;

		Vertex addVertex = new Vertex(idNum, label);
		Vertices.put(label, addVertex);
		VertexIDs.put(idNum, addVertex);
		return true;
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if(EdgeIDs.containsKey(idNum) || idNum < 0 || !Vertices.containsKey(sLabel) || !Vertices.containsKey(dLabel) || 
		 Vertices.get(sLabel).outgoingEdges.containsKey(Vertices.get(dLabel)) || Vertices.get(dLabel).ingoingEdges.containsKey(Vertices.get(sLabel))) return false;

		Edge addEdge = new Edge(idNum, sLabel, dLabel, weight);
		Vertices.get(sLabel).outgoingEdges.put(Vertices.get(dLabel), addEdge);
		Vertices.get(dLabel).ingoingEdges.put(Vertices.get(sLabel), addEdge);
		EdgeIDs.put(idNum, null);
		return true;
	}

	@Override
	public boolean delNode(String label) {
		if(!Vertices.containsKey(label)) return false;

		Vertex delVertex = Vertices.get(label);
		for(Edge delEdge: delVertex.ingoingEdges.values()) delEdge(delEdge._sLabel, delEdge._dLabel);
		for(Edge delEdge: delVertex.outgoingEdges.values()) delEdge(delEdge._sLabel, delEdge._dLabel);
		Vertices.remove(label, delVertex);
		VertexIDs.remove(delVertex._idNum);
		return true;
	}
	/*I got my two Node's from my DiGraph HashMap.
//I told the start node to give me the edge pointing to the second node. In my Node class, I used my HashMap to hash the 
//opposite Node to the edge. 
//So, I fed the start Node the end Node, it searched it's HashMap for it, and it returned the so-called edge.
//I say so-called because it may have returned null if it in fact DNE. If it DNE, the delete fails. If it does exist, 
//I can get the idNum from the edge and use that key to delete the edge from my DiGraph HashMap.
//I then tell the two Nodes to remove the respective edge*/
	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		if(!Vertices.containsKey(sLabel) || !Vertices.containsKey(dLabel) || !Vertices.get(sLabel).outgoingEdges.containsKey(Vertices.get(dLabel))) return false;
		Edge remEdge = Vertices.get(sLabel).outgoingEdges.get(Vertices.get(dLabel));
		EdgeIDs.remove(remEdge._idNum);
		Vertices.get(sLabel).outgoingEdges.remove(Vertices.get(dLabel), remEdge);
		Vertices.get(dLabel).ingoingEdges.remove(Vertices.get(sLabel), remEdge);
		return true;
	}

	@Override
	public long numNodes() {
		return VertexIDs.size();
	}

	@Override
	public long numEdges() {
		return EdgeIDs.size();
	}


	//Whenever you update a node's distance because you found a shorter path, you update the distance and add it to the priority queue. 
	//When you pop items off the priority queue, it comes off in order of shortest distance first. 
	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		MinBinHeap priorityQ = new MinBinHeap();
		ShortestPathInfo[] shortestPathsArr = new ShortestPathInfo[(int) numNodes()];
		priorityQ.insert(new EntryPair(Vertices.get(label), 0)); //Put (0,s) on priority queue PQ
		int i = 0;
		shortestPathsArr[i] = new ShortestPathInfo(label, 0);
		while(priorityQ.size()!= 0) {//Loop: until PQ is empty:
			Vertex v = Vertices.get(priorityQ.getMin().getValue()._name); //n=PQ.getMin().node; 
			long distance = priorityQ.getMin().priority;  //d=PQ.getMin().dist
			priorityQ.delMin(); // PQ.delMin()
			if(v._visited) continue; // if (G.nodes[n].handled) { continue; } // might be in pq from earlier pathlength
			shortestPathsArr[i] = new ShortestPathInfo(v._name, distance);  
			v._visited = true; //Mark n as known  
			Iterator<Edge> adjacent = v.outgoingEdges.values().iterator();
			i++;
			while(adjacent.hasNext()){ 
				Vertex adjacentV = Vertices.get(adjacent.next()._dLabel); 
				if(!adjacentV._visited){ //For each unknown node a adjacent to n 
					if(distance + v.outgoingEdges.get(adjacentV)._weight < adjacentV._startToThisDistance || adjacentV._startToThisDistance == 0){ //if a.dist>d+edge.weight then 
						adjacentV._startToThisDistance = v.outgoingEdges.get(adjacentV)._weight + distance; //update a.dist in table to be d+edge.weight
						priorityQ.insert(new EntryPair(Vertices.get(adjacentV._name), v.outgoingEdges.get(adjacentV)._weight + distance)); //and add a to PQ with priority d+edge.weight
					} 
				}
			}	
			//If you're making
			// your own tests, just make sure to cover the case where
			//there is no path"
		}for(Vertex v: Vertices.values()){ //test 4
				if(!v._visited){
					//If a path from source node to the destination node 
					//does not exist, set totalWeight to -1.
					shortestPathsArr[i] = new ShortestPathInfo(v._name, -1); 
					i++;
				}
			}
		return shortestPathsArr;	  
	}
}