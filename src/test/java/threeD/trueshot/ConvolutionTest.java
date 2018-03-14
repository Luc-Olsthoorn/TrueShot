package threeD.trueshot;

import junit.framework.TestCase;
import threeD.trueshot.util.dsp.Convolution;

public class ConvolutionTest extends TestCase
{
	public ConvolutionTest(String name)
	{
		super(name);
	}

	public void testConvolve()
	{
		double[] convolvedActual = new double[]{ 9, 28, 58, 100, 142, 184, 226, 268, 229, 172, 96};

		double[] signal	= new double[]{1,2,3,4,5,6,7,8};
		double[] kernel	= new double[]{9,10,11,12};

		int count = 0;

		Convolution convolution = new Convolution(kernel);
		double[] convoluted = convolution.linearConv(signal);
		for (int i = 0; i < convoluted.length; i++)
		{
			System.out.print(count + ": " + convoluted[i] + " ");
			assertEquals(convoluted[i], convolvedActual[i]);
			count++;
		}
	}
}
