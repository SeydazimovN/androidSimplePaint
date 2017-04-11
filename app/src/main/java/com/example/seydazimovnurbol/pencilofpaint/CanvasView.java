package com.example.seydazimovnurbol.pencilofpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.*;
import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by seydazimovnurbol on 4/7/17.
 */

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;

    public int currentColor = Color.BLACK;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(currentColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(4f);

        canvas.drawPath(mPath, paint);
    }

    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, x, y);
            mX = x;
            mY = y;
        }

    }

    public void  clearCanvas() {
        mPath.reset();
        invalidate();
    }
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startTouch(x, y);
            invalidate();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveTouch(x, y);
            invalidate();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            upTouch();
            invalidate();
        }

        return true;
    }
}
