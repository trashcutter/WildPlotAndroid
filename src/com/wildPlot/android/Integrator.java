package com.wildplot.android;

import java.util.Vector;


import com.wildplot.MyMath.Matrix_d;
import com.wildplot.system.Vec_d;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Integrator extends Fragment {
    public static final String FRAGMENT_NAME = "integrator_fragment";
    GlobalDataUnified globalData;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        View rootView = inflater.inflate(R.layout.integrator, container, false);
        
        globalData = (GlobalDataUnified) getActivity().getApplication();
        
        
       return rootView;
    }

	
	public void buttonClick(View view) {
	    View rootView = getView();
		
		final EditText textBox = (EditText) rootView.findViewById(R.id.stepText);
		int steps = Integer.parseInt(textBox.getText().toString());
		
		
//		initODE(solver, steps, progressBar);
		
		ODERunner odeRunner = new ODERunner(steps);
		
		Thread t = new Thread(odeRunner);
		t.start();
		
	}
	
	
	
	class ODERunner implements Runnable {

		int steps;

		public ODERunner(int steps) {
			super();
			this.steps = steps;
		}

		@Override
		public void run() {
			
			initODE();
		}

		private void initODE()
		{
			Vector<Double> xVals2 = new Vector<Double>();
			Vector<Double> yVals2 = new Vector<Double>();
			Vector<Double> xVals4 = new Vector<Double>();
			Vector<Double> yVals4 = new Vector<Double>();

			double a=0, b=Math.PI;
			int n = 5;
			double true_result = Math.PI+2;
			for(int k= 1; k <=2; k++){
				double h= (b-a)/n;
				double S= S(a,b,n);
				double T= T(a,b,n);
				xVals2.add(h);
				yVals2.add(S);
				xVals4.add(h);
				yVals4.add(T);
//				double error = Math.abs(T-true_result);
				
//				System.out.println( n +"\t" + h + "\t" + T + "\t" + error);
				n*=2;
			}


			
			double yMin = Double.POSITIVE_INFINITY;
			double yMax = Double.NEGATIVE_INFINITY;
			
			double xMin = Double.POSITIVE_INFINITY;
			double xMax = Double.NEGATIVE_INFINITY;
			double[] maxNmins = {xMin, xMax, yMin, yMax};
			
			addLineData(xVals2, yVals2, "S(h)", maxNmins);
//			addLineData(xVals4, yVals4, "T(h)", maxNmins);
			

			
			globalData.setFrame();
			globalData.setGrid();
			globalData.setAxisOnFrame();
			
			
			globalData.setXrange(maxNmins[0], maxNmins[1]);
			globalData.setYrange(maxNmins[2], maxNmins[3]);
			

		}
		public double f(double t) {
//			return 1.0+t*t;
			return 1.0+Math.sin(t);
		}
		
		public double S(double a, double b, int n){
			double h= (b-a)/n;
			return (4.0/3.0)*T(a,b,2*n) - (1.0/3.0)*T(a,b,n);
		}
		
		public double interpolate(Vec_d tt, Vec_d yy, double t){
			int n = tt.N();
			
			Matrix_d P = new Matrix_d(n,n);
			
			P.set(0, 0, yy.get(0));
			
			for(int i= 1; i<n; i++) {
				P.set(i, 0, yy.get(i));
				
				for(int k=0;k<=i;k++) {
					P.set(i, k, P.get(i, k-1) + (t-tt.get(i))/(tt.get(i) - tt.get(i-k)  )*(P.get(i, k-1) - P.get(i-1, k-1)));
				}
			}

			return P.get(n-1, n-1);
		}
		
		public double T(double a, double b, int n) {
			double h= (b-a)/n;
			double sum =0;
			double ti=a;
			
			
			sum+=(f(a)+f(b))/2.0;
			
			for(int i=1; i<n; i++){
				ti+=h;
				sum+=f(ti);
			}
			
			return h*sum;
			
		}
		private double[] addLineData(Vector<Double> xVals, Vector<Double> yVals, String name, double[] maxNmins) {
			
//			boolean isSpline = true;
			double[][] points = new double[2][xVals.size()];
			
			for(int i = 0; i< xVals.size(); i++) {
				double x = xVals.get(i);
				double y = yVals.get(i);
				
				if(x>maxNmins[1]) {
					maxNmins[1]= x;
				}
				if(x<maxNmins[0]){
					maxNmins[0]=x;
				}
				if(y>maxNmins[3]) {
					maxNmins[3]= y;
				}
				if(y<maxNmins[2]){
					maxNmins[2]=y;
				}
				
				points[0][i]=x;
				points[1][i]=y ;
			}
			globalData.lines(points, name);
			return maxNmins;
		}
		
		
	}
}
