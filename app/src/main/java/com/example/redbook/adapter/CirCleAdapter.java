package com.example.redbook.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.db.CircleDbUtils;
import com.example.redbook.db.CommentDBUtils;
import com.example.redbook.db.DBUtils;
import com.example.redbook.db.PinglunDBUtils;
import com.example.redbook.db.UserDBUtils;
import com.example.redbook.entity.Luntan;
import com.example.redbook.widget.MyItemDecoration;

import java.util.List;


/**

 * @description:动态圈子
 **/
public class CirCleAdapter extends BaseQuickAdapter<Luntan, BaseViewHolder> {
    private Context context;
    private OnCircleListener listener;
    public CirCleAdapter(Context context, OnCircleListener listener) {
        super(R.layout.item_circle);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Luntan luntan) {
        baseViewHolder.setText(R.id.tv_name, UserDBUtils.getInstance(context).getNameByUserId(luntan.getUser_id()));
        baseViewHolder.setText(R.id.tv_content,luntan.getContent());
        baseViewHolder.setText(R.id.tv_time,luntan.getTime());
        baseViewHolder.setText(R.id.tv_p, PinglunDBUtils.getInstance(context).getToTalByLuntanId(luntan.getId()+"")+"评论");
        baseViewHolder.setText(R.id.tv_z, DBUtils.getInstance(context).getZanTotal(luntan.getId())+"赞");
        boolean pl = CommentDBUtils.getInstance(context).isComment(App.getInstance().user.getUser_id(),luntan.getId()+"");
        if (pl){
            Glide.with(context).load(R.mipmap.p_check).into((ImageView) baseViewHolder.getView(R.id.img_pl));
        }else {
            Glide.with(context).load(R.mipmap.p_uncheck).into((ImageView) baseViewHolder.getView(R.id.img_pl));
        }
        boolean zan = DBUtils.getInstance(context).isZan(App.getInstance().user.getUser_id(),luntan.getId()+"");
        if (zan){
            Glide.with(context).load(R.mipmap.zan_check).into((ImageView) baseViewHolder.getView(R.id.img_zan));
        }else {
            Glide.with(context).load(R.mipmap.zan_uncheck).into((ImageView) baseViewHolder.getView(R.id.img_zan));
        }
        String path = UserDBUtils.getInstance(context).getHeadUrlBy(luntan.getUser_id());
        if (!TextUtils.isEmpty(path)){
            Glide.with(context).load(path).into((ImageView) baseViewHolder.getView(R.id.img_head));
        }
        List<String> stringList = CircleDbUtils.getInstance(context).findDiaryListByCircleId(luntan.getPic());
        if (stringList.size() == 0){
            baseViewHolder.getView(R.id.rl_img).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.rl_img).setVisibility(View.VISIBLE);
            CirCleImagListAdapter cirCleImagListAdapter = new CirCleImagListAdapter(context);
            RecyclerView recyclerView =  baseViewHolder.getView(R.id.rl_img);
            recyclerView.addItemDecoration(new MyItemDecoration(2));
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            cirCleImagListAdapter.setNewData(stringList);
            recyclerView.setAdapter(cirCleImagListAdapter);
            cirCleImagListAdapter.notifyDataSetChanged();
        }

        baseViewHolder.getView(R.id.ll_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onCommentClick(luntan);
                }
            }
        });
        baseViewHolder.getView(R.id.ll_z).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onZanClick(luntan.getId());
                }
            }
        });
    }
}
