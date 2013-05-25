package wildau.wildPlot.android.newParsing.AtomTypes;

import wildau.wildPlot.android.newParsing.ExpressionFormatException;
import wildau.wildPlot.android.newParsing.Factor;

/**
 * Created by mig on 25.05.13.
 */
public class NumberAtom implements IAtomType {
    private Factor.FactorType factorType = Factor.FactorType.NUMBER;
    private Double value;

    public NumberAtom(String factorString) {
        try {
            this.value = Double.parseDouble(factorString);
        } catch (NumberFormatException e) {
            factorType = Factor.FactorType.INVALID;
        }

    }

    @Override
    public double getValue() throws ExpressionFormatException{
        if (factorType != Factor.FactorType.INVALID)
            return value;
        else
            throw new ExpressionFormatException("Number is Invalid, cannot parse");
    }
}
