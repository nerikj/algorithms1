import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private int[] sites;
    private WeightedQuickUnionUF uf;
    private int rootTopIndex;
    private int rootBottomIndex;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gridSize = N;
        sites = new int[gridSize * gridSize];
        uf = new WeightedQuickUnionUF((gridSize * gridSize) + 2);
        rootTopIndex = (gridSize * gridSize);
        rootBottomIndex = (gridSize * gridSize) + 1;
    }

    public void open(int y, int x) {
        if (!isValidSite(y, x)) { throw new java.lang.IndexOutOfBoundsException(); }

        if (!isOpen(y, x)) {
            sites[yxTo1D(y, x)] = 1;

            if (y == 1) {
                uf.union(yxTo1D(y, x), rootTopIndex);
            } else if (y == gridSize) {
                uf.union(yxTo1D(y, x), rootBottomIndex);
            }

            if (isValidSite(y, x - 1) && isOpen(y, x - 1)) {
                uf.union(yxTo1D(y, x), yxTo1D(y, x - 1));
            }
            if (isValidSite(y + 1, x) && isOpen(y + 1, x)) {
                uf.union(yxTo1D(y, x), yxTo1D(y + 1, x));
            }
            if (isValidSite(y, x + 1) && isOpen(y, x + 1)) {
                uf.union(yxTo1D(y, x), yxTo1D(y, x + 1));
            }
            if (isValidSite(y - 1, x) && isOpen(y - 1, x)) {
                uf.union(yxTo1D(y, x), yxTo1D(y - 1, x));
            }
        }
    }

    public boolean isOpen(int y, int x) {
        if (!isValidSite(y, x)) { throw new java.lang.IndexOutOfBoundsException(); }
        return sites[yxTo1D(y, x)] != 0;
    }

    public boolean isFull(int y, int x) {
        if (!isValidSite(y, x)) { throw new java.lang.IndexOutOfBoundsException(); }
        return uf.connected(yxTo1D(y, x), rootTopIndex);
    }

    public boolean percolates() {
        return uf.connected(rootTopIndex, rootBottomIndex);
    }

    public static void main(String[] args) {
        //Percolation p = new Percolation(-1);

        // test client (optional)
        // Percolation p = new Percolation(3);
        // p.open(1, 1);
        // p.isOpen(1, 1);
        // System.out.println("Perc? " + p.percolates());
        // System.out.println("c? 0-4 " + p.connected(0, 4));
        // System.out.println("c? 0-5 " + p.connected(0, 5));
        // //p.printSites();
        // System.out.println("---");
        //
        // p.open(2, 1);
        // System.out.println("Perc? " + p.percolates());
        // System.out.println("c? 0-2 " + p.connected(0, 2));
        // System.out.println("c? 0-4 " + p.connected(0, 4));
        // System.out.println("c? 0-5 " + p.connected(0, 5));
        // System.out.println("c? 2-4 " + p.connected(2, 4));
        // System.out.println("c? 2-5 " + p.connected(2, 5));
        // System.out.println("c? 4-5 " + p.connected(4, 5));
        // System.out.println("---");
        //
        // p.open(2, 2);
        // System.out.println("Perc? " + p.percolates());
        // System.out.println("---");
        //
        // p.open(3, 1);
        // System.out.println("Perc? " + p.percolates());
        // System.out.println("c? 0-2 " + p.connected(0, 2));
        // System.out.println("c? 0-4 " + p.connected(0, 4));
        // System.out.println("c? 0-5 " + p.connected(0, 5));
        // System.out.println("c? 2-4 " + p.connected(2, 4));
        // System.out.println("c? 2-5 " + p.connected(2, 5));
        // System.out.println("c? 4-5 " + p.connected(4, 5));
        // System.out.println("---");
    }

    // TODO Remove (or make private)
    // public boolean connected(int a, int b) {
    // return uf.connected(a, b);
    // }

    // TODO Remove (or make private)
    // public void printSites() {
    // for (int y = 1; y <= gridSize; y++) {
    // for (int x = 1; x <= gridSize; x++) {
    // System.out.print("" + sites[yxTo1D(y, x)] + " ");
    // }
    // System.out.println("");
    // }
    // System.out.println("---");
    // for (int i = 0; i < uf.count(); i++) {
    // for (int j = 0; j < uf.count(); j++) {
    // if (uf.connected(i, j)) {
    // System.out.println("uf: " + i + " and " + j + " IS connected");
    // } else {
    // //System.out.println("uf: " + i + " and " + j + " is NOT connected");
    // }
    // }
    // }
    // }

    private boolean isValidSite(int y, int x) {
        return (x >= 1 && x <= gridSize) && (y >= 1 && y <= gridSize);
    }

    private int yxTo1D(int y, int x) {
        return (y - 1) * gridSize + (x - 1);
    }
}
