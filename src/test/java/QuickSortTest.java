import alg.QuickSort;
import metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test void randomCorrect(){
        Random r=new Random(1);
        for(int t=0;t<50;t++){
            int n= r.nextInt(200)+1;
            int[] a = r.ints(n).toArray();
            int[] e = a.clone(); Arrays.sort(e);
            Metrics.reset(); QuickSort.sort(a);
            assertArrayEquals(e,a);
        }
    }
}
