package alg;

import metrics.Metrics;

public class MergeSort {
    private static final int CUTOFF = 20;

    public static void sort(int[] a){
        int[] buf = new int[a.length];
        Metrics.allocated();
        sort(a, 0, a.length, buf, 0);
    }

    private static void sort(int[] a, int l, int r, int[] buf, int depth){
        Metrics.seenDepth(depth);
        int n = r - l;
        if (n <= 1) return;
        if (n <= CUTOFF){ insertion(a, l, r); return; }
        int m = (l + r) >>> 1;
        sort(a, l, m, buf, depth + 1);
        sort(a, m, r, buf, depth + 1);
        Metrics.comparisons++;
        if (a[m-1] <= a[m]) return;
        merge(a, l, m, r, buf);
    }

    private static void merge(int[] a, int l, int m, int r, int[] buf){
        int i=l, j=m, k=0;
        while (i<m && j<r){
            Metrics.comparisons++;
            buf[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        }
        while (i<m) buf[k++] = a[i++];
        while (j<r) buf[k++] = a[j++];
        System.arraycopy(buf, 0, a, l, k);
    }

    private static void insertion(int[] a, int l, int r){
        for (int i = l + 1; i < r; i++){
            int x = a[i], j = i - 1;
            while (j >= l){
                Metrics.comparisons++;
                if (a[j] <= x) break;
                a[j+1] = a[j]; j--;
            }
            a[j+1] = x;
        }
    }
}
