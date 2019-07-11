package DiGraph_A5;

import java.util.HashMap;

public class Vertex {
	//Within the Node class, I had fields for the name and the id, as well as a HashMap 
	//that would map Nodes to Edges for both the outgoing and ingoing edges (these were two separate maps).
	
	long _idNum;
	String _name;
	HashMap<Vertex, Edge> ingoingEdges;
	HashMap<Vertex, Edge> outgoingEdges;
	long _startToThisDistance;
	boolean _visited;
	
	public Vertex (long idNum, String name) {
		_idNum = idNum;
		_name = name;
		ingoingEdges = new HashMap<Vertex, Edge>();
		outgoingEdges = new HashMap<Vertex, Edge>();
		_visited = false;
	}
}
