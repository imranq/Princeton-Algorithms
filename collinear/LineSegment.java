import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
    public final Point a;
    public final Point b;
    private double slope;
    private double yint;
    
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
    
    public double getYIntercept() {
        return yint;
    }
    
    public double getSlope() {
        return slope;
    }
    
    public boolean equals(LineSegment newSegment) {
        Double testSlope = slope;
        if (testSlope == Double.NEGATIVE_INFINITY || testSlope == Double.POSITIVE_INFINITY) {
            if (newSegment.getSlope() == Double.NEGATIVE_INFINITY || newSegment.getSlope() == Double.POSITIVE_INFINITY) {
                testSlope = newSegment.getSlope();
            }
        }
        
        if (yint == newSegment.getYIntercept() && testSlope == newSegment.getSlope()) {
            return true;
        }
        return false;
    }
}