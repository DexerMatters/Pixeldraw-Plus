package com.mjtg.pixeldrawplus;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ColorListAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<Integer> colors;

    public ColorListAdapter(Context ctx, ArrayList<Integer> colors) {
        this.ctx = ctx;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = new View(ctx);
        view.setLayoutParams(new AbsListView.LayoutParams(dp2px(32), dp2px(32)));
        view.setBackgroundColor(colors.get(position));

        return view;
    }

    private int dp2px(float dipValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
