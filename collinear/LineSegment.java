import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
    public final Point a;
    public final Point b;
    public double slope;
    public double yint;
    
    public LineSegment(Point p, Point q) {    
        a = p;
        b = q;
        slope = a.slopeTo(b);
        yint = a.y - a.x*slope;
    }// constructs the line segment between points p and q
    
    public void draw() {
        StdDraw.line(a.x, a.y, b.x, b.y);
    } // draws this line segment
    
    public String toString() {
        return "This is a line segment from "+a.toString()+" to "+b.toString();
    }   // string representation
}