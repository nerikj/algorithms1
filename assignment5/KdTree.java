import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return size;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (!contains(p)) {
            put(p);
            size++;
        }
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return get(p) != null;
    }

    public void draw() {
        // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (Node n : levelOrder()) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledCircle(n.key.x(), n.key.y(), 0.005);

            if (n.isVert) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.key.x(), n.rect.ymin(), n.key.x(), n.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(n.rect.xmin(), n.key.y(), n.rect.xmax(), n.key.y());
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }

        SET<Point2D> intersecting = rangeTraverse(rect, root);
        return intersecting;
    }

    private SET<Point2D> rangeTraverse(RectHV query, Node root) {
        SET<Point2D> set = new SET<Point2D>();

        if (root != null && root.rect.intersects(query)) {
            if (root.lb != null) {
                set.union(rangeTraverse(query, root.lb));
            }
            if (root.lb != null) {
                set.union(rangeTraverse(query, root.rt));
            }
        }

        return set;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return null;
    }

    private static class Node {
        private Point2D key; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean isVert;

        public Node(Point2D key, RectHV rect, boolean isVert) {
            this.key = key;
            this.rect = rect;
            this.isVert = isVert;
        }
    }

    private void put(Point2D key) {
        root = put(root, key, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node put(Node n, Point2D key, boolean isVert, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            return new Node(key, new RectHV(xmin, ymin, xmax, ymax), isVert);
        }

        int cmp;
        if (isVert) {
            cmp = ((Double) key.x()).compareTo(n.key.x());
            if (cmp < 0) {
                n.lb = put(n.lb, key, !isVert, xmin, ymin, n.key.x(), ymax);
            } else if (cmp > 0) {
                n.rt = put(n.rt, key, !isVert, n.key.x(), ymin, xmax, ymax);
            }
        } else {
            cmp = ((Double) key.y()).compareTo(n.key.y());

            if (cmp < 0) {
                n.lb = put(n.lb, key, !isVert, xmin, ymin, xmax, n.key.y());
            } else if (cmp > 0) {
                n.rt = put(n.rt, key, !isVert, xmin, n.key.y(), xmax, ymax);
            }
        }

        return n;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        KdTree a = new KdTree();
        a.insert(new Point2D(0.7, 0.2));
        a.insert(new Point2D(0.5, 0.4));
        a.insert(new Point2D(0.2, 0.3));
        a.insert(new Point2D(0.4, 0.7));
        a.insert(new Point2D(0.9, 0.6));
        for (Node p : a.levelOrder()) {
            System.out.println("[" + p.key.x() + ", " + p.key.y() + "]");
        }
        System.out.println(a.contains(new Point2D(0.9, 0.5)));
        System.out.println(a.contains(new Point2D(0.9, 0.6)));
        a.draw();
    }

    private Point2D get(Point2D key) {
        return get(root, key);
    }

    private Point2D get(Node x, Point2D key) {
        if (x == null)
            return null;

        if (key.compareTo(x.key) == 0) {
            return x.key;
        }

        if (x.isVert) {
            int cmp = ((Double) key.x()).compareTo(x.key.x());
            if (cmp < 0)
                return get(x.lb, key);
            else
                return get(x.rt, key);
        } else {
            int cmp = ((Double) key.y()).compareTo(x.key.y());
            if (cmp < 0)
                return get(x.lb, key);
            else
                return get(x.rt, key);
        }
    }

    private Iterable<Node> levelOrder() {
        Queue<Node> keys = new Queue<Node>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null)
                continue;
            keys.enqueue(x);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return keys;
    }
}