package wildau.wildPlot.android.newParsing;

public class Factor {
    public static enum FactorType { VARIABLE, NUMBER, EXP_IN_BRACKETS, FUNCTION, INVALID};
    private FactorType factorType = FactorType.INVALID;
    
    public Factor(String factorString){
        
    }

    public FactorType getFactorType() {
        return factorType;
    }
    
    
    
}
