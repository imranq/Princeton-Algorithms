import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
   private ArrayList<LineSegment> segments = new ArrayList<>();
   private ArrayList<Double> slopeMapToIndex = new ArrayList<>();
   private ArrayList<ArrayList<Point>> indexToPointMap = new ArrayList<>();
   
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       //check null
       
       //check duplicate
       Arrays.sort(points);
       //create a copy of points
       Point[] pointsCopy = Arrays.copyOf(points, points.length);
       int col = 4;
       
       //go through each point
       for (Point p : points) {
           //sort the pointsCopy array w.r.t to p with the slope order
           Arrays.sort(pointsCopy, p.slopeOrder());
           
           //check each point, and find consecutive points
           double previousSlope = Double.NEGATIVE_INFINITY;
           ArrayList<Point> consecutivePoints = new ArrayList<Point>();
           consecutivePoints.add(p);
           
           //Keep adding points until slope changes
           
           for (int y = 1; y < pointsCopy.length; y++) {
               if (previousSlope == p.slopeTo(pointsCopy[y]) && !consecutivePoints.contains(pointsCopy[y])) {
                   consecutivePoints.add(pointsCopy[y]);
               } else {
                   if (consecutivePoints.size() >= col) {
                       addSegmentIfNotExists(consecutivePoints.toArray(new Point[consecutivePoints.size()]));
                   }
                   consecutivePoints.clear();
                   consecutivePoints.add(p);
                   consecutivePoints.add(pointsCopy[y]);
                   previousSlope = p.slopeTo(pointsCopy[y]);
               }
           }
           
           //reached end, possible for points to be added but slope not changed
           if (consecutivePoints.size() >= col) {
              addSegmentIfNotExists(consecutivePoints.toArray(new Point[consecutivePoints.size()]));
           }
       }      
   }
   
   private void addSegmentIfNotExists(Point[] consecutivePoints) {
       //go through each segment and figure out if yint and slope matches
       boolean validLine = true;
       Arrays.sort(consecutivePoints);
       Point startPoint = consecutivePoints[0];
       Point endPoint = consecutivePoints[consecutivePoints.length - 1];
       Double slope = startPoint.slopeTo(endPoint);
       
       //check if we have this slope already
       int slopeIndex = slopeMapToIndex.indexOf(slope);
       ArrayList<Point> slopePoints = new ArrayList<>();
//       StdOut.println(slopePoints);
       if (slopeIndex > -1) {
           slopePoints = indexToPointMap.get(slopeIndex);
           for (Point s: slopePoints) {
               if (startPoint.compareTo(s) == 0) {
                   return;
               }
           }
           slopePoints.add(startPoint);
           indexToPointMap.set(slopeIndex, slopePoints);
       } else {
           slopeMapToIndex.add(slope);
           slopePoints.add(startPoint);
           indexToPointMap.add(slopePoints);
       }
       
//       StdOut.println(consecutivePoints+" Slope: "+slope);
       segments.add(new LineSegment(startPoint, endPoint));
       
       
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
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
   }
}