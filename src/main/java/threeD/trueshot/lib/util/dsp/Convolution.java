package threeD.trueshot.lib.util.dsp;

public class Convolution
{
	public double[] kernel;

	/**
	 * Constructs a convolution object.
	 * @param kernel the filter
	 */
	public Convolution(double[] kernel)
	{
		this.kernel = kernel;
	}

	/**
	 * Convolves two 1 dimensional arrays in a linear fashion.
	 * @param sample the sample to filter
	 * @return the convoluted data
	 */
	public double[] linearConv(double[] sample)
	{
		double[] convoluted = new double[sample.length + kernel.length - 1];

		for (int i = 0; i < sample.length + kernel.length - 1; i++)
		{
			int kmin, kmax, k;

			convoluted[i] = 0;

			kmin = (i >= kernel.length - 1)
					? i - (kernel.length - 1)
					: 0;
			kmax = (i < sample.length - 1)
					? i
					: sample.length - 1;

			for (k = kmin; k <= kmax; k++)
			{
				convoluted[i] += sample[k] * kernel[i - k];
			}

		}
		return convoluted;
	}

	/**
	 * Convolves two 1 dimensional arrays in a linear fashion.
	 * @param sample the sample to filter
	 * @param kernel the filter
	 * @return the convoluted data
	 */
	public double[] linearConv(double[] sample, double[] kernel)
	{
		setKernel(kernel);
		return linearConv(sample);
	}

	/**
	 * Sets the kernel.
	 * @param kernel the filter
	 */
	public void setKernel(double[] kernel)
	{
		this.kernel = kernel;
	}

	/**
	 * Gets the kernel.
	 * @return the filter
	 */
	public double[] getKernel()
	{
		return kernel;
	}
}
