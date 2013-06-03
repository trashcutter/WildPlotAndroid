/**
 * 
 */
package com.wildPlot.android.rendering;

import com.wildPlot.android.rendering.graphics.wrapper.*;
import com.wildPlot.android.rendering.interfaces.Drawable;


/**
 * The LinesPoints objects draw points from a data array and connect them with lines on. 
 * These LinesPoints are drawn onto a PlotSheet object
 */
public class Lines implements Drawable {
	
	private PlotSheet plotSheet;
	
	private double[][] pointList;
	
	private Color color;
	
	/**
	 * Constructor for points connected with lines without drawn points
	 * @param plotSheet the sheet the lines and points will be drawn onto
	 * @param pointList x- , y-positions of given points
	 * @param color point and line color
	 */
	public Lines(PlotSheet plotSheet, double[][] pointList, Color color) {
		this.plotSheet = plotSheet;
		this.pointList = pointList;
		this.color = color;
	}
	
	/* (non-Javadoc)
	 * @see rendering.Drawable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		Color oldColor = g.getColor();
		Rectangle field = g.getClipBounds();
		g.setColor(color);
		
		int[] coordStart = plotSheet.toGraphicPoint(pointList[0][0],pointList[1][0],field);
		int[] coordEnd = coordStart;
		
		for(int i = 0; i< pointList[0].length; i++) {
			coordEnd = coordStart;
			coordStart = plotSheet.toGraphicPoint(pointList[0][i],pointList[1][i],field);
			g.drawLine(coordStart[0], coordStart[1], coordEnd[0], coordEnd[1]);
			//drawPoint(pointList[0][i], pointList[1][i], canvas, paint, field);
		}
		g.setColor(oldColor);
	}
	
	/**
	 * Draw points as karo
	 * @param x x-value of a point
	 * @param y y-value of a point
	 * @param g graphic object where to draw
	 * @param field given Rect field
	 */
	public void drawPoint(double x, double y, Graphics g, Rectangle field) {
		int[] coordStart 	= plotSheet.toGraphicPoint(x, y,field);
		g.drawRect(coordStart[0]-3, coordStart[1]-3, coordStart[0]-3+6, coordStart[1]-3+6);
//		g.drawLine(coordStart[0]-3, coordStart[1]-3, coordStart[0]+3, coordStart[1]-3);
//		g.drawLine(coordStart[0]+3, coordStart[1]-3, coordStart[0]+3, coordStart[1]+3);
//		g.drawLine(coordStart[0]+3, coordStart[1]+3, coordStart[0]-3, coordStart[1]+3);
//		g.drawLine(coordStart[0]-3, coordStart[1]+3, coordStart[0]-3, coordStart[1]-3);
	}
	
	/*
	 * (non-Javadoc)
	 * @see rendering.Drawable#isOnFrame()
	 */
	public boolean isOnFrame() {
		return false;
	}

    @Override
    public void abortAndReset() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public boolean isClusterable() {
        return true;
    }
}
