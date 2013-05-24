package wildau.wildPlot.android.newParsing;



public class Term {
    public static enum TermType { TERM_MUL_FACTOR, TERM_DIV_FACTOR, FACTOR, INVALID};
    private TermType termType = TermType.INVALID;
    private Factor factor = null;
    private Term term = null;
    
    
    public Term(String termString){
        
    }
    
    private boolean initAsTermMulFactor(String termString){
        for(int i = 0; i< termString.length(); i++){
            if(termString.charAt(i) == '*'){
                boolean isValidFirstPartTerm = false;
                String leftSubString = termString.substring(0, i);
                Term leftTerm = new Term(leftSubString);
                isValidFirstPartTerm = leftTerm.getTermType() != TermType.INVALID;
                
                if(!isValidFirstPartTerm)
                    continue;
                
                boolean isValidSecondPartFactor = false;
                String rightSubString = termString.substring(i+1, termString.length());
                Factor rightFactor = new Factor(rightSubString);
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
                Term leftTerm = new Term(leftSubString);
                isValidFirstPartTerm = leftTerm.getTermType() != TermType.INVALID;
                
                if(!isValidFirstPartTerm)
                    continue;
                
                boolean isValidSecondPartFactor = false;
                String rightSubString = termString.substring(i+1, termString.length());
                Factor rightFactor = new Factor(rightSubString);
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
    
    
    public TermType getTermType() {
        return termType;
    }
    
    
    
}
