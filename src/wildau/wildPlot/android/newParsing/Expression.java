package wildau.wildPlot.android.newParsing;


public class Expression {
    public static enum ExpressionType { EXP_PLUS_TERM, EXP_MINUS_TERM, TERM, INVALID};
    private ExpressionType expressionType = ExpressionType.INVALID;
    private Expression expression = null;
    private Term term = null;
    private Factor xVariableFactor = null;
    private Factor yVariableFactor = null;
    
    
    public Expression(String expressionString){
        boolean isReady = false;
        
        isReady = initAsExpPlusTerm(expressionString);
        if(!isReady)
            isReady = initAsExpMinusTerm(expressionString);
        if(!isReady)
            isReady = initAsTerm(expressionString);
        if(!isReady)
            this.expressionType = ExpressionType.INVALID;
        
    }
    
    private boolean initAsExpPlusTerm(String expressionString){
        for(int i = 0; i< expressionString.length(); i++){
            if(expressionString.charAt(i) == '+'){
                boolean isValidFirstPartExpression = false;
                String leftSubString = expressionString.substring(0, i);
                Expression leftExpression = new Expression(leftSubString);
                isValidFirstPartExpression = leftExpression.getExpressionType() != ExpressionType.INVALID;
                
                if(!isValidFirstPartExpression)
                    continue;
                
                boolean isValidSecondPartTerm = false;
                String rightSubString = expressionString.substring(i+1, expressionString.length());
                Term rightTerm = new Term(rightSubString);
                isValidSecondPartTerm = rightTerm.getTermType() != Term.TermType.INVALID;
                
                if(isValidSecondPartTerm){
                    this.expressionType = ExpressionType.EXP_PLUS_TERM;
                    this.expression=leftExpression;
                    this.term=rightTerm;
                    return true;
                }
                
            }
        }
        return false;
    }
    private boolean initAsExpMinusTerm(String expressionString){
        for(int i = 0; i< expressionString.length(); i++){
            if(expressionString.charAt(i) == '-'){
                boolean isValidFirstPartExpression = false;
                String leftSubString = expressionString.substring(0, i);
                Expression leftExpression = new Expression(leftSubString);
                isValidFirstPartExpression = leftExpression.getExpressionType() != ExpressionType.INVALID;
                
                if(!isValidFirstPartExpression)
                    continue;
                
                boolean isValidSecondPartTerm = false;
                String rightSubString = expressionString.substring(i+1, expressionString.length());
                Term rightTerm = new Term(rightSubString);
                isValidSecondPartTerm = rightTerm.getTermType() != Term.TermType.INVALID;
                
                if(isValidSecondPartTerm){
                    this.expressionType = ExpressionType.EXP_MINUS_TERM;
                    this.expression=leftExpression;
                    this.term=rightTerm;
                    return true;
                }
                
            }
        }
        return false;
    }
    
    private boolean initAsTerm(String expressionString){
        Term term = new Term(expressionString);
        boolean isValidTerm = term.getTermType() != Term.TermType.INVALID;
        if(isValidTerm){
            this.expressionType = ExpressionType.TERM;
            this.term = term;
            return true;
        }
        return false;
    }
    
    
    public ExpressionType getExpressionType(){
        return expressionType;
    }
    
}
