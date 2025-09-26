package bench;

import alg.*;
import metrics.CsvLog;
import metrics.Metrics;
import util.Gen;

import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] NS = {1, 10, 100, 1000, 10000};
    private static final int TRIALS = 10;

    public static void main(String[] args) {
        benchMergeSort();
        benchQuickSort();
        benchSelect();
        benchClosest();
        System.out.println("benchmarks done");
    }

    private static long time(Runnable r){ long t0=System.nanoTime(); r.run(); return System.nanoTime()-t0; }

    private static void benchMergeSort(){
        try (CsvLog csv = new CsvLog("mergesort.csv",
                "algorithm,n,time_ns,comparisons,allocations,max_depth")) {
            for (int n : NS) for (int t=0; t<TRIALS; t++){
                int[] a = Gen.randomIntArray(n);
                Metrics.reset();
                long ns = time(() -> MergeSort.sort(a));
                if (!Gen.isSorted(a)) throw new AssertionError("MergeSort wrong");
                csv.row("MergeSort", n, ns, Metrics.comparisons, Metrics.allocations, Metrics.maxDepth);
            }
        }
    }

    private static void benchQuickSort(){
        try (CsvLog csv = new CsvLog("quicksort.csv",
                "algorithm,n,time_ns,comparisons,allocations,max_depth,swaps,partitions")) {
            for (int n : NS) for (int t=0; t<TRIALS; t++){
                int[] a = Gen.randomIntArray(n);
                Metrics.reset();
                long ns = time(() -> QuickSort.sort(a));
                if (!Gen.isSorted(a)) throw new AssertionError("QuickSort wrong");
                csv.row("QuickSort", n, ns, Metrics.comparisons, Metrics.allocations,
                        Metrics.maxDepth, Metrics.swaps, Metrics.partitions);
            }
        }
    }

    private static void benchSelect(){
        try (CsvLog csv = new CsvLog("select_mom.csv",
                "algorithm,n,time_ns,comparisons,allocations,partitions")) {
            Random rnd = new Random(42);
            for (int n : NS) {
                for (int trial = 0; trial < 100; trial++) {
                    int[] a = Gen.randomIntArray(n);
                    int k = (n==0) ? 0 : rnd.nextInt(Math.max(1, n));
                    int[] b = Arrays.copyOf(a, a.length);
                    Arrays.sort(b);
                    Metrics.reset();
                    long ns = time(() -> { if (n>0) DeterministicSelect.select(a, k); });
                    if (n>0 && DeterministicSelect.select(Arrays.copyOf(b,b.length), k) != b[k])
                        throw new AssertionError("Select ref mismatch");
                    csv.row("Select(MoM5)", n, ns, Metrics.comparisons, Metrics.allocations, Metrics.partitions);
                }
            }
        }
    }

    private static void benchClosest(){
        try (CsvLog csv = new CsvLog("closest_pair.csv",
                "algorithm,n,time_ns,allocations,max_depth,distance_checks")) {
            for (int n : NS) for (int t=0; t<TRIALS; t++){
                double[][] pts = Gen.randomPoints(n);
                Metrics.reset();
                long ns = ClosestPair.timed(pts);
                csv.row("ClosestPair2D", n, ns, Metrics.allocations, Metrics.maxDepth, Metrics.distanceChecks);
            }
        }
    }
}
