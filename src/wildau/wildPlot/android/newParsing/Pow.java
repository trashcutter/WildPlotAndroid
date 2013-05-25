package wildau.wildPlot.android.newParsing;

/**
 * Created by mig on 25.05.13.
 */
public class Pow {
    private TopLevelParser parser;
    public static enum PowType {ATOM, ATOM_POW_FACTOR, ATOM_SQRT_FACTOR, INVALID};
    private PowType powType = PowType.INVALID;
    private Atom atom;
    private Factor factor;

    public Pow(String powString, TopLevelParser parser){
        this.parser = parser;

        boolean isReady;

        isReady = initAsAtom(powString);
        if(!isReady)
            isReady = initAsAtomPowFactor(powString);
        if(!isReady)
            isReady = initAsAtomSqrtFactor(powString);
        if(!isReady)
            this.powType = PowType.INVALID;
    }

    private boolean initAsAtom(String powString){
        Atom atom = new Atom(powString, parser);
        boolean isValidAtom = atom.getAtomType() != Atom.AtomType.INVALID;
        if(isValidAtom){
            this.powType = PowType.ATOM;
            this.atom = atom;
            return true;
        }
        return false;
    }
    private boolean initAsAtomPowFactor(String powString){
        int opPos = powString.indexOf("^");
        if(opPos > 1){
            String leftAtomString = powString.substring(0,opPos);
            String rightFactorString = powString.substring(opPos+1, powString.length());
            Atom leftAtom = new Atom(leftAtomString, parser);
            boolean isValidAtom = leftAtom.getAtomType() != Atom.AtomType.INVALID;
            if(isValidAtom){
                Factor rightFactor = new Factor(rightFactorString, parser);
                boolean isValidFactor = rightFactor.getFactorType() != Factor.FactorType.INVALID;
                if(isValidFactor){
                    this.powType= PowType.ATOM_POW_FACTOR;
                    this.atom = leftAtom;
                    this.factor = rightFactor;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean initAsAtomSqrtFactor(String powString){
        int opPos = powString.indexOf("**");
        if(opPos > 1){
            String leftAtomString = powString.substring(0,opPos);
            String rightFactorString = powString.substring(opPos+1, powString.length());
            Atom leftAtom = new Atom(leftAtomString, parser);
            boolean isValidAtom = leftAtom.getAtomType() != Atom.AtomType.INVALID;
            if(isValidAtom){
                Factor rightFactor = new Factor(rightFactorString, parser);
                boolean isValidFactor = rightFactor.getFactorType() != Factor.FactorType.INVALID;
                if(isValidFactor){
                    this.powType= PowType.ATOM_SQRT_FACTOR;
                    this.atom = leftAtom;
                    this.factor = rightFactor;
                    return true;
                }
            }
        }

        return false;
    }

    public double getValue(){
        switch (powType) {
            case ATOM:
                return atom.getValue();
            case ATOM_POW_FACTOR:
                return Math.pow(atom.getValue(), factor.getValue());
            case ATOM_SQRT_FACTOR:
                Math.sqrt(factor.getValue());
            case INVALID:
            default:
                throw new ExpressionFormatException("cannot parse Atom expression");
        }
    }

    public PowType getPowType() {
        return powType;
    }
}
