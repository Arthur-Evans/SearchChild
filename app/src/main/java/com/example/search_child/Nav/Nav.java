package com.example.search_child.Nav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.search_child.About.About;
import com.example.search_child.R;
import com.example.search_child.WriteNote.WriteNote;

import java.util.ArrayList;

public class Nav extends AppCompatActivity implements View.OnClickListener {

    public String User_id;

    private  ViewPager2 viewPager;

    private LinearLayout ll_playground,ll_face,ll_account;
    private ImageView  iv_playground,iv_face,iv_account,iv_Current;

    private ImageView imageView_write;

    private PopupMenu popupMenu;//定义菜单按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Intent intent =getIntent();
        Bundle bundle =intent.getExtras();
        User_id = bundle.getString("id");
        System.out.println(User_id);


        //初始化组件
        initPager();
        initTabView();

        //注册菜单
    }




    // Fragment 碎片化
    private void initTabView() {
        ll_playground =findViewById(R.id.id_tab_playground);
        ll_account =findViewById(R.id.id_tab_account);
        ll_face =findViewById(R.id.id_tab_face);

        iv_playground=findViewById(R.id.tab_iv_playground);
        iv_account =findViewById(R.id.tab_iv_account);
        iv_face =findViewById(R.id.tab_iv_face);

        ll_playground.setOnClickListener(this);
        ll_face.setOnClickListener(this);
        ll_account.setOnClickListener(this);

        iv_face.setSelected(true);
        iv_Current =iv_face;

    }

    private void initPager() {
        viewPager =findViewById(R.id.viewPager);
        ArrayList<Fragment> fragments =new ArrayList<>();
        fragments.add(0,nav_face.newInstance("寻找"));
        fragments.add(1,nav_playground.newInstance("广场"));
        fragments.add(2,nav_account.newInstance("账户"));
        viewPagerAdapter newAdapter =new viewPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter(newAdapter);



        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //滑动的动画效果
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            //滑动的改变选择
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        iv_Current.setSelected(false);
        switch (position){
            case R.id.id_tab_face:
                viewPager.setCurrentItem(0);
            case 0:
                iv_face.setSelected(true);
                iv_Current=iv_face;
                break;
            case R.id.id_tab_playground:
                viewPager.setCurrentItem(1);
            case 1:
                iv_playground.setSelected(true);
                iv_Current=iv_playground;
                break;
            case R.id.id_tab_account:
                viewPager.setCurrentItem(2);
            case 2:
                iv_account.setSelected(true);
                iv_Current=iv_account;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }

    public void MenuOnclick(View view) {
        popupMenu = new PopupMenu(this,view);
        getMenuInflater().inflate(R.menu.title_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.title_write:
                        Intent write =new Intent(Nav.this, WriteNote.class);
                        startActivity(write);
                        break;
                    case R.id.title_about:
                        Intent about =new Intent(Nav.this, About.class);
                        startActivity(about);
                        break;
                    case R.id.title_exit:
                        return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}