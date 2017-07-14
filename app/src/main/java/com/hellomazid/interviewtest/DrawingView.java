package com.hellomazid.interviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private float x, y;
    private static final int TOLERANCE = 2;
    private Bitmap bitmap;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4f);
    }

    private void startTouch(float x, float y) {
        this.x = x;
        this.y = y;
        path.moveTo(this.x, this.y);
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        if(dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(this.x, this.y, x, y);
            this.x = x;
            this.y = y;
        }
    }

    private void upTouch() {
        path.lineTo(this.x, this.y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bitmap = Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas mCanvas;
        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.WHITE); // set background white
        mCanvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
