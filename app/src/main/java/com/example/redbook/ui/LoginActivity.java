package com.example.redbook.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.databinding.ActivityLoginBinding;
import com.example.redbook.db.UserDBUtils;

import cn.project.base.baseui.BaseActivity;


/**

 * @description:登陆界面
 **/
public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityLoginBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        makerEditText();

        //注册
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class),101);
            }
        });
    }

    /**
     * 判断登录输入信息
     */
    private void makerEditText(){
        binding.account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
            }
        });

        //点击登录
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = binding.account.getText().toString();
                String password = binding.password.getText().toString();
                if (TextUtils.isEmpty(account)){
                    binding.tlAccount.setError("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    binding.tlPassword.setError("密码不能为空");
                    return;
                }
                //数据库查询用户信息
                int i = UserDBUtils.getInstance(App.getInstance()).Quer(password,account);
                if (i ==1){

                    App.getInstance().user = UserDBUtils.getInstance(App.getInstance()).selectByName(account);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    return;
                }
                if (i == -1){
                    Toast.makeText(getApplicationContext(),"登录失败，检查输入信息是否正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"账号或者密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }





}
