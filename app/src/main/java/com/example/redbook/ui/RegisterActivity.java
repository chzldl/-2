package com.example.redbook.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.redbook.App;
import com.example.redbook.R;
import com.example.redbook.databinding.ActivityRegisterBinding;
import com.example.redbook.db.UserDBUtils;
import com.example.redbook.entity.User;

import cn.project.base.baseui.BaseActivity;


/**

 * @description:
 **/
public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityRegisterBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        makerEditText();
    }

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
                binding.tlPasswordAgain.setErrorEnabled(false);
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
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        binding.passwordAgain.addTextChangedListener(new TextWatcher() {
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
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        //点击注册、
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = binding.account.getText().toString();
                String password = binding.password.getText().toString();
                String passwordAgagin = binding.passwordAgain.getText().toString();
                if (TextUtils.isEmpty(account)){
                    binding.tlAccount.setError("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    binding.tlPassword.setError("密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(passwordAgagin)){
                    binding.tlPasswordAgain.setError("密码不能为空");
                    return;
                }
                if (!password.equals(passwordAgagin)){
                    binding.tlPassword.setError("密码不一致");
                    binding.tlPasswordAgain.setError("密码不一致");
                    return;
                }

                User user = new User();
                user.setName(account);
                user.setPassword(password);
                user.setHead_url("");
                user.setUser_id(System.currentTimeMillis()+"");
                int i = UserDBUtils.getInstance(App.getInstance()).insert(user);
                if (i==1){
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i==-1){
                    Toast.makeText(getApplicationContext(),"该账号已经注册",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


}
