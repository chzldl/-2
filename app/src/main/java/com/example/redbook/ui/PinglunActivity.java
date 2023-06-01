package com.example.redbook.ui;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.adapter.PinglunAdapter;
import com.example.redbook.databinding.ActivityPinglunBinding;
import com.example.redbook.db.CommentDBUtils;
import com.example.redbook.db.PinglunDBUtils;
import com.example.redbook.entity.Comment;
import com.example.redbook.entity.Luntan;
import com.example.redbook.entity.Pinglun;
import com.example.redbook.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.project.base.baseui.BaseActivity;
import cn.project.base.utils.Constant;
import cn.project.base.utils.Event;

/**
 * 文件名：PinglunActivity
 * 作  者： 唐辉
 * 日  期：2/24/22 11:11 AM
 * 描述：TOOD
 */
public class PinglunActivity extends BaseActivity {
    ActivityPinglunBinding binding;

    Luntan luntan;

    PinglunAdapter pinglunAdapter;
    List<Pinglun> pinglunList;


    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityPinglunBinding) dataBinding;
    }

    @Override
    protected void initView() {
        binding.toolbar.tvTitle.setText("评论");
        luntan = (Luntan) getIntent().getSerializableExtra("detail");
        pinglunList = PinglunDBUtils.getInstance(getApplicationContext()).findAllByLuntanId(luntan.getId() + "");
        pinglunAdapter = new PinglunAdapter(PinglunActivity.this);
        pinglunAdapter.setNewData(pinglunList);
        binding.rlPinglun.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rlPinglun.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        binding.rlPinglun.setAdapter(pinglunAdapter);
        pinglunAdapter.notifyDataSetChanged();
        Comment comment = CommentDBUtils.getInstance(getApplicationContext()).getComment(App.getInstance().user.getUser_id(), luntan.getId() + "");
        if (comment != null) {
            binding.ryPinglun.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        binding.toolbar.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tvPinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etPinglun.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showToast("请输入评论内容");
                    return;
                }
                Comment comment = new Comment();
                comment.setComments("1");
                comment.setComments_id(luntan.getId() + "");
                comment.setUser_id(App.getInstance().user.getUser_id());
                CommentDBUtils.getInstance(getApplicationContext()).insert(comment);
                Pinglun pinglun = new Pinglun();
                pinglun.setLuntan_id(luntan.getId() + "");
                pinglun.setContent(content);
                pinglun.setTime(Utils.getTime());
                pinglun.setUsername(App.getInstance().user.getName());
                pinglun.setHead_url(App.getInstance().user.getUser_id());
                PinglunDBUtils.getInstance(getApplicationContext()).insert(pinglun);
                pinglunList.clear();
                pinglunList = PinglunDBUtils.getInstance(getApplicationContext()).findAllByLuntanId(luntan.getId() + "");
                pinglunAdapter.setNewData(pinglunList);
                pinglunAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pinglun;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new Event(Constant.CIRCLR_ADD_REFRESH));
    }
}
