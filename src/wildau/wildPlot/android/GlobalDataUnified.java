package wildau.wildPlot.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import wildau.wildPlot.android.control.FunctionParserWrapper;
import wildau.wildPlot.android.densityFunctions.ASH;
import wildau.wildPlot.android.densityFunctions.Density2D;
import wildau.wildPlot.android.densityFunctions.KDE;
import wildau.wildPlot.android.kernelFunctions.*;
import wildau.wildPlot.android.newParsing.TopLevelParser;
import wildau.wildPlot.android.parsing.FunctionParser;
import wildau.wildPlot.android.parsing.SplineInterpolation;
import wildau.wildPlot.android.regressionFunctions.LinearRegression;
import wildau.wildPlot.android.rendering.*;
import wildau.wildPlot.android.rendering.graphics.wrapper.Color;
import wildau.wildPlot.android.rendering.interfaces.*;



import android.app.Application;

public class GlobalDataUnified extends Application {
    public enum Kernel { Gaussian, Uniform, Cauchy, Cosine, Epanechnikov, Picard, Quartic, Triangular, Tricube, Triweight  };

    private Kernel kernel = Kernel.Gaussian;


    private boolean hasLinearRegression = false;
    private double lambda;
    private int m;

    //Assignment relevant Variables
    private double originX = 0.0;
    private double originY = 0.0;
    private double widthX = 1.0;
    private double widthY = 1.0;
    private int mX = 4;
    private int mY = 4;
	private boolean isLogX = false;
	
	private boolean isLogY = false;
	
	private boolean hasGrid = false;
	
	private int yTicPixelDistance = 75;
	private int xTicPixelDistance = 75;
	
	private int yMinorTicPixelDistance = 20;
	private int xMinorTicPixelDistance = 20;
	
	private Vector<Double> xPointVector = new Vector<Double>();
	private Vector<Double> yPointVector = new Vector<Double>();
	private double[][] touchPoints;
	private boolean arrayHasChanged = true;
	
	private Color touchPointColor =  Color.RED;

	private float lineThickness = 2;
	
	private int touchPointType = 0; //0 = points, 1 = linespoints, 2 = spline
	
	private boolean updated 								= false;
	private Vector<Function2D> func2DVector 				= new Vector<Function2D>();
    private Vector<String> funcExpressionVector			    = new Vector<String>();
    private Vector<String> funcExpression3DVector			= new Vector<String>();
	private Vector<double[][]> linesPointVector 			= new Vector<double[][]>();
	private Vector<double[][]> linesVector 					= new Vector<double[][]>();
	private HashMap<Object, String> NameList 				= new HashMap<Object, String>();
	private HashMap<Object, Color> colorDef 				= new HashMap<Object, Color>();
	private Vector<double[][]> pointVector 					= new Vector<double[][]>();
	private HashMap<double[][], Boolean> isSpline			= new HashMap<double[][], Boolean>();
	
	private final Color[] gradientColors = {
			Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.YELLOW
		};
	private int colorCnt = 1;
	
	
	private double xstart = -10;
	private double xend = 10;
	private double ystart = -10;
	private double yend = 10;
	
	private Vector<Drawable> paintables = new Vector<Drawable>();
	
	private AdvancedPlotSheet plotSheet = new AdvancedPlotSheet(xstart, xend, ystart, yend);
	private boolean hasFrame = false; 
	
	
	private XAxis xaxis = new XAxis(plotSheet, 0, 50, 25);
	private YAxis yaxis = new YAxis(plotSheet, 0, 50, 25);
	private boolean isAxisOnFrame= false;
	
