package alg;

import metrics.Metrics;
import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] a, int k){
        if (k < 0 || k >= a.length) throw new IllegalArgumentException();
        return select(a, 0, a.length, k);
    }

    private static int select(int[] a, int l, int r, int k){
        if (r - l <= 10){
            Arrays.sort(a, l, r);
            return a[l + k];
        }
        int pivot = medianOfMedians(a, l, r);
        int[] pos = partition3(a, l, r, pivot);
        int lt = pos[0], gt = pos[1];
        if (l + k < lt) return select(a, l, lt, k);
        if (l + k >= gt) return select(a, gt, r, k - (gt - l));
        return pivot;
    }

    private static int[] partition3(int[] a, int l, int r, int pivot){
        Metrics.partitions++;
        int i = l, lt = l, gt = r;
        while (i < gt){
            Metrics.comparisons++;
            if (a[i] < pivot){ int t=a[lt]; a[lt]=a[i]; a[i]=t; lt++; i++; }
            else if (a[i] > pivot){ int t=a[gt-1]; a[gt-1]=a[i]; a[i]=t; gt--; }
            else i++;
        }
        return new int[]{lt, gt};
    }

    private static int medianOfMedians(int[] a, int l, int r){
        int n = r - l;
        int groups = (n + 4) / 5;
        for (int g = 0; g < groups; g++){
            int s = l + g*5, e = Math.min(s+5, r);
            Arrays.sort(a, s, e);
            int mid = s + (e - s - 1)/2;
            int t = a[l+g]; a[l+g] = a[mid]; a[mid] = t;
        }
        return select(a, l, l + groups, (groups - 1)/2);
    }
}
