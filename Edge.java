package DiGraph_A5;

public class Edge {
	// For the Edge class, I just had fields for the weight, id, and the two nodes it was connected to.
	long _idNum;
	String _sLabel;
	String _dLabel; 
	long _weight;
	
	public Edge(long idNum, String sLabel, String dLabel, long weight) {
		_idNum = idNum;
		_sLabel = sLabel;
		_dLabel = dLabel;
		_weight = weight;
	}
}