	private FunctionParser parser = new FunctionParser();
	private ArrayList<String> functionNames = new ArrayList<String>();
	private boolean plotCommandIssued = false;

	
	//this probably brings a lot of errors if new stuff is unregarded in this method
	public void reset(){
		xstart = -10;
		xend = 10;
		ystart = -10;
		yend = 10;
		isLogX = false;
		
		isLogY = false;
		
		hasGrid = false;
		
		yTicPixelDistance = 75;
		xTicPixelDistance = 75;
		
		yMinorTicPixelDistance = 20;
		xMinorTicPixelDistance = 20;
		
		xPointVector = new Vector<Double>();
		yPointVector = new Vector<Double>();

		arrayHasChanged = true;
		
		lineThickness = 0.0f;
		
		touchPointType = 0; //0 = points, 1 = linespoints, 2 = spline
		
		updated 				= true;
		func2DVector 			= new Vector<Function2D>();
		linesPointVector 		= new Vector<double[][]>();
		linesVector 			= new Vector<double[][]>();
		NameList 				= new HashMap<Object, String>();
		colorDef 				= new HashMap<Object, Color>();
		pointVector 			= new Vector<double[][]>();
		isSpline				= new HashMap<double[][], Boolean>();
        funcExpressionVector	= new Vector<String>();
        funcExpression3DVector	= new Vector<String>();
		colorCnt = 1;
		

		
		paintables = new Vector<Drawable>();
		
		plotSheet = new AdvancedPlotSheet(xstart, xend, ystart, yend);
		hasFrame = false; 
		
		
		xaxis = new XAxis(plotSheet, 0, 50, 25);
		yaxis = new YAxis(plotSheet, 0, 50, 25);
		isAxisOnFrame= false;
		
		//parser = new FunctionParser();
		functionNames = new ArrayList<String>();
		plotCommandIssued = false;
	}
	
	
	/**
	 * add a function from the parser to the plot
	 * @param functionName name of the function needed for function parser
	 * @return true if everything went OK, else false
	 */
	public boolean plot(String functionName) {
		FunctionParserWrapper func = new FunctionParserWrapper(parser, functionName);
		return plot(func, functionName+"(x)");
	}
	
	/**
	 * add a direct Function2D object to the plot
	 * @param func the function that should be plotted
	 * @param name name of function for legend
	 * @return true if everything went OK, else false
	 */
	public boolean plot(Function2D func, String name) {
		
		//only set the limits, if they are not set by user directly because these 
		//bounds given here are(or will be) calculated by the program parser


		//TODO: abrastern vorher (Dopplungen)
		func2DVector.add(func);
		NameList.put(func, name);
		this.colorDef.put(func, gradientColors[colorCnt++%(gradientColors.length)]);
		this.updated = true;
		return true;
	}
	
	public void tablePlot(double[][] points, String name, boolean isSpline) {
		this.pointVector.add(points);
		NameList.put(points, name);
		this.isSpline.put(points, isSpline);
		this.colorDef.put(points, gradientColors[colorCnt++%(gradientColors.length)]);
		this.updated = true;
	}
	
	/**
	 * draw points and connect them with lines using data from array
	 * @param points array with data points
	 * @param name for legend (not yet implemented)
	 */
	public void linesPoints(double[][] points, String name) {
		this.linesPointVector.add(points);
		NameList.put(points, name);
		this.colorDef.put(points, gradientColors[colorCnt++%(gradientColors.length)]);
		this.updated = true;
	}
	
	/**
	 * draw points and connect them with lines using data from array
	 * @param points array with data points
	 * @param name for legend (not yet implemented)
	 */
	public void lines(double[][] points, String name) {
		this.linesVector.add(points);
		NameList.put(points, name);
		this.colorDef.put(points, gradientColors[colorCnt++%(gradientColors.length)]);
		this.updated = true;
	}
	
	public FunctionParser getParser() {
		return parser;
	}
	public void setParser(FunctionParser parser) {
		this.parser = parser;
	}
	public ArrayList<String> getFunctionNames() {
		return functionNames;
	}
	public void setFunctionNames(ArrayList<String> functionNames) {
		this.functionNames = functionNames;
	}
	public void addFunctionName(String name) {
		this.functionNames.add(name);
	}
	
	
	public boolean isPlotCommandIssued() {
		return plotCommandIssued;
	}
	public void setPlotCommandIssued(boolean plotCommandIssued) {
		this.plotCommandIssued = plotCommandIssued;
	}
	
	public void setXrange(double xstart, double xend) {
		this.xstart = xstart;
		this.xend = xend;
		
		this.updated = true;
	}
	
