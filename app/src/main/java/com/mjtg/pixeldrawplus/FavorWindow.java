package com.mjtg.pixeldrawplus;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.cardview.widget.CardView;

public class FavorWindow {
    Context ctx;
    int layout_id;

    public FavorWindow(Context ctx, @LayoutRes int layout_id) {
        this.ctx = ctx;
        this.layout_id = layout_id;
    }

    public View show() {
        PopupWindow window = new PopupWindow(ctx);
        View v = LayoutInflater.from(ctx).inflate(layout_id, null);
        CardView cardView = new CardView(ctx);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setUseCompatPadding(true);
        cardView.setCardElevation(6);
        cardView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        cardView.addView(v);
        window.setContentView(cardView);
        window.showAtLocation(MainActivity.instance.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return v;
    }
}
