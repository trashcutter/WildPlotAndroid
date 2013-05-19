package wildau.wildPlot.android;

import java.io.IOException;
import java.util.Vector;


import wildau.wildPlot.system.HarmonicOscillator;
import wildau.wildPlot.system.ODE;
import wildau.wildPlot.system.ODE.SOLVER;
import wildau.wildPlot.system.ODE_FUN;
import wildau.wildPlot.system.Oregonator;
import wildau.wildPlot.system.Vec_d;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class Ode extends Fragment {
    public static final String FRAGMENT_NAME = "ode_fragment";
    GlobalDataUnified globalData;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.ode, container, false);


        //setContentView(R.layout.ode);
        globalData = (GlobalDataUnified) getActivity().getApplication();


        Spinner mySpinner = (Spinner) rootView.findViewById(R.id.solverSpinner);
        mySpinner.setAdapter(new ArrayAdapter<ODE.SOLVER>(getActivity(), android.R.layout.simple_spinner_item, ODE.SOLVER.values()));

        mySpinner.setSelection(5);

        return rootView;
    }


    public void buttonClick(View view) {
        View rootView = getView();
        Spinner mySpinner = (Spinner) rootView.findViewById(R.id.solverSpinner);
        ODE.SOLVER solver = (ODE.SOLVER) mySpinner.getSelectedItem();
        final EditText textBox = (EditText) rootView.findViewById(R.id.stepText);
        int steps = Integer.parseInt(textBox.getText().toString());
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.odeProgressBar);

        //		initODE(solver, steps, progressBar);

        ODERunner odeRunner = new ODERunner(progressBar, solver, steps);

        Thread t = new Thread(odeRunner);
        t.start();

    }



    class ODERunner implements Runnable {

        ProgressBar progressBar;
        ODE.SOLVER solver; 
        int steps;

        public ODERunner(ProgressBar progressBar, SOLVER solver, int steps) {
            super();
            this.progressBar = progressBar;
            this.solver = solver;
            this.steps = steps;
        }

        @Override
        public void run() {

            initODE();
        }

        private void initODE()
        {
            //			System.out.print("Program ODE_SYSTEM started!\n");

            // to achieve english number-formatting (i.e. 10.25 instead of 10,25)
            // this is necessary for Gnuplot to work
            //		Thread.CurrentThread.CurrentCulture =
            //			   new System.Globalization.CultureInfo("en-US");

            // set the working directory, there is an equivalent in Java... I just don't know it!
            //		Environment.CurrentDirectory = @"C:\Users\rgillert\Desktop";
            //Console.WriteLine("Working directory: " + Environment.CurrentDirectory);

            // prepare the ode system ...

            // example of an harmonic Oscillator
            //				ODE_FUN f = new HarmonicOscillator(1.0, 20.0);
            //				ODE_FUN f = new Oregonator();
            //				double ta = 0.0, tb = 50/(1.0*0.02);
            //				Vec_d xa = new Vec_d(new double[]{(8.0*0.06)/(2.0*2e3), (8.0*0.06)/(8e5) , (Math.pow(8.0*0.06, 2))/(2e3*1.0*0.02)});

            ODE_FUN f = new HarmonicOscillator(1, 5);
            double ta = 0.0, tb = 20.0;
            Vec_d xa = new Vec_d(new double[]{1.0,0.0});


            //	Example of a Lotka-Volterra System
            String name = "Oregonator";
            //			ODE_FUN f = new LotkaVolterra(0.5, 0.25, 0.5, 0.1);name = "Lotka Volterra";
            //			ODE_FUN f = new HarmonicOscillator(2*Math.PI, 2000);name = "Harmonic Oscillator";
            //			ODE_FUN f = new Oregonator();name = "Oregonator";
            //			double ta = 0.0, tb = 10.0;
            //			Vec_d xa = new Vec_d(new double[] { 1.0, 0.0 });


            //			ODE_FUN f = new StiffExample();name = "Stiff Example";
            //			double ta = 0.0, tb = 0.01;

            //			ODE_FUN f = new Glyco();name = "Glyco";
            //			double ta = 0.0, tb = 0.5;
            //			
            //			Vec_d xa = new Vec_d(new double[] { 0.0, 0.0, 0.0, 2.1, 1.4, 0.1 });
            System.out.println("1 ^__^");
            //			ODE_FUN f=new TripleReaction();
            //			double ta=0.0, tb=0.000265;
            //			Vec_d xa = new Vec_d(new double[] { 2.0, 1.0, 1.0 });

            ODE ode = new ODE(f, ta, tb, xa);
            System.out.println("2 ^__^");
            // ... and solve it



            //progressBar.setIndeterminate(true);
            progressBar.setMax(100);


            ode.solve(solver, steps, progressBar);
            //			progressBar.setIndeterminate(false);

            progressBar.setProgress(100);



            //			ode.solve(1.0e-6, 10);			// solve wit adaptive stepsize
            //			ode.solve_implicit(20000);
            //			ode.solve_implicit(ODE.SOLVER.RK4,10000);
            System.out.println("3 ^__^");
            // do output to console
            //			ode.print(System.out);

            // do output to file
            //			FileStream fs = new FileStream("test.dat", FileMode.Create);
            //			TextWriter tw = new StreamWriter(fs);
            Vector<Double> xVals2 = new Vector<Double>();
            Vector<Double> yVals2 = new Vector<Double>();

            Vector<Double> xVals4 = new Vector<Double>();
            Vector<Double> yVals4 = new Vector<Double>();

            Vector<Double> xVals6 = new Vector<Double>();
            Vector<Double> yVals6 = new Vector<Double>();


            //				ode.print(bw);
            ode.printCol(xVals2,yVals2,2);
            ode.printCol(xVals4,yVals4,4);
            //ode.printCol(xVals6,yVals6,6);
            //				ode.printCol(bw8,8);
            //				ode.printCol(bw10,10);
            //				ode.printCol(bw12,12);



            //			double[][] array = new double[2][];
            //			
            //			array[0] = tr2.getPointArray()[1];
            //			array[1] = tr4.getPointArray()[1];

            System.out.println("Number of steps: " + ode.get_n());
            //			PlotControl pc = new PlotControl();

            double yMin = Double.POSITIVE_INFINITY;
            double yMax = Double.NEGATIVE_INFINITY;

            double xMin = Double.POSITIVE_INFINITY;
            double xMax = Double.NEGATIVE_INFINITY;
            double[] maxNmins = {xMin, xMax, yMin, yMax};

            addLineData(xVals2, yVals2, name, maxNmins);
            addLineData(xVals4, yVals4, name, maxNmins);
            //addLineData(xVals6, yVals6, name, maxNmins);

            int m=100;
            double[] t = new double[m+1];
            Vec_d[] y=ode.dense_output(t);

            double[] xValues = new double[t.length];
            double[] yValues = new double[t.length];
            double[] y2Values = new double[t.length];
            for (int i=0; i<t.length; i++)
            {
                xValues[i] = t[i];
                yValues[i] = y[i].get(0);
                y2Values[i] = y[i].get(1);
                //Console.WriteLine("{0,6}  {1,20:E12}  {2,20:E12}  {3,20:E12}", i, t[i], y[i][0], y[i][1]);
            }

            double[][] y1 = new double[2][];

            double[][] y2 = new double[2][];
            y1[0] = xValues;
            y1[1] = yValues;
            y2[0] = xValues;
            y2[1] = y2Values;

            globalData.lines(y1, "y1");
            globalData.lines(y2, "y2");


            //			globalData.tablePlot(points2, name, isSpline);
            //			globalData.tablePlot(points4, name, isSpline);
            //			globalData.tablePlot(points6, name, isSpline);

            globalData.setFrame();
            globalData.setGrid();
            globalData.setAxisOnFrame();


            globalData.setXrange(maxNmins[0], maxNmins[1]);
            globalData.setYrange(maxNmins[2], maxNmins[3]);

            //			pc.tablePlot("test_Column2.txt", isSpline);
            //			pc.tablePlot("test_Column4.txt", isSpline);
            //			pc.tablePlot("test_Column6.txt", isSpline);
            //			pc.tablePlot("test_Column8.txt", isSpline);
            //			pc.tablePlot("test_Column10.txt", isSpline);
            //			pc.tablePlot("test_Column12.txt", isSpline);

            //			pc.linesPoints("test_Column2.txt");
            //			pc.linesPoints("test_Column4.txt");
            //pc.tablePlot(array,"",  true);
            //			pc.linesPoints(array);
            //			pc.setAxisOnFrameBorder();
            //			pc.setXlim(-0, 3000);
            //			pc.setYlim(0, 0.008);
            //			pc.setGrid();
            //			pc.setPlotTitle(name);
            //			pc.setyName("Concentrations/mM");
            //			pc.setxName("Time / min");
            //			pc.start();


            //			System.out.println("\nProgram ODE_SYSTEM finished!");
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
