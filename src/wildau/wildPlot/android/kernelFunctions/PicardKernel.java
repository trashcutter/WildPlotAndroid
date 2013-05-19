package wildau.wildPlot.android.kernelFunctions;


import wildau.wildPlot.android.rendering.interfaces.Function2D;

public class PicardKernel implements Function2D {

	@Override
	public double f(double x) {
		
		return (0.5*Math.exp(-1.0*Math.abs(x)));
	}

}
