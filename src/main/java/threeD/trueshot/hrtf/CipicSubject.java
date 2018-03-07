package threeD.trueshot.hrtf;

import org.nd4j.linalg.api.ndarray.INDArray;
import threeD.trueshot.util.csv.CSVReader;

/**
 * This is a CIPIC Database Subject.
 */
public class CipicSubject implements HrtfSubject
{
    private String name;
    private INDArray hrir_l;
    private INDArray hrir_r;
    private INDArray ITD;
    private INDArray OnL;
    private INDArray OnR;

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
        hrir_l = CSVReader.getCipic(name).readHrir_l();
        hrir_r = CSVReader.getCipic(name).readHrir_r();
        ITD = CSVReader.getCipic(name).readITD();
        OnL = CSVReader.getCipic(name).readOnL();
        OnR = CSVReader.getCipic(name).readOnR();
    }

    @Override
    public INDArray getHrir_l()
    {
        return hrir_l;
    }

    @Override
    public INDArray getHrir_r()
    {
        return hrir_r;
    }

    @Override
    public INDArray getITD()
    {
        return ITD;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public INDArray getOnL()
    {
        return OnL;
    }

    @Override
    public INDArray getOnR()
    {
        return OnR;
    }
}
