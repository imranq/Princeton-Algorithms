public class LineSegment {
    Point a;
    Point b;
    
    public LineSegment(Point p, Point q) {    
        a = p;
        b = q;
    }// constructs the line segment between points p and q
    
    public void draw() {
        a.draw();
        b.draw();
    } // draws this line segment
    
    public String toString() {
        return "This is a line segment from "+a.toString()+" to "+b.toString();
    }   // string representation
}