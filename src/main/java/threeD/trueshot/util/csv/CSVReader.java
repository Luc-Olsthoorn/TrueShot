package threeD.trueshot.util.csv;

/**
 * As a general way of organizing different CSVReaders for different types of HRTF Databases, I propose we group them
 * in this class. We can make static calls to the type of CSV reader we want with respect to the HRTF Database Type.
 */
public class CSVReader
{
    /*
        Don't need to instantiate this class.
     */
    private CSVReader(){}

    /**
     * Gets instance of the CSV Cipic CSV Parser.
     * @param name the name of the HRTF subject
     * @return instance of CSVCipic
     */
    public static CSVCipic getCipic(String name)
    {
        return new CSVCipic(name);
    }
}
