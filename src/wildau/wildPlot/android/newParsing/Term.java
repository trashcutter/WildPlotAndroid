package wildau.wildPlot.android.newParsing;



public class Term {
    private TopLevelParser parser;
    public static enum TermType { TERM_MUL_FACTOR, TERM_DIV_FACTOR, FACTOR, INVALID};
    private TermType termType = TermType.INVALID;
    private Factor factor = null;
    private Term term = null;
    
    
    public Term(String termString, TopLevelParser parser){
        this.parser = parser;
        boolean isReady = false;

        isReady = initAsTermMulFactor(termString);
        if(!isReady)
            isReady = initAsTermDivFactor(termString);
        if(!isReady)
            isReady = initAsFactor(termString);
        if(!isReady)
            this.termType = TermType.INVALID;
    }
    
    private boolean initAsTermMulFactor(String termString){
        for(int i = 0; i< termString.length(); i++){
            if(termString.charAt(i) == '*'){
                boolean isValidFirstPartTerm = false;
                String leftSubString = termString.substring(0, i);
                Term leftTerm = new Term(leftSubString, parser);
                isValidFirstPartTerm = leftTerm.getTermType() != TermType.INVALID;
                
                if(!isValidFirstPartTerm)
                    continue;
                
                boolean isValidSecondPartFactor = false;
                String rightSubString = termString.substring(i+1, termString.length());
                Factor rightFactor = new Factor(rightSubString, parser);
                isValidSecondPartFactor = rightFactor.getFactorType() != Factor.FactorType.INVALID;
                
                if(isValidSecondPartFactor){
                    this.termType = TermType.TERM_MUL_FACTOR;
                    this.term=leftTerm;
                    this.factor=rightFactor;
                    return true;
                }
                
            }
        }
        
        return false;
    }
    
    private boolean initAsTermDivFactor(String termString){
        for(int i = 0; i< termString.length(); i++){
            if(termString.charAt(i) == '/'){
                boolean isValidFirstPartTerm = false;
                String leftSubString = termString.substring(0, i);
                Term leftTerm = new Term(leftSubString, parser);
                isValidFirstPartTerm = leftTerm.getTermType() != TermType.INVALID;
                
                if(!isValidFirstPartTerm)
                    continue;
                
                boolean isValidSecondPartFactor = false;
                String rightSubString = termString.substring(i+1, termString.length());
                Factor rightFactor = new Factor(rightSubString, parser);
                isValidSecondPartFactor = rightFactor.getFactorType() != Factor.FactorType.INVALID;
                
                if(isValidSecondPartFactor){
                    this.termType = TermType.TERM_DIV_FACTOR;
                    this.term=leftTerm;
                    this.factor=rightFactor;
                    return true;
                }
                
            }
        }
        
        return false;
    }

    private boolean initAsFactor(String termString){
        Factor factor = new Factor(termString, parser);
        boolean isValidTerm = factor.getFactorType() != Factor.FactorType.INVALID;
        if(isValidTerm){
            this.termType = TermType.FACTOR;
            this.factor = factor;
            return true;
        }
        return false;
    }
    
    
    public TermType getTermType() {
        return termType;
    }
    public double getValue() throws ExpressionFormatException{
        switch (termType) {
            case TERM_MUL_FACTOR:
                return term.getValue() * factor.getValue();
            case TERM_DIV_FACTOR:
                return  term.getValue() / factor.getValue();
            case FACTOR:
                return factor.getValue();
            case INVALID:
            default:
                throw new ExpressionFormatException("could not parse Term");
        }
    }
    
    
}
