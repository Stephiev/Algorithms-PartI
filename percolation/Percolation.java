/*---------------------------------------------------------
 *  Stephanie Vasquez-Soltero
 *  Nov. 30th, 2016
 *  Percolation Data Model Object
 *---------------------------------------------------------*/

//    Corner cases.  By convention, the row and column indices are integers between
//    1 and n, where (1, 1) is the upper-left site: Throw a 
//    java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or
//    isFull() is outside its prescribed range. The constructor should throw a 
//    java.lang.IllegalArgumentException if n ≤ 0.

// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites; // Array to keep track of open sites
    private WeightedQuickUnionUF wquf; // Weighted union find object
    private int nSize; // Variable to be able to calculate index of site
    private int virtualTopIndex = 0; // Virtual top index
    private int virtualBottomIndex; // Virtual bottom index

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        nSize = n;
        virtualBottomIndex = nSize*nSize + 1;
        
        openSites = new boolean[n*n + 2];
        openSites[virtualTopIndex] = true;
        openSites[virtualBottomIndex] = true;

//        for (int i = 1; i <= n*n; i++) {
//            openSites[i] = false;
//        }

        // create wquf object with two virtual sites
        // Need to check mapping to wquf
        wquf = new WeightedQuickUnionUF(n*n+2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        acceptableRange(row, col); 
        int currentIndex = getIndex(row, col);
      
        // Because they're all intiially blocked you can connect them to the virtual sites here
        // Connect to top
        if (row == 1) {
            wquf.union(virtualTopIndex, currentIndex);
        }
         // Connect to bottom
        if (row == nSize) {
            wquf.union(virtualBottomIndex, currentIndex);
        }

        // Exit if already open
        if (isOpen(row, col)) return;

        // Open this site
        openSites[currentIndex] = true;

        // If neighors are open, connect the two
        // Left neighbor
        if (col - 1 > 0) {
            if (isOpen(row, col - 1) && !isConnectedWithNeighbor(getIndex(row, col - 1), currentIndex)) {
                // connect them
                wquf.union(getIndex(row, col - 1), currentIndex);
            }
        }
        // Right neightbor
        if (col + 1 <= nSize) {
            if (isOpen(row, col + 1) && !isConnectedWithNeighbor(getIndex(row, col + 1), currentIndex)) {
                wquf.union(getIndex(row, col + 1), currentIndex);
            }
        }

        // Top neighbor
        if (row + 1 <= nSize) {
            if (isOpen(row + 1, col) && !isConnectedWithNeighbor(getIndex(row + 1, col), getIndex(row, col))) {
                wquf.union(getIndex(row + 1, col), getIndex(row, col));
            }
        }

        // Bottom neighbor
        if (row - 1 > 0) {
            if (isOpen(row - 1, col) && !isConnectedWithNeighbor(getIndex(row - 1, col), getIndex(row, col))) {
                wquf.union(getIndex(row - 1, col), getIndex(row, col));
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        acceptableRange(row, col);
        return (openSites[getIndex(row, col)]);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        acceptableRange(row, col);
        return (!openSites[getIndex(row, col)]);
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.connected(virtualTopIndex, virtualBottomIndex);
    }

    public int getIndex(int row, int col) {
        return nSize*row - (nSize - col);
    }

    // Check if a given site is connected to the specified neighbor
    private boolean isConnectedWithNeighbor(int p, int q) {
        return wquf.connected(p, q);
    }

    // Ensure the row/columns are within range, need to change in main fucntion when checking neighbors
    private void acceptableRange(int row, int col) {
        if (row < 1 || col < 1 || row > nSize || col > nSize) {
            throw new java.lang.IndexOutOfBoundsException("row or column index is out of bounds");
        } 
    }

    public static void main(String[] args) {    // test client (optional)
        Percolation testing = new Percolation(2);
        testing.open(1, 1);
        testing.open(1, 2);
        testing.open(2, 2);
//        testing.open(2,3);
//        testing.open(3,3);
//        testing.open(4,4);
//        testing.open(3,4);
        System.out.println("Percolates? " + testing.percolates());
    }
}
