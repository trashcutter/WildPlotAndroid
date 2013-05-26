package wildau.wildPlot.android.newParsing.AtomTypes;

import wildau.wildPlot.android.newParsing.ExpressionFormatException;
import wildau.wildPlot.android.newParsing.Atom;

/**
 * Created by mig on 25.05.13.
 */
public class NumberAtom implements IAtomType {
    private Atom.AtomType factorType = Atom.AtomType.NUMBER;
    private Double value;

    public NumberAtom(String factorString) {
        try {
            this.value = Double.parseDouble(factorString);
        } catch (NumberFormatException e) {
            factorType = Atom.AtomType.INVALID;
        }

    }

    @Override
    public double getValue() throws ExpressionFormatException{
        if (factorType != Atom.AtomType.INVALID)
            return value;
        else
            throw new ExpressionFormatException("Number is Invalid, cannot parse");
    }
}
