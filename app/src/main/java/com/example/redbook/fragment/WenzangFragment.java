package com.example.redbook.fragment;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.redbook.R;
import com.example.redbook.adapter.IWenzhangListener;
import com.example.redbook.adapter.WenZhangAdapter;
import com.example.redbook.databinding.FragmentWenzhangBinding;
import com.example.redbook.entity.Dongman;
import com.example.redbook.entity.Wenzhang;
import com.example.redbook.ui.DetailActivity;
import com.example.redbook.utils.Utils;
import com.example.redbook.widget.MyItemDecoration;

import cn.project.base.baseui.BaseFragment;

public class WenzangFragment extends BaseFragment implements IWenzhangListener {
    WenZhangAdapter wenZhangAdapter;

    FragmentWenzhangBinding binding;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_wenzhang;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentWenzhangBinding) dataBinding;
    }

    @Override
    protected void initView() {
        wenZhangAdapter = new WenZhangAdapter(getActivity(),this);
        binding.rlWenzang.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.rlWenzang.addItemDecoration(new MyItemDecoration(20));
        binding.rlWenzang.setAdapter(wenZhangAdapter);
    }

    @Override
    protected void initListener() {
        wenZhangAdapter.setNewData(Utils.getShijiebeiList());
        wenZhangAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWenzangClick(Wenzhang wenzhang) {
        Dongman dongman = new Dongman();
        dongman.setContent(wenzhang.getContent());
        dongman.setImg(dongman.getImg());
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("detail",dongman);
        startActivity(intent);
    }
}
