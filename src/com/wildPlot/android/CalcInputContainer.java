package com.wildplot.android;

import java.util.ArrayList;

import com.wildplot.android.parsing.FunctionParser;




public class CalcInputContainer {
	ArrayList<CalcInput> calcVector = new ArrayList<CalcInput>();
	FunctionParser parser = new FunctionParser();
	boolean isFreshCalculated= false;
	GlobalDataUnified globalData = null;
	
	int funcNr = 1;
	
	public void setGlobalData(GlobalDataUnified globalData) {
		this.globalData = globalData;
		this.globalData.setParser(parser);
	}
	
	public void add(CalcInput ci) {
		calcVector.add(ci);
	}
	public void deleteLast(){
		if(calcVector.size() > 0)
			calcVector.remove(calcVector.size()-1);
	}
	
	public void deleteAll(){
		calcVector.removeAll(calcVector);
	}
	
	public void calc() {
		parser.parse("f"+ funcNr + "(x)=" + this.toString());
		isFreshCalculated = true;
	}
	
	public void plot() {
	    System.err.println("CalcPlotTest: f"+ funcNr + "(x)=" + this.toString());

		globalData.plotWithNewParser("f"+ funcNr + "(x)=" + this.toString());

	}

    public void plot(String funcName) {
        System.err.println("CalcPlotTest:"+ funcName + "(x)=" + this.toString());

        globalData.plotWithNewParser(funcName + "(x)=" + this.toString());

    }

    public void splot() {
        System.err.println("CalcPlotTest: f"+ funcNr + "(x)=" + this.toString());

        globalData.splotWithNewParser("f"+ funcNr + "(x,y)=" + this.toString());

    }
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(CalcInput ci : calcVector) {
			sb.append(ci.getInput());
		}
		String returnStr = (isFreshCalculated)? ("=" + parser.getFunctionOutput("f"+funcNr, 1)):sb.toString();
		isFreshCalculated = false;
		return returnStr;
	}
	
}
