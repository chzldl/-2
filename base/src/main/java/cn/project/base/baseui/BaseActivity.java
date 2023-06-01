package cn.project.base.baseui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.project.base.utils.Event;

/**
 * @authors: 唐辉
 * @date: 2022/9/5
 * @description:
 **/
public abstract class BaseActivity extends AppCompatActivity {
    protected ViewDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this,setLayoutId());
        UltimateBarX.statusBar(this)
                .fitWindow(false)
                .color(Color.TRANSPARENT)
                .light(false)
                .apply();
        setDataBinding(binding);
        initView();
        initListener();
    }
    protected abstract int setLayoutId();
    protected abstract void setDataBinding(ViewDataBinding dataBinding);
    protected abstract void initView();
    protected abstract void initListener();
    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event event){
        //你的逻辑
    }
}
