import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] grid;
    private final int d;
    private final int topSite;
    private final int bottomSite;
    private final WeightedQuickUnionUF topMap;
    private final WeightedQuickUnionUF map;
    private int numOpenSites;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        grid = new boolean[n][n];
        d = n;
        topSite = n*n;
        bottomSite = n*n+1;
        numOpenSites = 0;
        map = new WeightedQuickUnionUF(n*n+2); 
        topMap = new WeightedQuickUnionUF(n*n+1); 

    }
    
    private void checkParams(int i, int j) {
        if (i >= d || i < 0 || j >= d || j < 0) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    
    private int hash(int x, int y) {
        return x*d+y;
    }
    
    public boolean isOpen(int i, int j) {
        i = i-1;
        j = j-1;
        checkParams(i, j);
        return grid[i][j];    
    }
    
    public boolean isFull(int i, int j) {
        i = i-1;
        j = j-1;
        checkParams(i,j);
        int pt = hash(i,j);
        return topMap.find(pt) == topMap.find(topSite);
    }
    
    public boolean percolates() {
        return map.connected(bottomSite, topSite);
    }
    
    public int numberOfOpenSites() {
        return numOpenSites;
    }
    
    public void open(int i, int j) {
        if (!isOpen(i,j)) {
            i = i-1;
            j = j-1;
            checkParams(i,j);
            grid[i][j] = true;
            numOpenSites++;
            int pt = hash(i,j);
            int[][] coords = {
                {0,1},
                {0,-1},
                {1,0},
                {-1,0}
            };
            
            for (int c = 0; c < coords.length; c++) {
                if (validConnection(i+coords[c][0],j+coords[c][1])) {
                    map.union(pt, hash(i+coords[c][0], j+coords[c][1]));
                    topMap.union(pt, hash(i+coords[c][0], j+coords[c][1]));
                }
            }

            if (i == 0) {
                map.union(pt, topSite);
                topMap.union(pt, topSite);
            } 
            
            if (i == d-1) {
                map.union(pt, bottomSite);
            }
        }
    }
    
    private boolean validConnection(int x, int y) {
        if (x >= 0 && x < d && y >= 0 && y < d && grid[x][y]) {
            return true;
        } 
        return false;
    }
    
    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        perc.open(1, 1);
        perc.open(2, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        System.out.println("Is 1,1 Open: "+perc.isOpen(1,1));       
        System.out.println("Number of open sites: "+perc.numberOfOpenSites());
    }
}