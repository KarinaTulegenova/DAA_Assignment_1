import alg.DeterministicSelect;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {
    @Test void hundredTrials(){
        Random r=new Random(42);
        for(int t=0;t<100;t++){
            int n= r.nextInt(200)+1;
            int[] a = r.ints(n).toArray();
            int[] b = a.clone(); Arrays.sort(b);
            int k = r.nextInt(n);
            int v = DeterministicSelect.select(a,k);
            assertEquals(b[k], v);
        }
    }
}
