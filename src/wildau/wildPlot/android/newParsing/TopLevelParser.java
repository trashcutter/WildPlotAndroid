package wildau.wildPlot.android.newParsing;

import wildau.wildPlot.android.rendering.interfaces.Function2D;
import wildau.wildPlot.android.rendering.interfaces.Function3D;

import java.util.HashMap;

public class TopLevelParser implements Function2D, Function3D{
    private HashMap<String, TopLevelParser> parserRegister;
    private HashMap<String, Double> varMap = new HashMap<String, Double>();
    private double x = 0.0, y = 0.0;
    private Expression expression = null;
    private boolean isValid = false;
    private String expressionString;


    public TopLevelParser(String expressionString, HashMap<String, TopLevelParser> parserRegister){
        this.parserRegister = parserRegister;
        this.expressionString = expressionString;
        expression =  new Expression(expressionString, this);
        isValid = expression.getExpressionType() != Expression.ExpressionType.INVALID;

    }

    public double getVarVal(String varName){
        return varMap.get(varName);
    }
    public void setVarVal(String varName, String varVal){
        varMap.put(varName, Double.parseDouble(varVal));
    }
    public void setVarVal(String varName, double varVal){
        varMap.put(varName, varVal);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double f(double x) {
        this.x = x;
        if(isValid)
            return expression.getValue();
        else
            throw new ExpressionFormatException("illegal Expression, cannot parse and return value");
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public double f(double x, double y) {
        this.x = x;
        this.y = y;
        if(isValid)
            return expression.getValue();
        else
            throw new ExpressionFormatException("illegal Expression, cannot parse and return value");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
		HashMap<String, TopLevelParser> newParserRegister = new HashMap<String, TopLevelParser>();
		for(String key : parserRegister.keySet()){
			newParserRegister.put(key, (TopLevelParser)parserRegister.get(key).clone());
		}
		
        return new TopLevelParser(this.expressionString, this.parserRegister);
    }

    public double getFuncVal(String funcName, double xVal){
        TopLevelParser funcParser = this.parserRegister.get(funcName);
        return funcParser.f(xVal);
    }

    public double getFuncVal(String funcName, double xVal, double yVal){
        TopLevelParser funcParser = this.parserRegister.get(funcName);
        return funcParser.f(xVal, yVal);
    }
}
