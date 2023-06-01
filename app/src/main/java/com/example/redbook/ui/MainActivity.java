package com.example.redbook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.redbook.R;
import com.example.redbook.adapter.MainAdapter;
import com.example.redbook.databinding.ActivityMainBinding;
import com.example.redbook.fragment.HomeFragment;
import com.example.redbook.fragment.MeFragment;
import com.example.redbook.fragment.TabCircleFragment;
import com.example.redbook.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import cn.project.base.baseui.BaseActivity;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private long clickTime;
    private ActivityMainBinding binding;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding  = (ActivityMainBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        binding.radioGroup.setOnCheckedChangeListener(this);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new VideoFragment());
        fragments.add(new TabCircleFragment());
        fragments.add(new MeFragment());
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(),fragments);
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
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            try {
                //正常退出
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}