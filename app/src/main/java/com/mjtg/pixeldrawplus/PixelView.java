package com.mjtg.pixeldrawplus;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.mjtg.pixeldrawplus.R;

import static android.content.ContentValues.TAG;
import static com.mjtg.pixeldrawplus.MainActivity.instance;

public class PixelView extends View {
    private Bitmap bitmap;
    private float base=700;
    private Context ctx;
    private int pCountX;
    private int pCountY;
    private int pSize;
    private float lWidth=2;
    private FrameLayout.LayoutParams params;
    public static class OnPixelTouchListener{
        public void run(int x, int y, MotionEvent motionEvent){}
    }

    public PixelView(Context context, Bitmap initBitmap) {
        super(context);
        this.ctx=context;
        Log.d(TAG, "PixelView: "+bitmap.getWidth());
        this.bitmap=initBitmap;
    }

    public PixelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PixelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        pSize=getMeasuredWidth()/pCountX;
        Log.d(TAG, "onDraw: "+bitmap.getWidth());
        drawPixels(canvas);
        super.onDraw(canvas);

    }
    public void drawBitmap(Bitmap bitmap){
        int h=pCountY=bitmap.getHeight(),
                w=pCountX=bitmap.getWidth();
        FrameLayout.LayoutParams params=this.params=(FrameLayout.LayoutParams) getLayoutParams();
        params.width= (int) (base*(w/h));
        params.height=(int) (base*(h/w));
        setLayoutParams(params);
        this.bitmap=bitmap;
        Log.d(TAG, "PixelView: "+bitmap.getWidth());
        invalidate();
    }

    public void setOnPixelTouchListener(final OnPixelTouchListener listener){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=event.getX();
                float y=event.getY();
                for (int i = 0; i < pCountX; i++)
                    for (int i1 = 0; i1 < pCountY; i1++) {
                        if (x > i * pSize
                                && x < (i + 1) * pSize
                                && y > i1 * pSize
                                && y < (i1 + 1) * pSize) {
                            listener.run(i, i1,event);
                        }
                    }
                return true;
            }
        });
    }

    public void setPixel(int x,int y,int color){
        //Log.d(TAG, "setPixel: "+bitmap.getWidth());
        bitmap.setPixel(x,y,color);
        invalidate();
    }
    public int getPixel(int x,int y){
        return bitmap.getPixel(x,y);
    }
    private void drawPixels(Canvas cvs){
        for(int x=0;x<pCountX;x++){
            for(int y=0;y<pCountY;y++){
                Paint p=new Paint();
                p.setColor(bitmap.getPixel(x,y));
                p.setStyle(Paint.Style.FILL);
                cvs.drawRect(x*pSize+lWidth,y*pSize+lWidth,(x+1)*pSize-lWidth,(y+1)*pSize-lWidth,p);
            }
        }
    }

}
