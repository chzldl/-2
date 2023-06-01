package com.example.redbook.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.R;
import com.example.redbook.db.UserDBUtils;
import com.example.redbook.entity.Pinglun;


/**
 * 文件名：PinglunAdapter
 * 作  者： 唐辉
 * 描述：评论
 */
public class PinglunAdapter extends BaseQuickAdapter<Pinglun, BaseViewHolder> {
    private Context context;

    public PinglunAdapter(Context context) {
        super(R.layout.item_pinglun);
        this.context =context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Pinglun item) {
        helper.setText(R.id.tv_name,item.getUsername());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_time,item.getTime());
        String path = UserDBUtils.getInstance(context).getNameByUserId(item.getHead_url());
        if (!TextUtils.isEmpty(path)){
            Glide.with(context).load(path).into((ImageView) helper.getView(R.id.image_head));
        }
    }
}
