
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x
     *            the <em>x</em>-coordinate of the point
     * @param y
     *            the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to
     * standard draw.
     *
     * @param that
     *            the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally,
     * if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)
     * / (x1 - x0). For completness, the slope is defined to be +0.0 if the line
     * segment connecting the two points is horizontal; Double.POSITIVE_INFINITY
     * if the line segment is vertcal; and Double.NEGATIVE_INFINITY if (x0, y0)
     * and (x1, y1) are equal.
     *
     * @param that
     *            the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return ((double) (that.y - this.y)) / (that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that
     *            the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point
     *         (x0 = x1 and y0 = y1); a negative integer if this point is less
     *         than the argument point; and a positive integer if this point is
     *         greater than the argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is
     * defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point v, Point w) {
                // The slopeOrder() method should return a comparator that
                // compares its two argument points by the slopes they make with
                // the invoking point (x0, y0). Formally, the point (x1, y1) is
                // less than the point (x2, y2) if and only if the slope (y1 −
                // y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
                // Treat horizontal, vertical, and degenerate line segments as
                // in the slopeTo() method.

                double vSlope = Point.this.slopeTo(v);
                double wSlope = Point.this.slopeTo(w);

                if (vSlope < wSlope) {
                    return -1;
                } else if (vSlope > wSlope) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }

    public static void main(String[] args) {
        Point p;
        Point q;
        Point r;

        // compareTo
        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 4);
        assert p1.compareTo(p2) == -1;
        assert p1.compareTo(p1) == 0;
        assert p2.compareTo(p1) == 1;

        // slopeTo
        p = new Point(172, 489);
        q = new Point(409, 51);
        // System.out.println(p.slopeTo(q));
        assert p.slopeTo(q) == -1.8481012658227849;

        p = new Point(3, 8);
        q = new Point(6, 4);
        // System.out.println(p.slopeTo(q));
        assert p.slopeTo(q) == -1.3333333333333333;

        // slopeOrder
        p = new Point(488, 455);
        q = new Point(51, 204);
        r = new Point(8, 271);
        System.out.println(p.slopeTo(q));
        assert p.slopeTo(q) == 0.5743707093821511;
        System.out.println(p.slopeTo(r));
        assert p.slopeTo(r) == 0.38333333333333336;

    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
