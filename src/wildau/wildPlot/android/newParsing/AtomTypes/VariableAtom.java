package wildau.wildPlot.android.newParsing.AtomTypes;

import wildau.wildPlot.android.newParsing.Factor;
import wildau.wildPlot.android.newParsing.TopLevelParser;

/**
 * Created by mig on 25.05.13.
 */
public class VariableAtom implements IAtomType {
    private Factor.FactorType factorType = Factor.FactorType.NUMBER;
    private TopLevelParser parser;
    private String varName;

    public VariableAtom(String factorString, TopLevelParser parser){
        this.parser = parser;
        this.varName = factorString;
    }

    @Override
    public double getValue() {
        return parser.getVarVal(varName);
    }
}
