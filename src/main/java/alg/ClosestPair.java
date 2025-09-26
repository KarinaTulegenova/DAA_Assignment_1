package alg;

import metrics.Metrics;
import java.util.*;

public class ClosestPair {
    private record Point2D(double x, double y) {}

    public static double solve(double[][] pts){
        Point2D[] points = new Point2D[pts.length];
        for (int i = 0; i < pts.length; i++) points[i] = new Point2D(pts[i][0], pts[i][1]);
        Arrays.sort(points, Comparator.comparingDouble(o -> o.x));
        Point2D[] buf = new Point2D[points.length];
        Metrics.allocated();
        return rec(points, 0, points.length, buf, 0);
    }

    private static double rec(Point2D[] p, int l, int r, Point2D[] buf, int depth){
        Metrics.seenDepth(depth);
        int n = r - l;
        if (n <= 3) return brute(p, l, r);
        int m = (l + r) >>> 1; double midx = p[m].x;
        double d = Math.min(rec(p, l, m, buf, depth+1), rec(p, m, r, buf, depth+1));

        int i=l, j=m, k=0;
        while (i<m && j<r) buf[k++] = (p[i].y <= p[j].y) ? p[i++] : p[j++];
        while (i<m) buf[k++] = p[i++]; while (j<r) buf[k++] = p[j++];
        System.arraycopy(buf, 0, p, l, k);

        k = 0;
        for (int t=l; t<r; t++) if (Math.abs(p[t].x - midx) < d) buf[k++] = p[t];
        for (int a=0; a<k; a++){
            for (int b=a+1; b<k && (buf[b].y - buf[a].y) < d && b <= a+8; b++){
                Metrics.distanceChecks++;
                d = Math.min(d, dist(buf[a], buf[b]));
            }
        }
        return d;
    }

    private static double brute(Point2D[] p, int l, int r){
        double best = Double.POSITIVE_INFINITY;
        for (int i=l; i<r; i++)
            for (int j=i+1; j<r; j++){
                Metrics.distanceChecks++;
                best = Math.min(best, dist(p[i], p[j]));
            }
        Arrays.sort(p, l, r, Comparator.comparingDouble(o -> o.y));
        return best;
    }

    private static double dist(Point2D a, Point2D b){
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }

    public static long timed(double[][] pts){
        long t0 = System.nanoTime();
        double d = solve(pts);
        long t1 = System.nanoTime();
        if (d < 0) System.out.print("");
        return t1 - t0;
    }
}
