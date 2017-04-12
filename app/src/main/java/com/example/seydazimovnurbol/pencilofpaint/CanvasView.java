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
	private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
	
	public int getCurrentColor() {
		return currentColor;
	}
	
	public void setCurrentColor(int currentColor) {
		this.currentColor = currentColor;
		mPaint.setColor(currentColor);
	}
	
	private int currentColor = Color.BLACK;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
	
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

	    createCanvas(w, h);
	    
        // mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
	    canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
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

    public void clearCanvas() {
	    createCanvas(getWidth(), getHeight());
        invalidate();
    }
    
    private void upTouch() {
	    mPath.lineTo(mX, mY);
	    mCanvas.drawPath(mPath, mPaint);
	    mPath.reset();
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
	
	private void createCanvas(int w, int h) {
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		
		mPath = new Path();
		mCanvas = new Canvas(mBitmap);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(currentColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeWidth(4f);
	}
}
