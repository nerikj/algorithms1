import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> q;
    private Queue<Board> solution;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        q = new MinPQ<Node>();

        if (isSolvable()) {
            solution = new Queue<Board>();
            Node n = new Node(initial, null, 0);
            q.insert(n);

            while (!n.board.isGoal()) {
                n = q.delMin();
                solution.enqueue(n.board);

                for (Board board : n.board.neighbors()) {
                    if (!board.equals(n.previousBoard)) {
                        // System.out.println("Adding neigh " + board);
                        q.insert(new Node(board, n.board, n.moves + 1));
                    }
                }
            }
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return true;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return solution.size();
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Board previousBoard;
        int moves;
        int priority;

        public Node(Board board, Board previousBoard, int moves) {
            this.board = board;
            this.previousBoard = previousBoard;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node o) {
            int other = o.board.manhattan() + o.moves;
            if (this.priority < other) {
                return -1;
            } else if (this.priority > other) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}