package com.wildPlot.android.rendering.graphics.wrapper;

public class FontMetrics {
    private Graphics g;

    public FontMetrics(Graphics g) {
        super();
        this.g = g;
    }
    
    public int stringWidth(String text){
        return Math.round(g.getPaint().measureText(text));
        
    }

    public int getHeight() {
        return Math.round(g.getPaint().getTextSize());    
    }

}
