public class Pair {
    private int x;
    private int y;
    
    public Pair (int i, int j) {
        x = i;
        y = j;
    }
    
    public boolean equals (Pair a) {
        if ((x == a.x && y == a.y) || (x == a.y && y == a.x)) return true;
        return false;
    }
    
    public int x() {
        return x;
    }
    
    public int y() {
        return y;
    }
}