package com.example.search_child.AC_login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.search_child.Bean.UserLoginBean;
import com.example.search_child.Bean.UserSignBean;
import com.example.search_child.HttpUtil.HttpUtil_Login;
import com.example.search_child.Nav.Nav;
import com.example.search_child.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AC_Register extends Activity implements View.OnClickListener {

    private ImageView btn_exit;

    private Button  btn_signup;

    private EditText edit_account,edit_password,edit_password_true;

    private Spinner spinner_age;

    private RadioGroup rg_sex;

    private RadioButton btn_man,btn_woman;

    private  String age;

    private  String address="http://47.94.239.21/register/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_register);
        InitView();
        //退出按钮
        btn_exit.setOnClickListener(v ->{
            finish();
        });
        //获取列表的值
        spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                age= "其他";
            }
        });

        btn_signup.setOnClickListener(this);
    }

    private void InitView() {
        btn_exit=findViewById(R.id.btn_register_exit);
        btn_signup =findViewById(R.id.btn_register);
        edit_account=findViewById(R.id.reg_acc);
        edit_password =findViewById(R.id.reg_psw);
        edit_password_true =findViewById(R.id.reg_psw_true);
        spinner_age=findViewById(R.id.age_spinner);
        rg_sex=findViewById(R.id.Sex);
        btn_man=findViewById(R.id.btn_man);
        btn_woman=findViewById(R.id.btn_woman);
    }

    @Override
    public void onClick(View view) {
        //获取各个信息的值
        String acc =edit_account.getText().toString();
        String psw =edit_password.getText().toString();
        String psw_true =edit_password_true.getText().toString();
        String str_sex="N";
        if(btn_man.isChecked())
            str_sex="M";
        else if(btn_woman.isChecked())
            str_sex="W";

        if(acc.equals("")||psw.equals("")||psw_true.equals(""))
        {
            Toast.makeText(AC_Register.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else if(!psw.equals(psw_true))
        {
            Toast.makeText(AC_Register.this, "两次输入的密码不一样！！！", Toast.LENGTH_SHORT).show();
            edit_password.setText("");
            edit_password_true.setText("");
        }
        else{
            SignupwithOkhttp(address,acc,psw,str_sex,age);
        }
    }

    private void SignupwithOkhttp(String address, String acc, String psw, String str_sex, String age) {
        HttpUtil_Login.registerWithOkHttp(address, acc, psw, age, str_sex, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AC_Register.this,"请求失败，网络出小差了~~~",Toast.LENGTH_SHORT).show();
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
                        Gson gson =new Gson();
                        UserSignBean userbean =gson.fromJson(responseData, UserSignBean.class);

                        System.out.println(userbean.getCode());
                        System.out.println(userbean.getId());

                        if(userbean.getCode().equals("成功")){

                            Toast.makeText(AC_Register.this,"注册成功",Toast.LENGTH_SHORT).show();

                            Intent intent =new Intent(AC_Register.this, MainActivity.class);

                            startActivity(intent);

                            finish();
                        }
                        else
                        {
                                Toast.makeText(AC_Register.this,"用户名重复",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}