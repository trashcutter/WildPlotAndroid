package com.wildPlot.android.kernelFunctions;


import com.wildPlot.android.rendering.interfaces.Function2D;

public class UniformKernel implements Function2D {

	@Override
	public double f(double x) {
		
		return (Math.abs(x)<=1)?0.5:0;
	}

}
