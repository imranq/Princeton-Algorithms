import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double condiff;
    
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <=0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        double[] trial_results = new double[trials];
        int rx = 1;
        int ry = 1;
//        int[] pointsChosen = new int[n*n]
        Percolation perc;
        trial_results = new double[trials];
        
        for (int i = 0; i < trials; i++) {
//            int count = 0;
            perc = new Percolation(n);
            do {
                int pt = StdRandom.uniform(n*n);
//                    System.out.println(pt);
                rx = pt % n;
                ry = (pt - rx) / n;
                perc.open(rx + 1,ry + 1);
            } while (!perc.percolates());
                
            trial_results[i] = (perc.numberOfOpenSites()*1.0 / (n*n*1.0));
        }
        mean = StdStats.mean(trial_results);
        stddev = StdStats.stddev(trial_results);
        condiff = 1.96*stddev / Math.sqrt(trials);
    }
    
    public double mean() {
        return mean;
    } // sample mean of percolation threshold
    
    public double stddev() {
        return stddev;
    }// sample standard deviation of percolation threshold
    
    public double confidenceLo() {
        return mean - condiff;
    }// low  endpoint of 95% confidence interval
    
    public double confidenceHi(){
        return mean + condiff;
    }// high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("Mean = %f \n", percStats.mean());
        System.out.printf("StdDev = %f \n", percStats.stddev());
        System.out.printf("Confidence Interval = [%f,%f] \n", percStats.confidenceLo(), percStats.confidenceHi());
    }// test client (described below)

}
