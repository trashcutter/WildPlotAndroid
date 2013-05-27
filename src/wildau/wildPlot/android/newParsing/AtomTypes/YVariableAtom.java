package wildau.wildPlot.android.newParsing.AtomTypes;

import wildau.wildPlot.android.newParsing.Atom;
import wildau.wildPlot.android.newParsing.ExpressionFormatException;
import wildau.wildPlot.android.newParsing.TopLevelParser;
import wildau.wildPlot.android.newParsing.TreeElement;

/**
 * @author Michael Goldbach
 *
 */
public class YVariableAtom implements TreeElement {
    private Atom.AtomType atomType = Atom.AtomType.VARIABLE;
    private TopLevelParser parser;

    public YVariableAtom(TopLevelParser parser){
        this.parser = parser;
    }

    public Atom.AtomType getAtomType() {
        return atomType;
    }

    @Override
    public double getValue() {

        if (atomType != Atom.AtomType.INVALID){

            return parser.getY();
        }
        else
            throw new ExpressionFormatException("Number is Invalid, cannot parse");
    }

    @Override
    public boolean isVariable() {
        return true;
    }
}
