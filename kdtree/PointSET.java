import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;



public class PointSET {
	private SET<Point2D> square;

	public PointSET() {
		square = new SET<Point2D>();
	} 

	public boolean isEmpty() {
		// is the set empty? 	
		return square.isEmpty();
	}

	public int size() {
		 // number of points in the set 
		return square.size();
	}                        
	
	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		square.add(p);
	}             
	
	public boolean contains(Point2D p) {
		// does the set contain point p? 
		return square.contains(p);
	}            
	
	public void draw() {
		// draw all points to standard draw 
		for (Point2D p : square) {
			p.draw();
		}		
	}                        
	
	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle (or on the boundary) 
		Queue<Point2D> q = new Queue<Point2D>();
		for (Point2D p : square) {
			if (rect.contains(p)) {
				q.enqueue(p);
			}
		}
		return q;
	}             
	
	public Point2D nearest(Point2D target) {
	    // a nearest neighbor in the set to point p; null if the set is empty 
	    Point2D nearestPoint = null;
	    for (Point2D p : square) {
	    	if (nearestPoint == null || nearestPoint.distanceSquaredTo(target) > p.distanceSquaredTo(target)) {
	    		nearestPoint = p;
	    	}
	    }

	    return nearestPoint;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional) 
		PointSET s = new PointSET();
		s.insert(new Point2D(1,2));
		s.insert(new Point2D(5,6));
		s.insert(new Point2D(2.3,3.1));
		s.insert(new Point2D(2.25,3.1));
		StdOut.print(s.nearest(new Point2D(2,3)));
		s.draw();
	}                 
}