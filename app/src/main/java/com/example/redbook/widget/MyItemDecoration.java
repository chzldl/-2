package com.example.redbook.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 文件名：MyItemDecoration
 * 描述：RecycleView 分割线
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public MyItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }

}
