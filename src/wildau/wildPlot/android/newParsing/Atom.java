package wildau.wildPlot.android.newParsing;

import wildau.wildPlot.android.newParsing.AtomTypes.IAtomType;

/**
 * Created by mig on 26.05.13.
 */
public class Atom {
    private TopLevelParser parser;
    public static enum AtomType {VARIABLE, NUMBER, EXP_IN_BRACKETS, FUNCTION_X, FUNCTION_X_Y, INVALID};
    private AtomType atomType = AtomType.INVALID;
    private IAtomType atomObject;
    private Expression expression;

    public Atom(String atomString, TopLevelParser parser){
        this.parser = parser;
    }

    public AtomType getAtomType() {
        return atomType;
    }

    public double getValue() throws ExpressionFormatException{
        //TODO: add Functions
        switch (atomType) {
            case VARIABLE:
                return atomObject.getValue();
            case NUMBER:
                return atomObject.getValue();
            case EXP_IN_BRACKETS:
                return expression.getValue();
//            case FUNCTION_X:
//                break;
//            case FUNCTION_X_Y:
//                break;
            case INVALID:
            default:
                throw new ExpressionFormatException("cannot parse Atom object");
        }
    }
}
