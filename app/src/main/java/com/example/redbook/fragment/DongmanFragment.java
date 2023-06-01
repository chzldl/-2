package com.example.redbook.fragment;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.redbook.R;
import com.example.redbook.adapter.DongmanAdapter;
import com.example.redbook.adapter.IDongmanListener;
import com.example.redbook.databinding.FragmentDongmanBinding;
import com.example.redbook.entity.Dongman;
import com.example.redbook.ui.DetailActivity;
import com.example.redbook.utils.Utils;
import com.example.redbook.widget.MyItemDecoration;

import cn.project.base.baseui.BaseFragment;

public class DongmanFragment extends BaseFragment implements IDongmanListener {
    DongmanAdapter dongmanAdapter;
    FragmentDongmanBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_dongman;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentDongmanBinding) dataBinding;
    }

    @Override
    protected void initView() {

        dongmanAdapter = new DongmanAdapter(getActivity(),this);
        binding.rlWenzang.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.rlWenzang.addItemDecoration(new MyItemDecoration(20));
        binding.rlWenzang.setAdapter(dongmanAdapter);
    }

    @Override
    protected void initListener() {
        dongmanAdapter.setNewData(Utils.getDongmanList());
        dongmanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDongmanClick(Dongman dongman) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("detail",dongman);
        startActivity(intent);
    }
}
