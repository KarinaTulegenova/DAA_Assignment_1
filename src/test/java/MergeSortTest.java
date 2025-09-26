import alg.MergeSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test void simple(){
        int[] a={5,2,4,1,3}; int[] e=a.clone(); Arrays.sort(e);
        MergeSort.sort(a); assertArrayEquals(e,a);
    }
    @Test void empty(){ int[] a={}; MergeSort.sort(a); assertArrayEquals(new int[]{},a); }
    @Test void single(){ int[] a={7}; MergeSort.sort(a); assertArrayEquals(new int[]{7},a); }
    @Test void dups(){
        int[] a={3,1,2,1,3}; int[] e=a.clone(); Arrays.sort(e);
        MergeSort.sort(a); assertArrayEquals(e,a);
    }
}
