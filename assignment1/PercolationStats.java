import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percolationThresholds;
    private int totalIterations;

    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        Percolation percolation;
        int iterations;
        double percolationThreshold;
        int sites;

        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        percolationThresholds = new double[T];
        totalIterations = T;

        for (int i = 0; i < T; i++) {
            percolation = new Percolation(N);
            iterations = 0;
            sites = N*N;

            do {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(row, col)) {
                    //System.out.println("Opening row " + row + ", col " + col);
                    percolation.open(row, col);
                    iterations += 1;
                }
            } while (!percolation.percolates());

            percolationThreshold = iterations / (double) sites;
            percolationThresholds[i] = percolationThreshold;
            //System.out.println("Finished in " + iterations + " iterations! threshold: " + percolationThreshold);
        }
    }

    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(percolationThresholds);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return mean() - ((1.96*stddev()) / (double) Math.sqrt(totalIterations));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + ((1.96*stddev()) / (double) Math.sqrt(totalIterations));
    }

    public static void main(String[] args) {
        // int size = 2;
        // int iterations = 10000;
        // // test client (described below)
        // PercolationStats p = new PercolationStats(size, iterations);
        // System.out.println("Running " + iterations + " iterations with grid size " + size);
        // System.out.println("mean: " + p.mean());
        // System.out.println("stddev: " + p.stddev());
        // System.out.println("confidenceLo: " + p.confidenceLo());
        // System.out.println("confidenceHi: " + p.confidenceHi());
    }
}
