package com.mjtg.pixeldrawplus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;

public class SideWindows {
    public static SideBar createToolWindow(Context ctx) {
        final SideBar sideBar = new SideBar(ctx);

        sideBar.addToggleButton(R.drawable.ic_pen, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawManager.changeListenerToPixelView(sideBar);
            }
        });
        sideBar.addToggleButton(R.drawable.ic_painter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawManager.changeListenerToPixelView(sideBar);
            }
        });
        sideBar.addToggleButton(R.drawable.ic_erase, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawManager.changeListenerToPixelView(sideBar);
            }
        });
        sideBar.addToggleButton(R.drawable.ic_tool, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawManager.changeListenerToPixelView(sideBar);
            }
        });
        sideBar.addButton(R.drawable.ic_arrow_backward, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return sideBar;
    }

    public static SideBar createColorWindow(Context ctx) {
        SideBar sideBar = new SideBar(ctx);

        sideBar.addColorList(new ArrayList<Integer>());
        return sideBar;
    }
    /*public static SideBar createMainWindow(Context ctx){
        SideBar sideBar=new SideBar(ctx);
    }*/
}
