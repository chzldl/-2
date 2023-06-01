package com.example.redbook.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.R;


/**
 * @authors: 唐辉
 * @date: 2022/9/13
 * @description:
 **/
public class CirCleImagListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    public CirCleImagListAdapter(Context context) {
        super(R.layout.item_circle_img_list);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        Glide.with(context).load(s).into((ImageView) baseViewHolder.getView(R.id.img));
    }
}
