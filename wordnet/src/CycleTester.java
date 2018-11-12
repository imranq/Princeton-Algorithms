import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.Stack;
//import java.lang.*;
//import java.io.File;

public class CycleTester {

    private boolean[] marked;
    private boolean[] currentlyProcessing;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    
    // constructor takes a WordNet object
    public CycleTester(Digraph G)         
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        currentlyProcessing = new boolean[G.V()];
        
        for (int v=0; v < G.V(); v++) {
            if (!marked[v]) cycleDFS(G, v);
        }
    }
    
    private void cycleDFS(Digraph G, int v) {
        marked[v] = true;
        currentlyProcessing[v] = true;
//        System.out.println("Checking vertex "+v);
        for (int i : G.adj(v)) {
            if (this.hasCycle()) return;
            else if (!marked[i]) {
                edgeTo[i] = v;
                cycleDFS(G, i);
            } else if (currentlyProcessing[i]) { //the point was marked previously, so cycle detected
                //put all edgeTo values into the cycle stack
                cycle = new Stack<Integer>();
                for (int x = v; x != i; x = edgeTo[x]) { 
                    cycle.push(x);
                }
                cycle.push(i);
                cycle.push(v);
            }      
        }
        currentlyProcessing[v] = false;
    }
    


    public boolean hasCycle() {
        return cycle != null;
    }
  
    public static void main(String[] args)  // see test client below
    {

        String[] userInput = {"cycle.txt", "nocycle.txt"}; 
        for (String input : userInput) {
            String[] graphArr = new In(input).readAll().split("\n");
            int[] idA = new int[graphArr.length]; 
            int[] idB = new int[graphArr.length];            
//get max vertex
            int counter = 0;
            int maxValue = -1;
            for (String entry : graphArr) {
                String[] idArr = entry.split(" ");
                int a = Integer.parseInt(idArr[0]); 
                int b = Integer.parseInt(idArr[1]);
                
                if (a > maxValue) {
                    maxValue = a;
                }
                
                if (b > maxValue) {
                    maxValue = b;
                }
                
                idA[counter] = a; 
                idB[counter] = b;
                counter++;
            }
            
            Digraph graph = new Digraph(maxValue+1);
            
            for (int i = 0; i < idA.length; i++) {
                graph.addEdge(idA[i], idB[i]);
            }
            CycleTester ct = new CycleTester(graph);
            System.out.println("Does the graph from "+userInput[0]+" have a cycle?");
            if (ct.hasCycle())
                System.out.println("Has a cycle");
            else
                System.out.println("Does not have a cycle");
        }   
    }
}