	public void setYrange(double ystart, double yend) {
		this.ystart = ystart;
		this.yend = yend;
		
		this.updated = true;
	}
	public AdvancedPlotSheet getPlotSheet() {
		updatePoints();
		this.plotSheet = new AdvancedPlotSheet(xstart, xend, ystart, yend);
		xaxis = new XAxis(plotSheet, 0, xTicPixelDistance, xMinorTicPixelDistance);
		yaxis = new YAxis(plotSheet, 0, yTicPixelDistance, yMinorTicPixelDistance);
		
		if(this.isLogX)
			this.plotSheet.setLogX();
		if(this.isLogY){
			this.plotSheet.setLogY();
			this.yaxis.setLog();
		}
		if(hasFrame){
			this.plotSheet.setFrameThickness(50);
			if(this.isAxisOnFrame) {
				xaxis.setOnFrame();
				yaxis.setOnFrame();
			} else {
				xaxis.unsetOnFrame();
				yaxis.unsetOnFrame();
			}
		}
		
		if(this.hasGrid){
			YGrid yGrid;

			yGrid = new YGrid(plotSheet, 0,xTicPixelDistance);

			XGrid xGrid;

			xGrid = new XGrid(plotSheet, 0, yTicPixelDistance);

			plotSheet.addDrawable(yGrid);
			plotSheet.addDrawable(xGrid);
		}
		
		
		for(double[][] points:this.linesVector){
			Color thisColor = colorDef.get(points);
			Lines lines = new Lines(plotSheet, points, thisColor);
			plotSheet.addDrawable(lines);
		}
		for(double[][] points:this.linesPointVector){
		    Color thisColor = colorDef.get(points);
			LinesPoints linesPoints = new LinesPoints(plotSheet, points, thisColor);
			plotSheet.addDrawable(linesPoints);
		}
		for(Function2D func:func2DVector){
			FunctionDrawer functionDrawer = new FunctionDrawer(func, plotSheet, colorDef.get(func));
			functionDrawer.setSize(lineThickness);
			plotSheet.addDrawable(functionDrawer);
		}
		
		
		if(this.xPointVector.size() > 0){
			//draw hand drawn points
			switch(this.touchPointType) {
			case 0: PointDrawer2D pointDrawer = new PointDrawer2D(plotSheet, this.touchPoints, touchPointColor);
			plotSheet.addDrawable(pointDrawer); break;
			case 1: LinesPoints linesPoints = new LinesPoints(plotSheet, this.touchPoints, touchPointColor);
			plotSheet.addDrawable(linesPoints); break;
			case 2: 
				System.err.println("spline processing!");
				for(int i = 0; i< touchPoints[0].length; i++)
				    System.err.println("sp: " + touchPoints[0][i] + " : " + touchPoints[1][i]);
				SplineInterpolation interpol = new SplineInterpolation(this.touchPoints[0], this.touchPoints[1]);
				double leftLimit = this.touchPoints[0][0];
				double rightLimit = this.touchPoints[0][0];
				for(int i = 0; i< this.touchPoints[0].length;i++){
					if(leftLimit > this.touchPoints[0][i]){
						leftLimit = this.touchPoints[0][i];
					}
					if(rightLimit < this.touchPoints[0][i]){
						rightLimit = this.touchPoints[0][i];
					}
					
				}
				System.err.println("spline limits: left: " + leftLimit + "right: " + rightLimit);
				System.err.println("spline Test: " + interpol.f(0));
				FunctionDrawer functionDrawer = new FunctionDrawer(interpol, plotSheet, touchPointColor, leftLimit, rightLimit);
				functionDrawer.setSize(lineThickness);
				plotSheet.addDrawable(functionDrawer); break;
			}
				
			
		}
		
		for(double[][] points:pointVector){
			if(this.isSpline.get(points)) {
				SplineInterpolation interpol = new SplineInterpolation(points[0], points[1]);
				double leftLimit = points[0][0];
				double rightLimit = points[0][0];
				for(int i = 0; i< points[0].length;i++){
					if(leftLimit > points[0][i]){
						leftLimit = points[0][i];
					}
					if(rightLimit < points[0][i]){
						rightLimit = points[0][i];
					}
					
				}
				
				FunctionDrawer functionDrawer = new FunctionDrawer(interpol, plotSheet, colorDef.get(points), leftLimit, rightLimit);
				functionDrawer.setSize(lineThickness);
				plotSheet.addDrawable(functionDrawer);
				
			}else{
				PointDrawer2D pointDrawer = new PointDrawer2D(plotSheet, points, colorDef.get(points));
				plotSheet.addDrawable(pointDrawer);
				}
		}
        double[][] pointsOfAssignment = touchPoints;
        if (pointsOfAssignment != null && pointsOfAssignment[0].length > 0) {
            XAxisHistoGram histogramX = new XAxisHistoGram(plotSheet, pointsOfAssignment, this.originX,
                    this.widthX, Color.red);
            YAxisHistoGram histogramY = new YAxisHistoGram(plotSheet, pointsOfAssignment, this.originY,
                    this.widthY, Color.red);
            histogramX.setFilling(true);
            histogramY.setFilling(true);
            histogramX.setFillColor(new Color(0f, 1f, 0f, 0.5f));
            histogramY.setFillColor(new Color(0f, 1f, 0f, 0.5f));
            Function2D kernelFunc;
            switch (kernel) {
                case Uniform:
                    kernelFunc = new UniformKernel();
                    break;
                case Cauchy:
                    kernelFunc = new CauchyKernel();
                    break;
                case Cosine:
                    kernelFunc = new CosineKernel();
                    break;
                case Epanechnikov:
                    kernelFunc = new EpanechnikovKernel();
                    break;
                case Picard:
                    kernelFunc = new PicardKernel();
                    break;
                case Quartic:
                    kernelFunc = new QuarticKernel();
                    break;
                case Triangular:
                    kernelFunc = new TriangularKernel();
                    break;
                case Tricube:
                    kernelFunc = new TricubeKernel();
                    break;
                case Triweight:
                    kernelFunc = new TriweightKernel();
                    break;
                default:
                case Gaussian:
                    kernelFunc = new GaussianKernel();

            }
            Density2D density2D = new Density2D(pointsOfAssignment, this.widthX, this.widthY, kernelFunc);
            ReliefDrawer reliefDrawer = new ReliefDrawer(0.9, 110, density2D, plotSheet, true);
            reliefDrawer.setThreadCnt(2);
            PointDrawer2D pointDrawer = new PointDrawer2D(plotSheet, pointsOfAssignment, Color.red.darker());
            KDE kde = new KDE(pointsOfAssignment[0], this.widthX, kernelFunc);
            FunctionDrawer KDEfunctionX = new FunctionDrawer(kde, plotSheet, new Color(255, 0, 0));
            KDE kdeY = new KDE(pointsOfAssignment[1], this.widthY, kernelFunc);
            FunctionDrawer_y KDEfunctionY = new FunctionDrawer_y(kdeY, plotSheet, new Color(255, 0, 0));
            ASH ashX = new ASH(this.originX, mX, this.widthX, pointsOfAssignment[0]);
            FunctionDrawer ASHFunctionX = new FunctionDrawer(ashX, plotSheet, new Color(0, 0, 255));
            ASH ashY = new ASH(this.originX, mY, this.widthY, pointsOfAssignment[1]);
            FunctionDrawer_y ASHFunctionY = new FunctionDrawer_y(ashY, plotSheet, new Color(0, 0, 255));
            histogramX.setOnFrame();
            histogramY.setOnFrame();
            KDEfunctionX.setOnFrame();
            KDEfunctionY.setOnFrame();
            ASHFunctionX.setOnFrame();
            ASHFunctionY.setOnFrame();
            histogramX.setAutoscale(1000);
            histogramY.setAutoscale(1000);
            KDEfunctionX.setAutoscale(1000);
            KDEfunctionY.setAutoscale(1000);
            ASHFunctionX.setAutoscale(1000);
            ASHFunctionY.setAutoscale(1000);
            double maxX = histogramX.getMaxValue();
            double tmpMax = KDEfunctionX.getMaxValue(1000);
            if (maxX < tmpMax) {
                maxX = tmpMax;
            }
            tmpMax = ASHFunctionX.getMaxValue(1000);
            if (maxX < tmpMax) {
                maxX = tmpMax;
            }
            double maxY = histogramY.getMaxValue();
            tmpMax = KDEfunctionY.getMaxValue(1000);
            if (maxY < tmpMax) {
                maxY = tmpMax;
            }
            tmpMax = ASHFunctionY.getMaxValue(1000);
            if (maxY < tmpMax) {
                maxY = tmpMax;
            }
            double extraSpaceFactor = 0.1;
            histogramX.setExtraScaleFactor(1.0 / (maxX + extraSpaceFactor * maxX));
            histogramY.setExtraScaleFactor(1.0 / (maxY + extraSpaceFactor * maxY));
            KDEfunctionX.setExtraScaleFactor(1.0 / (maxX + extraSpaceFactor * maxX));
            KDEfunctionY.setExtraScaleFactor(1.0 / (maxY + extraSpaceFactor * maxY));
            ASHFunctionX.setExtraScaleFactor(1.0 / (maxX + extraSpaceFactor * maxX));
            ASHFunctionY.setExtraScaleFactor(1.0 / (maxY + extraSpaceFactor * maxY));
            plotSheet.addDrawable(reliefDrawer);
            plotSheet.addDrawable(reliefDrawer.getLegend());
            //			plotSheet.addDrawable(yGrid);
            //			plotSheet.addDrawable(xGrid);
            plotSheet.addDrawable(histogramX);
            plotSheet.addDrawable(histogramY);
            plotSheet.addDrawable(KDEfunctionX);
            plotSheet.addDrawable(KDEfunctionY);
            plotSheet.addDrawable(ASHFunctionX);
            plotSheet.addDrawable(ASHFunctionY);
            plotSheet.addDrawable(pointDrawer);
            if (this.hasLinearRegression) {
                LinearRegression linearRegression = new LinearRegression(pointsOfAssignment, m, lambda);
                FunctionDrawer linearRegressionFuncDraw = new FunctionDrawer(linearRegression, plotSheet, Color.cyan);
                linearRegressionFuncDraw.setSize(2.0f);
                plotSheet.addDrawable(linearRegressionFuncDraw);
            }
        }
		plotSheet.addDrawable(xaxis);
		plotSheet.addDrawable(yaxis);
		
		updated = false;
		return plotSheet;
	}

