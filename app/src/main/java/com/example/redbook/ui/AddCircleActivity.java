package com.example.redbook.ui;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;


import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.adapter.OnUploadListener;
import com.example.redbook.adapter.UploadImgAdapter;
import com.example.redbook.databinding.ActivityAddCircleBinding;
import com.example.redbook.db.CircleDbUtils;
import com.example.redbook.db.LuntanDBUtils;
import com.example.redbook.entity.CircleEntity;
import com.example.redbook.entity.Luntan;
import com.example.redbook.utils.ImageEngine;
import com.example.redbook.utils.Utils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.utils.permission.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.project.base.baseui.BaseActivity;
import cn.project.base.utils.Constant;
import cn.project.base.utils.Event;

/**
 * @authors: 唐辉
 * @date: 2022/9/13
 * @description:
 **/
public class AddCircleActivity extends BaseActivity implements OnUploadListener {
    ActivityAddCircleBinding binding;
    UploadImgAdapter uploadImgAdapter;
    List<String> stringList = new ArrayList<>();
    private ArrayAdapter adapter;
    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityAddCircleBinding) dataBinding;
    }

    @Override
    protected void initView() {
        uploadImgAdapter = new UploadImgAdapter(AddCircleActivity.this,stringList,this);
    }

    @Override
    protected void initListener() {
        binding.gvUpload.setAdapter(uploadImgAdapter);
        binding.toolbar.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbar.tvTitle.setText("添加动态");

        if (PermissionUtil.checkAndRequestPermissionsInActivity(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            preLoadAlbums();
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= binding.etContent.getText().toString();
                if (TextUtils.isEmpty(content)){
                    showToast("请输入内容");
                    return;
                }
                String circle_id = System.currentTimeMillis()+"";
                Luntan luntan = new Luntan();
                luntan.setUser_id(App.getInstance().user.getUser_id());
                luntan.setContent(content);
                luntan.setPic(circle_id);
                luntan.setTime(Utils.getTime());
                LuntanDBUtils.getInstance(getApplicationContext()).insert(luntan);
                if (stringList.size() >0){
                    for (int i = 0; i < stringList.size(); i++) {
                        CircleEntity circleEntity  = new CircleEntity();
                        circleEntity.setCircle_id(circle_id);
                        circleEntity.setUser_id(App.getInstance().user.getUser_id());
                        circleEntity.setPath(stringList.get(i));
                        CircleDbUtils.getInstance(getApplicationContext()).insert(circleEntity);
                    }
                }
                showToast("发布成功");
                EventBus.getDefault().postSticky(new Event(Constant.CIRCLR_ADD_REFRESH));
                finish();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_circle;
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
    public void onUploadClick() {
        EasyPhotos
                .createAlbum(AddCircleActivity.this, true,true, ImageEngine.getInstance())
                .setCount(9)
                .setFileProviderAuthority("com.example.redbook.fileprovider")
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        //获取file，进行对应操作
                        if (photos.size()>0){
                            stringList.clear();
                            for (int i = 0; i < photos.size(); i++) {
                                stringList.add(photos.get(i).path);
                            }
                            uploadImgAdapter.addNewData(stringList);
                            binding.gvUpload.setAdapter(uploadImgAdapter);

                        }
                    }
                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    public void onUploadItemClick(String path) {

    }
}
