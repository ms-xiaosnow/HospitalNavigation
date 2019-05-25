package com.hospitalnavigation;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hospitalnavigation.Adapter.MyAdapter;
import com.hospitalnavigation.fregment.InformationFragment;
import com.hospitalnavigation.fregment.MineFragment;
import com.hospitalnavigation.fregment.NavigationFragment;
import com.hospitalnavigation.fregment.RegisterFragment;
import com.hospitalnavigation.login.LoginActivity;
import com.hospitalnavigation.utils.SPUtils;
import com.hospitalnavigation.utils.SystemBarTintManager;
import com.hospitalnavigation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static long DOUBLE_CLICK_TIME = 0L;

    //声明相关变量
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private List<Datas> mDatas;
    private String[] lvs = {"医院导航", "用户挂号", "就诊信息", "个人中心"};
    private int[] imgs = {R.mipmap.ic_navigation, R.mipmap.ic_register, R.mipmap.ic_info, R.mipmap.ic_mine};
    private MyAdapter arrayAdapter;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;
    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;
    private TextView tv_login, tv_logout, tv_setting;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        tv_login = findViewById(R.id.tv_login);
        tv_logout = findViewById(R.id.tv_logout);
        tv_setting = findViewById(R.id.tv_setting);
        login = (boolean) SPUtils.get(MainActivity.this, "loginState", false);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }


            }
        });
        if (login) {
            tv_login.setText("惟愿无事常相见");
        }

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(MainActivity.this, "暂未开发");
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(MainActivity.this);
                normalDialog.setMessage("是否退出登录?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPUtils.clear(MainActivity.this);
                                finish();
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });


        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            currIndex = 0;
            fragmentTags = new ArrayList<>(Arrays.asList("NavigationFragment", "RegisterFragment", "InfoFragment", "MineFragment"));
            showFragment();
        } else {
            initFromSavedInstantsState(savedInstanceState);
        }


        findViews(); //获取控件

        toolbar.setTitle("智慧医院导航系统");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色

        setSupportActionBar(toolbar);

        getSupportActionBar().

                setHomeButtonEnabled(true); //设置返回键可用

        getSupportActionBar().

                setDisplayHomeAsUpEnabled(true);

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new

                ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                }

        ;
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //设置菜单列表
        mDatas = new ArrayList<>();
        for (int i = 0; i < lvs.length; i++) {
            Datas datas = new Datas();
            datas.setName(lvs[i]);
            datas.setIcon(imgs[i]);
            mDatas.add(datas);
        }

        arrayAdapter = new MyAdapter(this, mDatas);
        lvLeftMenu.setAdapter(arrayAdapter);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbar.getTitle().equals("智慧医院导航系统")) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
                    fragment = instantFragment(0);
                    for (int i = 0; i < fragmentTags.size(); i++) {
                        Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
                        if (f != null && f.isAdded()) {
                            fragmentTransaction.hide(f);
                        }
                    }
                    if (fragment.isAdded()) {
                        fragmentTransaction.show(fragment);
                    } else {
                        fragmentTransaction.add(R.id.fl, fragment, fragmentTags.get(currIndex));
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                    fragmentManager.executePendingTransactions();
                }
            }
        });


        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        toolbar.setTitle("智慧医院导航系统");
                        currIndex = 0;
                        break;
                    case 1:
                        if (!login) {
                            ToastUtils.show(MainActivity.this, "请登录...");
                        } else {
                            toolbar.setTitle("用户挂号");
                            currIndex = 1;
                        }
                        break;
                    case 2:
                        if (!login) {
                            ToastUtils.show(MainActivity.this, "请登录...");
                        } else {
                            toolbar.setTitle("就诊信息");
                            currIndex = 2;
                        }

                        break;
                    case 3:
                        if (!login) {
                            ToastUtils.show(MainActivity.this, "请登录...");
                        } else {
                            toolbar.setTitle("个人中心");
                            currIndex = 3;
                        }
                        break;
                }
                showFragment();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = this.getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU && mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }

    private void initFromSavedInstantsState(Bundle savedInstanceState) {
        currIndex = savedInstanceState.getInt(CURR_INDEX);
        fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fl, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new NavigationFragment();
            case 1:
                return new RegisterFragment();
            case 2:
                return new InformationFragment();
            case 3:
                return new MineFragment();
            default:
                return null;
        }
    }
}
