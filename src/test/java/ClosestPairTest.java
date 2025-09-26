import alg.ClosestPair;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    private static double brute(double[][] p){
        double best=Double.POSITIVE_INFINITY;
        for(int i=0;i<p.length;i++)
            for(int j=i+1;j<p.length;j++){
                double dx=p[i][0]-p[j][0], dy=p[i][1]-p[j][1];
                double d=Math.hypot(dx,dy);
                if (d<best) best=d;
            }
        return (p.length<2)?Double.POSITIVE_INFINITY:best;
    }

    @Test void smallNMatchesBrute(){
        Random r=new Random(7);
        for(int n=0;n<=200;n+=20){
            double[][] pts=new double[n][2];
            for(int i=0;i<n;i++){ pts[i][0]=r.nextDouble(); pts[i][1]=r.nextDouble(); }
            double fast = ClosestPair.solve(pts);
            double slow = brute(pts);
            if (n<2) assertTrue(Double.isInfinite(fast) || Double.isInfinite(slow));
            else assertEquals(slow, fast, 1e-9);
        }
    }
}
