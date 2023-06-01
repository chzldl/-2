package com.example.redbook.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.R;
import com.example.redbook.entity.Dongman;

public class DongmanAdapter extends BaseQuickAdapter<Dongman, BaseViewHolder> {
    private Context context;
    private IDongmanListener listener;
    public DongmanAdapter( Context context,IDongmanListener listener) {
        super(R.layout.item_dongman);
        this.context = context;
        this.listener =listener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Dongman dongman) {
        Glide.with(context).load(dongman.getImg()).into((ImageView) baseViewHolder.getView(R.id.img));
        baseViewHolder.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDongmanClick(dongman);
                }
            }
        });
    }
}
