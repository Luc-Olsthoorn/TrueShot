package threeD.trueshot.hrtf;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface HrtfSubject
{
    /* Honestly, if we are going to allow different databases we need to include
     /  more about the format of the database
     /  For example: CIPIC only allows a number of elevations and azimuths.
     /     %25 locations
     /     azimuths = [-80 -65 -55 -45:5:45 55 65 80]

     /     %50 locations
     /     elevations = -45 + 5.625*(0:49);
     */

    public INDArray getHrirL();
    public INDArray getHrirR();
    public INDArray getItd();
    public String getName();
    public INDArray getOnL();
    public INDArray getOnR();
    public double[] getPossibleElevations();
    public double[] getPossibleAzimuths();
}
