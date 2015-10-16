import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return set.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D point2d : set) {
            if (rect.contains(point2d)) {
                q.enqueue(point2d);
            }
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        Point2D n = null;
        for (Point2D current : set) {
            if (n == null || (current.distanceTo(p) < n.distanceTo(p))) {
                n = current;
            }
        }
        return n;
    }


    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}