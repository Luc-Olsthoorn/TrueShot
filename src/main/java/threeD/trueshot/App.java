package threeD.trueshot;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import threeD.trueshot.hrtf.Hrtf;
import threeD.trueshot.hrtf.HrtfSubject;


/**
 * Launcher for TrueShot
 */
public class App 
{
    public static void main( String[] args )
    {
        INDArray three_d_array = Nd4j.create(new double[]{1, 2, 3, 4, 5, 6, 7, 8}, new int[]{2,2,2});
        System.out.println(three_d_array);

        // Works
        HrtfSubject cipic = Hrtf.getCipicSubject("58");
    }
}
