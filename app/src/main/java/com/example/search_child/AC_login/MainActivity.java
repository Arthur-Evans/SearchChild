package com.example.search_child.AC_login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.search_child.Bean.UserLoginBean;
import com.example.search_child.HttpUtil.HttpUtil_Login;
import com.example.search_child.Nav.Nav;
import com.example.search_child.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

public class MainActivity extends Activity  {

    private TextView mBtnLogin,mBtnSignup;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private  EditText input_account,input_psw;

    private CheckBox checkBox;

    private String loginAddress="http://47.94.239.21/login/";

    private String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 设置没有title
        setContentView(R.layout.activity_main);

        //自动登录
        SharedPreferences token = getSharedPreferences("soft",MODE_PRIVATE);
        SharedPreferences.Editor editor_login =token.edit();
        //初始化控件
        initView();
        //注册界面
        Register();

        // 判断文件中账号密码是否存在
        if(token.getString("username",username)!=null&&
        token.getString("password",password)!=null){
            String name =token.getString("username",username);
            String pwd =token.getString("password",password);
            if(name!=null&&pwd!=null)
            {
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
                loginWithOkHttp(loginAddress,name,pwd);
            }
            else
            {
                ChooseLogin(editor_login);
            }
        }else   //选择手动登录
        {
            mBtnLogin.setOnClickListener(v ->{
                ChooseLogin(editor_login);
            });
        }
    }


    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        mBtnSignup =findViewById(R.id.Signup);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = findViewById(R.id.input_layout_name);
        mPsw = findViewById(R.id.input_layout_psw);
        input_account=findViewById(R.id.input_email);
        input_psw =findViewById(R.id.input_psw);
        checkBox =findViewById(R.id.checkbox);
    }

    private void ChooseLogin(SharedPreferences.Editor editor) {
        String account,psw;
        account=input_account.getText().toString();
        psw =input_psw.getText().toString();

        if(!checkBox.isChecked()){
            Toast toast = Toast.makeText(MainActivity.this, "请勾选下方的" +
                    "我已阅读并同意《用户协议》与《隐私协议》", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(account.equals("")||psw.equals(""))
        {
            Toast toast = Toast.makeText(MainActivity.this, "账号和密码不能为空", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            // 计算出控件的高与宽
            mWidth = mBtnLogin.getMeasuredWidth();
            mHeight = mBtnLogin.getMeasuredHeight();
            // 隐藏输入框
            mName.setVisibility(View.INVISIBLE);
            mPsw.setVisibility(View.INVISIBLE);
            //运行动画

//            inputAnimator(mInputLayout, mWidth, mHeight);
            progress.setVisibility(View.VISIBLE);
            progressAnimator(progress);
            mInputLayout.setVisibility(View.INVISIBLE);
            loginWithOkHttp(loginAddress,account,psw,editor);
        }
    }



    private void Register() {
        mBtnSignup.setOnClickListener(v ->{
            Intent register =new Intent(MainActivity.this,AC_Register.class);
            startActivity(register);
        });
    }


    private void loginWithOkHttp(String address,String account,String password) {
        HttpUtil_Login.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                System.out.println(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"请求失败，网络出小差了~~~",Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                        mInputLayout.setVisibility(View.VISIBLE);
                        mName.setVisibility(View.VISIBLE);
                        mPsw.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.i("Result",responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code()==200){
                            System.out.println("请求成功");
                            Gson gson =new Gson();
                            UserLoginBean userbean =gson.fromJson(responseData,UserLoginBean.class);
                            System.out.println(userbean.getCode());
                            System.out.println(userbean.getId());
                            System.out.println(userbean.getName());
                            System.out.println(userbean.getStatusCode());
                            if(userbean.getCode().equals("成功")){
                                // 进行活动
                                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(MainActivity.this, Nav.class);
                                Bundle bundle =new Bundle();
                                bundle.putCharSequence("id", userbean.getId());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                 if(userbean.getCode().equals("密码错误"))
                                     Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                 else
                                     Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                                mInputLayout.setVisibility(View.VISIBLE);
                                mName.setVisibility(View.VISIBLE);
                                mPsw.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }



    private void loginWithOkHttp(String address,String account,String password,SharedPreferences.Editor editor) {
        HttpUtil_Login.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                System.out.println(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"请求失败，网络出小差了~~~",Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                        mInputLayout.setVisibility(View.VISIBLE);
                        mName.setVisibility(View.VISIBLE);
                        mPsw.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.i("Result",responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.code()==200){
                            System.out.println("请求成功");
                            Gson gson =new Gson();
                            UserLoginBean userbean =gson.fromJson(responseData,UserLoginBean.class);
                            System.out.println(userbean.getCode());
                            System.out.println(userbean.getId());
                            System.out.println(userbean.getName());
                            System.out.println(userbean.getStatusCode());
                            if(userbean.getCode().equals("成功")){
                                //保存账号和密码
                                editor.putString("username",account);
                                editor.putString("password",password);
                                editor.commit();
                                // 进行活动
                                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(MainActivity.this, Nav.class);
                                Bundle bundle =new Bundle();
                                bundle.putCharSequence("id", userbean.getId());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                if(userbean.getCode().equals("密码错误"))
                                    Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                                mInputLayout.setVisibility(View.VISIBLE);
                                mName.setVisibility(View.VISIBLE);
                                mPsw.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }
    /**
     * 输入框的动画效果
     *
     * @param view
     *            控件
     * @param w
     *            宽
     * @param h
     *            高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，
                 *
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(500);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    private static class JellyInterpolator implements TimeInterpolator {
        @Override
        public float getInterpolation(float v) {
            return 1;
        }
    }
}