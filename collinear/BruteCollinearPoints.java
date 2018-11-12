import java.util.Comparator;
//import java.lang.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    LineSegment segments[];
    Point pairs[];
    int pairInd;
    
    
    public BruteCollinearPoints(Point[] points) {
        
        int numPoints = points.length*(points.length-1);
        int pairs[][] = new int[numPoints/2][2]; //max number of segments
        int pairInd = 0;        
        
        if (points.length > 3) {
            for (int w=0; w < points.length-3; w++) {
                for (int x=w+1; x < points.length-2; x++) {
                    for (int y=x+1; y < points.length-1; y++) {
                        for (int z=y+1; y < points.length; y++) {
                            int indices[] = {w,x,y,z};
//                        points[0] 
                            
                            //sort indices based on distance from origin
                            //once sorted based on distance, only need three comparisons, and greatest distance is obviosu
                            
                            
                            //test whether each slope is the same
                            double testSlope = points[indices[0]].slopeTo(points[indices[1]]);
                            boolean valid = true;
                            double greatestDistance = points[indices[1]].distanceTo(points[indices[1]]);
                            int tp[] = {indices[0], indices[1]};    
                            
                            for (int i=0; i < 3; i++) {
                                for (int j = i+1; j < 4; j++) {
                                    if (testSlope != points[indices[i]].slopeTo(points[indices[j]])) {
                                        valid = false;
                                    //if the testSlope is the same, then we check if the distance between the points is the greater distance
                                    } else if (points[indices[i]].distanceTo(points[indices[j]]) > greatestDistance) {
                                        greatestDistance = points[indices[i]].distanceTo(points[indices[j]]);
                                        tp[0] = indices[i];
                                        tp[1] = indices[j];
                                    }
                                }
                            }
                            
                            //if no two points had a different slope...
                            if (valid == true) {
                                //search if point and slope already exists...if slope same, check if y-int also same
                                int sameLineInd = -1;
                                for (int a = 0; a < pairInd; a++) {
                                    int p[] = pairs[a];
                                    
                                    //if distance is greater or equal
                                    if (points[tp[0]].distanceTo(points[tp[1]]) >= points[p[0]].distanceTo(points[p[1]])) {
                                        double ts = points[tp[0]].slopeTo(points[tp[1]]);
                                        double ps = points[p[0]].slopeTo(points[p[1]]);
                                        
                                        double ty = points[tp[0]].y - ts*points[tp[0]].x;
                                        double py = points[p[0]].y - ps*points[p[0]].x;
                                        
                                        //if slope an y-int same
                                        if (ts == ps && ty == py) {
                                            sameLineInd = a;
                                            pairs[x][0] = tp[0];
                                            pairs[x][1] = tp[1];
                                        }
                                    }
                                }
                                
                                if (sameLineInd < 0) {
                                    pairs[pairInd] = tp;
                                    pairInd++;
                                }
                            }   
                        }
                    }
                }
            }
        }
        
//        for (int[] p : pairs) {
//         System.out.println(p[0]);
//        }
        segments = new LineSegment[pairInd];
        System.out.println(pairInd);
        for (int x = 0; x < pairInd; x++) {
            segments[x] = new LineSegment(points[pairs[x][0]], points[pairs[x][1]]);
//            System.out.println(segments[x]);
        }
    }
   // finds all line segments containing 4 points
   
    public int numberOfSegments() {
       return segments.length;
    }// the number of line segments
   
    public LineSegment[] segments() {
//        System.out.println(segments);
        return segments;
    }// the line segments
    
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