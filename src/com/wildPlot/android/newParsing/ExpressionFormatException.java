package com.wildPlot.android.newParsing;

/**
 * Created by mig on 25.05.13.
 */
public class ExpressionFormatException extends IllegalArgumentException {
    public ExpressionFormatException() {
        super();
    }

    public ExpressionFormatException(String detailMessage) {
        super(detailMessage);
    }
}
