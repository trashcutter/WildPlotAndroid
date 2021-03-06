/****************************************************************************************
 * Copyright (c) 2014 Michael Goldbach <michael@wildplot.com>                           *
 *                                                                                      *
 * This program is free software; you can redistribute it and/or modify it under        *
 * the terms of the GNU General Public License as published by the Free Software        *
 * Foundation; either version 3 of the License, or (at your option) any later           *
 * version.                                                                             *
 *                                                                                      *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY      *
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.             *
 *                                                                                      *
 * You should have received a copy of the GNU General Public License along with         *
 * this program.  If not, see <http://www.gnu.org/licenses/>.                           *
 ****************************************************************************************/
package com.wildPlot.android.rendering;

import com.wildPlot.android.rendering.graphics.wrapper.GraphicsWrap;
import com.wildPlot.android.rendering.interfaces.Drawable;

import java.util.Vector;


public class DrawableContainer implements Drawable {
    Vector<Drawable> drawableVector = new Vector<Drawable>();
    private boolean isOnFrame = false;
    private boolean isOnAbort = false;
    private boolean isCritical = false;
    private Drawable currentDrawable = null;

    public DrawableContainer(boolean isOnFrame, boolean isCritical){
        this.isOnFrame = isOnFrame;
        this.isCritical = isCritical;
    }

    public void addDrawable(Drawable drawable){
        drawableVector.add(drawable);

    }

    @Override
    public void paint(GraphicsWrap g) {
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

    @Override
    public boolean isCritical() {
        return isCritical;
    }

    public int getSize(){
        return drawableVector.size();
    }
}
