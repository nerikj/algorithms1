import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points.length < 4) {
            throw new java.lang.IllegalArgumentException();
        }
        
        Arrays.sort(points);
        Point[] points2 = new Point[points.length];

        int pos = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points2.length; j++) {
                if (points2[j] != null && points2[j].compareTo(points[i]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
            
            points2[pos++] = points[i];
        }

        Arrays.sort(points2);

        // System.out.println("POINTS:");
        // for (int i = 0; i < points.length; i++) {
        // System.out.println(points[i]);
        // }
        // System.out.println("/POINTS");

        // finds all line segments containing 4 points
        if (points2 == null || points2[0] == null || points2[1] == null || points2[2] == null || points2[3] == null) {
            throw new java.lang.NullPointerException();
        }

        for (int i = 0; i < points2.length; i++) {
            Point p = points2[i];

            for (int j = i + 1; j < points2.length; j++) {
                Point q = points2[j];

                for (int k = j + 1; k < points2.length; k++) {
                    Point r = points2[k];

                    for (int l = k + 1; l < points2.length; l++) {
                        Point s = points2[l];
                        double slopePQ = p.slopeTo(q);
                        double slopeQR = q.slopeTo(r);
                        double slopeRS = r.slopeTo(s);

                        if (slopePQ == slopeQR && slopeQR == slopeRS) {
                            lineSegments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
    
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
