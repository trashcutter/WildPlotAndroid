package com.wildPlot.android.rendering.graphics.wrapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;

public class BufferedImage {
    private Bitmap bitmap;
    
    public static final Bitmap.Config TYPE_INT_ARGB = Bitmap.Config.ARGB_8888;
    
    public BufferedImage(int width, int height, Bitmap.Config bitmapConfig){
        bitmap = Bitmap.createBitmap(width, height, bitmapConfig);
    }
    
    public Graphics2D createGraphics(){
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.TRANSPARENT, Mode.CLEAR);
        Paint paint = new Paint();
        paint.setStyle(Style.STROKE);
        
//        Paint transPainter = new Paint();
//        transPainter.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
//                   
//        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), transPainter);
        return new Graphics2D(canvas, paint);
    }
    
    public Graphics2D getGraphics(){
        return createGraphics();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    
    
}
