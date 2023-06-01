package com.example.redbook.fragment;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.databinding.FragmentMeBinding;
import com.example.redbook.db.UserDBUtils;
import com.example.redbook.ui.UserInfoActivity;
import com.example.redbook.utils.CacheDataManager;
import com.example.redbook.utils.ImageEngine;
import com.example.redbook.widget.MenuOptionsView;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.utils.permission.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.project.base.baseui.BaseFragment;
import cn.project.base.dialog.InputDialog;
import cn.project.base.utils.Constant;
import cn.project.base.utils.Event;

public class MeFragment extends BaseFragment {

    private FragmentMeBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (FragmentMeBinding) dataBinding;
    }

    @Override
    protected void initView() {
        if (PermissionUtil.checkAndRequestPermissionsInActivity(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            preLoadAlbums();
        }
        if (!TextUtils.isEmpty(App.getInstance().user.getHead_url())){
            Glide.with(getActivity()).load(App.getInstance().user.getHead_url()).into(binding.imageHead);
        }
        if (!TextUtils.isEmpty(App.getInstance().user.getName())){
            binding.tvName.setText(App.getInstance().user.getName());
        }
    }

    @Override
    protected void initListener() {
        binding.icChangeHead.setMenuOptionsClickListener(new MenuOptionsView.MenuOptionsClickListener() {
            @Override
            public void layoutClick() {
                EasyPhotos
                        .createAlbum(getActivity(), true,true, ImageEngine.getInstance())
                        .setCount(1)
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                //获取file，进行对应操作
                                if (photos.size()>0){
                                    String path =  photos.get(0).path;
                                    EventBus.getDefault().postSticky(new Event(Constant.UPDATE_HEAD));
                                    Glide.with(getActivity()).load(path).into(binding.imageHead);
                                    App.getInstance().user.setHead_url(path);
                                    UserDBUtils.getInstance(getActivity()).update(getActivity(),App.getInstance().user);
                                }
                            }
                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });

        //用户信息
        binding.icUser.setMenuOptionsClickListener(new MenuOptionsView.MenuOptionsClickListener() {
            @Override
            public void layoutClick() {

                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });

        binding.icChangePassword.setMenuOptionsClickListener(new MenuOptionsView.MenuOptionsClickListener() {
            @Override
            public void layoutClick() {
                new InputDialog.Builder(getActivity())
                        // 标题可以不用填写
                        .setTitle(getString(R.string.personal_data_pwd_hint))
                        .setContent(binding.icChangePassword.getRightTitle())
                        .setHint(getString(R.string.personal_data_pwd_hint))
                        .setListener((dialog, content) -> {
                            if (!TextUtils.isEmpty(content)) {
                                App.getInstance().user.setPassword(content);
                                UserDBUtils.getInstance(getActivity()).update(getActivity(),App.getInstance().user);
                                showToast("修改成功");
                                reStartApp();
                            }
                        })
                        .show();
            }
        });
        binding.icWnt.setRightText(CacheDataManager.getTotalCacheSize(getActivity()));
        binding.icWnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //正常退出
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 预加载相册扫描，可以增加点速度，写不写都行
     * 该方法如果没有授权读取权限的话，是无效的，所以外部加不加权限控制都可以，加的话保证执行，不加也不影响程序正常使用。
     */
    private void preLoadAlbums() {
        EasyPhotos.preLoad(getActivity());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionUtil.onPermissionResult(getActivity(), permissions, grantResults,
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

    private void clean(){
        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity()).builder()
                .setTitle("提示")
                .setMsg("是否清除缓存")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CacheDataManager.clearAllCache(getActivity());
                        binding.icWnt.setRightText("0 M");
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        myAlertDialog.show();

    }

    @Override
    public void onEventMainThread(Event event) {
        super.onEventMainThread(event);
        if (Constant.UPDATE_HEAD.equals(event.eventMessage)){
            if (!TextUtils.isEmpty(App.getInstance().user.getHead_url())){
                Glide.with(getActivity()).load(App.getInstance().user.getHead_url()).into(binding.imageHead);
            }
        }
    }


    public void reStartApp() {
        Intent intent = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT", "reboot");
        startActivity(intent);
        getActivity().finish();
    }
}
