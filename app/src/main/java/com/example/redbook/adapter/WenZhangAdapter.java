package com.example.redbook.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.R;
import com.example.redbook.entity.Wenzhang;

public class WenZhangAdapter extends BaseQuickAdapter<Wenzhang, BaseViewHolder> {
    private Context context;
    private IWenzhangListener listener;
    public WenZhangAdapter(Context context,IWenzhangListener listener) {
        super(R.layout.item_wenzhang);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Wenzhang wenzhang) {
        baseViewHolder.setText(R.id.tv_title,wenzhang.getTitle());
        Glide.with(context).load(wenzhang.getPath()).into((ImageView) baseViewHolder.getView(R.id.img));
        baseViewHolder.getView(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onWenzangClick(wenzhang);
                }
            }
        });
    }
}
