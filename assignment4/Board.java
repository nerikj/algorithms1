import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int N;
    private int[][] tiles;
    private int[][] goal;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        if (blocks == null) {
            throw new java.lang.NullPointerException();
        }

        N = blocks.length;
        tiles = cloneArray(blocks);
        
        goal = new int[N][N];
        int counter = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = counter++;
            }
        }
        goal[N - 1][N - 1] = 0;
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        int misplaced = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != goal[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattanDistance = 0;
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++) {
                int value = tiles[x][y];
                if (value != 0) {
                    int targetX = (value - 1) / N; // expected x-coordinate
                                                   // (row)
                    int targetY = (value - 1) % N; // expected y-coordinate
                                                   // (col)
                    int dx = x - targetX; // x-distance to expected coordinate
                    int dy = y - targetY; // y-distance to expected coordinate
                    manhattanDistance += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manhattanDistance;
    }

    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] cloneTiles = cloneArray(tiles);
        int x1;
        int y1;
        int x2;
        int y2;
        do {
            x1 = StdRandom.uniform(0, N);
            y1 = StdRandom.uniform(0, N);
        } while (tiles[x1][y1] == 0);
        do {
            x2 = StdRandom.uniform(0, N);
            y2 = StdRandom.uniform(0, N);
        } while (tiles[x2][y2] == 0);

        // System.out.println("Swp " + x1 + "/" + y1 + " and " + x2 + "/" + y2);
        int tmp = cloneTiles[x1][y1];
        cloneTiles[x1][y1] = cloneTiles[x2][y2];
        cloneTiles[x2][y2] = tmp;

        return new Board(cloneTiles);
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.toString().compareTo(that.toString()) == 0) {
            return true;
        }

        return false;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> q = new Queue<Board>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    // take top
                    if (i > 0) {
                        int[][] neighbour = cloneArray(tiles);
                        neighbour[i][j] = neighbour[i - 1][j];
                        neighbour[i - 1][j] = 0;
                        q.enqueue(new Board(neighbour));
                    }

                    // take right
                    if (j < N - 1) {
                        int[][] neighbour = cloneArray(tiles);
                        neighbour[i][j] = neighbour[i][j + 1];
                        neighbour[i][j + 1] = 0;
                        q.enqueue(new Board(neighbour));
                    }

                    // take bottom
                    if (i < N - 1) {
                        int[][] neighbour = cloneArray(tiles);
                        neighbour[i][j] = neighbour[i + 1][j];
                        neighbour[i + 1][j] = 0;
                        q.enqueue(new Board(neighbour));
                    }

                    // take left
                    if (j > 0) {
                        int[][] neighbour = cloneArray(tiles);
                        neighbour[i][j] = neighbour[i][j - 1];
                        neighbour[i][j - 1] = 0;
                        q.enqueue(new Board(neighbour));
                    }
                }
            }
        }

        return q;
    }

    public String toString() {
        // string representation of this board (in the output format specified
        // below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Board b = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
        System.out.println(b);
        System.out.println(b.twin());
        // for (Board n : b.neighbors()) {
        // System.out.println(n);
        // }
    }

    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
}
