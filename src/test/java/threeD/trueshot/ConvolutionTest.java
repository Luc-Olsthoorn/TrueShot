package threeD.trueshot;

import junit.framework.TestCase;

public class ConvolutionTest extends TestCase
{
	public ConvolutionTest(String name)
	{
		super(name);
	}


	public void testConvolve()
	{
		double[] signal	= new double[]{1,2,3,4,5,6,7,8};
		double[] kernel	= new double[]{9,10,11,12};
		double[] convoled = new double[signal.length + kernel.length];

		for (int i = 0; i < signal.length + kernel.length - 1; i++)
		{
			int kmin, kmax, k;

			convoled[i] = 0;

			kmin = (i >= kernel.length - 1) ? i - (kernel.length - 1) : 0;
			kmax = (i < signal.length - 1) ? i : signal.length - 1;

			for (k = kmin; k <= kmax; k++)
			{
				convoled[i] += signal[k] * kernel[i - k];
			}
		}


	}

}
