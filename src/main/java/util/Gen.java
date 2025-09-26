package util;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class Gen {
    private Gen(){}

    public static int[] randomIntArray(int n){
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }

    public static boolean isSorted(int[] a){
        for (int i = 1; i < a.length; i++) if (a[i] < a[i-1]) return false;
        return true;
    }

    public static double[][] randomPoints(int n){
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double[][] p = new double[n][2];
        for (int i = 0; i < n; i++) { p[i][0]=r.nextDouble(); p[i][1]=r.nextDouble(); }
        return p;
    }

    public static int[] copy(int[] a){ return Arrays.copyOf(a, a.length); }
}
