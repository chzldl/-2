package com.example.redbook.fragment;

import android.content.Intent;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.adapter.CirCleAdapter;
import com.example.redbook.adapter.OnCircleListener;
import com.example.redbook.databinding.FragmentTabCircleBinding;
import com.example.redbook.db.DBUtils;
import com.example.redbook.db.LuntanDBUtils;
import com.example.redbook.entity.Luntan;
import com.example.redbook.entity.Zan;
import com.example.redbook.ui.AddCircleActivity;
import com.example.redbook.ui.PinglunActivity;
import com.example.redbook.widget.MyItemDecoration;

import java.util.List;

import cn.project.base.baseui.BaseFragment;
import cn.project.base.utils.Constant;
import cn.project.base.utils.Event;

/**
 * @authors: 唐辉
 * @date: 2022/9/6
 * @description:
 **/
public class TabCircleFragment extends BaseFragment implements OnCircleListener {
    FragmentTabCircleBinding binding;
    CirCleAdapter cirCleAdapter;
    @Override
    protected void initView() {
        cirCleAdapter = new CirCleAdapter(getActivity(),this);
        binding.rlCircle.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rlCircle.addItemDecoration(new MyItemDecoration(10));
    }

    @Override
    protected void initListener() {
        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCircleActivity.class));
            }
        });

        List<Luntan> luntanList = LuntanDBUtils.getInstance(getActivity()).findAll();
        cirCleAdapter.setNewData(luntanList);
        binding.rlCircle.setAdapter(cirCleAdapter);
        cirCleAdapter.notifyDataSetChanged();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tab_circle;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentTabCircleBinding) dataBinding;
    }

    @Override
    public void onEventMainThread(Event event) {
        super.onEventMainThread(event);
        //刷新界面
        if (Constant.CIRCLR_ADD_REFRESH.equals(event.eventMessage)){
            List<Luntan> luntanList = LuntanDBUtils.getInstance(getActivity()).findAll();
            cirCleAdapter.setNewData(luntanList);
            binding.rlCircle.setAdapter(cirCleAdapter);
            cirCleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCommentClick(Luntan luntan) {
        Intent intent = new Intent(getActivity(), PinglunActivity.class);
        intent.putExtra("detail", luntan);
        startActivity(intent);
    }

    @Override
    public void onZanClick(int id) {
        int  has =  DBUtils.getInstance(getActivity()).hasZan(App.getInstance().user.getUser_id(),id+"");
        if (has == -1){//没有评论
            Zan zan = new Zan();
            zan.setComments("1");
            zan.setComments_id(id+"");
            zan.setUser_id(App.getInstance().user.getUser_id());
            DBUtils.getInstance(getActivity()).insertZan(zan);
        }else {
            boolean z = DBUtils.getInstance(getActivity()).isZan(App.getInstance().user.getUser_id(),id+"");
            Zan zan = DBUtils.getInstance(getActivity()).getZan(App.getInstance().user.getUser_id(),id+"");
            if (z){
                zan.setComments("0");
            }else {
                zan.setComments("1");
            }
            DBUtils.getInstance(getActivity()).update(getActivity(),zan);
            List<Luntan> luntanList = LuntanDBUtils.getInstance(getActivity()).findAll();
            cirCleAdapter.setNewData(luntanList);
            cirCleAdapter.notifyDataSetChanged();
        }


    }
}