    private void updatePoints(){
        if(arrayHasChanged) {
            this.touchPoints = new double[2][xPointVector.size()];

            for(int i= 0; i< xPointVector.size();i++){
                this.touchPoints[0][i] = xPointVector.get(i);
                this.touchPoints[1][i] = yPointVector.get(i);
            }
            arrayHasChanged = false;
        }
    }

	/**
	 * activate grid lines on plot
	 */
	public void setGrid() {
		this.hasGrid = true;
		this.updated = true;
	}
	
	/**
	 * deactivate grid lines on plot (standard behavior)
	 */
	public void unsetGrid() {
		this.hasGrid = false;
		this.updated = true;
	}
	public void setFrame(){
		this.hasFrame = true;
		this.updated = true;
	}
	
	public void unsetFrame(){
		this.hasFrame = false;
		this.updated = true;
	}
	
	public void setAxisOnFrame() {
		this.isAxisOnFrame = true;
		this.updated = true;
	}
	
	public void unsetAxisOnFrame() {
		this.isAxisOnFrame = false;
		this.updated = true;
	}
	
	public void addPaintable(Drawable paint) {
		this.paintables.add(paint);
		this.updated = true;
	}
	public boolean isUpdated() {
		return updated;
	}

	public double getXstart() {
		return xstart;
	}

