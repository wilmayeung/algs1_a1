
public class PercolationStats {

    private double[] mX;
    private double mMean, mStdDev;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        mX = new double[t];
        
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            int openSites = 0;
            
            while (!p.percolates()) {
                int row, col;
                
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                } while (p.isOpen(row, col));
                
                p.open(row, col);
                openSites++;
            }
            
            mX[i] = ((double) openSites) / (n * n);
        }
        
        mMean = StdStats.mean(mX);
        mStdDev = StdStats.stddev(mX);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mMean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return mStdDev;
    }
       
    // test client, described below
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length < 2) {
            throw new java.lang.IllegalArgumentException();
        }
        
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(n, t);
        
        double mean = ps.mean();
        double stddev = ps.stddev();
        System.out.printf("mean                    = %f\n", mean);
        System.out.printf("stddev                  = %f\n", stddev);
        
        final double intervalConstant = 1.96;
        double interval = (stddev * intervalConstant) / Math.sqrt(t);
        System.out.printf("95%% confidence interval = %f, %f\n",
                          mean - interval, mean + interval);
    }

}
