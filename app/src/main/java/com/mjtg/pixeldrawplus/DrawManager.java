package com.mjtg.pixeldrawplus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class DrawManager {
    public static int color = Color.BLACK;
    public static ArrayList<Integer> colors = new ArrayList<>();
    public static ListView colorList;
    public static View colorHint;

    public static class OnPenListener extends PixelView.OnPixelTouchListener {
        @Override
        public void run(int x, int y, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                MainActivity.instance.pixelView.setPixel(x, y, color);
            super.run(x, y, motionEvent);
        }
    }

    public static class OnPainterListener extends PixelView.OnPixelTouchListener {
        @Override
        public void run(int x, int y, MotionEvent motionEvent) {
            MainActivity.instance.pixelView.setPixel(x, y, color);
            super.run(x, y, motionEvent);
        }
    }

    public static class OnEraseListener extends PixelView.OnPixelTouchListener {
        @Override
        public void run(int x, int y, MotionEvent motionEvent) {
            MainActivity.instance.pixelView.setPixel(x, y, Color.TRANSPARENT);
            super.run(x, y, motionEvent);
        }
    }

    public static class OnPickListener extends PixelView.OnPixelTouchListener {
        @Override
        public void run(int x, int y, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) return;
            color = MainActivity.instance.pixelView.getPixel(x, y);
            colors.add(color);

            colorList.setAdapter(new ColorListAdapter(MainActivity.instance, colors));
            colorHint.setBackgroundColor(color);
            super.run(x, y, motionEvent);
        }
    }

    public static void changeListenerToPixelView(SideBar sideBar) {
        MainActivity instance = MainActivity.instance;
        PixelView view = instance.pixelView;
        CardView[] buttons = sideBar.getAllButtons();
        for (int i = 0; i < buttons.length; i++) {
            if ((boolean) buttons[i].getTag()) {
                if (i == 0)
                    view.setOnPixelTouchListener(new OnPenListener());
                if (i == 1)
                    view.setOnPixelTouchListener(new OnPainterListener());
                if (i == 2)
                    view.setOnPixelTouchListener(new OnEraseListener());
                if (i == 3)
                    view.setOnPixelTouchListener(new OnPickListener());
                return;
            }
        }
    }

    public static BitmapDrawable spawnColorImage(int color) {
        Bitmap map = Bitmap.createBitmap(824, 824, Bitmap.Config.ARGB_8888);
        Canvas cvs = new Canvas(map);
        Paint p = new Paint();
        p.setAntiAlias(true);
        cvs.drawColor(color);
        p.setShader(new LinearGradient(0, 0, 0, 824, new int[]{Color.TRANSPARENT, Color.WHITE}, null, Shader.TileMode.CLAMP));
        cvs.drawRect(0, 0, 824, 824, p);
        p.setShader(new LinearGradient(0, 0, 824, 0, new int[]{Color.TRANSPARENT, Color.BLACK}, null, Shader.TileMode.CLAMP));
        cvs.drawRect(0, 0, 824, 824, p);

        return new BitmapDrawable(map);
    }
}
