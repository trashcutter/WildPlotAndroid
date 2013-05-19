package wildau.wildPlot.android.rendering;

import wildau.wildPlot.android.rendering.graphics.wrapper.Graphics;
import wildau.wildPlot.android.rendering.interfaces.Drawable;

import java.util.Vector;

/**
 * Created by Michael Goldbach on 19.05.13.
 */
public class DrawableContainer implements Drawable {
    Vector<Drawable> drawableVector = new Vector<Drawable>();
    private boolean isOnFrame = false;

    public DrawableContainer(boolean isOnFrame){
        this.isOnFrame = isOnFrame;
    }

    public void addDrawable(Drawable drawable){
        drawableVector.add(drawable);
    }

    @Override
    public void paint(Graphics g) {
        for(Drawable drawable: drawableVector){
            drawable.paint(g);
        }
    }

    @Override
    public boolean isOnFrame() {
        return isOnFrame;
    }

    @Override
    public void abortAndReset() {

    }

    @Override
    public boolean isClusterable() {
        return false;
    }
}
