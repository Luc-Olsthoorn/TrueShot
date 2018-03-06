package threeD.trueshot.hrtf;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface HrtfSubject
{
    public INDArray getHrir_l();
    public INDArray getHrir_r();
    public INDArray getITD();
    public String getName();
    public INDArray getOnL();
    public INDArray getOnR();
}
