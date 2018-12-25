import java.util.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
   private List<LineSegment> segments = new ArrayList<>();
    
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       //check null
       
       //check duplicate
       
       //create a copy of points
       Point[] pointsCopy = Arrays.copyOf(points, points.length);
       int col = 4;
       
       //go through each point
       for (Point p : points) {
           //sort the pointsCopy array wrt to p with the slope order
           Arrays.sort(pointsCopy, p.slopeOrder());
           
           //check each point, and find consecutive points
           double previousSlope = Double.NEGATIVE_INFINITY;
           List<Point> consecutivePoints = new ArrayList<>();
           consecutivePoints.add(p);
           StdOut.println("-------------------");
           for (int y = 0; y < pointsCopy.length; y++) {
//              StdOut.println("Point slope from "+p+" => "+pointsCopy[y]+": "+pointsCopy[y].slopeTo(p)+", Previous Slope="+previousSlope+", Consecutive Points: "+consecutivePoints);
               if (previousSlope == p.slopeTo(pointsCopy[y])) {
                   consecutivePoints.add(pointsCopy[y]);
               } else {
                   if (consecutivePoints.size() >= col) {
//                      consecutivePoints.add(pointsCopy[y]);
                       addSegmentIfNotExists(consecutivePoints);
                   }
                   consecutivePoints.clear();
                   consecutivePoints.add(p);
                   consecutivePoints.add(pointsCopy[y]);
                   previousSlope = p.slopeTo(pointsCopy[y]);
               }
           }
           
           if (consecutivePoints.size() >= col) {
              addSegmentIfNotExists(consecutivePoints);
           }
       }      
   }
   
   private void addSegmentIfNotExists(List<Point> consecutivePoints) {
       //go through each segment and figure out if yint and slope matches
       boolean validLine = true;
//       StdOut.println("Presort: "+consecutivePoints);
       Collections.sort(consecutivePoints);
//       StdOut.println("Postsort: "+consecutivePoints);
       LineSegment newSegment = new LineSegment(consecutivePoints.get(0), consecutivePoints.get(consecutivePoints.size()-1));
//       StdOut.println(newSegment);

       for (LineSegment l: segments) {
           if (l.equals(newSegment)) {
//               StdOut.println("Line duplicate: "+l);
               validLine = false;
               break;
           }
       }
       
       if (validLine) {
           segments.add(newSegment);
           StdOut.println(newSegment);
           StdOut.println("Consecutive Points: "+consecutivePoints);
       }
       
   }
   
   public int numberOfSegments()        // the number of line segments 
   {
       return 0;
   }
   
   public LineSegment[] segments()                // the line segments 
   {
       return segments.toArray(new LineSegment[segments.size()]);
   }
   
   public static void main(String[] args) {
    StdDraw.clear();
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
//        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
   }
}