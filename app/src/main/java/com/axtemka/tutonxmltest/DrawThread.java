package com.axtemka.tutonxmltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private Paint backgroundPaint = new Paint();
    private Paint circlePaint = new Paint();


    double xCenter = 30;
    double yCenter = 30;
    double radius = 50;
    int down = 1, right = 1;

    double Vx = 10;
    double Vy = 10;

    {
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Paint.Style.FILL);

        circlePaint.setColor(Color.RED);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }
    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }
    public void requestStop() {
        running = false;
    }
    @Override
    public void run() {

        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    canvas.drawCircle((float)xCenter, (float)yCenter, (float)radius, circlePaint);
                    updatePositions(canvas);

                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    public void updatePositions(Canvas canvas){
        if (xCenter + radius >= canvas.getWidth()){
            right = -1;
        }
        if (yCenter + radius >= canvas.getHeight()){
            down = -1;
        }
        if (xCenter - radius <= 0){
            right = 1;
        }
        if (yCenter - radius <= 0){
            down = 1;
        }
        xCenter += Vx * right;
        yCenter += Vy * down;
    }
}