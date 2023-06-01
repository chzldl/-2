package com.example.redbook.fragment;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.redbook.R;
import com.example.redbook.adapter.IVideoListener;
import com.example.redbook.adapter.VideoAdapter;
import com.example.redbook.databinding.FragmentVideoBinding;
import com.example.redbook.ui.VideoActivity;
import com.example.redbook.utils.Utils;
import com.example.redbook.widget.MyItemDecoration;

import cn.project.base.baseui.BaseFragment;

public class VideoFragment extends BaseFragment implements IVideoListener {

    private VideoAdapter videoAdapter;
    private FragmentVideoBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentVideoBinding) dataBinding;
    }

    @Override
    protected void initView() {
        videoAdapter = new VideoAdapter(getActivity(),this);
        binding.rlVideo.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.rlVideo.addItemDecoration(new MyItemDecoration(20));
        binding.rlVideo.setAdapter(videoAdapter);
    }

    @Override
    protected void initListener() {

        videoAdapter.setNewData(Utils.getVideoList());
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVideoClick(Integer integer) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("detail",integer);
        startActivity(intent);
    }
}
