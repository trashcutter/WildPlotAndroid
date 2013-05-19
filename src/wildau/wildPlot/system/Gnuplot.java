package wildau.wildPlot.system;
//package system;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//
//// a very simple class to get gnuplot opened by Java and plot some data
//// this could be done much more clean, effective and beautiful
//
//public class Gnuplot
//{
//	// adopt this variable to the path, where gnuplot can be found
//	private static String PATH="C:/gnuplot/binary/";
//
//	private PrintWriter  pw=null;
//	private OutputStream os=null;
//
//	public Gnuplot()
//	{
//		try
//		{
//			Process p = Runtime.getRuntime().exec(PATH + "gnuplot.exe -persist");
//			os = p.getOutputStream();
//		}
//		catch(IOException e) 
//		{
//			e.printStackTrace();
//			System.exit(-1);
//		}
//	}
//
//	public void plot(ODE ode)
//	{
//		ode.stamp("test.dat");
//		pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));
//		pw.println("plot 'test.dat' using 1:2 with lines");
//		pw.println("pause mouse");
//		pw.close();
//	}
//
//	public void plot(ode_system.ODE ode)
//	{
//		ode.stamp("test.dat");
//		pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));
//		pw.println("plot 'test.dat' using 1:2 with lines, 'test.dat' using 1:3 with lines");
//		pw.println("pause mouse");
//		pw.close();
//	}
//}