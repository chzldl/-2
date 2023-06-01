package com.example.redbook.ui;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.databinding.ActivityUserinfoBinding;
import com.example.redbook.db.UserDBUtils;
import com.example.redbook.utils.ImageEngine;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.utils.permission.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.project.base.baseui.BaseActivity;
import cn.project.base.dialog.InputDialog;
import cn.project.base.utils.Constant;
import cn.project.base.utils.Event;
import cn.project.base.widget.SwitchButton;

/**
 * @authors: 唐辉
 * @date: 2022/9/15
 * @description:
 **/
public class UserInfoActivity extends BaseActivity {
    ActivityUserinfoBinding binding;

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityUserinfoBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        if (PermissionUtil.checkAndRequestPermissionsInActivity(this,
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            preLoadAlbums();
        }
        setUserinfo();
        binding.toolbar.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbar.tvTitle.setText("用户信息");


       binding.ivPersonDataAvatar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EasyPhotos
                       .createAlbum(UserInfoActivity.this, true,true, ImageEngine.getInstance())
                       .setCount(1)
                       .start(new SelectCallback() {
                           @Override
                           public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                               //获取file，进行对应操作
                               if (photos.size()>0){
                                   String path =  photos.get(0).path;
                                   EventBus.getDefault().postSticky(new Event(Constant.UPDATE_HEAD));
                                   Glide.with(UserInfoActivity.this).load(path).into(binding.ivPersonDataAvatar);
                                   App.getInstance().user.setHead_url(path);
                                   UserDBUtils.getInstance(getApplicationContext()).update(getApplicationContext(),App.getInstance().user);
                                   setUserinfo();
                               }
                           }
                           @Override
                           public void onCancel() {

                           }
                       });
           }
       });
    }


    /**
     * 预加载相册扫描，可以增加点速度，写不写都行
     * 该方法如果没有授权读取权限的话，是无效的，所以外部加不加权限控制都可以，加的话保证执行，不加也不影响程序正常使用。
     */
    private void preLoadAlbums() {
        EasyPhotos.preLoad(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionUtil.onPermissionResult(this, permissions, grantResults,
                new PermissionUtil.PermissionCallBack() {
                    @Override
                    public void onSuccess() {
                        preLoadAlbums();
                    }

                    @Override
                    public void onShouldShow() {
                    }

                    @Override
                    public void onFailed() {
                    }
                });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_userinfo;
    }

    private void setUserinfo(){
        if (App.getInstance().user !=null){
            if (!TextUtils.isEmpty(App.getInstance().user.getName())){
                binding.sbPersonDataName.setRightText(App.getInstance().user.getName());
            }

            if (!TextUtils.isEmpty(App.getInstance().user.getHead_url())){
                Glide.with(UserInfoActivity.this).load(App.getInstance().user.getHead_url()).into(binding.ivPersonDataAvatar);
            }
        }
    }
}
