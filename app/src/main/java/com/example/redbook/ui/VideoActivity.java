package com.example.redbook.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;


import com.example.redbook.R;
import com.example.redbook.databinding.ActivityVideoBinding;

import cn.project.base.baseui.BaseActivity;

public class VideoActivity extends BaseActivity {

    private ActivityVideoBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityVideoBinding) dataBinding;
    }

    @Override
    protected void initView() {
        Integer path = getIntent().getIntExtra("detail",0);
        binding.video.setVideoURI(Uri.parse("android.resource://com.example.redbook/"+path));//路径
        /**
         * 控制视频的播放 主要通过MediaController控制视频的播放
         */
        //创建MediaController对象
        MediaController mediaController = new MediaController(this);
        binding.video.setMediaController(mediaController); //让videoView 和 MediaController相关联
        binding.video.setFocusable(true); //让VideoView获得焦点
        binding.video.start(); //开始播放视频
        //给videoView添加完成事件监听器，监听视频是否播放完毕
        binding.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoActivity.this, "该视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void initListener() {

    }
}
