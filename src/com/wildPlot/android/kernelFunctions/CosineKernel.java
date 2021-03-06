package com.wildPlot.android.kernelFunctions;


import com.wildPlot.android.rendering.interfaces.Function2D;

public class CosineKernel implements Function2D {

	@Override
	public double f(double x) {
		return (Math.abs(x)<=1)?((Math.PI/4.0)*Math.cos((Math.PI/2.0)*x)):0;
	}

}
