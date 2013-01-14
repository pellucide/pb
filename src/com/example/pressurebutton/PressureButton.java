package com.example.pressurebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.AbsSeekBar;
import android.widget.Button;
import android.widget.TextView;

public class PressureButton<mThumb> extends Button {

	private static final String TAG = "PressureButton";

	/**
     * A callback that notifies clients when the progress level has been
     * changed. This includes changes that were initiated by the user through a
     * touch gesture or arrow key/trackball as well as changes that were initiated
     * programmatically.
     */
    public interface OnPressureChangeListener {

        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param seekBar The SeekBar whose progress has changed
         * @param progress The current progress level. This will be in the range 0..max where max
         *        was set by {@link ProgressBar#setMax(int)}. (The default value for max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(PressureButton pressureButton, int pressure, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStartTrackingTouch(PressureButton pressureButton);

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStopTrackingTouch(PressureButton pressureButton);
    }
    
    private OnPressureChangeListener mOnSeekBarChangeListener;
	private float mPressure=0.0f;
    private LayerDrawable mThumb;
    private int mScaledTouchSlop;
    private float mTouchDownX;
    private float mTouchDownY;
    private boolean mIsDragging;
	
    public PressureButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated const
        //mThumb = context.getResources().getDrawable(R.drawable.red_button);
        mThumb = (LayerDrawable) context.getResources().getDrawable(R.drawable.push_button);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        
        //setBackgroundResource(R.drawable.red_button);
        GradientDrawable innerBevel1 = (GradientDrawable) mThumb.findDrawableByLayerId(R.id.innerBevel1);
        GradientDrawable innerBevel2 = (GradientDrawable) mThumb.findDrawableByLayerId(R.id.innerBevel2);
        Rect paddingRect = new Rect();
        innerBevel1.getPadding(paddingRect);
        innerBevel2.getPadding(paddingRect);
		Log.d(TAG, "innerBevel1 padding="+paddingRect);
		Log.d(TAG, "innerBevel2 padding="+paddingRect);
	}
    
    
    public PressureButton(Context context) {
        this(context, null);
    }

    public PressureButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }
    
    
    @Override
    public void setPressed(boolean pressed) {
    	//super.setPressed(pressed);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent() called");
        if (!isEnabled()) {
            return false;
        }
        boolean inBounds=false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	mTouchDownX = event.getX();
            	mTouchDownY = event.getY();
            	inBounds =  trackTouchEvent(event);
            	if (inBounds) {
            		setPressed(true);
            		if (mThumb != null) {
            			invalidate(); // This may be within the padding region
            		}
            		onStartTrackingTouch();
            	}
            	break;

            case MotionEvent.ACTION_MOVE:
                inBounds =  trackTouchEvent(event);
                if (inBounds) {
                	setPressed(true);
                	if (mThumb != null) {
                		invalidate(); // This may be within the padding region
                	}
                	onStartTrackingTouch();
                } else {
                    onStopTrackingTouch();
                    setPressed(false);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                inBounds =  trackTouchEvent(event);
                if (inBounds) {
                    onStopTrackingTouch();
                    setPressed(false);
                    invalidate();
                    mPressure = 0.0f;
                }
                break;
        }
        return inBounds;
    }

    private boolean trackTouchEvent(MotionEvent event) {
    	Rect boundingRect = new Rect();
    	this.getDrawingRect(boundingRect);
    			
    	if (boundingRect.contains((int)event.getX(), (int)event.getY())) {
    		mPressure = event.getPressure();
		    Log.d(TAG, "mPressure = "+mPressure);
    		return true;
    	}
    	else
    		return false;
    }
    

    /**
     * This is called when the user has started touching this widget.
     */
    void onStartTrackingTouch() {
        mIsDragging = true;
    }

    /**
     * This is called when the user either releases his touch or the touch is
     * canceled.
     */
    void onStopTrackingTouch() {
        mIsDragging = false;
    }
    
    Paint mPaint = new Paint();
    Rect rect = new Rect();
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        rect = getBackground().getBounds();
        mThumb.setBounds(rect);
		//Log.d(TAG, "mThumb bounds="+rect);
		//Log.d(TAG, "onDraw() called");
		//canvas.getClipBounds(rect);
		//Log.d(TAG, "canvas bounds="+rect+","+(int) (mPressure * 10000));
		//Log.d(TAG, "ColorFilter="+(int) (mPressure * 10000));
		//int alpha=(int) (mPressure * 3*255);
		//Log.d(TAG, "Alpha="+alpha);
		
		//mPaint.setAlpha(alpha);
		//canvas.drawCircle(mTouchDownX, mTouchDownY, 18.0f * mPressure * 10, mPaint);
		mThumb.setAlpha((int) (mPressure * 3 * 255) +20);
    	//mThumb.setColorFilter((0xFF000000 +(int) (mPressure * 10000)), PorterDuff.Mode.MULTIPLY);
        this.mThumb.draw(canvas);
    }

}
