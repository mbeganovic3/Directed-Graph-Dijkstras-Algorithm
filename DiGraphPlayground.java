package DiGraph_A5;

public class DiGraphPlayground {

  public static void main (String[] args) {
  
      // thorough testing is your responsibility
      //
      // you may wish to create methods like 
      //    -- print
      //    -- sort
      //    -- random fill
      //    -- etc.
      // in order to convince yourself your code is producing
      // the correct behavior
	 
	  long start = System.currentTimeMillis(); 
      
      // start of function 

	  efficiencyTest();

      // end of function 

      // ending time 
      long end = System.currentTimeMillis(); 
      System.out.println("takes " + 
                                  (end - start) + "ms"); 
      exTest();
    }
	public static void efficiencyTest() {
		int j = 0;
  		DiGraph d = new DiGraph();
  		String label = Long.toString(j);
  		String prevLabel = label;
  		for (long i = 0; i < 100000; i++) {
  			d.addNode(i, label);
  			d.addEdge(i, prevLabel, label, 1, null);
  			prevLabel = label;
  		}
        System.out.println("done");
	}
        
    public static void exTest(){
      DiGraph d = new DiGraph();
      d.addNode(1, "f");
      d.addNode(3, "s");
      d.addNode(7, "t");
      d.addNode(0, "fo");
      d.addNode(4, "fi");
      d.addNode(6, "si");
      d.addEdge(0, "f", "s", 0, null);
      d.addEdge(1, "f", "si", 0, null);
      d.addEdge(2, "s", "t", 0, null);
      d.addEdge(3, "fo", "fi", 0, null);
      d.addEdge(4, "fi", "si", 0, null);
      System.out.println("numEdges: "+d.numEdges());
      System.out.println("numNodes: "+d.numNodes());
    }
}