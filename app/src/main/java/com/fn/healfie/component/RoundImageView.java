package com.fn.healfie.component;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.fn.healfie.R;

/**
 * 3.setXfermode
 *
 * @author Long
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private int DEFAULT_RADIUS = 10;
    private Context context;
    private AttributeSet attrs;
    private int defStyleAttr;
    private Paint paint = new Paint();
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap maskBitmap = null;
    private RectF mRoundRect = new RectF();

    private int x_radius;
    private int y_radius;
    private int oldWidth;
    private int oldHeight;


    public RoundImageView(Context context) {
        super(context);
        initObjectAttribute();
    }

    public void changeRadius(int value) {
        DEFAULT_RADIUS = value;
        initObjectAttribute();
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        x_radius = a.getDimensionPixelSize(R.styleable.RoundImageView_x_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        y_radius = a.getDimensionPixelSize(R.styleable.RoundImageView_y_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        a.recycle();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
        initObjectAttribute();

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        x_radius = a.getDimensionPixelSize(R.styleable.RoundImageView_x_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        y_radius = a.getDimensionPixelSize(R.styleable.RoundImageView_y_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        a.recycle();
    }

    private void initObjectAttribute() {
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

//		if(getScaleType() != ScaleType.CENTER_CROP)
//		{
//			setScaleType(ScaleType.CENTER_CROP);
//		}
    }


    private void create_mask_bitmap(int w, int h) {
        if (null != maskBitmap && !maskBitmap.isRecycled()) {
            maskBitmap.recycle();
            maskBitmap = null;
        }

        maskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(maskBitmap);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);
        mCanvas.drawRoundRect(new RectF(0, 0, w, h), x_radius, y_radius, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRoundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

        if (oldWidth != getMeasuredWidth() || oldHeight != getMeasuredHeight()) {
            oldWidth = getMeasuredWidth();
            oldHeight = getMeasuredHeight();

            if (oldWidth > 0 && oldHeight > 0) {
                create_mask_bitmap(oldWidth, oldHeight);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);

        Drawable mDrawable = getDrawable();
        if (mDrawable == null) {
            return;
        }

        if (mDrawable.getIntrinsicWidth() == 0 || mDrawable.getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (null == maskBitmap) {
            return;
        }

        @SuppressLint("WrongConstant") int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.save();
        Matrix mDrawMatrix = getImageMatrix();
        if (mDrawMatrix != null) {
            canvas.concat(mDrawMatrix);
        }
        mDrawable.draw(canvas);
        canvas.restore();

        paint.setXfermode(mXfermode);
        canvas.drawBitmap(maskBitmap, null, mRoundRect, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }
}
