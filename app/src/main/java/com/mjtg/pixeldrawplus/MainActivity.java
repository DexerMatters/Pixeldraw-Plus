package com.mjtg.pixeldrawplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import static com.mjtg.pixeldrawplus.SideWindows.*;

public class MainActivity extends AppCompatActivity {
    public PixelView pixelView;
    public DisplayMetrics dm;
    public static MainActivity instance;
    public static Bitmap colorBitmap;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        dm = getResources().getDisplayMetrics();

        //Pixel View init
        pixelView = findViewById(R.id.pixelView);
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inScaled = false;
        pixelView.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clock, ops).copy(Bitmap.Config.ARGB_8888, true));

        //Window init
        createToolWindow(this).show();
        createColorWindow(this).showAt(dm.widthPixels - dp2px(60), dp2px(8));

        //draw image

    }

    private float[] pPreX, pPreY;
    private float vPreX, vPreY, pPreDis, vPreScale;
    private int mode = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (event.getPointerCount() == 2) {
            float x1 = event.getX(0),
                    x2 = event.getX(1),
                    y1 = event.getY(0),
                    y2 = event.getY(1);
            if (action == MotionEvent.ACTION_MOVE) {
                if (mode == 0) {
                    pPreX = new float[]{x1, x2};
                    pPreY = new float[]{y1, y2};
                    pPreDis = distanceOf(x1, x2, y1, y2);
                    vPreX = pixelView.getX();
                    vPreY = pixelView.getY();
                    vPreScale = pixelView.getScaleX();
                    mode = 1;
                    return true;
                } else {
                    float dis = distanceOf(x1, x2, y1, y2);
                    pixelView.setScaleX(vPreScale + (dis - pPreDis) / pPreDis);
                    pixelView.setScaleY(vPreScale + (dis - pPreDis) / pPreDis);
                    pixelView.setX(vPreX + x1 - pPreX[0]);
                    pixelView.setY(vPreY + y1 - pPreY[0]);
                }
            }
            if (action == MotionEvent.ACTION_POINTER_UP)
                mode = 0;
        }
        return true;
    }

    private float distanceOf(float x1, float x2, float y1, float y2) {
        double x = Math.abs(x1 - x2);
        double y = Math.abs(y1 - y2);
        return (float) Math.sqrt(x * x + y * y);
    }

    private int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
