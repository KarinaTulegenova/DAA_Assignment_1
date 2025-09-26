package alg;

import metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] a){ qsort(a, 0, a.length, 0); }

    private static void qsort(int[] a, int l, int r, int depth){
        Metrics.seenDepth(depth);
        int n = r - l;
        if (n <= 1) return;
        if (n <= CUTOFF){ insertion(a, l, r); return; }

        Metrics.partitions++;
        int p = l + ThreadLocalRandom.current().nextInt(n);
        int pivot = a[p];

        int i=l, lt=l, gt=r;
        while (i < gt){
            Metrics.comparisons++;
            if (a[i] < pivot) swap(a, lt++, i++);
            else if (a[i] > pivot) swap(a, --gt, i);
            else i++;
        }
        if (lt - l < r - gt){ qsort(a, l, lt, depth+1); qsort(a, gt, r, depth+1); }
        else { qsort(a, gt, r, depth+1); qsort(a, l, lt, depth+1); }
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

    private static void swap(int[] a, int i, int j){
        Metrics.swaps++;
        int t=a[i]; a[i]=a[j]; a[j]=t;
    }
}
