import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet W;
    
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        W = wordnet;
    
    }
    
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int maxDistance = -1;
        String maxNoun = "";
        
        for (int m=0; m < nouns.length; m++) {
            //sum distances
            int tDistance = 0;
            for (int n = 0; n < nouns.length; n++) {
                tDistance += W.distance(nouns[m],nouns[n]);
            }
            
            if (maxDistance < tDistance) {
                maxDistance = tDistance;
                maxNoun = nouns[m];
            }
        }
        return maxNoun;
    }
    
    public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet("../synsets.txt", "../hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = {"Turing", "von_Neumann", "Mickey_Mouse"};
        System.out.println(outcast.outcast(nouns));
    }
}