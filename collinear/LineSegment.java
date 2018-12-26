import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
    private final Point a;
    private final Point b;
    private double slope;
    private double yint;
    
    public LineSegment(Point p, Point q) {    
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        this.a = p;
        this.b = q;
        
        slope = a.slopeTo(b);

    }// constructs the line segment between points p and q
    
    public void draw() {
        a.drawTo(b);
    } // draws this line segment
    
    public String toString() {
        return a+" to "+b;
    }   // string representation
}