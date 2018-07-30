public class BruteCollinearPoints {
    LineSegments segments;
    
    
    
    public BruteCollinearPoints(Point[] points) {
        
        int numPoints = points*(points-1);
        Point pairs[] = new Point[numPoints][2];
        
        for (int w=0; x < points.length; x++) {
            for (int x=w+1; y < points.length; y++) {
                for (int y=x+1; y < points.length; y++) {
                    for (int z=y+1; y < points.length; y++) {
                        points = new Point[4];
//                        points[0] 
                        points[0] = points[w];
                        points[1] = points[x];
                        points[2] = points[y];
                        points[3] = points[z];
                        
                        for (int i=0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
//                                if (points[i].slopeOrder()) {
//                                
//                                }
                            }
                        }
                        pairs[] = [a,b];
                    }
                }
            }
        }
        
        int possibleSegments = 0;
        for (int x=0; x < numPoints; x++) {
            
            
        }
    }
   // finds all line segments containing 4 points
   
public int numberOfSegments() {
       return segments.length;
   }// the number of line segments
   public LineSegment[] segments() {
       return segments;
   }// the line segments
}