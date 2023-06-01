package cn.project.base.baseui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
public abstract class BaseFragment extends Fragment {
    protected ViewDataBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UltimateBarX.statusBar(this)
                .fitWindow(false)
                .color(Color.TRANSPARENT)
                .light(false)
                .apply();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,setLayoutId(),container,false);
        setDataBinding(binding);
        initView();
        initListener();
        return binding.getRoot();
    }
    @Subscribe( sticky = true,threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event event){
        //你的逻辑
    }

    protected abstract int setLayoutId();
    /**
     * 用于传递DataBinding给fragment,要注意不能在setDataBinding之前调用dataBinding修改ui不然会空指针，比如构造方法或onCreate
     * @param dataBinding
     */
    protected abstract void setDataBinding(ViewDataBinding dataBinding);
    protected abstract void initView();
    protected abstract void initListener();



    public void update(){}
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }


    public void back(Fragment fragment){
        FragmentTransaction fragmentTransaction = getParentFragment().getChildFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        //事件订阅通知，用于传递数据更新界面
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

}
