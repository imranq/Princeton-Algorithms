import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    
    
    public BruteCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        if (points.length <= 3) {
            throw new IllegalArgumentException("Too few points");
        }
       
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
            
            
            
        ArrayList<LineSegment> foundSegments = new ArrayList<>(); 
        
        for (int w=0; w < pointsCopy.length-3; w++) {
            for (int x=w+1; x < pointsCopy.length-2; x++) {
                for (int y=x+1; y < pointsCopy.length-1; y++) {
                    for (int z=y+1; z < pointsCopy.length; z++) {
                        if (pointsCopy[w].slopeTo(pointsCopy[x]) == pointsCopy[w].slopeTo(pointsCopy[y]) && pointsCopy[w].slopeTo(pointsCopy[y]) == pointsCopy[x].slopeTo(pointsCopy[z])) {
                            foundSegments.add(new LineSegment(pointsCopy[w], pointsCopy[z]));
                        }
                    }
                }
            }
        }
        
        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
        
    }
   // finds all line segments containing 4 points
   
    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated points.");
                }
            }
        }
    }
    
    
    // the number of line segments
    public int numberOfSegments() {
       return segments.length;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    
    
    public static void main(String[] args) {
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
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}