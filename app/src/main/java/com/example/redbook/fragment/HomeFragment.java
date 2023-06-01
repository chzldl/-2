package com.example.redbook.fragment;

import android.widget.RadioGroup;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.redbook.R;
import com.example.redbook.adapter.MainAdapter;
import com.example.redbook.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import cn.project.base.baseui.BaseFragment;

public class HomeFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private FragmentHomeBinding binding;
    @Override
    protected int setLayoutId() {
        return  R.layout.fragment_home;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentHomeBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

        binding.radioGroup.setOnCheckedChangeListener(this);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WenzangFragment());
        fragments.add(new DongmanFragment());
        fragments.add(new GameFragment());
        fragments.add(new CHongwuFragment());
        fragments.add(new FoodFragment());
        MainAdapter adapter = new MainAdapter(getChildFragmentManager(),fragments);
        binding.mainPager.setOffscreenPageLimit(2);
        binding.mainPager.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.tab_home:
                binding.mainPager.setCurrentItem(0,true);
                break;
            case R.id.tab_analyse:
                binding.mainPager.setCurrentItem(1,true);
                break;
            case R.id.tab_circle:
                binding.mainPager.setCurrentItem(2,true);
                break;
            case R.id.tab_me:
                binding.mainPager.setCurrentItem(3,true);
                break;
            case R.id.tab_food:
                binding.mainPager.setCurrentItem(4,true);
                break;
        }
    }
}