	public double getXend() {
		return xend;
	}

	public double getYstart() {
		return ystart;
	}

	public double getYend() {
		return yend;
	}

	public boolean isHasFrame() {
		return hasFrame;
	}

	public int getTouchPointType() {
		return touchPointType;
	}

	public void setTouchPointType(int buttonType) {
		this.touchPointType = buttonType;
		this.updated = true;
	}
	
	public void addPointFromScreen(double x, double y){
		this.xPointVector.add(x);
		this.yPointVector.add(y);
		this.arrayHasChanged = true;
		this.updated = true;
	}
	
	public void setLogX() {
		this.isLogX = true;
		this.updated = true;
	}

	public void setLogY() {
		this.isLogY = true;
		this.updated = true;
	}
	
	public void unsetLogX() {
		this.isLogX = false;
		this.updated = true;
	}

	public void unsetLogY() {
		this.isLogY = false;
		this.updated = true;
	}

	public boolean isLogX() {
		return isLogX;
	}

	public boolean isLogY() {
		return isLogY;
	}

	public boolean isHasGrid() {
		return hasGrid;
	}
    public void setOriginX(double originX) {
        this.originX = originX;
        this.updated = true;
    }

    public void setOriginY(double originY) {
        this.originY = originY;
        this.updated = true;
    }

    public void setWidthX(double widthX) {
        this.widthX = widthX;
        this.updated = true;
    }

    public void setWidthY(double widthY) {
        this.widthY = widthY;
        this.updated = true;
    }

    public void sethX(int hX) {
        this.mX = hX;
        this.updated = true;
    }

    public void sethY(int hY) {
        this.mY = hY;
        this.updated = true;
    }

    public double[][] getPointsOfAssignment() {
        updatePoints();
        return touchPoints;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
        this.updated = true;
    }

    public void setLinearRegression(int m, double lambda){
        this.hasLinearRegression = true;
        this.m = m;
        this.lambda =  lambda;
        this.updated = true;
    }
}
