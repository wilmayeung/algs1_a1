public class Percolation {
    
    // An alternative solution is to use 1 union find object only.
    // After the system percolates, create a new union find object
    // and then instantiate it with currently opened sites (without
    // the virtual bottom index.  As the test do not check the time
    // in isFull(), it will optimized for passing auto grader.
    // During instantiation, there would still be 2 copies of union
    // find objects but VM's garbage collection would remove the
    // unused one afterwards.  Thus instead of having 2 copies
    // all the times, only have 2 copies at one point during the life
    // of the program.
    //
    // Arguably you may reset the union find obiect instead of
    // creating the new one but I can't extend or rewrite
    // WeigtedQuickUnionUF() (auto grader rule).

    private WeightedQuickUnionUF mUf, mIsFullUf;
    private boolean[][] mSites;
    private int mN;
    private boolean mPercolates;
    
    public Percolation(int n) {
        mN = n;
        
        // N-by-N grid with 2 virtual sites
        mUf = new WeightedQuickUnionUF(mN * mN + 2);
        mIsFullUf = new WeightedQuickUnionUF(mN * mN + 1);
        
        mSites = new boolean[mN][mN];
        
        for (int i = 0; i < mN; i++) {
            for (int j = 0; j < mN; j++) {
                mSites[i][j] = false;
            }
        }
        
        mPercolates = false;
        
    }

    public void open(int i, int j) {
        if (i < 1 || j < 1 || i > mN || j > mN) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        if ((i + 1) <= mN && isOpen(i + 1, j)) {
            mUf.union(site(i, j), site(i + 1, j));
            mIsFullUf.union(site(i, j), site(i + 1, j));
        }
        
        if ((i - 1) >= 1 && isOpen(i - 1, j)) {
            mUf.union(site(i, j), site(i - 1, j));
            mIsFullUf.union(site(i, j), site(i - 1, j));
        }
        
        if (j > 1 && isOpen(i, j - 1)) {
            mUf.union(site(i, j), site(i, j - 1));
            mIsFullUf.union(site(i, j), site(i, j - 1));
        }
        
        if (j < mN && isOpen(i, j + 1)) {
            mUf.union(site(i, j), site(i, j + 1));
            mIsFullUf.union(site(i, j), site(i, j + 1));
        }


        // Connect to top virtual site
        if (i == 1) {
            mUf.union(0, site(i, j));
            mIsFullUf.union(0, site(i, j));
        }
        
        // Connect to bottom virtual site
        if (i == mN) {
            mUf.union(site(i, j), mN * mN + 1);
        }
        
        mSites[i - 1][j - 1] = true;
    }
    
    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > mN || j > mN) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        return mSites[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > mN || j > mN) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        return mIsFullUf.connected(0, site(i, j));
    }
    
    public boolean percolates() {
        if (!mPercolates) {
            mPercolates = mUf.connected(0, mN * mN + 1);
        }
        
        return mPercolates;
    }

    private int site(int i, int j) {
        return (i - 1) * mN + j;
    }
}
