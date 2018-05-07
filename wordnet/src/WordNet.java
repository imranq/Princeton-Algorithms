import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Bag;
import java.util.Map;
import java.util.HashMap;
//import java.io.File;

public class WordNet {
    // constructor takes the name of the two input files
    private final Digraph wordgraph;
    private final SAP wordsap;
    private final Map<Integer, String> idToSynset;
    private final Map<String, Integer> nounToId;
    private LinearProbingHashST<String, Bag<Integer>> nouns;

    
    /*
     * Why use Iterable?
     * How to calculate distance?
     * 
     */
    
    public WordNet(String synset_file, String hypernym_file) { //returns a file
        //process input files
        //Load the synset definitions?
        if (synset_file == null || hypernym_file == null) {
            throw new IllegalArgumentException("The synsets are null");
        }

        
        
        String[] synarr = new In(synset_file).readAll().split("\n");
        String[] hyperarr = new In(hypernym_file).readAll().split("\n");
        idToSynset = new HashMap<>();
        nounToId = new HashMap<>();
        nouns = new LinearProbingHashST<String, Bag<Integer>>();

        wordgraph = new Digraph(synarr.length);
        for (String hyperset_data : hyperarr) {
            String[] hdata = hyperset_data.split(",");

            for (int i = 1; i < hdata.length; i++) {
                wordgraph.addEdge(Integer.parseInt(hdata[0]), Integer.parseInt(hdata[i]));
            }
        }
        
        CycleTester cycleTest = new CycleTester(wordgraph);
        if (!rootedDAG(wordgraph))
            throw new IllegalArgumentException("The graph has more than one root");
        if (cycleTest.hasCycle()) 
            throw new IllegalArgumentException("The graph has a cycle");
        
        for (String element : synarr) {
            String[] sdata = element.split(",");
            String nouns_data = sdata[1].trim();
            int id = Integer.parseInt(sdata[0]);
            String[] noun_arr = nouns_data.split(" ");
            idToSynset.put(id, nouns_data);
            for (String noun : noun_arr) {
                nounToId.put(noun, id);
                
                if (nouns.contains(noun)) {
                    nouns.get(noun).add(id);
                } else {
                    Bag<Integer> ids = new Bag<Integer>();
                    ids.add(id);
                    nouns.put(noun, ids);
                }

            }
        }
        
        wordsap = new SAP(wordgraph);
    }
    
    private boolean rootedDAG(Digraph G) {
       int roots = 0;
       for (int i=0; i < G.V(); i++) {
           //check whether node has 
           if (!G.adj(i).iterator().hasNext()){
               roots++;
               if (roots > 1) {
                   return false;
               }
           }
       }
       if (roots == 1) {
           return true;
       } else {
           return false;
       }
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }
        
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !nounToId.containsKey(nounA) || !nounToId.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        
        Bag<Integer> idA = nouns.get(nounA);
        Bag<Integer> idB = nouns.get(nounB);
        //how many edges exist between two ids
        return wordsap.length(idA,idB);
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !nounToId.containsKey(nounA) || !nounToId.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        
        Bag<Integer> idA = nouns.get(nounA);
        Bag<Integer> idB = nouns.get(nounB);
        //find root between two nouns
        //go through each adjacency for each id until both paths get a match
        
        return idToSynset.get(wordsap.ancestor(idA, idB));
    }
    

    // do unit testing of this class
    public static void main(String[] args) {
        String[] userInput = {"synsets6.txt", "hypernyms.txt"};//{"synsets.txt", "hypernyms.txt"};//reader.next().split(" ");
        
        WordNet wordnet = new WordNet("../synsets.txt", "../hypernyms.txt");
        System.out.println(wordnet.sap("quantity_unit","fixed_oil"));
        System.out.println(wordnet.distance("black_bile","scallion"));

    }
}
