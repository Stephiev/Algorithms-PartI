/*---------------------------------------------------------
 *  Stephanie Vasquez-Soltero
 *  Nov. 30th, 2016
 *  PercolationStats Object
 *---------------------------------------------------------*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] trialsArray;
    private int trials;
    
    public PercolationStats(int n, int trials) {  // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials cannot be less than 1");
        }
        
        trialsArray = new double[trials];
        this.trials = trials;
     
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int threshold = 0;
            while (!percolation.percolates()) {
                boolean openSitefound = false;
                int row = 0;
                int col = 0;
                while (!openSitefound) {
                    row = StdRandom.uniform(1, n+1);
                    col = StdRandom.uniform(1, n+1);
                    openSitefound = !(percolation.isOpen(row, col));
                }
                percolation.open(row, col);
                threshold += 1;
                
            }
            trialsArray[i] = (double) threshold / (n * n);
        }
    }  
    
    public double mean()  {
        return StdStats.mean(trialsArray);
    }                        // sample mean of percolation threshold
    
    public double stddev()   {
        return StdStats.stddev(trialsArray);
    }                     // sample standard deviation of percolation threshold
    
    public double confidenceLo()  {
        double confidenceLo = mean() - (stddev() * 1.96) / Math.sqrt(trials);
        return confidenceLo;
    }                // low  endpoint of 95% confidence interval
    
    public double confidenceHi()   {
        double confidenceHi = mean() + (stddev() * 1.96) / Math.sqrt(trials);
        return confidenceHi;
    }               // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }   // test client (described below)
}