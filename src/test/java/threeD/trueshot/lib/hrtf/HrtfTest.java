package threeD.trueshot.lib.hrtf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class HrtfTest extends TestCase
{
    HrtfSubject subject;

    public HrtfTest(String testName)
    {
        super(testName);
        subject = Hrtf.getCipicSubject("58");
    }

    public static Test suite()
    {
        return new TestSuite(HrtfTest.class);
    }

    /**
     * Test to see that the Hrir_l was parsed correctly.
     */
    public void testHrir_l()
    {
        double value = subject.getHrirL().getDouble(24, 49, 199);
        assertEquals(value, 0.00010402);
    }

    /**
     * Test to see that the Hrir_r was parsed correctly.
     */
    public void testHrir_r()
    {
        double value = subject.getHrirR().getDouble(24, 49, 199);
        assertEquals(value, -3.4013e-05);
    }

    /**
     * Test to see that the ITD was parsed correctly.
     */
    public void testITD()
    {
        double value = subject.getItd().getDouble(24, 49);
        assertEquals(value, 27.25);
    }

    /**
     * Test to see that the name was entered correctly
     */
    public void testName()
    {
        String value = subject.getName();
        assertEquals(value, "58");
    }

    /**
     * Test to see that OnL was parsed correctly.
     */
    public void testOnL()
    {
        double value = subject.getOnL().getDouble(24,49);
        assertEquals(value, 51.75);
    }

    /**
     * Test to see that OnR was parsed correctly.
     */
    public void testOnR()
    {
        double value = subject.getOnR().getDouble(24,49);
        assertEquals(value, 24.5);
    }

}
