package com.example.redbook.ui;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.example.redbook.R;
import com.example.redbook.databinding.ActivityDetailBinding;
import com.example.redbook.entity.Dongman;

import cn.project.base.baseui.BaseActivity;

public class DetailActivity extends BaseActivity {

    private ActivityDetailBinding binding;
    Dongman dongman;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityDetailBinding) dataBinding;
    }

    @Override
    protected void initView() {
        binding.toolbar.tvTitle.setText("详情");
        binding.toolbar.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dongman = (Dongman) getIntent().getSerializableExtra("detail");
        binding.tvContent.setText(dongman.getContent());
        Glide.with(this).load(dongman.getImg()).into(binding.img);
    }

    @Override
    protected void initListener() {

    }
}
