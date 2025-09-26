package metrics;

public final class Metrics {
    public static long comparisons;
    public static long allocations;
    public static int  maxDepth;

    public static long swaps;          // QS
    public static long partitions;     // QS/Select
    public static long distanceChecks; // ClosestPair

    private Metrics(){}

    public static void reset() {
        comparisons = allocations = swaps = partitions = distanceChecks = 0L;
        maxDepth = 0;
    }

    public static void allocated(){ allocations++; }
    public static void seenDepth(int d){ if (d > maxDepth) maxDepth = d; }
}
