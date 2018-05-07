import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.Stack;
import java.util.LinkedList;
//import java.lang.*;

public class SAP {
    // constructor takes a digraph (not necessarily a Directed Acyclic Graph)
    private final Digraph G;
    private int ancestor;
    private int pathLength;
    private int a;
    private int b;
    
    
    
    public SAP(Digraph D) {
        G = D;
    }

    private boolean validIndex(int v) {
        if (v > -1 && v < G.V()) {
            return true;
        }
        return false;
    }

    private void processIfNotAlreadyProcessed(int v, int w) {
        if (!validIndex(v) || !validIndex(w))
            throw new IllegalArgumentException("Invalid index");
        if (a == v && b == w) return;
        sapBFS(v,w);
    } 
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        processIfNotAlreadyProcessed(v, w);
        return pathLength;
    }
    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        processIfNotAlreadyProcessed(v, w);
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) 
            throw new IllegalArgumentException("Invalid argument");
        sapBFS(v,w);
        return pathLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) 
            throw new IllegalArgumentException("Invalid argument");
        
        sapBFS(v,w);
        return ancestor;
    }

    private void sapBFS(int v, int w) {
    //returns the path of edges from v -> x -> w, x being the common ancestor
        BreadthFirstDirectedPaths aTree = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bTree = new BreadthFirstDirectedPaths(G, w);
        
        a = v;
        b = w;
        
        pathLength = -1;
        ancestor = -1;
        //get a list of common ancestors
        for (int i=0; i<G.V(); i++) {
            if (aTree.hasPathTo(i) && bTree.hasPathTo(i)) {
                int tLength = aTree.distTo(i) + bTree.distTo(i);
                if (pathLength == -1 || tLength < pathLength) {
                    pathLength = tLength;
                    ancestor = i;
                }
            }
        }
    }
    
    private void sapBFS(Iterable<Integer> v, Iterable<Integer> w) {
    //returns the path of edges from v -> x -> w, x being the common ancestor
        BreadthFirstDirectedPaths aTree = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bTree = new BreadthFirstDirectedPaths(G, w);
        
        
        pathLength = -1;
        ancestor = -1;
        //get a list of common ancestors
        for (int i=0; i<G.V(); i++) {
            if (aTree.hasPathTo(i) && bTree.hasPathTo(i)) {
                int tLength = aTree.distTo(i) + bTree.distTo(i);
                if (pathLength == -1 || tLength < pathLength) {
                    pathLength = tLength;
                    ancestor = i;
                }
            }
        }
    }
        
        
    // do unit testing of this class
    public static void main(String[] args) {
        
        
    }
}



