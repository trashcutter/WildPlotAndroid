package com.wildPlot.android.kernelFunctions;


import com.wildPlot.android.rendering.interfaces.Function2D;

public class TriweightKernel implements Function2D {

	@Override
	public double f(double x) {
		return (Math.abs(x)<=1)?((35.0/32.0)*(1.0-(x*x))*(1.0-(x*x))*(1.0-(x*x))):0;
	}

}
