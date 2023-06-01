package com.example.redbook.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.R;

public class VideoAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private Context context;
    private IVideoListener listener;
    public VideoAdapter(Context context,IVideoListener listener) {
        super(R.layout.irem_video);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Integer integer) {
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(0)
                                .centerCrop()
                )
                .load(integer)
                .into((ImageView) baseViewHolder.getView(R.id.video));
        baseViewHolder.getView(R.id.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onVideoClick(integer);
                }
            }
        });
    }
}
