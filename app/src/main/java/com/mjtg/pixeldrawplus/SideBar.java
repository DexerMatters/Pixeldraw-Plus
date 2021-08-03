package com.mjtg.pixeldrawplus;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class SideBar {
    Context ctx;
    LinearLayout layout;
    View colorViewport;
    SeekBar rBar;
    SeekBar gBar;
    SeekBar bBar;
    SeekBar aBar;
    TextView colorPreview;
    int tempColor;
    public SideBar(Context ctx){
        this.ctx=ctx;
        layout=new LinearLayout(ctx);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-2,-2));
        layout.setX(dp2px(10));
        layout.setY(dp2px(10));
    }
    public void addButton(@DrawableRes int res_id, View.OnClickListener clickListener){
        CardView button= (CardView) LayoutInflater.from(ctx).inflate(R.layout.sidebar_button,null);
        ImageView image=button.findViewById(R.id.icon);
        image.setImageResource(res_id);
        button.setOnClickListener(clickListener);
        layout.addView(button);
    }
    public void addToggleButton(final int res_id, final View.OnClickListener clickListener){
        final CardView button= (CardView) LayoutInflater.from(ctx).inflate(R.layout.sidebar_button,null);
        final ImageView image=button.findViewById(R.id.icon);

        button.setTag(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(boolean)button.getTag()){
                    disableAll(res_id,image);
                    button.setCardBackgroundColor(ctx.getResources().getColor(R.color.selected));
                    image.setImageResource(res_id);
                    button.setTag(true);
                    clickListener.onClick(button);
                    return;
                }
                disableAll(res_id,image);
            }
        });
        layout.addView(button);
        disableAll(res_id,image);
    }
    public void addColorList(ArrayList<Integer> colors){
        LinearLayout layout_= (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout.sidebar_list,null);
        ListView listView=layout_.findViewById(R.id.color_list);
        listView.setAdapter(new ColorListAdapter(ctx,colors));
        layout_.setLayoutParams(new ViewGroup.LayoutParams(-2,MainActivity.instance.dm.heightPixels-dp2px(10)));
        View hint=layout_.findViewById(R.id.color_hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v_=new FavorWindow(ctx,R.layout.window_color_selector).show();
                v_.findViewById(R.id.color_viewport).setBackground(DrawManager.spawnColorImage(DrawManager.color));
                colorPreview=v_.findViewById(R.id.color_preview);
                colorViewport=v_.findViewById(R.id.color_viewport);

                rBar = v_.findViewById(R.id.seekBar_red);
                gBar=v_.findViewById(R.id.seekBar_green);
                bBar=v_.findViewById(R.id.seekBar_blue);
                aBar=v_.findViewById(R.id.seekBar_opacity);
                colorViewport.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float x=event.getX(),y=event.getY();
                        try {
                            int color=DrawManager
                                    .spawnColorImage(tempColor)
                                    .getBitmap()
                                    .getPixel(
                                            Math.round(x/dp2px(230)*825)
                                            ,Math.round(y/dp2px(230)*825));
                            tempColor=color;
                            colorPreview.setBackgroundColor(color);
                            rBar.setProgress(Color.red(color));
                            gBar.setProgress(Color.green(color));
                            bBar.setProgress(Color.blue(color));
                        }catch (IllegalArgumentException e){
                            e.printStackTrace();
                        }

                        return true;
                    }
                });
                rBar.setOnSeekBarChangeListener(new OnColorBarChangeListener());
                gBar.setOnSeekBarChangeListener(new OnColorBarChangeListener());
                bBar.setOnSeekBarChangeListener(new OnColorBarChangeListener());
                aBar.setOnSeekBarChangeListener(new OnColorBarChangeListener());
            }
        });
        hint.setBackgroundColor(Color.BLACK);
        layout=layout_;
        DrawManager.colorList=listView;
        DrawManager.colorHint=hint;
    }
    public CardView[] getAllButtons(){
        CardView[] views=new CardView[layout.getChildCount()];
        for(int i=0;i<layout.getChildCount();i++){
            views[i]= (CardView) layout.getChildAt(i);
        }
        return views;
    }
    public void show(){
        ViewGroup v= (ViewGroup) MainActivity.instance.getWindow().getDecorView();
        if(v.indexOfChild(layout)!=-1)
            layout.setVisibility(View.VISIBLE);
        else
            v.addView(layout);
    }
    public void showAt(float x,float y){
        layout.setX(x);
        layout.setY(y);
        show();
    }
    public void close(){
        layout.setVisibility(View.GONE);
    }
    private int dp2px(float dipValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    private void disableAll(int res_id,ImageView view){
        for(int i=0;i<layout.getChildCount();i++){
            CardView every= (CardView) layout.getChildAt(i);
            every.setTag(false);
            view.setImageResource(res_id);
            every.setCardBackgroundColor(Color.WHITE);

        }
    }

    private class OnColorBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int a=aBar.getProgress();
            int r=rBar.getProgress();
            int g=gBar.getProgress();
            int b=bBar.getProgress();
            float[] hsv=new float[3];
            Color.colorToHSV(Color.rgb(r,g,b),hsv);
            hsv[1]=1.0f;
            int sati=Color.HSVToColor(hsv);
            colorPreview.setBackgroundColor(Color.argb(a,r,g,b));
            colorViewport.setBackground(DrawManager.spawnColorImage(sati));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
