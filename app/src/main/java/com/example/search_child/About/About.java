package com.example.search_child.About;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.search_child.R;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView imageView =findViewById(R.id.btn_about_exit);
        imageView.setOnClickListener(v ->{
            finish();
        });
    }
}