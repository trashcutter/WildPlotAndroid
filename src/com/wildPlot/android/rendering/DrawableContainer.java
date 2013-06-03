package com.wildPlot.android.rendering;

import com.wildPlot.android.rendering.graphics.wrapper.Graphics;
import com.wildPlot.android.rendering.interfaces.Drawable;

import java.util.Vector;

/**
 * Created by Michael Goldbach on 19.05.13.
 */
public class DrawableContainer implements Drawable {
    Vector<Drawable> drawableVector = new Vector<Drawable>();
    private boolean isOnFrame = false;
    private boolean isOnAbort = false;
    private Drawable currentDrawable = null;

    public DrawableContainer(boolean isOnFrame){
        this.isOnFrame = isOnFrame;
    }

    public void addDrawable(Drawable drawable){
        drawableVector.add(drawable);
    }

    @Override
    public void paint(Graphics g) {
        isOnAbort = false;
        currentDrawable = null;
        for(Drawable drawable: drawableVector){
            currentDrawable = drawable;
            if(isOnAbort)
                return;
            drawable.paint(g);
        }
    }

    @Override
    public boolean isOnFrame() {
        return isOnFrame;
    }

    @Override
    public void abortAndReset() {
        isOnAbort = true;
        if(currentDrawable != null)
            currentDrawable.abortAndReset();
    }

    @Override
    public boolean isClusterable() {
        return false;
    }
}
