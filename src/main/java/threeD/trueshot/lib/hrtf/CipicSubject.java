package threeD.trueshot.lib.hrtf;

import org.nd4j.linalg.api.ndarray.INDArray;
import threeD.trueshot.lib.util.csv.CSVReader;

/**
 * This is a CIPIC Database Subject.
 */
public class CipicSubject implements HrtfSubject
{
    private String name;
    private INDArray hrirL;
    private INDArray hrirR;
    private INDArray itd;
    private INDArray onL;
    private INDArray onR;

    // Elevations
    private final double[] azimuths = new double[]{-80, -65, -55, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0,
                                                       5, 10, 15, 20, 25, 30, 35, 40, 45, 55, 65, 80};
    // This was not fun :(
    private final double[] elevations = new double[]{-45, -39.375, -33.75, -28.125, -22.5, -16.875, -11.25, -5.625, 0,
                                                    5.625, 11.25, 16.875, 22.5, 28.125, 33.75, 39.375, 45, 50.625, 56.25,
                                                    61.875, 67.5, 73.125, 78.75, 84.375, 90, 95.625, 101.25, 106.875, 112.5,
                                                    118.125, 123.75, 129.375, 135, 140.625, 146.25, 151.875, 157.5, 163.125,
                                                    168.75, 174.375, 180, 185.625, 191.25, 196.875, 202.5, 208.125, 213.75,
                                                    219.375, 225, 230.625};

    /**
     *
     * @param name
     */
    public CipicSubject(String name)
    {
        this.name = name;
        fillSubject();
    }

    /*
        Time to parse the .csv files and create our HRTF Subject
     */
    private void fillSubject()
    {
        hrirL = CSVReader.getCipic(name).readHrir_l();
        hrirR = CSVReader.getCipic(name).readHrir_r();
        itd = CSVReader.getCipic(name).readITD();
        onL = CSVReader.getCipic(name).readOnL();
        onR = CSVReader.getCipic(name).readOnR();
    }

    /**
     * Gets the HrirL.
     * @return INDArray hrirL object
     */
    @Override
    public INDArray getHrirL()
    {
        return hrirL;
    }

    /**
     * Gets the HrirR.
     * @return INDArray hrirR object
     */
    @Override
    public INDArray getHrirR()
    {
        return hrirR;
    }

    /**
     * Gets the ide.
     * @return INDArray itd object
     */
    @Override
    public INDArray getItd()
    {
        return itd;
    }

    /**
     * Gets the name of the Subject
     * @return the subject's name
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Gets the onL
     * @return INDArray onL object
     */
    @Override
    public INDArray getOnL()
    {
        return onL;
    }

    /**
     * Gets the onR
     * @return INDArray onR object
     */
    @Override
    public INDArray getOnR()
    {
        return onR;
    }

    /**
     * Gets the possible elevations that the current HRTF supports.
     * @return double[] containing the angles supported
     */
    @Override
    public double[] getPossibleElevations()
    {
        return elevations;
    }

    /**
     * Gets the possible azimuths that the current HRTF supports.
     * @return double[] containing the angles supported
     */
    @Override
    public double[] getPossibleAzimuths()
    {
        return azimuths;
    }
}
